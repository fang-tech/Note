# 1-DCC 动态配置中心的实现

## 1. 项目结构

- dynamic.config.center.config: 项目的propeties以及自动装配Bean对象
  - DynamicConfigCenterAutoConfig: 这个Bean对象用于在bean对象初始化(也就是Bean对象生命周期: 赋值的后一步)后执行特定的代码, 这里就是扫描并处理DCCValue注解
  - DynamicConfigCenterAutoProperties: DCC的properties配置文件, 里面定义了DCC有什么需要配置的内容
  - DynamicConfigCenterRegisterAutoProperties: 里面定义了Redis需要配置的内容, 并且给出默认值
  - DynamicConfigCenterRegisterAutoConfig: 根据上面的两个Properties中的内容装配Redisson, DynamicConfigCenterService, DynamicConfigCenterAdjustListener, DynamicConfigCenterRedisTopic这四个Bean对象
- domain: 领域层, 关注扫描DCCValue已经将topic中的值从Redis中读取并注入到程序中某个字段的业务
  - model.AttributeVO: Topic装载的值, 记录了字段名和需要注入的值
  - service.DynamicConfigCenterService: 提供了
    - 解析Bean对象中的DCCValue注解, 并尝试将这个值设置到Redis中并注入到某个field中(如果不是初始化的情况, 也就是Redis中已经存在这个值的时候, 就不用注入), 已经将所有用DCCValue注解标注的bean对象都用map存储起来
    - listener调用的服务, 将更新后的Redis中的值注入到程序对应字段中

## 2. 程序串行的执行过程

1. 在启动SpringBoot以后, 所有的Bean对象都会在初始化以后, 执行被我们重写了的`postProcessAfterInitialization(Object bean, String beanName)`, 截获这个对象以后, 执行DynamicConfigCenterService中的`processDCCAnnotations(Object bean)`, 下面是详细的执行过程
   1. 首先需要解代理(具体的原因在\[项目细节\]里面有), 因为这里的bean对象可能是代理后的对象
   2. 从解代理以后的class中扫描有没有字段被@DCCValue修饰
   3. 将DCCValue中的key如果不存在于Redis中, 说明这个字段是第一次被扫描, 这个时候将DCCValue中的默认值存到Redis中. 如果存在则取出来最新的值
   4. 将c步骤中获取到要注入的值注入到对应的field中
   5. 将这对key(这里的key是经过了system_attributeName拼接后的), 和对应的原始bean对象存到线程安全Map里面
2. 在测试程序中我们通过在AutoConfig里面装配的dynamicConfigCenterRedisTopic发布消息
3. AdjustListener订阅了这个消息, 在监听到消息以后, 执行` dynamicConfigCenterService.adjustAttributeValue(attributeVO)`将值注入到对应的Bean对象中
   1. 获取key, 从`processDCCAnnotations`存入的dccBeanGroup中读取出来需要的注入的Bean对象
   2. 更新Redis中的值, 并将这个value注入到对应的field中

## 3. 项目细节

### 类已经被代理了, 如果回退到原来的类?

要回答这个问题就需要回头看到代理是怎么实现的, 我们才能在实现中捕捉到这件事情是不是可行的

```java
public interface TargetSource extends TargetClassAware {

	@Override
	@Nullable
	Class<?> getTargetClass();

	boolean isStatic();

	@Nullable
	Object getTarget() throws Exception;

	void releaseTarget(Object target) throws Exception;

}
```

Spring在创建代理**JDK动态代理**的对象的过程中会将目标类的信息保存到AdviseSupport(继承自TargetSource), 通过这个类我们就能获取到原始的类和实例

而如果是使用CGLIB动态代理, 因为CGLIB代理是通过继承实现的, 只需要`getSuperClass()`就能获取到原先的类

### 获取代理类的原始class

通过`AopUtils.getTargetClass()`方法

```java
public static Class<?> getTargetClass(Object candidate) {
		Assert.notNull(candidate, "Candidate object must not be null");
		Class<?> result = null;
		if (candidate instanceof TargetClassAware) {
			result = ((TargetClassAware) candidate).getTargetClass();
		}
		if (result == null) {
			result = (isCglibProxy(candidate) ? candidate.getClass().getSuperclass() : candidate.getClass());
		}
		return result;
	}
```

所有使用JDK动态代理被代理的类都是继承了TargetSource的, TargetSourc继承TargetClassAware, 所以能通过检查是不是继承自TargetClassAwar来识别这个类是不是代理类

在确定类是代理类以后, 通过`TargetClassAware`中的`getTargetClass()`方法获取该代理类的原始类

如果这个result为空, 说明是通过CGLIB动态代理的或者压根就没被代理, 如果是CGLIB代理的返回父类, 反之返回本身的类就行

### 获取代理对象的原始对象

通过 `AopProxyUtils.getSingletonTarget()`实现

```java
public static Object getSingletonTarget(Object candidate) {
    if (candidate instanceof Advised) {
        TargetSource targetSource = ((Advised) candidate).getTargetSource();
        if (targetSource instanceof SingletonTargetSource) {
            return ((SingletonTargetSource) targetSource).getTarget();
        }
    }
    return null;
}
```

Advised继承自TargetAware, SingletonTargetSource是一种特殊的TargetSource, 单例模式的目标对象继承SingletonTargetSource而不是TargetSource

1. 先判断candidate是不是代理对象: `if (candidate instanceof Advised) `
2. 判断是不是单例类型: `if (targetSource instanceof SingletonTargetSource) `

**不同于获取原始类的class, 针对CGLIB和JDK动态代理有不同的处理方式, 对于获取原对象, 只有存储下来原对象, 并在需要的时候返回这一种处理方式**

### 为什么这样的回退代理的功能

这是在注解开发中非常重要的一环, 因为在动态代理的过程中可能会丢失原对象的注解信息

#### 在JDK代理中

- JDK动态代理基于接口实现, 生成的代理类继承自`java.lang.reflect.Proxy`, 代理对象的实际类型不再是原始类, 是动态生成的代理类

- JDK代理众所周知只能代理接口中定义的方法, 如果注解是加在实现类上的, 而不是接口上的, 代理对象就无法访问这些注解

#### 在CGLIB中

- CGLib生成的是原始类的子类

- 虽然会继承父类的注解，但在某些框架处理中仍可能出现问题

**所以在注解驱动开发中, 识别类是不是代理类, 并将类回退到原始类是个必要的工作, 防止类因为代理而导致注解丢失**

#### 多层代理问题

如果一个类被多层代理, 使用`AopUtils.getTargetClass()`实际上只能获取到"**上一层**"的Class, 而不是最原始的Class, 这个时候就需要使用`AopProxyUtils.ultimateTargetClass()`, 这也算是xfg项目中的一个小bug