# Synchronized关键字

## 大厂问题

Synchronized可以作用在哪里? 分别通过对象锁和类锁进行举例。

Synchronized本质上是通过什么保证线程安全的? 分三个方面回答：加锁和释放锁的原理，可重入原理，保证可见性原理。

Synchronized由什么样的缺陷? Java Lock是怎么弥补这些缺陷的。

Synchronized和Lock的对比，和选择?

Synchronized在使用时有何注意事项?

Synchronized修饰的方法在抛出异常时,会释放锁吗?

多个线程等待同一个Synchronized锁的时候，JVM如何选择下一个获取锁的线程?

Synchronized使得同时只有一个线程可以执行，性能比较差，有什么提升的方法?

我想更加灵活的控制锁的释放和获取(现在释放锁和获取锁的时机都被规定死了)，怎么办?

什么是锁的升级和降级? 什么是JVM里的偏斜锁、轻量级锁、重量级锁?

不同的JDK中对Synchronized有何优化?

## Synchronized的使用

- 一把锁同时只能被一个线程获取, 没有获得锁得线程只能等待
- 每个实例都对应自己的一把锁(this), 不同实例之间互不影响; 锁对象是\*.class以及synchronized修饰的是static的时候, 所有对象公用一把锁
- synchronized修饰的方法, 无论方法正常执行完毕还是抛出异常, 都会释放锁

### 对象锁

包括方法锁(默认锁对象是this, 当前实例对象)和同步代码块(自己指定锁对象)

#### 代码块形式: 手动指定锁定对象, 可以是this, 也可以是自定义的锁

- 锁是this, 在创建线程的时候传入锁对象实例

```java
public class SynchronizedObjectLock implements Runnable {
    static SynchronizedObjectLock instance = new SynchronizedObjectLock();

    @Override
    public void run() {
        // 同步代码块形式——锁为this,两个线程使用的锁是一样的,线程1必须要等到线程0释放了该锁后，才能执行
        synchronized (this) {
            System.out.println("我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "结束");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
/*
我是线程Thread-0
Thread-0结束
我是线程Thread-1
Thread-1结束
*/
```

- 锁是自定义的锁, 这个时候不用传入锁对象, 一般通过这个实现同步, 是在类中创建锁

```java
public class SynchronizedObjectLock implements Runnable {
    static SynchronizedObjectLock instance = new SynchronizedObjectLock();
    // 创建2把锁
    Object block1 = new Object();
    Object block2 = new Object();

    @Override
    public void run() {
        // 这个代码块使用的是第一把锁，当他释放后，后面的代码块由于使用的是第二把锁，因此可以马上执行
        synchronized (block1) {
            System.out.println("block1锁,我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("block1锁,"+Thread.currentThread().getName() + "结束");
        }

        synchronized (block2) {
            System.out.println("block2锁,我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("block2锁,"+Thread.currentThread().getName() + "结束");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
/*
block1锁,我是线程Thread-0
block1锁,Thread-0结束
block2锁,我是线程Thread-0　　// 可以看到当第一个线程在执行完第一段同步代码块之后，第二个同步代码块可以马上得到执行，因为他们使用的锁不是同一把
block1锁,我是线程Thread-1
block2锁,Thread-0结束
block1锁,Thread-1结束
block2锁,我是线程Thread-1
block2锁,Thread-1结束
*/
```

### 方法锁形式: synchronized修饰普通方法, 锁对象默认为this

```java
public class SynchronizedObjectLock implements Runnable {
    static SynchronizedObjectLock instance = new SynchronizedObjectLock();

    @Override
    public void run() {
        method();
    }

    public synchronized void method() {
        System.out.println("我是线程" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "结束");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
/*
我是线程Thread-0
Thread-0结束
我是线程Thread-1
Thread-1结束
*/
```

### 类锁

指synchronized修饰静态的方法或指定锁对象为Class对象, 这两种情况都是所有线程共用一把类锁

#### synchronized修饰静态的方法

- 修饰普通方法

```java
public class SynchronizedObjectLock implements Runnable {
    static SynchronizedObjectLock instance1 = new SynchronizedObjectLock();
    static SynchronizedObjectLock instance2 = new SynchronizedObjectLock();

    @Override
    public void run() {
        method();
    }

    // synchronized用在普通方法上，默认的锁就是this，当前实例
    public synchronized void method() {
        System.out.println("我是线程" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "结束");
    }

    public static void main(String[] args) {
        // t1和t2对应的this是两个不同的实例，所以代码不会串行
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
/*
我是线程Thread-0
我是线程Thread-1
Thread-1结束
Thread-0结束
*/
```

- 修饰静态方法 : 正确实现了两个线程之间method方法的互斥访问

```java
public class SynchronizedObjectLock implements Runnable {
    static SynchronizedObjectLock instance1 = new SynchronizedObjectLock();
    static SynchronizedObjectLock instance2 = new SynchronizedObjectLock();

    @Override
    public void run() {
        method();
    }

    // synchronized用在静态方法上，默认的锁就是当前所在的Class类，所以无论是哪个线程访问它，需要的锁都只有一把
    public static synchronized void method() {
        System.out.println("我是线程" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "结束");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
/*
我是线程Thread-0
Thread-0结束
我是线程Thread-1
Thread-1结束
*/
```

#### 指定锁对象为类对象

````java
public class SynchronizedObjectLock implements Runnable {
    static SynchronizedObjectLock instance1 = new SynchronizedObjectLock();
    static SynchronizedObjectLock instance2 = new SynchronizedObjectLock();

    @Override
    public void run() {
        // 所有线程需要的锁都是同一把
        synchronized(SynchronizedObjectLock.class){
            System.out.println("我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "结束");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
/*
我是线程Thread-0
Thread-0结束
我是线程Thread-1
Thread-1结束
*/
````

## Synchronized 原理分析

### 字节码层面

Synchronized锁在字节码层面本质上是通过monitor指令实现的

- moniterenter: 在对象执行的时候, 使其锁计数器+1
- moniterexit: 在对象执行的时候, 使其锁计数器-1
- 在进入到synchronized代码块的时候, 会**尝试获取这个synchronized绑定的对象的moniter**
  - **同一时间只能有一个线程持有一个特定对象的moniter, 也就是不存在同时有多个线程持有相同一个对象的moniter**
  - **每个对象都有一个自己的moniter**
  - 重入性: **同一个线程可以多次获取同一个moniter**
- happens-before: 监视器锁规则: 对同一个监视器的解锁 happens-before 对该监视器的加锁

### JVM中锁的优化

JVM中地`monitorexit`和`monitorenter`字节码依赖于底层的操作系统的Mutex Lock来实现, 但是使用Mutex Lock需要将当前线程挂起并从用户态切换到内核态来执行, 这种切换的代价是十分昂贵的. 现实中的大部分情况下, 同步方法都是运行在单线程环境, 也就是无锁竞争环境

syncronized中的锁会随着竞争情况, 锁逐渐由轻量转为重量, 转变过程

`无锁` -> `偏向锁` -> `轻量级锁` -> `重量级锁(mutex)`

#### Mark Word

对象头中记录hashcode或者锁信息的字段, Mark Word是实现锁升级的关键, 也是轻量级锁用于CAS的内容

在64位操作系统中, 

| 锁的类型 | 0~60bit           | 61bit          | 62~63bit |
| -------- | ----------------- | -------------- | -------- |
| 无锁     | hashcode          | 0              | 01       |
| 偏向锁   | 线程ID            | 是否持有偏向锁 | 01       |
| 轻量级锁 | lock record的引用 | /              | 00       |
| 重量级锁 | 指向monitor       | /              | 10       |
| GC标记   |                   |                | 11       |

#### lock record

是**线程私有的数据结构**, 存储在线程的栈帧中, 在第一次进入到同步块的时候创建, 用于存储锁对象的mark word副本, 包含两个关键字段

- displaced_mard_word: 保存锁对象的原始mark word的拷贝(无锁状态下的哈希码, 分代年龄等). 用于解锁的时候回复锁对象的对象头
- owner: 指向当前锁对象的引用, 用于标识该所记录属于哪个对象

#### 偏向锁 biased lock

很多时候不仅不存在多线程竞争, 而且总是由同一个线程多次获取, 这种时候的锁释放和获取带来的不必要的性能开销, 偏向锁也是基于这个情景进行的优化(但是这已经是过去式了, 现在还满足这个情景的应用已经很少了, 在JDK15开始, 偏向锁已经是默认关闭了)

**偏向锁使用了一种等待竞争出现才会释放锁的机制**

**第一次访问同步代码块**

- 创建lock record到线程的私有栈帧中, 记录下来锁对象的**原始**的mark record

- 对锁对象的mark record进行CAS, 期望值是原始的mark record, 新值是偏向锁结构

- 再次访问这个同步代码块
  - 对象进入到了偏向锁状态, 后续该线程重入的时候不需要重新CAS, 只需要校验线程ID是否一样

**偏向锁的释放和撤销**

**释放:** 就是正常的退出了同步代码块, 这个时候不会修改锁对象的Mark Word, 将lock record中的onwer置为null

**撤销:** 在锁已经偏向, 其他没有持有偏向锁的线程尝试获取锁的时候被动触发

- JVM会在全局安全点(STW的时候)暂停持有锁的线程A, 检查其状态, 并遍历线程栈, 将Mark Word重置为无锁状态
  - 如果已经退出了同步代码块, 则不改变锁级别, 让线程B竞争偏向锁, 重偏向
  - 如果还在同步代码块中, 说明发生了竞争, 升级为轻量级锁, 线程B通过自旋竞争

> 例外情况
>
> 1. 原持有线程已经终止了, 对象头中的线程ID失效, JVM将锁重置为无锁状态
> 2. 调用锁对象的hashcode()方法的时候会导致锁对象直接升级成重量级锁
> 3. 批量重偏向, 批量撤销: 太恶心了, 有需要再去了解

#### 轻量级锁

多个线程近似地可以看作在不同时段获取同一把锁的时候, JVM采用轻量级锁来避免线程的阻塞和唤醒

当一个线程获取到一个锁对象发现是轻量级锁的时候, 会将Mark Word复制到Displaced Mark Word中

> 第一个尝试获取锁的线程

通过CAS自旋来尝试获取锁

- 检查锁对象是不是无锁状态(01状态码)
  - 是无锁状态, 将Mark Word存入Displaced Mark Word

- 尝试CAS
  - 期望值是Displaced Mark Word
  - 新值是自己线程的lock record的指针
  - 设置为轻量锁状态

- 失败了说明锁已经被竞争走了

锁的释放

- 在离开同步代码块的时候, 通过CAS将Displaced Mark Word设置回去
- 设置为无锁状态

> 其他尝试竞争锁的线程

- 发现状态位是00, 轻量锁状态
  - 循环检查状态位是00
- 发现状态位是01, 说明是无锁状态
  - 复制displaced mark word
  - 尝试CAS

> 锁的升级

在某个线程自旋次数超过了阈值的时候(不是CAS的次数), 说明有严重的竞争情况, 将锁升级成重量级锁

>  自适应轻量级锁

自旋的次数会动态自适应变化, JVM根据自旋获取锁的成功率, 如果成功率高, JVM就会认为该锁自旋锁获取到的可能性很大, 就会增加自旋的次数. 反之自旋很少获取到锁, 就会减少自旋次数, 以防止浪费时间, 速速进入到重量级锁阶段

#### 重量级锁

在存在线程在竞争锁的时候, 并且轻量级锁阶段自旋超过了指定的次数, 就进入到了重量锁阶段

将mark word更新为指向monitor状态位10

重量级锁是悲观锁, 也就是获取锁失败的线程会进入到阻塞状态

重量级锁主要维护两个队列

- Contention List: 所有请求锁的线程都会被放到这个队列中
- Entry List: 已经被唤醒的队列
- Wait List: 被wait的队列, 等待被唤醒
- Owner: 指向获取了锁的线程

虽然维护了队列, 但是JVM并不会按照顺序获取下一个要执行的线程, 而是随机挑选, 是**非公平锁**

- 优点: 增大了吞吐量
- 缺点: 可能会导致饥饿现象

#### 锁粗化

在使用同步锁的时候, 我们会将同步锁的作用范围限制到尽量小的范围, 更有利于锁的释放, 但是如果存在连串的一系列操作都是对一个对象反复加锁和解锁, 频繁的互斥同步操作带来的不必要的性能开销

```java
public static String test04(String s1, String s2, String s3) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    sb.append(s3);
    return sb.toString();
```

上面连续的append, JVM会检测到这是一连串针对一个相同对象的加锁解锁, JVM就会将锁的范围粗化到整个一系列操作的外部, 使这三次的append操作都只需要加锁解锁一次

#### 锁消除

JIT会对一些要求同步, 但是被检测到不可能存在共享数据竞争的锁进行消除, JVM会判断再一段程序中的同步明显不会逃逸出去从而被其他线程访问到，那JVM就把它们当作栈上数据对待，认为这些数据是线程独有的，不需要加同步。此时就会进行锁消除

当然在实际开发中，我们很清楚的知道哪些是线程独有的，不需要加同步锁，但是在Java API中有很多方法都是加了同步的，那么此时JVM会判断这段代码是否需要加锁。如果数据并不会逃逸，则会进行锁消除。比如如下操作：在操作String类型数据时，由于String是一个不可变类，对字符串的连接操作总是通过生成的新的String对象来进行的。因此Javac编译器会对String连接做自动优化。在JDK 1.5之前会使用StringBuffer对象的连续append()操作，在JDK 1.5及以后的版本中，会转化为StringBuidler对象的连续append()操作。

```java
public static String test03(String s1, String s2, String s3) {
    String s = s1 + s2 + s3;
    return s;
}
```

