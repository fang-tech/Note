## Java基础

### 包装缓存机制

`Byte`,`Short`,`Integer`,`Long` 这 4 种包装类默认创建了数值 **[-128，127]** 的相应类型的缓存数据，`Character` 创建了数值在 **[0,127]** 范围的缓存数据，`Boolean` 直接返回 `TRUE` or `FALSE`

```java
Short s1 = 123;
Short s2 = 123;
s1 == s2 // true
```

- 如果我们手动创建对象, 这个时候就不会使用缓存中的数据, 返回的就是在堆区的新的对象的地址
```java
Integer i1 = 40; // 发生了自动装箱, 等同于Integer i1 = Integer.valueOf(40);
Integer i2 = new Integer(40);
i1 == i2; // false
```

### 静态方法为什么不能调用非静态成员

1. 非静态成员的值是在运行时, 创建了这个对象的实例以后才能够访问
2. 在类的非静态成员还不存在的时候, 静态方法就已经存在了
3. 所以我们很容易得出结论, 静态成员只能访问静态方法

### 浅拷贝和深拷贝

> 这个问题也是和CPP中的拷贝问题是一样的问题, 只不过在java中拷贝函数使用过implement Cloneable来是实现的

我们将需要被拷贝的类中的所有数据复制一份, 创建一个新的实例, 但是在这个实例中, 因为我们是直接复制的, 浅拷贝会直接复制内部对象的引用地址, 原对象和浅拷贝对象共用一份内部对象

- 如何解决
    - 实现Clonebale, 在其中手动将内部对象也clone一份, 这个时候拷贝对象中的内部对象使用的就是新的实例
    

### hashCode与equals与==

> 这三个方式, 都能进行相等的判断

- == 比较的是值, 如果比较的对象, 这个时候比较的就是对象的地址
- equals我们默认是这个比较的是对象的值是否相等, 这也是我们往往预期的行为
- hashCode()返回的是对象的hash值, 如果我们需要将对象放在hash集合对象中, 我们必须重写这个方法

> 在Java中, 如果两个对象通过equals判断相等, 那么它们的hashcode也必须相等, 这样才能保证集合的工作不会出现错误

- 如果两个对象的hashcode不同说明两个对象一定不同(同样的值一定能算出来同样的hashcode), 但是hashcode相同两个对象不一定完全相同, 会有hash碰撞

- 重写hash方法
```java
@Override 
public int hashCode() { 
    return Objects.hash(name, age); // 根据 name 和 age 生成哈希值 
}
```
### String, StringBuffer, StringBuilder

- String是不可变的, 我们将两个string加起来的操作, 实际上是隐式地调用StringBuilderr的append方法后再toString(), 创建的是一个新的对象
```java
String str1 = "hello";
str1 += "world";
// 代码等同于
StringBuilder strbuilder.append(str1).append(world).toString();
```
那么如果我们需要频繁修改String对象的时候, 这个时候就会频繁创建StringBuilder对象用于拼接字符串
- 如果我们需要频繁修改字符串的时候, 或拼接字符串的时候, 使用StingBuilder拼接后再toString()
- StringBuffer相较于StringBuilder是**线程安全**的, 损失大概15%的性能

### String 为什么是不可变的

> String内部有一个char数组用于存储字符串, 这个字符串是private final

1. char数组是private, 外界无法直接访问, 同时String也没有向外提供修改的接口
2. final保证了无法通过继承String类来实现对char数组的修改
    - 子类无法修改父类的final字段的值

### 字符串常量池

> JVM中为字符串专门开辟的一块区域, 避免字符串的重复创建

使用和创建一个字符串字面量的时候(比如"hello"和final 字段修饰的String)
1. 检查这个变量在不在字符串常量池中, 在的话会直接返回对应的引用
2. 如果不在的话就会, 在字符串常量池中创建, 并返回对应的索引, 使用new会在堆区中再新建一个实例
3. 可以通过intern方法指定获取常量池中的字符串对象

### Exception 和 Error的区别

所有的异常类的共同祖先是Throwable类, 这个类的两个重要子类就是Exception和Error

- Exception : 程序本身能处理的异常, 可以通过catch捕获
    - 可检查的错误, 这类的错误是我们能预料到的错误, 如果受检查异常没有被 `catch` 或者 `throws` 出去, 就会无法通过编译
    - 不可检查错误, 其实就是运行时错误, 这类错误只有运行的时候才会出现, 比如空指针错误, 错误传参等
- Error : 程序无法处理的错误, 不建议使用catch捕获 (如OOM, StackOverFlow这种)

### 序列化

> 序列化用于持久化Java对象或者网络传输Java对象, 序列化的主要目的是通过网络传输对象或者说是将对象存储到文件系统, 数据库, 内存中

- 序列化 : 将数据结构或对象转换成可以存储或传输的形式, 通常是二进制字节流, 也可以是JSON, XML等文本形式
- 反序列化 : 将序列化产生的数据转化回对象的过程

#### JDK自带的序列化手段

实现Serializable接口就行了
```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private RpcMessageTypeEnum rpcMessageTypeEnum;
}
```

#### serialVersionUID的作用 

充当版本号的作用, 反序列的时候会检查UID和当前类的UID是否一致, 如果不一致则会抛出InvalidClassException
- 虽然是static但是这个特殊的变量还是会被写到序列化数据中

#### 让某些字段不被序列化

- `transient`关键字修饰

### 常用的序列化工具

> JDK官方的序列化不支持跨语言的调用, 性能差, 存在安全问题, 一般不使用JDK自带的序列化方式

- Kryo
- Protobuf

### Java代理模式

> 使用代理对象来代替对真实对象的访问, 这样就能在不修改原目标对象的前提下, 提供额外的功能操作, 拓展目标对象的功能

#### 静态代理

创建一个代理类, 让代理类实现被代理的类实现的接口, 然后将被代理类传入代理类, 再重实现对应的接口方法, 增强原来的方法

#### 动态代理

> 静态代理的维护成本太高了, 我们如果修改了被代理类还要重新修改代理类, 过于臃肿

我们不需要针对每个目标类都单独创建一个代理类, 并且也不许要我们必须实现接口, 我们可以通过代理实现类(CGLIB动态代理机制)

- JDK动态代理类使用步骤
    1. 定义一个接口及其实现类
    2. 自定义 `InvocationHandler(接口)`并重写`invoke`方法, 在`invoke`方法中调用原生方法, 并自定义一些处理逻辑
    3. 通过 `Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)` 方法创建代理对象；

- CGLIB动态代理
    - JDK动态代理只能代理实现了接口的类

```java
public interface MethodInterceptor
extends Callback{
    // 拦截被代理类中的方法
    public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,MethodProxy proxy) throws Throwable;
}
```
1. **obj** : 被代理的对象（需要增强的对象）
2. **method** : 被拦截的方法（需要增强的方法）
3. **args** : 方法入参
4. **proxy** : 用于调用原始方法

- 使用步骤
    1. 自定义一个类
    2. 实现MethodInterceptor并重写intercept方法, intercept用于拦截增加被代理类的方法, 和JDK动态代理中的 invoke 方法类似
    3. 通过Enhancer类的create()创建代理类

```java
package github.javaguide.dynamicProxy.cglibDynamicProxy;

public class AliSmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
```

- 实现方法拦截器
```java
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 自定义MethodInterceptor
 */
public class DebugMethodInterceptor implements MethodInterceptor {


    /**
     * @param o           被代理的对象（需要增强的对象）
     * @param method      被拦截的方法（需要增强的方法）
     * @param args        方法入参
     * @param methodProxy 用于调用原始方法
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //调用方法之前，我们可以添加自己的操作
        System.out.println("before method " + method.getName());
        Object object = methodProxy.invokeSuper(o, args);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return object;
    }

}
```

- 获取代理类
```java
import net.sf.cglib.proxy.Enhancer;

public class CglibProxyFactory {

    public static Object getProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new DebugMethodInterceptor());
        // 创建代理类
        return enhancer.create();
    }
}
```

#### Unsafe类

[[unsafe]]

### SPI

> Service Provider Interface,  服务提供者的接口, 服务使用者提供接口, 也就是实现规范, 服务提供商实现具体的服务

[[spi]]

### 泛型

Java中的泛型是编译时概念, 采用泛型擦除机制
- 泛型信息只存在于编译阶段, 在运行阶段被移除
- 泛型类型会被替换为它的 "擦除类型" (通常是Object, 如果有上界则替换为上界类)
- 所有的泛型参数都会被替换成为它的擦除类型

#### 泛型擦除后怎么发挥作用

1. 编译器的类型安全检查 : 这也是泛型的核心作用, 确保类型正确
2. 自动类型转换, 所有的泛型类型参数都会变成Object, 但是编译器会在需要的地方自己添加类型转换

> 所有的泛型类在类级别是共享的, 所有的泛型类实例都关联到同一份字节码上 ?

