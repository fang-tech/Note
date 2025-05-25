# volatile关键字

## 大厂的问题

volatile关键字的作用是什么?

volatile能保证原子性吗?

之前32位机器上共享的long和double变量的为什么要用volatile? 现在64位机器上是否也要设置呢?

i++为什么不能保证原子性?

volatile是如何实现可见性的? 内存屏障。

volatile是如何实现有序性的? happens-before等

说下volatile的应用场景?

## volatile关键字的作用

### 防止指令重排序/实现有序性

最典型的例子就是双重检查中, 需要对对象的实例加上volatile关键字的问题

```java
public class Singleton {
    public static volatile Singleton singleton;
    /**
     * 构造函数私有，禁止外部实例化
     */
    private Singleton() {};
    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

首先为什么会出现问题, 原因就是`singleton = new Singleton();`不是一个原子指令, 实际上是三个步骤

1. 分配内存空间
2. 初始化对象
3. 将内存空间的地址赋值给引用

而处理器或者编译器是可以对2和3步骤进行指令重排的, 重拍以后就变成了

1. 分配内存空间
2. 将内存空间的地址赋值给引用
3. 初始化对象

这个时候如果线程A执行到了第2步, 线程B也尝试去获取这个单例对象, 这个时候因为引用已经不是null了, B线程就会获取到一个没有初始化完毕的对象

而加上volatile关键字以后, 就能不会出现这样的指令重排, 原因有二

- 从happens-before中的volatile规则来看, 保证了对于instance的写操作一定happens-before于instance的读操作
- 从volatile实现的原理来看, 通过在这个写操作前后加上内存屏障, 保证了有序性

### 实现可见性

通过给一个变量加上volatile关键字, 能保证这个变量在多线程语义下, 被一个线程写了以后, 这个修改对于其他线程是可见的, 如果没有加上这个关键字, 就会因为每个线程有自己独有的工作线程而导致的对于共享变量的修改的不可见

### 保证原子性: 单次读/写

>  问题2 : volatile能保证原子性吗?

volatile能保证单次读/写操作的原子性, 但是无法保证这种情况之外的原子性

> 问题3 : i++为什么不能保证原子性

i++实际上并不是单次读/写, 而是三步操作

- 读取i的值
- 对i加一
- 将i的值写回内存

volatile是无法保证这三个操作是具有原子性的

> 问题4 : 共享的long和double变量的为什么要用volatile?

在32位的系统上, 硬件是不保证读写long和double这种64位的变量是原子的, 可能被分解成两个32位的操作, 这个时候就需要使用volatile让JVM来保证它们的读写操作的原子的

- JVM主要通过加锁机制保证, 会在访问的时候自动添加内部锁
- 特殊的硬件指令实现

## volatile的实现原理

### 可见性

基于内存屏障来实现

通过插入特定类型的内存屏障来禁止特定类型的指令重排序. 插入一条内存屏障会告诉CPU和编译器, 不管什么指令都不能和这条内存屏障重排序

在volatile修饰的共享变量进行写操作的时候, 会多出lock前缀的指令, 这条指令会导致

- 将当前处理器缓存行的数据写回到系统内存
- 写回内存的操作会使其他CPU中缓存了该内存地址的数据无效(MESI协议)

这样就会保证主存中存有最新的修改后的共享变量数据

其他线程如果还要读共享变量, 会先尝试从CPU缓存中读取, 但是会因为数据无效, 重新从主存中读取, 从而读取到最新的修改后的数据

### 有序性

同样是基于内存屏障实现的

在happens-before规则里面有这么一条volatile规则: 对于volatile字段的写操作, happens-before于后续任意对于volatile字段的读操作

为了实现volatile的内存语义, 编译器在生成字节码的时候, 会在指令序列中插入内存屏障来禁止特定类型的处理器重排序

- 在每个volatile写操作的前面插入一个StoreStore屏障
  - 禁止上面的普通写和下面的volatile写重排序
- 在每个volatile写操作的后面插入一个StoreLoad屏障(全能型屏障)
  - 禁止上面的volatile写操作和下面可能有的volatile读/写重排序
- 在每个volatile读操作的后面插入一个LoadLoad屏障
  - 禁止上面的volatile读和下面的所有读操作重排序
- 在每个volatile读操作的后面插入一个LoadStore屏障
  - 禁止上面的volatile和下面的所有写操作重排序

## volatile应用场景

使用volatile必须具备的条件

- 对变量的写不依赖于该变量的值, 也就是不能有i++这种先读才能写的操作, 不能需要看到之前变量写了什么才能写入新的值
- 变量的值不应该和其他变量的值有联系
- 状态真正独立于程序内其他内容, 这个变量的变化不应该影响程序的其他逻辑或状态, 因为volatile无法保证复杂操作的原子性

### 1. 双重检查

经典案例

```java
public class Singleton {
    public static volatile Singleton singleton;
    /**
     * 构造函数私有，禁止外部实例化
     */
    private Singleton() {};
    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

需要通过volatile保证singleton写操作happens-before singleton读操作

### 2. 状态标志

```java
volatile boolean shutdownRequested;
......
public void shutdown() { shutdownRequested = true; }
public void doWork() { 
    while (!shutdownRequested) { 
        // do stuff
    }
}
```

### 3. 开销较低的读-写锁策略

通过volatile保证变量的可见性, 通过synchronized保证写操作的原子性

```java
@ThreadSafe
public class CheesyCounter {
    // Employs the cheap read-write lock trick
    // All mutative operations MUST be done with the 'this' lock held
    @GuardedBy("this") private volatile int value;
 
    public int getValue() { return value; }
 
    public synchronized int increment() {
        return value++;
    }
}
```
