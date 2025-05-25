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

## Syncronized原理分析

