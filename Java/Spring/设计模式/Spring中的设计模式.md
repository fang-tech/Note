---
tags:
  - 含有八股内容
  - 设计模式
  - Spring
  - Java
---
# 1. 工厂模式

# 2. GoF之代理模式 (结构型设计模式)

## 2.1 代理模式简介

### 为什么需要代理模式

#### 间接访问

- 在程序中A和B无法直接交互, 可以通过代理C完成A <-> C <-> B以完成交互

#### 增强功能

- 程序的功能需要增强时, 可以通过套一层代理模式, 让代理模式实现额外的功能
- 使代码更灵活, 遵循OCP

#### 保护

- 程序中, 目标需要被保护时, 我们可能需要控制访问权限,  增加额外的控制逻辑

### 代理模式中的角色

![[image (1).png]]

- 目标类 (真实主题)
- 代理类 (代理主题)
- 代理类和目标类的公共接口 (抽象主题) : 客户端在使用代理类的时候就像在使用目标类, 所以代理类和目标类要实现共同的接口
 
## 2.2 静态代理

### 业务场景

### 三种解决模式

> 继承式

- 优点
    - 相较于将代码直接重新复制黏贴到代理类上, 不用修改源代码, 符合OCP开闭原则
- 缺点
    - 采用继承式关系, 代码耦合度高
    - 需要为每个实现类编写代理类, 类膨胀

```java
public class OrderServiceProxy extends OrderServiceImpl{
    @Override
    public void Insert() {
        long start = System.currentTimeMillis();
        super.Insert();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) + "ms");
    }

    @Override
    public void Delete() {
        long start = System.currentTimeMillis();
        super.Delete();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) + "ms");
    }

    @Override
    public void Update() {
        long start = System.currentTimeMillis();
        super.Update();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) + "ms");
    }
}

```

> 静态代理式

- 实现
    - 在代理类创建目标类的属性, 在公共接口中调用目标类的方法, 从而完成代理
- 优点
    - 相对比与继承式, 关联式关系代码耦合度更低
    - 符合OCP开闭原则
- 缺点
    - 类膨胀, 一个接口对应一个接口类, 再加上目标类, 类膨胀迅速, 开发维护困难

```java
public class OrderServiceProxy implements OrderService{

    private OrderServiceImpl service;

    public OrderServiceProxy(OrderServiceImpl service) {
        this.service = service;
    }

    @Override
    public void Insert() {
        long begin = System.currentTimeMillis();
        service.Insert();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");
    }

    @Override
    public void Delete() {
        long begin = System.currentTimeMillis();
        service.Delete();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");

    }

    @Override
    public void Update() {
        long begin = System.currentTimeMillis();
        service.Update();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");
    }
}
```
## 2.3 动态代理

### 三种动态代理技术

- JDK : 通过共同接口实现代理, 缺点是只能代理接口
- CGLIB : 通过继承完成代理, 所以不能用于代理有final关键字修饰的类, 安东尼是能代理类, 也能代理接口
### JDK动态代理

> 使用步骤

1. 创建目标对象
2. 通过`Proxy.newProxyInstance(类加载器, 接口类型, 调用处理器)` 创建代理对象
3. 实现调用处理器, 并将其传入

> 代码

- Client

```java
// 目标对象
        OrderService target = new OrderServiceImpl();
        // 代理对象
        OrderService proxy = (OrderService) ProxyUtil.newProxyInstance(target);
        proxy.Delete();
        proxy.Insert();
        proxy.Update();
```

- ProxyUtil

```java
public static Object newProxyInstance(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new TimerInvocationHandler(target));
    }
```

- Handler

```java
public class TimerInvocationHandler implements InvocationHandler {
    private Object target;

    public TimerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retValue = method.invoke(target, args);
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");
        return retValue;
    }
}
```
### CGLIB动态代理

> 实现步骤

1. 创建增强器对象
2. 为其设置父类和回调接口
3. 创建回调接口类, 并传入
4. 通过增强器对象创建代理类

> 代码

- Client

```java
    public static void main(String[] args) {
        // 创建增强器
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(OrderServiceImpl.class);
        // 设置回调接口
        enhancer.setCallback(new TimerMethodInterceptor());
        OrderServiceImpl proxy = (OrderServiceImpl) enhancer.create();
        proxy.Delete();
        proxy.Insert();
        proxy.Update();
    }
```

- Inteterceptor

```java
public class TimerMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retValue = methodProxy.invokeSuper(o, objects);
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");
        System.out.println("method = " + method);
        System.out.println("methodProxy = " + methodProxy);
        return retValue;
    }
}
```