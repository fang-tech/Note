---
tags:
  - Java
  - Spring
---
## 2. IoC注入

### 2.1 set注入

- 在代码中, 创建setXxx的方法
- 在需要创建依赖的Bean里创建Property属性, 添加name和ref
    - name填入xxx(set方法后面的单词的首字母小写结果)
    - ref 填入需要注入的实际的bean的id, 这里的注入, 可以理解为一种向set方法里传参

### 2.2 构造注入

- 是在创建对象的时候注入, 和set注入的时机不一样
- 需要有参构造方法

### 2.3 注入过程说明

> 设置依赖注入的过程

1. 编写xml文件, 在其中添加bean, 并设置id与绑定class
2.  向对应的bean中注入数据
3. 在主程序中获取

> xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="userDao" class="com.func.spring.dao.UserDao"></bean>
    <bean id="vipDao" class="com.func.spring.dao.VipDao"></bean>

    <!--构造注入-->
    <!--参数名和索引均不指定, 让spring自己做类型匹配-->
    <bean id="csbean3" class="com.func.spring.service.CustomService">
        <constructor-arg ref="userDao"></constructor-arg>
        <constructor-arg ref="vipDao"></constructor-arg>
    </bean>
    <!--根据构造方法的参数名字注入-->
    <bean id="csbean2" class="com.func.spring.service.CustomService">
        <constructor-arg name="userDao" ref="userDao"></constructor-arg>
        <constructor-arg name="vipDao" ref="vipDao"></constructor-arg>
    </bean>
    <!--根据索引注入-->
    <!--ref填入的是ben的id-->
    <bean id="customService" class="com.func.spring.service.CustomService">
        <constructor-arg index="0" ref="userDao"></constructor-arg>
        <constructor-arg index="1" ref="vipDao"></constructor-arg>
    </bean>
</beans>
```

> java文件

```java
    public void testConstructor(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

        CustomService customService = applicationContext.getBean("customService", CustomService.class);
        customService.save();

        ApplicationContext applicationContext1 = new ClassPathXmlApplicationContext("beans.xml");
        CustomService customService1 = applicationContext1.getBean("csbean2", CustomService.class);
        customService1.save();

        ApplicationContext applicationContext2 = new ClassPathXmlApplicationContext("beans.xml");
        CustomService customService2 = applicationContext2.getBean("csbean3", CustomService.class);
        customService2.save();
    }
```

### 2.4 set注入专题

#### 2.4.1 内部bean和外部bean

- 通过在外部新建一个bean, 然后另一个bean通过设置ref属性的引入方式称为外部引入

- 通过在该bean中嵌套另一个bean的方式称为内部bean 

```xml
    <!--外部bean, 通过ref引用-->
    <bean id="oderDao" class="com.func.spring.dao.OderDao"></bean>
    <bean id="oderServ1" class="com.func.spring.service.OderService">
        <property name="oderDao" ref="oderDao"></property>
    </bean>

    <!--内部bean, 通过嵌套生成-->
    <bean id="oderServ2" class="com.func.spring.service.OderService">
        <property name="oderDao">
            <bean class="com.func.spring.dao.OderDao"></bean>
        </property>
    </bean>
```

#### 2.4.2 注入简单类型

> 有哪些属于简单类型

- Spring源码中对简单类型的界定
```java
public class BeanUtils{
    
    //.......
    
    /**
	 * Check if the given type represents a "simple" property: a simple value
	 * type or an array of simple value types.
	 * <p>See {@link #isSimpleValueType(Class)} for the definition of <em>simple
	 * value type</em>.
	 * <p>Used to determine properties to check for a "simple" dependency-check.
	 * @param type the type to check
	 * @return whether the given type represents a "simple" property
	 * @see org.springframework.beans.factory.support.RootBeanDefinition#DEPENDENCY_CHECK_SIMPLE
	 * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#checkDependencies
	 * @see #isSimpleValueType(Class)
	 */
	public static boolean isSimpleProperty(Class<?> type) {
		Assert.notNull(type, "'type' must not be null");
		return isSimpleValueType(type) || (type.isArray() && isSimpleValueType(type.getComponentType()));
	}

	/**
	 * Check if the given type represents a "simple" value type: a primitive or
	 * primitive wrapper, an enum, a String or other CharSequence, a Number, a
	 * Date, a Temporal, a URI, a URL, a Locale, or a Class.
	 * <p>{@code Void} and {@code void} are not considered simple value types.
	 * @param type the type to check
	 * @return whether the given type represents a "simple" value type
	 * @see #isSimpleProperty(Class)
	 */
	public static boolean isSimpleValueType(Class<?> type) {
		return (Void.class != type && void.class != type &&
				(ClassUtils.isPrimitiveOrWrapper(type) ||
				Enum.class.isAssignableFrom(type) ||
				CharSequence.class.isAssignableFrom(type) ||
				Number.class.isAssignableFrom(type) ||
				Date.class.isAssignableFrom(type) ||
				Temporal.class.isAssignableFrom(type) ||
				URI.class == type ||
				URL.class == type ||
				Locale.class == type ||
				Class.class == type));
	}
    
    //........
}
```

**通过源码分析得知，简单类型包括：**
- **基本数据类型**
- **基本数据类型对应的包装类**
- **String或其他的CharSequence子类**
- **Number子类**
- **Date子类**
- **Enum子类**
- **URI**
- **URL**
- **Temporal子类**
- **Locale**
- **Class**
- **另外还包括以上简单值类型对应的数组类型。**

> 给简单类型注入, 使用value属性赋值, 而不是ref, ref给对象属性赋值, 需要特别注意的是简单类型中的Date类型

```XML
    <bean id="spv" class="com.func.spring.bean.SimpleValue">
        <property name="i1" value="213"/>
        <property name="i2" value="123"/>
        <property name="b1" value="false"/>
        <property name="b2" value="true"/>
        <property name="c1" value="c"/>
        <property name="c2" value="d"/>
        <property name="string" value="fung"/>
        <property name="season" value="SPRING"/>
        <property name="clazz" value="java.lang.String"/>
        <!--如果要把Data当成简单类型, 输入需要满足特定的格式-->
        <!--value后面的日期字符串格式不能随便写，必须是Date对象toString()方法执行的结果-->
        <!--实际开发中, 一般不会将Data当成简单类型-->
<!--        <property name="birth" value="Fri Sep 30 15:26:38 CST 2022"/>-->
    </bean>
```

#### 2.4.3 简单类型的注入的经典应用

> 用于配置配置文件

```xml
<bean id="dataSource" class="com.func.spring.jdbc.MyDataSource">
        <property name="password" value="root"/>
        <property name="username" value="root"/>
        <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3360/spring6"/>
    </bean>
```

#### 2.4.4 注入数组

> 在\<property>下再添加一组\<array>标签即可 , 然后在里面添加数据

```xml
    <bean id="w1" class="com.func.spring.bean.Woman">
        <property name="name" value="蔡徐坤"/>
    </bean>
    <bean id="w2" class="com.func.spring.bean.Woman">
        <property name="name" value="陈立农"/>
    </bean>
    <bean id="w3" class="com.func.spring.bean.Woman">
        <property name="name" value="及格"/>
    </bean>

    <bean id="qian" class="com.func.spring.bean.YuQian">
        <property name="habits">
            <array>
                <value>抽烟</value>
                <value>喝酒</value>
                <value>烫头</value>
            </array>
        </property>

        <property name="women">
            <array>
                <ref bean="w1"/>
                <ref bean="w2"/>
                <ref bean="w3"/>
            </array>
        </property>
    </bean>
```

#### 2.4.5 Set集合, Map集合, Properties类型注入

> Set

```xml
        <!--测试Set-->
        <property name="addr">
            <set>
                <value>ff</value>
                <value>ff</value>
                <value>df</value>
                <value>ff</value>
            </set>
        </property>
```

> Map

```xml
        <!--测试Map-->
        <property name="phones">
            <map>
                <entry key="1" value="110"/>
                <entry key="2" value="120"/>
                <entry key="3" value="119"/>
            </map>
        </property>
```

> Properties

```xml
        <!--测试Properties-->
        <property name="properties">
            <props>
                <prop key="username">root</prop>
                <prop key="password">root</prop>
                <prop key="url">jdbc:mysql://localhost:3306:/spring</prop>
                <prop key="driver">com.mysql.cj.jdbc.Driver</prop>
            </props>
        </property>
    </bean>
```

#### 2.4.6 注入特殊字符与null

> 特殊字符 : 两种方式, 一种是换成转义字符, 还有一种解决方式是使用!\<\[CDATA\[\]\]\>

- 代码

```xml
    <bean id="math" class="com.func.spring.bean.MathBean">
        <!--会报错-->
<!--        <property name="data" value="2 < 3"/>-->

        <!--转义-->
<!--        <property name="data" value="2 &lt; 3"/>-->

        <!--CDATA方式, 其中的value标签需要额外写出来-->
        <property name="data">
            <value><![CDATA[2 < 3]]></value>
        </property>
    </bean>
```

- 转移字符表

| **特殊字符** | **转义字符** |
| -------- | -------- |
| >        | \&gt;     |
| <        | \&lt;     |
| '        | \&apos;   |
| "        | \&quot;   |
| &        | \&amp;    |

> 注入null与空字符

```xml
<bean id="cat" class="com.func.spring.bean.Cat">
        <!--默认不写的时候, 值就是null-->

        <!--手动设置的方法-->
<!--        <property name="name">-->
<!--            <null/>-->
<!--        </property>-->

        <!--空字符串-->
        <property name="name" value=""/>
<!--        <property name="name">-->
<!--            <value/>-->
<!--        </property>-->
    </bean>
```


### 2.5 基于命名空间的注入和配置复用

> 命名空间的引入, 可以简化配置的过程

> p命名空间, 能简化set注入的过程

1. 在XML头部信息中添加p命名空间的配置信息：xmlns:p="[http://www.springframework.org/schema/p"](http://www.springframework.org/schema/p%22)
2. 提供setter方法

```xml
    <!--p命名空间注入-->
    <bean id="dog" class="com.func.spring.bean.Dog" p:age="123" p:name="虎牙"></bean>
```

> c命名空间注入, 简化了构造注入的过程

1. 需要在xml配置文件头部添加信息：xmlns:c="[http://www.springframework.org/schema/c](http://www.springframework.org/schema/c)"
2. 需要提供构造方法。
```xml
    <!--c命名空间注入-->
    <bean id="dog" class="com.func.spring.bean.Dog" c:age="12" c:name="lichengxing" >
    </bean>
```

> util命名空间与配置复用 : 通过设置该命名空间能让一份properties被多处引用配置

1. 在xml中进行如下设置
![[images/Pasted image 20241224140647.png]]

```xml
    <!-- util命名空间, 让配置复用-->
    <util:properties id="prop">
        <prop key="username">root</prop>
        <prop key="password">root</prop>
        <prop key="url">jdbc:mysql://localhost:3306/spring</prop>
        <prop key="driver">com.mysql.cj.jdbc.Driver</prop>
    </util:properties>

    <!--c命名空间注入-->
    <bean id="dog" class="com.func.spring.bean.Dog" p:age="12" p:name="lichengxing" >
        <property name="properties" ref="prop"/>
    </bean>
    <bean id="dog1" class="com.func.spring.bean.Dog" p:age="2" p:name="Lichengxing">
        <property name="properties" ref="prop"/>
    </bean>
```

### 2.6 基于XML的自动装配

> 我们可以不用ref来主动设置bean的注入的参数, 通过该功能, XML能够自动出装配参数

> byName : 通过参数名自动装配, 但这里的参数名实际上是对应的setter方法的名字

- 设置autowrite属性为byName
```xml
    <bean id="cs" class="com.func.spring.service.CustomService" autowire="byName"></bean>
    <bean id="vipDao" class="com.func.spring.dao.VipDao"></bean>
    <bean id="userDao" class="com.func.spring.dao.UserDao"></bean>
```

> byType : 通过参数类型自动装配, 通过这种方式, 实例化的bean可以不设置id

- 设置为byType
```xml
    <bean class="com.func.spring.dao.VipDao"></bean>
    <bean class="com.func.spring.dao.UserDao"></bean>

    <bean id="cs" class="com.func.spring.service.CustomService" autowire="byType"></bean>
```

### 2.7  XML装配外部资源文件

> 建立外部资源文件

```properties
username=root
password=root
url=jdbc:mysql://localhost:3306/spring
driver=com.mysql.cj.jdbc.Driver
```

> 在bean中引入

1. 修改xml约束, 增加context相关内容
2. 导入外部资源
3. 引用外部资源
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder location="jdbc.properties"/>

    <bean id="prop" class="com.func.spring.jdbc.MyDataSource">
        <property name="url" value="${url}"/>
        <property name="driver" value="${driver}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
    </bean>

</beans>
```

## 3. Bean的作用域

### 3.1 Bean作用域之单例和多例

> 默认情况下

- 在默认情况下Spring是单例的, 创建实例的时机是在启动容器的时候创建的, 即在new完`ClassPathXmlApplicationContext`之后即创建完毕, 后面再getBean, 获取到的都是同一个实例

> 设置为原型/多例的情况

- 通过设置bean的属性scope为prototype, 这个时候创建对象为多例
- 创建对象的时机时在getbean的时候创建

> xml

```xml
    <bean id="scope" class="com.func.spring.bean.SpringBean" scope="prototype"></bean>
```


### 3.2 自定义scope


> 自定义xml : 这里只是简单提了一下, 这个自定义的scope是直接用的Spring中内置好的类实现的

```xml
    <bean id="scope" class="com.func.spring.bean.SpringBean" scope="ThreadScope"></bean>

    <bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
        <property name="scopes">
            <map>
                <entry key="ThreadScope">
                    <bean class="org.springframework.context.support.SimpleThreadScope"/>
                </entry>
            </map>
        </property>
    </bean>
```

> 测试代码

```java
    @Test
    public void testThreadScope(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-bean.xml");
        SpringBean scope = applicationContext.getBean("scope", SpringBean.class);
        System.out.println(scope);

        SpringBean scope1 = applicationContext.getBean("scope", SpringBean.class);
        System.out.println(scope1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SpringBean scope2 = applicationContext.getBean("scope", SpringBean.class);
                System.out.println(scope2);
                SpringBean scope3 = applicationContext.getBean("scope", SpringBean.class);
                System.out.println(scope3);
            }
        }).start();

    }
```

## 4. GoF之工厂模式

## 5. Bean

### 5.1 Bean的四种实例化方法

#### 5.1.1 通过构造方法的实例化

- 就是先前创建bean实例的方式, 直接创建对应的bean, 就会通过对应class的无参构造方法实例化对象
#### 5.1.2 通过简单工厂模式实例化

- 步骤
    1. 创建工厂类, 创建产品类
    2. 在工厂类中添加简单工厂方法, 用于获取对应的产品
    3. 工厂方法需要是public static 类型的, 返回对应产品的新的实例
    4. 在xml文件中声明产品类的bean, 设置的class是工厂类, 并添加`factory-mothod`属性, 填写内容是产品对应工厂方法的名字

> 最后的对象, 其实是自己new出来的

```java
public class StarFactory {

    public static Star get(){
        return new Star();
    }
}

@Data
public class Star {
    private String name;
}
```

```xml
    <bean id="StarBean" class="com.func.spring.StarFactory" factory-method="get"></bean>
```

#### 5.1.3 通过factory-bean实例化

> 通过工厂方法模式实例化 : 每个产品对应一个工厂角色

 - 步骤
     1. 创建对应的产品类和工厂类
     2. 创建对应的工厂方法(不是static的, 也因此, 需要指定工厂类)
     3. 在xml中声明工厂类bean
     4. 通过设置factory-bean为工厂类bean, factory-method为对应的工厂方法, 来实例化新的bean

```java
public class Gun {
}

public class GunFactory {
    public Gun get(){
        return new Gun();
    }
}

```

```xml
    <bean id="GunFactory" class="com.func.spring.GunFactory"></bean>
    <bean id="gun" factory-method="get" factory-bean="GunFactory"></bean>
```

#### 5.1.4 通过FactoryBean接口实例化

> 实质上是对第三种方法的简化, 通过实现接口来简化第三种方案

- 步骤
    1. 创建产品类和工厂类
    2. 工厂类继承FactoryBean<>接口, 并实现其中的方法
    3. 在getObject()中返回new的产品实例
    4. 在xml中声明产品bean, 但是其class填写工厂类

```java
public class Person {
    public Person(){
        System.out.println("调用了Person的无参构造");
    }

public class PersonFactory implements FactoryBean<Person> {
    @Override
    public Person getObject() throws Exception {
        System.out.println("调用了Person的工厂方法");
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
```

```xml
    <bean id="person" class="com.func.spring.PersonFactory"></bean>
```

#### 5.1.5 Bean工厂实例化方法的实际运行

> 通过Bean工厂实例化, 来实现对于Date这一属性的赋值

1. 先创建Date工厂
2. 在工厂的getObject方法中, 返回规定了格式的Date对象
3. 返回对应format的Date对象
4. 在xml实例化

```java
@Data
public class Student {
    private Date birth;
}

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DateFactory implements FactoryBean<Date> {
    private String strDate;

    @Override
    public Date getObject() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date birth = sdf.parse(this.strDate);
        return birth;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
```

```xml
    <bean class="com.func.spring.DateFactory" id="date">
        <property name="strDate" value="1980-11-20"/>
    </bean>

    <bean id="stu" class="com.func.spring.Student">
        <property name="birth" ref="date"/>
    </bean>
```

## 6. Bean的生命周期

### 6.1 Bean的生命周期之五步

1. 调用bean的无参构造方法创建bean
2. 为bean赋值
3. 调用bean的init()方法, 初始化bean
4. 使用bean
5. 销毁bean, 调用bean的destroy()方法 : 发生在容器关闭的时候

```java
public class User {
    private String name;
    public User(){
        System.out.println("第一步: 构造bean");
    }

    public void setName(String name) {
        System.out.println("第二步: 为bean赋值");
        this.name = name;
    }
    public void initBean(){
        System.out.println("第三步: 初始化bean");
    }

    public void destroyBean(){
        System.out.println("第五步: 销毁bean");
    }
}
```

> 容器的关闭需要将ApplicationContest类型强转为ClassPathXmlApplicationContext后才能调用close()方法

```java
    @Test
    public void testLifeCycleFive(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        User user = applicationContext.getBean("user", User.class);
        System.out.println("第四步: 使用bean" + user);

        ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
        context.close();
    }
```

> 需要人为创建和绑定初始化和销毁方法

```xml
    <bean id="user" class="com.func.spring.User" init-method="initBean" destroy-method="destroyBean">
        <property name="name" value="张三"/>
    </bean>
```


### 6.2 Bean生命周期之七步

> 通过bean后处理器, 在初始化前后添加了两个周期

1. 创建对象实现后处理器接口, 并重写其中的方法
2. 在xml文件中实例化, 这个实现了后处理接口的对象, 这个后处理器的作用范围是整个配置文件

```java
public class BeanProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + " " + bean + " 在初始化前执行了后处理器中的before方法");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + " " + bean + " 在初始化前执行了后处理器中的after方法");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
```

```xml
    <bean class="com.func.spring.BeanProcessor"></bean>
```

### 6.3 Bean的生命周期之十步

> 添加三部分别在

1. bean后处理器的before方法之前 : 对应的接口有`BeanClassLoaderAware, BeanFactoryAware, BeanNameAware`
2. bean后处理器的before方法之后 : 对应的接口有`InitializingBean`
3. bean销毁之前, 即destroy方法调用之前 : 对应的接口有`DisposableBean`

```java
public class User implements BeanClassLoaderAware, BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {
    private String name;
    public User(){
        System.out.println("第一步: 构造bean");
    }

    public void setName(String name) {
        System.out.println("第二步: 为bean赋值");
        this.name = name;
    }
    public void initBean(){
        System.out.println("第三步: 初始化bean");
    }

    public void destroyBean(){
        System.out.println("第五步: 销毁bean");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("Bean的类加载器是" + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("生产bean的工厂是" + beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("bean的名字是" + name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行了 afterPropertiesSet() 方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行了销毁bean前的会执行的方法");
    }
}
```

### 6.4 生命周期之不同作用域

- 只有单例的bean实例, Spring才会全程管理
- 如果是多例的bean. Spring只会管理到使用bean那一步, 后续的两步DisposableBean接口对应的Destroy方法和bean实例的销毁方法都不会再由Spring管理

### 6.5 如何让自己new的对象给Spring管理

> 如何在半途中将自己new的对象交给Spring管理

1. new一个自己的对象
2. 通过DefaultListableBeanFactory类, 注册单例, 并通过这个类从容器中获取bean

```java
    public void testRegisterBean(){
        User user = new User();
        System.out.println(user);

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerSingleton("userBean",user);
        User user1 = factory.getBean("userBean", User.class);
        System.out.println(user1);
    }
```

## 7. Bean的循环依赖

> 什么情况是循环依赖

- 当对象A中有属性B, 同时对象B中也有属性A, 那么这个在通过Spring实例化的时候, 就出现了循环依赖

#### 7.1 通过setter实例化的bean对象

- 只有Setter + singleton的组合, 才能即使产生了循环依赖, 但是也能成功实例化

> 为什么这个组合能成功, 但是prototype却会创建失败

- Spring在创建单例的实例的过程中采取先"曝光", 后赋值的形式, 这对于Spring来说是两个阶段
- Spring会在第一个阶段将所有的单例的bean先通过无参构造方法创建好, 再进行曝光
- 在第二个阶段再进行赋值操作, 这样在赋值的时候, 就能成功的将创建好的对象传入
- 同时, 根据上面的原理, 我们可以知道, 相互依赖的两个对象, 其实只要有一个对象是单例的, 就能成功实例化

```java
@AllArgsConstructor
@Data
public class Husband {
    private String name;
    private Wife wife;

    @Override
    public String toString() {
        return "Husband{" +
                "name='" + name + '\'' +
                ", wife=" + wife.getName() +
                '}';
    }
}

@AllArgsConstructor
@Data
public class Wife {
    private String name;
    private Husband husband;
}

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring1.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        System.out.println(husband);
```

### 7.2 通过有参构造器实例化bean对象

> 根据上面的原理, 其实能得出, 如果是通过有参构造器实例化bean对象, 那么怎么样都是不能够成功创建的

### 7.3 Bean的循环依赖之源码分析

> 通过分析Spring源码, 能得出Spring通过三级缓存完成了提前曝光的过程

- 一级缓存 : 缓存的是完整的单例Bean对象, 已经完成了赋值
- 二级缓存 : 缓存的是没有进行赋值的单例Bean对象
- 三级缓存 : 缓存的是单例Bean对象的工厂对象, 这个对象也是被提前曝光的对象
- Spring在getSingleton()方法中, 从第一级逐级访问来查看该层缓存有没有保存对应的bean对象

## 8.回顾反射机制

[[javaAdvance#44. 反射]]

## 9. 手写spring框架

### 9.1 创建相关类

1. 创建ApplicationContext接口, 并在其中添加`getBean(String beanId)`方法
2. 创建实现类

### 9.2 创建bean实例并存储到map中

1. 创建map集合用于存储beans
2. 解析xml文件, 并将解析获取到的类调用无参构造方法后实例化到beanMap中
    1. 使用SAXReader.read从Resource文件夹中获取resourse路径对应的xml文件的文本流
    2. 利用第一步获取到的document对象, 从中获取到根标签, 并从中获取到所有一级子标签List -> beanNodes
    3. 从一级子标签获取id和class属性, 通过class属性填写的类路径实例化bean, 并以id为名字存入beanMap中
3. 以此完成了一级存储和曝光

### 9.3 给bean的属性赋值

1. 遍历beanNodes
2. 获取id属性, 以对应beanMap中的实例, 获取所有的property子标签properties, 以完成赋值
3. 通过properties获取属性name和value(或ref)
4. 通过name, 拼接出set方法的方法名
5. 通过反射获取到set方法
6. 调用set方法赋值, 通过属性是value还是ref, 判断是简单类型还是引用类型
    1. 是简单类型
        1. 获取到类中对应name的field的类型
        2. 通过switch case语句, 完成对value的类型转换
        3. 调用set方法将值注入
    2. 非简单类型
        1. 直接从beanMap中通过ref的名字获取到对应对象的引用
        2. 调用set方法将值注入

### 9.4 完整源码

```java
/**
 * @author 方天宇
 * @version 1.0
 * @className ClassPathXmlApplicationContext
 * @date 2024/12/26
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
    // Map用于存储bean
    private Map<String, Object> beanMap = new HashMap<>();


    /**
     * 构造方法解析xml文件, 并创建所有的实例bean, 以此实现曝光
     * @param resource xml文件的资源路径
     */
    public ClassPathXmlApplicationContext(String resource) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(ClassLoader.getSystemClassLoader().getResourceAsStream(resource));
            // 获取到所有的bean标签
            List<Element> beanNodes = document.getRootElement().elements();
            // 遍历标签
            beanNodes.forEach(beanNode -> {
                String id = beanNode.attributeValue("id");
                String className = beanNode.attributeValue("class");

                // 通过反射机制创建对象, 并放入map集合中
                try {
                    Class<?> clazz = Class.forName(className);
                    // 创建新的实例
                    Constructor<?> constructor = clazz.getConstructor();
                    Object bean= constructor.newInstance();
                    // 将新的实例添加到Map集合中
                    beanMap.put(id, bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        // 给bean的属性赋值, 将创建对象和赋值这两阶段分开进行, 以此实现曝光
        beanNodes.forEach(beanNode ->{
            String id = beanNode.attributeValue("id");
            List<Element> properties = beanNode.elements("property");
            properties.forEach(property -> {
                try {
                    String propertyName = property.attributeValue("name");
                    String value = property.attributeValue("value");
                    String ref = property.attributeValue("ref");
                    // 获取属性类型
                    Class<?> propertyType = beanMap.get(id).getClass().getDeclaredField(propertyName).getType();
                    // 获取set方法
                    String setMethodName = "set" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                    Method setMethod = beanMap.get(id).getClass().getDeclaredMethod(setMethodName, propertyType);
                    // 获取set方法
                    Object propertyValue = null;
                    if (value == null) {
                        // 非简单类型
                        setMethod.invoke(beanMap.get(id), beanMap.get(ref));
                    }
                    if (ref == null) {
                        // 简单类型
                        String propertySimpleName = propertyType.getSimpleName();
                        // 将值从String转为对应的简单类型
                        switch (propertySimpleName) {
                            case "int" : case "Integer" :
                                propertyValue = Integer.valueOf(value);
                                break;
                            case "double" : case "Double":
                                propertyValue = Double.valueOf(value);
                                break;
                            case "float": case "Float":
                                propertyValue = Float.valueOf(value);
                                break;
                            case "long" : case "Long" :
                                propertyValue = Long.valueOf(value);
                                break;
                            case "short" : case "Short":
                                propertyValue = Short.valueOf(value);
                                break;
                            case "boolean": case "Boolean":
                                propertyValue = Boolean.valueOf(value);
                                break;
                            case "char" : case "Character" :
                                propertyValue = value.charAt(0);
                                break;
                            default:
                                propertyValue = value;
                       
break;
                        }
                        setMethod.invoke(beanMap.get(id), propertyValue);
                    }
                } catch (Exception e) {
                   e.printStackTrace();
                }
            });

        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String beanId) {
        return beanMap.get(beanId);
    }
}
```
## 10. IoC注解式开发

### 10.1 回顾注解开发

#### 10.1.1注解的意义

- 在Spring中引入注解的目的是为了简化繁琐的配置过程

#### 10.1.2 注解中属性的定义

 > 代码
 
 ```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    String value();
}
```

> 说明

- 类型 : @interface
- 字段 : 是未来可以在注解中填写的内容, 会被存放在这个注解类中, 未来也可以通过获取到这个注解类从而获取到其中的值
- 元注解
    - 上面的@Target和@Retention都被称为元注解, 是用来修饰注解的注解
    - @Retention : 注解的保持性策略, 规定了注解什么时候可以被反射机制读取, 定义了注解的生命周期, 在什么时候可以访问
        - SOURCE : 注解只在源码中保存, 编译时会被丢弃, 用于编译检查工具
        - CLASS(默认) : 注解会保留在`.class`文件中, 但运行的时候无法通过反射获取, 用于编译器和工具使用(Lombok)
        - RUNTIME : 注解会一直保留到运行的时候, 可以通过反射获取
    - @Target : 注解可以修饰的位置, 也就是注解可以放在什么东西上面, 有方法, 类, 属性...
#### 10.1.3 通过反射机制读取注解

1. 设置包名, 获取到包的绝对路径
2. 获取到包下面的所有的文件流
3. 以流的方式载入, forEach遍历 (流式获取更多的是代码规范, 实际上直接遍历files也是可以的)
    1. 通过文件名以及包名, 获取到完整的类路径名
    2. 反射获取类, 检查该类是否有该注解
    3. 从类中获取注解. 解析注解
    4. 通过注解创建实例并存入map中

#### 10.1.4 完整代码

```java
        HashMap<String, Object> beanMap = new HashMap<>();

        String packageName = "com.func.spring";
        String packagePath = packageName.replaceAll("\\.", "/");
        URL url = ClassLoader.getSystemClassLoader().getResource(packagePath);
        System.out.println(url);
        File f = new File(url.getPath());
        File[] files = f.listFiles();
        Arrays.stream(files).forEach(file -> {
            String fileName = file.getName();
            String className = packageName + "." +  fileName.split("\\.")[0];
            System.out.println(className);
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Component.class)) {
                    Component component = clazz.getAnnotation(Component.class);
                    String value = component.value();
                    Constructor<?> constructor = clazz.getConstructor(String.class);
                    Object o = constructor.newInstance(value);
                    beanMap.put(value, o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        System.out.println(beanMap);
```

### 10.2 Bean的注解种类

> 四种注解类型 : 通过观察源码, 其实可以得出, 另外三种注解类型, 其实只是@Component的别名, 所以不同类的注解更多的是为了增强可读性

- @Component
- @Service
- @Controller
- @Repository

> 源码

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {
    String value() default "";
}
```

- 注解只能放在类上, 可以通过反射获取

### 10.3 注解的使用
#### 10.3.1 使用注解

> 添加注解, 填入的value就是BeanId

```java
@Component("user")
public class User {
    @Value("UserName")
    private String name;
}
```

> XML中扫描含有注解的类

- 先要添加context命名空间
- 指定要扫描的包
- 如果有多个包, 可以用 `,` 隔开, 或者填入父包的地址
```java
<?xml version="1.0" encoding="UTF-8"?>
<beans
       xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

        <context:component-scan base-package="com.func.spring" use-default-filters="true">
                <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Component"/></context:component-scan>
</beans>
```
#### 10.3.2 选择性实例化Bean

> 我们可以选择化实例某一类的注解

```xml
        <context:component-scan base-package="com.func.spring" use-default-filters="true">
                <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Component"/></context:component-scan>
```

- use-default-filters
    - true使用默认的过滤器, 相当于不过滤, 所有注解bean还是会被实例化
    - false不使用..., 会不初始化所有的注解bean
- exclude-filter(include-filter) 
    - 需要排除(添加)的注解的种类, 填入种类的注解bean会被不实例化(实例化)

### 10.4 负责注入的注解

> 简单类型的注入

1. 在属性名上填写 `@Value`, 通过这种方式, 可以不些setter方法
2. 在属性对应的setter方法上 `@Value`
3. 在构造方法的参数列表对应属性前填入 `@Value`

> 代码

- 属性名

```java
@Service("vip")
public class Vip {
    @Value("vipN")
    private String name;
}
```

- 构造方法
```java
@Component("user")
public class User {
//    @Value("UserName")
    private String name;

    public User(@Value("方") String name) {
        this.name = name;
    }
```

> 非简单类型

> 通过AutoWrited注入

- 使用和@Vaule一样, 不过AutoWrited是自动装配, 和之前的xml中的auto-write一样
- 不过是byType
- 如果不写, Spring也会自动帮你装配非简单类型, 只不过这需要该类只有一个构造方法, 不然会报错

> @Qualifier

- 如果想通过byName自动装配
- 需要再额外配合上@Qualifier("name") 注解来进行匹配

> @Resource

- 属于JDK拓展包, 需要额外导入
- 是通过byName方式找到bean的

```xml
<dependency>
  <groupId>javax.annotation</groupId>
  <artifactId>javax.annotation-api</artifactId>
  <version>1.3.2</version>
</dependency>
```

> 代码

```java
    @Resource(name = "vip")
    private Vip vip;
```
### 10.5 全注解式开发

> 不再通过xml配置文件, 而是通过配置类配置

- 配置类

```java
@Configuration
@ComponentScan({"com.func.spring"})
public class Spring6Configuration {
}
```

- 加载配置类

```java
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Spring6Configuration.class);
        Object user = annotationConfigApplicationContext.getBean("user");
        System.out.println(user.toString());
```

## 11. GoF之代理模式

[[Spring中的设计模式#2. GoF之代理模式 (结构型设计模式)]]

## 12. 面向切面编程AOP

### 12.1 AOP的介绍

#### 什么是AOP

- 是OOP的延申拓展, 在面对对象基础上, 进一步提取出来公共组件和行为
- 这样的公共组件, 我们也称为程序的横切面, 所以AOP是一种面向横切面编程的思想, 这样的公共组件有 (日志记录, 权限控制, 事务管理等)
- 不同于通过公共工具类的方式实现这种提取, AOP采取了更自动化的方式 : 代理模式

#### AOP和代理模式

- 代理模式可以看作是AOP的实现, 通过动态的代理, 可以很轻松的实现AOP, 会自动的在我们需要的位置增强代码
- 同时最关键的是, 这是一种非侵入式的实现, 遵守了OCP开闭原则

#### Spring 中的 AOP

- Spring中主要通过JDK 动态代理和cglib代理实现
    - 如果代理的是接口类, 或者代理的类实现了接口, 使用JDK
    - 没有实现接口的类, 则使用的是cglib实现
    - 也可以在后续的配置文件中指定实现方式

### 12.2 AOP相关术语

- 目标对象 Target
    - 被织入的对象
- 代理对象 Proxy
    - 一个目标对象被织入通知后产生的新的对象
- 织入 Weaving
    - 把通知应用到目标对象上的过程
- 通知 Advice
    - 也叫增强, 就是具体要织入的代码
    - 种类有
        - 环绕通知
        - 前置通知
        - 后置通知
        - 异常通知
        - 最终通知
- 切点 Pointcut
    - 织入了切面的方法
- 连接点 Joinpoint
    - 可以织入切面的位置, 种类和通知的种类对应

### 12.3 切点表达式

切点表达式用于定义通知被切入的方法

切入点表达式语法格式：

`execution([访问控制权限修饰符] 返回值类型 [全限定类名]方法名(形式参数列表) [异常])`

访问控制权限修饰符：

- 可选项。
- 没写，就是4个权限都包括。
- 写public就表示只包括公开的方法。

返回值类型：

- 必填项。
- \*表示返回值类型任意。

全限定类名：

- 可选项。
- 两个点“..”代表当前包以及子包下的所有类。
- 省略时表示所有的类。

方法名：

- 必填项。
- \*表示所有方法。
- set\*表示所有的set方法。

形式参数列表：

- 必填项
- () 表示没有参数的方法
- (..) 参数类型和个数随意的方法
- (\*) 只有一个参数的方法
- (\*, String) 第一个参数类型随意，第二个参数是String的。

异常：

- 可选项。
- 省略时表示任意异常类型。

### 12.4 使用Spring的AOP

#### 配置

- 导入依赖spring-aspects
- 在xml文件中添加context和aop的命名空间

#### 复用和执行顺序

- 可以将**切点复用**, 提取出来, 被修饰的方法并不会真的被执行, 只是给一个方这个方法的位置,  设置`@Pointcut`注解
- 可以通过设置`@order(整数)`来控制切面的执行顺序, 数字越小优先级越高

#### 步骤

1. 创建与编写切面类
2. 配置配置文件

#### 代码

##### 切面类

```java
@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(* com.func.spring.aop.OrderService.*(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void beforeAdvice(){
        System.out.println("前置通知");
    }

    @Around("pointcut()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知开始");
        // 执行目标方法
        proceedingJoinPoint.proceed();
        System.out.println("环绕通知开始");
    }
    @AfterReturning("pointcut()")
    public void afterAdvice(){
        System.out.println("后置通知");
    }
    @AfterThrowing("pointcut()")
    public void throwingAdvice(){
        System.out.println("异常通知");
    }
    @After("pointcut()")
    public void finalAdvice(){
        System.out.println("最终通知");
    }
}
```

##### xml配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <context:component-scan base-package="com.func.spring.aop"/>

    <!--使用cglib方式动态代理-->
    <aop:aspectj-autoproxy expose-proxy="true"/>
    <!--生成代理对象-->
    <aop:aspectj-autoproxy proxy-target-class="true"/>
</beans>
```

- proxy-target-class : 默认为false, 这个时候就是介绍AOP的时候说明的动态代理顺序, 如果为true则全程使用cglib执行动态代理
- expose-proxy : 为true的时候, 但是带有@Aspect注解的bean都会生成代理对象
    - 问题背景 : 如果在目标类的方法中调用了另一个方法, 这个时候目标方法内部的自己的其他方法, 是该类自己调用的, 因为不是通过代理对象调用的, 增强逻辑将不会生效
    - 这个时候, 想让那个内部执行的自己的方法也执行增强逻辑, 就可以让这个参数为true, 暴露代理对象, 然后通过当前线程获取代理对象, 在内部调用中使用代理对象, 从而触发增强逻辑
      ```java
    public void insert(){
        System.out.println("正在插入数据");
        OrderService proxy = (OrderService) AopContext.currentProxy();
        proxy.delete();
        System.out.println("proxy = " + proxy);
        System.out.println("this = " + this);
    }
      ```
      - 这种方式是带有一定的侵入性的
    

#### 执行结果

- 不带有异常的

```plain text
环绕通知开始
前置通知
aspect2环绕通知开始
aspect2前置通知
正在添加数据
aspect2后置通知
aspect2最终通知
aspect2环绕通知开始
后置通知
最终通知
环绕通知开始
```

- 带有异常的

```plain text
环绕通知开始
前置通知
aspect2环绕通知开始
aspect2前置通知
正在添加数据
aspect2异常通知
aspect2最终通知
异常通知
最终通知
```

#### 总结

- 如果实现了环绕通知, 因为环绕通知需要能够在方法的执行前后都进行通知, 所以有环绕通知后, 方法的执行需要手动调用
```java
    @Around("pointcut()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知开始");
        // 执行目标方法
        proceedingJoinPoint.proceed();
        System.out.println("环绕通知开始");
    }
```

- 执行顺序 : 其实看运行结果和环绕执行的机制很容易知道, 不再赘述
- 如果有异常, 那么后置方法是不会执行的, 因为没有return这个环节, 但是最终通知是会执行的, 因为该类型的通知, 其实是被放到`finally{}`代码块中执行的

#### 全注解开发

- 在注解类上再加个`@EnableAspectJAutoProxy()`的注解就行
- 代码

```java
@Configuration
@ComponentScan("com.func.spring.aop")
@EnableAspectJAutoProxy(exposeProxy = true)
public class SpringConfiguration {
}
```

### 12.5 xml开发(不推荐)

- 只给出实例代码

```xml
         <bean id="timer" class="com.func.spring.aop.TimerAspect"/>
        <bean id="order" class="com.func.spring.aop.OrderService"/>
    
        <!--aop配置-->
        <aop:config>
            <!--切面表达式-->
            <aop:pointcut id="pointcut" expression="execution(* com.func.spring.aop.OrderService.* (..))"/>
            <!--切面-->
            <aop:aspect ref="timer">
                <aop:around method="time" pointcut-ref="pointcut"/>
            </aop:aspect>
        </aop:config>

```

### 12.6 应用案例

[[Spring6_语雀版讲义#15.5 AOP的实际案例：事务处理]]
[[Spring6_语雀版讲义#15.6 AOP的实际案例：安全日志]]

## 13. Spring对事务的支持 (JdbcTemplate)

[[Spring6_语雀版讲义#16.1 事务概述]]

## 14. Spring6整合Junit5

[[Spring6_语雀版讲义#十七、Spring6整合JUnit5]]

## 15. Spring6集成Mabatis

[[Spring6_语雀版讲义#十八、Spring6集成MyBatis3.5]]

## 16. Spring中的八大模式

[[Spring6_语雀版讲义#十九、Spring中的八大模式]]