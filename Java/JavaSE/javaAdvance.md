## 1. 继承的基础使用

```java
public class Son extends Father{}
```

> 1. 成员变量访问特点 : 看等号左边是谁, 先调用谁中的变量
> 2. 成员方法访问特点: 看new的是谁, 先调用谁的方法
> 3. java不支持多继承

## 2. 方法的重写

需要在重写的方法上加上@Override注解

> 1. 子类重写父类方法之后, 权限必须要保证大于等于父类权限\
>    - public > protected -> 默认 -> private
> 2. 私有方法不能重写, 构造方法不能重写, 静态方法不能重写
>
> 3. 子类重写父类方法方法以后, 返回的类型, 必须是父类方法返回类型的子类(原类型)

## 3. super和this

1. new子类对象时, 会先调用父类的构造函数
2. 原因: 每个构造方法的第一行,默认都会有一行super(), 不写jvm会自动提供一个, super()代表的是父类无参构造

## 4. super的具体使用

1.  调用父类的构造方法 
   - `super()/super(params)`
2. 调用父类成员变量
   - `super.成员变量`
3. 调用父类成员方法
   - `super.成员方法(params)`

## 5. this的具体使用

1.  调用子类的构造方法 
   - `this()/this(params)`
2. 调用子类成员变量
   - `this.成员变量`
3. 调用子类成员方法
   - `this.成员方法(params)`

> 无论是this()还是super()都只能在第一行调用, 所以两个是不能同时使用的

## 6. 抽象类

1. 关键字 : abstract
2. 定义抽象方法
   - 修饰符 abstract 返回值类型 方法名(参数);
3. 抽象类:
   - public abstract class 类名{}

> 1. 抽象类中不一定有抽象方法
> 2. 抽象方法所在的类一定是抽象类  
> 3. 抽象类的子类必须重写抽象类中的所有抽象方法, 除非该子类同样是抽象类

## 7. 接口和抽象类的区别

1. 接口的特点

   ```java
   1. 接口可以多继承 -> 一个接口可以继承多个接口
       public interface InterfaceA extends InterfaceB, InterfaceC{}
   2. 接口可以多实现 -> 一个类可以同时实现一个或多个接口
       public class InterfaceImpl implements InterfaceA, InterfaceB{}
   3. 一个子类可以继承一个父类的同时实现一个或多个接口 -> 一个类可以同时extends 和implements
       public class InterfaceImpl extends Father implements InterfaceA, InterfaceB{}
   
   4. 无论是接口还是抽象类, 父类中的抽象方法, 子类都必须重写
   ```

> 当一个接口实现多个接口时, 如果接口中的抽象方法有重命名且参数一样的时候吗, 只需要重写一次, 就会重载所有的父类的方法

2. 接口中的成员

   1. 抽象方法

      ```java
      public abstract Type method(params);
      ```

      > 不写public abstract 默认也有

   2. 默认方法与静态方法

      ```java
      public default Type method(params);
      
      public static Type method(params);
      ```

   3. 成员变量

      ```java
      public static final Type name = ?;
      
      不写public static final 默认也有
      ```

3. 接口和抽象类的使用情况对比

   - 当只需要定义一组无关实现的行为, 这个时候可以使用接口
   - 有一部分通用的实现已经完成, 还有一部分的行为需要实现的时候

## 8. 多态

- 能使用多态的前提是有继承和重写

- 最核心的写法就是用父类的指针new子类, 调用公共方法

  - ```java
    Fu fu = new Zi();
    ```

  > 看new的是谁, 就先调用谁那里的方法, 如果子类没有就去父类寻找

## 9. 多态的向下转型

向下转型之前需要先进行类型判断

`if (animal instanceof Dog){}`


## 10. 权限修饰符

1. 不同权限的访问能力

   |                | public | protected | default | private |
   | -------------- | ------ | --------- | ------- | ------- |
   | 同类           | yes    | yes       | yes     | yes     |
   | 同包不同类     | yes    | yes       | yes     | no      |
   | 不同包父子类   | yes    | yes       | no      | no      |
   | 不同包非父子类 | yes    | no        | no      | no      |

> 编写代码的时候, 如果没有特殊的考虑, 只需要简单遵循下面的三条准则就行
>
> 1. 属性 : private
> 2. 成员方法 : public
> 3. 构造方法 : public 

## 11. final修饰符

1. final class -> 不能被继承
2. final 方法 -> 不能被重写
3. final 局部变量 -> 值不能二次赋值
4. final 修饰对象 -> 地址不能改变, 但是对象的属性值可以改变
5.  final 成员变量 -> 需要有初始值, 并且不能二次赋值

> final和abstract不能同时修饰一个方法

## 12. 代码块

- 只需要关注静态代码块

  ```java
  static{
      
  }
  ```

  > 静态代码块中的代码会最先执行, 并且只会执行一次, 可以用于构建链接这类只需要执行一次且需要最先执行的代码

## 13. 静态成员内部类

1. 格式 : 

   ```java
   public class A{
       static class B{
           
       }
   }
   ```

2. 特点 : 

   > 静态内部类不能调用外部的非静态成员

## 14. 匿名内部类

> 没有显式声明出类名的内部类

```java
new interface/abstractClass(){
    重写方法
}.重写的方法(); 
```



## 15异常处理与finally

```java
try{

}catch{

}finally{

}
```

> 1. finally里面的代码是一定会执行的代码, 一般用于释放内存(释放GC不会自动回收的内存, 比如Socket, IO流)
> 2. 执行的顺序 : try{...} -> finally{...} -> catch{...}
> 3. 如果在finally, 我们的代码直接返回了, 那么代码就会无法执行catch中的内容

## 16. Object类中的方法

Object是所有类的根类, 其中有`toString()`, `equeals()`, `clone()`三个方法和`compareTo`, `Comparetor`两个接口需要注意

1. toString()方法 : 

   ```java 
   // 这个方法相当于python中的_str_字段
   // 直接打印出来一个类, 它的值就是你在toString定义的返回
   @Override
   public String toString() {
       // 这是Object的实现
       // 默认的时候返回的是对象地址
       return getClass().getName() + "@" + Interger.toHexString(hasCode());
   }
   ```

2. equals()方法 : 

   ```java
   // Object中的实现, 比较的是地址
   public boolean equals(Object o) {
       return(this == o);
   }
   ```

3. clone()方法 :

   ```java
   /*
   实现的步骤 :
   1. 需要实现的类, implements Cloneable接口
   2. 重写clone()方法
   */
   @Override 
   public Object clone() throw ...{
       super.cone();
   }
   ```

4. compareTo接口 : 

   ```java
   public class Person implements Comparable{
       int age;
       public int conpareTo(Object o){
           return this.age - o.age;
     }
   }
   ```

5. ComparaTor接口 :

   ```java
   public class Person implements Comparator{
       //剩下的和compareTo相同
   }
   ```

## 17. String基础知识

1. String的不可变性 :
   - String类一旦创建就是个不可变的变量, 一旦String类进行了改变就会新建一个String用于承载新的字符串
   - 这种设计的优点
     1. 线程安全性, 因为是不可变的, 所以在共享数据的时候是线程安全的
     2. 安全性, 防止了恶意的篡改
     3. 字符串常量池, Java使用字符串常量池来管理, 池内相同内容的字符串共享一个对象

2. 何时创建了新的对象?
   1. 直接new了一个对象的时候
   2. = 右边的字符串拼接式中有变量存在
   3. = 右边的字符串常量没有被创建过

## 18. StringBuilder

StringBuider就像是CPP中的正常的String一样的使用方法, 底层维护的是一个非final的byte数组, 因为是可变的, 所以在拼接这个操作上的性能高于String, 所以它是高效的, 但也正因为它的可修改性, 它也是线程不安全的, 扩容机制和CPP中的vector类似, 不过每次扩容的规模是*2+2

```java
StringBuilder sb = new StringBuilder();

sb.append(String...);
```

## 19. Math工具类 :

```java
static int abs(int a) -> 绝对值
static double ceil(double a) -> 向上取整
static double floor(double a) -> 向下取整
static long round(double a) -> 四舍五入
static int max/min(int a, int b) -> 取最大或最小
```

## 20. BigInteger : 大数

处理long都无法处理的大数, 可以使用整型构造, 也可以选用String构造

```java
Biginteger b = new BigInteger();
// 转化相关的函数
b.intValue();
b.longValue();
b.floatValue();
...
```

## 21. BigDecimal : 准确的小数

直接使用double或者float计算, 会有精度的损失, 并且无法精确表达像0.2这样的数字, 比如钱等数字, 带有小数位, 同时我们不能让它的计算有精度的损失且显示多个小数位, 所以我们构建了一个这样的类

```java
// 两种构建方法
BigDecimal bd = new BigDecimal(String / double)
// 使用String的构建方式是完全可预期的, 但是使用double的构建方式是无法预期的, 因为传进去的double不一定能准确表达那个数字, 比如0.1, 实际上可能是0.100000123123
//它同样具有转化相关的函数, 这里不赘述

// 它的除法是特殊的
divide(BigDecimal divisor, int scale, RoundingMode roundingMode)
    divisor -> 除号后的数据
    scale   -> 保留的小数位数
    roundingMode -> 取舍的模式 (四舍五入这种), 常用的有
                        UP, -> 直接+!
                        DOWN -> 直接舍去
                        HALF_UP -> 四舍五入
```

## 22. Calendar 日历类

``` java
//1. 声明日历类
Calendar calendar = Calendar.getInstance();

int year = calendar.get(Calendar.YEAR);
System.out.println(calendar.get(Calendar.DATE));

calendar.add(Calendar.YEAR, 1); => 年份加一
    
Scanner sc = new Scanner(System.in);

int year = sc.nextInt();

calendar.set(year, 2, 1); -> 设置为三月一号
```

## 23. SimpleDateFormat : 格式化输入输出日期

```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

String date = sdf.format(new Date());
System.out.println("date = " + date);

String date2 = "2000-12-06 12:12:12";
try {
    Date date3 = sdf.parse(date2);
    System.out.println("date3 = " + date3);
} catch (Exception e) {
    e.printStackTrace();
}
```

## 24. JDK8新增日期类

1. LocalDate 和 LocalDateTime

```java
public static void calendar() {
    LocalDate localDate = LocalDate.now();
    System.out.println(localDate);

    LocalDate localDate1 = LocalDate.of(2021, 12, 31);
    System.out.println(localDate1);

    LocalDateTime localDateTime = LocalDateTime.now();
    System.out.println(localDateTime);

    LocalDateTime localDateTime1 = LocalDateTime.of(2021, 12, 31, 23, 59, 59);
    System.out.println(localDateTime1)
}
```

2. get()方法和with()方法

```java
public static void with(){
    LocalDate localDate = LocalDate.now();
    LocalDate localDate1 = localDate.withYear(2021).minusMonths(2).withDayOfYear(1);
    System.out.println(localDate1);

}
public static void get(){
    LocalDateTime localDateTime = LocalDateTime.now();
    int year = localDateTime.getYear();
    int month = localDateTime.getMonthValue();
    int day = localDateTime.getDayOfMonth();
    int hour = localDateTime.getHour();
    int minute = localDateTime.getMinute();
    int second = localDateTime.getSecond();
    System.out.println(year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分" + second + "秒");
}
```

3. plusAndMinus

```java
public static void plusAndMinus() {
    LocalDate localDate = LocalDate.now();
    LocalDate localDate1 = localDate.plusYears(1).minusMonths(2).plusDays(3);
    System.out.println(localDate1);
}
```

4. PeriodAndDuration

```java
public static void PeriodAndDuration() {
    LocalDate localDate = LocalDate.of(2022, 11, 3);
    LocalDate localDate1 = LocalDate.of(2011, 1, 23);

    Period period = Period.between(localDate1, localDate);
    System.out.println(period.getYears());
    System.out.println(period.getMonths());
    System.out.println(period.getDays());

    LocalDateTime localDateTime = LocalDateTime.of(2022, 12, 1, 12, 12, 12);
    LocalDateTime localDateTime1 = LocalDateTime.now();

    Duration duration = Duration.between(localDateTime1, localDateTime);

    System.out.println(duration.toDays());
    System.out.println(duration.toHours());
    System.out.println(duration.toMinutes());
    System.out.println(duration.toMillis());
}
```

5. DateTimeFormatter

```java
public static void DataTimeFormatter() throws Exception {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.now();
    String time = dtf.format(localDateTime); // format是将时间转换为字符串
    System.out.println(time);

    String time1 = "2021-12-31 23:59:59";
    LocalDateTime localDateTime1 = LocalDateTime.parse(time1, dtf); // parse是将字符串转换为时间
    System.out.println(localDateTime1);
    throw new Exception("SHABI FANGTIANYU");
}
```

## 25. System类

|                             方法                             |                             说明                             |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
|               static long currentTimeMillis()                |            返回以毫秒为单位的当前时间, 可以测效率            |
|                 static void exit(int status)                 |                 终止当前正在运行的java虚拟机                 |
| static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length) | 数组复制<br />stc:源数组<br />srcPos:从源数组的哪个索引开始复制<br/>dest:目标数组<br>destPos:从目标数组的哪个索引开始粘贴<br />Ilength : 复制多少个元素<br/> |

```java
import static java.lang.System.exit;

public class APIModule.Demo02System {
    public static void main(String[] args) {
//        system();
//    exit();
    arrayCopy();
    }

    private static void arrayCopy() {
        int[] src = {1, 2, 3, 4, 5};
        int[] dest = {6, 7, 8, 9, 10};
        System.arraycopy(src, 1, dest, 0, 4);
        for (int i : dest) {
            System.out.println(i);
        }
    }

    private static void exit() {
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                System.out.println("程序退出");
                System.exit(0);
            }
            System.out.println(i);
        }
//        System.exit(0);
    }


    private static void system() {
        //获取当前时间的毫秒值
        long now = System.currentTimeMillis();
        System.out.println("now = " + now);
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "毫秒");
    }

}
```

## 26. Arrays数组工具类

|                        方法                        |         说明         |
| :------------------------------------------------: | :------------------: |
|          static String toString(int[] a)           | 按照格式打印数组元素 |
|             static void sort(int[] a)              |       升序排序       |
|     static int binarySearch(int[] a, int key)      |       二分查找       |
| static int[] copyOf(int[] original, int newLength) |       数组扩容       |

```java
private static void arrayDemo() {
    int[] numbers = {1,2,3,4,5,0};
    String numsString = Arrays.toString(numbers);
    System.out.println("numsString = " + numsString);

    Arrays.sort(numbers);
    System.out.println("Sorted numbers = " + Arrays.toString(numbers));

    int index = Arrays.binarySearch(numbers, 3);
    System.out.println("Index of 3 = " + index);

    int[] copy = Arrays.copyOf(numbers, numbers.length + 5);
    System.out.println("Copy of numbers = " + Arrays.toString(copy));
}
```

## 27. 包装类

| 基本类型 | 包装类    |
| -------- | --------- |
| byte     | Byte      |
| short    | Short     |
| int      | Integer   |
| long     | Long      |
| float    | Float     |
| double   | Double    |
| char     | Charactor |
| boolean  | Boolean   |

1. 装箱 : 将基本类型转换为对应的包装类

2.  `static Integer valueOf(int i)/(String s)`

3. 拆箱 : 将包装类转成基本类型
4. `int intValue();`
5. 多数情况装箱和拆箱是自动的, 无需在意

> 有个机制, 如果给的值是-128 ~ 127之间的数字, 会直接给一个已经创建好的Integer对象, 该类预先存储了这个范围内的Integer类, 超出了这个范围才会创建新的类

## 28. String和基本类型之间的转换

1. 将基本类型转成String
   -  \+ 拼接
   - `static String valueOf(int i)`

2. 将String转成基本类型

   ```java
   int number = Integer.parseInt("String")
   ```

> 每个包装类中都有一个类似的方法 : parseXXX

## 29. javabean和包装类

> 定义javabean的时候一般会将基本类型的属性定义成包装类型的属性

```java
1.举例:如果uid为Integer型,默认值是null
2.将来javabean中的数据都是和数据库表联系起来的,我们可以将javabean中的数据添加到表中
如果表中的uid为主键自增的,此时添加语句时uid中的数据不用我们单独维护赋值了,添加语句的sql语句就可以这样写:
insert into user(uid,username,password) values (NULL,'金莲','36666');

3.到时候,我们需要将javabean中封装的数据获取出来放到sql语句中,如果uid为主键自增,而且javabean中的uid为包装类型,默认值为NULL,这样就不用单独维护uid的值了,也不用先给javabean中的uid赋值,然后在保存到数据库中了,咱们就可以直接使用uid的默认值,将默认值放到sql语句的uid列中

4.而且将javabean中的属性变成包装类,还可以使用包装类中的方法去操作此属性值    
```

## 30. 创建线程

### 第一种方式 extends Thread

1. 定义一个类, 继承Thread
2. 重写run方法, 在run方法中设置线程任务
3. 创建自定义线程类的对象
4. 调用Thread中的start方法, jvm自动启动其中的run方法

#### Thread类中的方法

```java
void start() -> 开启线程,jvm自动调用run方法
void run()  -> 设置线程任务,这个run方法是Thread重写的接口Runnable中的run方法
String getName()  -> 获取线程名字
void setName(String name) -> 给线程设置名字
static Thread currentThread() -> 获取正在执行的线程对象(此方法在哪个线程中使用,获取的就是哪个线程对象)   
static void sleep(long millis)->线程睡眠,超时后自动醒来继续执行,传递的是毫秒值    
```

#### Thread中其他的方法

```java
void setPriority(int newPriority)   -> 设置线程优先级,优先级越高的线程,抢到CPU使用权的几率越大,但是不是每次都先抢到
    
int getPriority()  -> 获取线程优先级
    
void setDaemon(boolean on)  -> 设置为守护线程,当非守护线程执行完毕,守护线程就要结束,但是守护线程也不是立马结束,当非守护线程结束之后,系统会告诉守护线程人家结束了,你也结束吧,在告知的过程中,守护线程会执行,只不过执行到半路就结束了
    
static void yield() -> 礼让线程,让当前线程让出CPU使用权
   
void join() -> 插入线程或者叫做插队线程    
```

### 第二种方式 实现Runnable接口

为方法实现Runnable接口, 然后`Thread t1 = new Thread(myRunnable)`, 接下来就当线程对象正常使用

### 第三种方式  匿名内部类创建多线程

```java
new Thread(new Runnable() {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"...执行了"+i);
        }
    }
},"阿庆").start();
```

## 31. 解决线程安全问题

#### 同步代码块

> 相当于有一个大家共享的代码块, 同一时间只有一个线程正在运行这部分的代码, 我们在声明一个同步代码块的时候传进去的对象就是一个锁, 抢到了锁的线程才能进入到同步代码块中执行

```
while(true) {
    try {
        Thread.sleep(100L);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    // 同步代码块
    synchronized (obj) {
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + "正在卖第" + ticket + "张票");
            ticket--;
        }
    }
}
```

### 同步方法

#### 普通同步方法 : 非静态

```java
1.格式:
  修饰符 synchronized 返回值类型 方法名(参数){
      方法体
      return 结果
  }
2.默认锁:this
```

```java
public void method01(){
    synchronized (this) {
        System.out.println(this+"..............");
        if (ticket>0) {
            System.out.println(Thread.currentThread().getName() + "正在卖第" + ticket + "张票");
            ticket--;
        }
    }
}
public synchronized void method02(){
    if (ticket>0) {
        System.out.println(Thread.currentThread().getName() + "正在卖第" + ticket + "张票");
        ticket--;
    }
}
```

#### 静态同步方法

```java
1.格式:
  修饰符 static synchronized 返回值类型 方法名(参数){
      方法体
      return 结果
  }
2.默认锁:class对象
```

```java
public static synchronized void method02() {
    int ticket = 100;
    while (ticket > 0) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "正在卖第" + ticket + "张票");
        ticket--;
    }
}
public static void  method01() {
    int ticket = 100;
    synchronized (MyTicket04.class) {
        while (ticket > 0) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "正在卖第" + ticket + "张票");
            ticket--;
        }
    }
}

```

## 32. 死锁

在锁存在嵌套的时候会出现, 锁的嵌套 : 获取锁1后再获取锁2, 即一个线程在持有一个锁的前提下等待获取零一个锁, 这个时候就可能出现死锁

- A持有锁1, 现在想同时获取锁2
- 而B持有锁2, 现在想同时获取锁1
- 两个线程, 现在相互等待获取对方持有的锁, 如果没有外力作用, 将永远阻塞

死锁的形式化定义 : 

- 指的是两个或者两个以上的线程在执行的过程中由于竞争同步锁而产生的一种阻塞现象;如果没有外力的作用,他们将无法继续执行下去,这种情况称之为死锁

```java
public class LockA {
    public static LockA lockA = new LockA();
}
```

```java
public class LockB {
    public static LockB lockB = new LockB();
}
```

```java
public class DieLock implements Runnable{
    private boolean flag;

    public DieLock(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag){
            synchronized (LockA.lockA){
                System.out.println("if...lockA");
                synchronized (LockB.lockB){
                    System.out.println("if...lockB");
                }
            }
        }else{
            synchronized (LockB.lockB){
                System.out.println("else...lockB");
                synchronized (LockA.lockA){
                    System.out.println("else...lockA");
                }
            }
        }
    }
}

```

```java
public class Test01 {
    public static void main(String[] args) {
        DieLock dieLock1 = new DieLock(true);
        DieLock dieLock2 = new DieLock(false);

        new Thread(dieLock1).start();
        new Thread(dieLock2).start();
    }
}
```

> 因为操作系统或者说java总是倾向于为每个线程分配同等的运行时间, 所以一般不会出现一个线程直接获取到所有的锁的情况, 而是先各自获取到lockA, lockB, 然后出现死锁

## 33. 线程状态

| 线程状态                | 导致状态发生条件                                             |
| ----------------------- | ------------------------------------------------------------ |
| NEW(新建)               | 线程刚被创建，但是并未启动。还没调用start方法。              |
| Runnable(可运行)        | 线程可以在java虚拟机中运行的状态，可能正在运行自己代码，也可能没有，这取决于操作系统处理器。 |
| Blocked(锁阻塞)         | 当一个线程试图获取一个对象锁，而该对象锁被其他的线程持有，则该线程进入Blocked状态；当该线程持有锁时，该线程将变成Runnable状态。 |
| Waiting(无限等待)       | 一个线程在等待另一个线程执行一个（唤醒）动作时，该线程进入Waiting状态。进入这个状态后是不能自动唤醒的，必须等待另一个线程调用notify或者notifyAll方法才能够唤醒。 |
| Timed Waiting(计时等待) | 同waiting状态，有几个方法有超时参数，调用他们将进入Timed Waiting状态。这一状态将一直保持到超时期满或者接收到唤醒通知。带有超时参数的常用方法有Thread.sleep 、Object.wait。 |
| Terminated(被终止)      | 因为run方法正常退出而死亡，或者因为没有捕获的异常终止了run方法而死亡。或者调用过时方法stop() |





<img src="E:\Desktop\Github\java\线程状态图.png" alt="线程状态图" style="zoom:60%;" />

## 34. wait和notify

```java
Lock lock = new ReentrantLock();

lock.lock(); // 加锁
/// 需要同步的代码
lock.unlock(); // 释放锁
```

> 锁是一种更简洁的实现多线程加锁的方式

## 35. Callable接口 : 有返回的值的线程

和Runnable接口的区别就是在于实现的run方法上, Callable接口实现的线程call方法是可以有返回值的, 并且可以throws

```java
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "调用了Callable接口的call()方法";
    }
}
```

```java
public static void main(String[] args) throws Exception {
    MyCallable myCallable = new MyCallable();

    FutureTask<String> futureTask = new FutureTask<>(myCallable);

    Thread thread  = new Thread(futureTask);
    thread.start();

    System.out.println(futureTask.get());

}
```

## 36. 线程池

用来循环利用已经创建好了的线程

```java
ExecutorService es = Executors.newFixedThreadPool(2);
es.submit(new MyRunnable());
Future<String> fs = es.submit(new MyCallable());
System.out.println(fs.get());
```

## 37. Timer - 定时器

```java
1.概述:定时器
2.构造:
  Timer()
3.方法:
  void schedule(TimerTask task, Date firstTime, long period)  
                task:抽象类,是Runnable的实现类
                firstTime:从什么时间开始执行
                period: 每隔多长时间执行一次,设置的是毫秒值    
```

```java
public class Demo01Timer {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("金莲对涛哥说:涛哥,快起床了~~~");
            }
        },new Date(),2000L);
    }
}
```



## 38. 集合

### 各个集合的顶级接口 : Collection接口

```java
// 创建
Collection<E> 对象名 = new 实现类对象<E>();
<E>: 泛型, 只能写引用数据类型;
// 泛型的细节 : 等号前面的泛型必须写, 但是等号后面的泛型可以不写, jvm会自动推导

3.常用方法:
  boolean add(E e) : 将给定的元素添加到当前集合中(我们一般调add时,不用boolean接收,因为add一定会成功)
  boolean addAll(Collection<? extends E> c) :将另一个集合元素添加到当前集合中 (集合合并)
  void clear():清除集合中所有的元素
  boolean contains(Object o)  :判断当前集合中是否包含指定的元素
  boolean isEmpty() : 判断当前集合中是否有元素->判断集合是否为空
  boolean remove(Object o):将指定的元素从集合中删除
  int size() :返回集合中的元素个数。
  Object[] toArray(): 把集合中的元素,存储到数组中 
```

```java
Collection<Integer> collection = new ArrayList<>();
collection.add(1);
collection.add(2);

System.out.println(collection);//   [1, 2]

Collection<Integer> collection1 = new ArrayList<>();
collection1.add(3);
collection1.add(4);

collection.addAll(collection1);
System.out.println(collection);//  [1, 2, 3, 4]

collection.remove(1);
System.out.println(collection);//   [2, 3, 4]
System.out.println(collection.size());//    3
System.out.println(collection.isEmpty());//     false
System.out.println(collection.contains(123));//     false
System.out.println(collection.contains(2));//       true
collection.clear();
System.out.println(collection);//  []
```

### 迭代器

#### 基本使用

1. 主要作用 : 遍历集合

```java
ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

Iterator<Integer> iterator = list.iterator();
while (iterator.hasNext()) {
    int e = iterator.next();
    System.out.println(e);// 1 2 3 4 5
	}
```

> 尽量不要一次性next两次, 会有空指针的问题出现

#### 并发修改异常

```java
// 简单来说就是, 在遍历的途中, 为集合多加或删除了元素, 
// 这个时候就会报ConcurrentModificationException的并发修改异常错误
// 这个报错源于, 迭代器实际操作次数和预期操作次数不同

ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

Iterator<Integer> iterator = list.iterator();
while (iterator.hasNext()) {
    int e = iterator.next();
    if (e == 3) {
        list.add(6);
    }
    System.out.println(e);// 1 2 3 4 5
}
```

```java
ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

//        Iterator<Integer> iterator = list.iterator();
ListIterator<Integer> iterator = list.listIterator();
while (iterator.hasNext()) {
    int e = iterator.next();
    if (e == 3) {
        iterator.add(6);
    }
    System.out.println(e);// 1 2 3 4 5
```

> 概括的说, 就是直接给迭代器添加元素就不会产生歧义

### ArrayList 底层分析

```java
1.概述:ArrayList是List接口的实现类
2.特点:
  a.元素有序-> 按照什么顺序存的,就按照什么顺序取
  b.元素可重复
  c.有索引-> 可以利用索引去操作元素
  d.线程不安全
      
3.数据结构:数组       
4.常用方法:
  boolean add(E e)  -> 将元素添加到集合中->尾部(add方法一定能添加成功的,所以我们不用boolean接收返回值)
  void add(int index, E element) ->在指定索引位置上添加元素
  boolean remove(Object o) ->删除指定的元素,删除成功为true,失败为false
  E remove(int index) -> 删除指定索引位置上的元素,返回的是被删除的那个元素
  E set(int index, E element) -> 将指定索引位置上的元素,修改成后面的element元素
  E get(int index) -> 根据索引获取元素
  int size()  -> 获取集合元素个数
```

```java
1.ArrayList构造方法:
  a.ArrayList() 构造一个初始容量为十的空列表
  b.ArrayList(int initialCapacity) 构造具有指定初始容量的空列表 
      
2.ArrayList源码总结:
  a.不是一new底层就会创建初始容量为10的空列表,而是第一次add的时候才会创建初始化容量为10的空列表
  b.ArrayList底层是数组,那么为啥还说集合长度可变呢?
    ArrayList底层会自动扩容-> Arrays.copyOf    
  c.扩容多少倍?
    1.5倍
```

```java
ArrayList() 构造一个初始容量为十的空列表
=========================================
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
Object[] elementData; ->ArrayList底层的那个数组
    
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}   

=========================================
list.add("a");

public boolean add(E e) {
    modCount++;
    add(e, elementData, size);// e->要存的元素  elementData->集合数组,长度开始为0,size->0
    return true;
}

private void add(E e->元素, Object[] elementData->集合数组, int s->0) {
    if (s == elementData.length)
        elementData = grow();
    elementData[s] = e;
    size = s + 1;
}

private Object[] grow() {
    return grow(size + 1);
}

private Object[] grow(int minCapacity->1) {
    int oldCapacity = elementData.length;//0
    if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        int newCapacity = ArraysSupport.newLength(oldCapacity,
                minCapacity - oldCapacity, /* minimum growth */
                oldCapacity >> 1           /* preferred growth */);
        return elementData = Arrays.copyOf(elementData, newCapacity);
    } else {
        return elementData = new Object[Math.max(DEFAULT_CAPACITY->10, minCapacity->1)];
    }
}
==========================================
假设ArrayList中存了第11个元素,会自动扩容-> Arrays.copyOf

private Object[] grow(int minCapacity) {//11
    int oldCapacity = elementData.length;//10
    if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        int newCapacity(15) = ArraysSupport.newLength(oldCapacity->10,
                minCapacity - oldCapacity->1, /* minimum growth */
                oldCapacity >> 1 ->5          /* preferred growth */);
        return elementData = Arrays.copyOf(elementData, newCapacity);
    } else {
        return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
    }
}    


public static int newLength(int oldLength->10, int minGrowth->1, int prefGrowth->5) {
        // preconditions not checked because of inlining
        // assert oldLength >= 0
        // assert minGrowth > 0

        int prefLength = oldLength + Math.max(minGrowth, prefGrowth); // 15
        if (0 < prefLength && prefLength <= SOFT_MAX_ARRAY_LENGTH) {
            return prefLength;
        } else {
            // put code cold in a separate method
            return hugeLength(oldLength, minGrowth);
        }
}
```

> ArrayList<String>  list = new  ArrayList<String>() -> 现在我们想用都是new
>
> 但是将来开发不会想使用就new集合,都是调用一个方法,查询出很多数据来,此方法返回一个集合,自动将查询出来的数据放到集合中,我们想在页面上展示数据,遍历集合
>
> 而且将来调用方法,返回的集合类型,一般都是接口类型
>
> List<泛型> list = 对象.查询方法()

### linkedList

```java
1.概述:LinkedList是List接口的实现类
2.特点:
  a.元素有序
  b.元素可重复
  c.有索引 -> 这里说的有索引仅仅指的是有操作索引的方法,不代表本质上具有索引
  d.线程不安全
      
3.数据结构:双向链表      
    
4.方法:有大量直接操作首尾元素的方法
  - public void addFirst(E e):将指定元素插入此列表的开头。
  - public void addLast(E e):将指定元素添加到此列表的结尾。
  - public E getFirst():返回此列表的第一个元素。
  - public E getLast():返回此列表的最后一个元素。
  - public E removeFirst():移除并返回此列表的第一个元素。
  - public E removeLast():移除并返回此列表的最后一个元素。
  - public E pop():从此列表所表示的堆栈处弹出一个元素。
  - public void push(E e):将元素推入此列表所表示的堆栈。
  - public boolean isEmpty()：如果列表没有元素，则返回true。
```

### Collection集合工具类

```java
1.概述:集合工具类
2.特点:
  a.构造私有
  b.方法都是静态的
      
3.使用:类名直接调用
    
4.方法:
  static <T> boolean addAll(Collection<? super T> c, T... elements)->批量添加元素 
  static void shuffle(List<?> list) ->将集合中的元素顺序打乱
  static <T> void sort(List<T> list) ->将集合中的元素按照默认规则排序
  static <T> void sort(List<T> list, Comparator<? super T> c)->将集合中的元素按照指定规则排序 
```

```java
Collections.sort(list1);
```

```java
1.方法:static <T> void sort(List<T> list, Comparator<? super T> c)->将集合中的元素按照指定规则排序
    
2.Comparator比较器
  a.方法:
    int compare(T o1,T o2)
                o1-o2 -> 升序
                o2-o1 -> 降序    
```

```java
public class Student implements Comparable<Student>{
     @Override
    public int compareTo(Student o) {
        return this.getScore()-o.getScore();
    }
}
```

```java
Arrays中的静态方法:
static <T> List<T> asList(T...a) -> 直接指定元素,转存到list集合中
```

```java
public class Demo04Collections {
 public static void main(String[] args) {
     List<String> list = Arrays.asList("张三", "李四", "王五");
     System.out.println(list);
 }
}
```

### 泛型

#### 泛型类

```java
1.定义:
   public class 类名<E>{
       
   }

2.什么时候确定类型
  new对象的时候确定类型  
```

```java
public class MyArrayList <E>{
    //定义一个数组,充当ArrayList底层的数组,长度直接规定为10
    Object[] obj = new Object[10];
    //定义size,代表集合元素个数
    int size;

    /**
     * 定义一个add方法,参数类型需要和泛型类型保持一致
     *
     * 数据类型为E  变量名随便取
     */
    public boolean add(E e){
        obj[size] = e;
        size++;
        return true;
    }

    /**
     * 定义一个get方法,根据索引获取元素
     */
    public E get(int index){
        return (E) obj[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(obj);
    }
}
```

#### 泛型方法

```java
1.格式:
  修饰符 <E> 返回值类型 方法名(E e)
      
2.什么时候确定类型
  调用的时候确定类型
```

```java
public class ListUtils {
    //定义一个静态方法addAll,添加多个集合的元素
    public static <E> void addAll(ArrayList<E> list,E...e){
        for (E element : e) {
            list.add(element);
        }
    }

}
```

#### 泛型接口

```java
1.格式:
  public interface 接口名<E>{
      
  }
2.什么时候确定类型:
  a.在实现类的时候还没有确定类型,只能在new实现类的时候确定类型了 ->比如 ArrayList
  b.在实现类的时候直接确定类型了 -> 比如Scanner    
```

```java
public interface MyList <E>{
    public boolean add(E e);
}
```

#### 泛型通配符

```java
public static void method(ArrayList<?> list){
        for (Object o : list) {
            System.out.println(o);
        }
```

```java
1.作用:可以规定泛型的范围
2.上限:
  a.格式:<? extends 类型>
  b.含义:?只能接收extends后面的本类类型以及子类类型    
3.下限:
  a.格式:<? super 类型>
  b.含义:?只能接收super后面的本类类型以及父类类型  
```

```java
//上限  ?只能接收extends后面的本类类型以及子类类型
public static void get1(Collection<? extends Number> collection){

}

//下限  ?只能接收super后面的本类类型以及父类类型
public static void get2(Collection<? super Number> collection){

}
```

### HashSet

```java
1.概述:HashSet是Set接口的实现类
2.特点:
  a.元素唯一
  b.元素无序
  c.无索引
  d.线程不安全
3.数据结构:哈希表
  a.jdk8之前:哈希表 = 数组+链表
  b.jdk8之后:哈希表 = 数组+链表+红黑树
            加入红黑树目的:查询快
4.方法:和Collection一样
5.遍历:
  a.增强for
  b.迭代器
```

### LinkedHashSet

```java
1.概述:LinkedHashSet extends HashSet
2.特点:
  a.元素唯一
  b.元素有序
  c.无索引
  d.线程不安全
3.数据结构:
  哈希表+双向链表
4.使用:和HashSet一样
```

### HashSet的去重复过程

```java
1. 先比较哈希值, 如果不一样, 存;
2. 如果哈希值一样, 再比较内容, 如果不一样, 存
```

### HashSet存储自定义类型如何去重复

```java
1. 重写hashCode方法 : 让HashSet比较属性的哈希值;
2. 重写equeals方法 : 让HashSet比较属性的内容
```

```java
@Override
public int hashCode() {
return Objects.hash(name, age);
}
```

> 如果不重写equals方法, 那么不同的对象, 他们的哈希值肯定是不一样的, 但是比较内容的时候, equals调用的是Object的默认实现, 在比较的是两个对象的地址值, 所以即时对象的属性值一样, 也不能去重复

## 39. Map集合

```java
1.概述:HashMap是Map的实现类
2.特点:
  a.key唯一,value可重复 -> 如果key重复了,会发生value覆盖
  b.无序
  c.无索引
  d.线程不安全
  e.可以存null键null值
3.数据结构:
  哈希表
4.方法:
  V put(K key, V value)  -> 添加元素,返回的是
  V remove(Object key)  ->根据key删除键值对,返回的是被删除的value
  V get(Object key) -> 根据key获取value
  boolean containsKey(Object key)  -> 判断集合中是否包含指定的key
  Collection<V> values() -> 获取集合中所有的value,转存到Collection集合中
      
  Set<K> keySet()->将Map中的key获取出来,转存到Set集合中  
  Set<Map.Entry<K,V>> entrySet()->获取Map集合中的键值对,转存到Set集合中
```

```java
HashMap<String, String> map = new HashMap<>();
String Ret = map.put("001", "Tom");
System.out.println("Ret: " + Ret);// Ret: null
Ret = map.put("001", "Jerry");
System.out.println("Ret: " + Ret);// Ret: Tom
// key 重复，返回被覆盖的值

map.put(null, null);
System.out.println(map);

Ret = map.put(null, "null");
System.out.println("Ret: " + Ret);// Ret: null


System.out.println(map);
//        Ret = map.remove("001");
System.out.println("Ret: " + Ret);// Ret: Jerry
// key 存在，返回被删除的值

Collection<String> values = map.values();
System.out.println(values);// [null, Jerry] 
```

```java
1.概述:LinkedHashMap extends HashMap
2.特点:
  a.key唯一,value可重复 -> 如果key重复了,会发生value覆盖
  b.有序
  c.无索引
  d.线程不安全
  e.可以存null键null值
3.数据结构:
  哈希表+双向链表
4.使用:和HashMap一样      
```

```java
public class Demo02LinkedHashMap {
    public static void main(String[] args) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("八戒","嫦娥");
        map.put("涛哥","金莲");
        map.put("涛哥","三上");
        map.put("唐僧","女儿国国王");
        System.out.println(map);
    }
}
```

### Map遍历的方式

#### 获取key, 再根据key获取value

```java
// 根据 key 遍历
Set<String> set = map.keySet();
for (String Key : set) {
    System.out.println(Key + " : " + map.get(Key));
}
```

#### 直接获取键值对

```java
// 根据 Entry 遍历
Set<Map.Entry<String, String>> entries = map.entrySet();
for (Map.Entry<String, String> entry : entries) {
    System.out.println(entry.getKey() + " : " + entry.getValue());
}
```

### Map存储自定义对象时如何去重复

> 和Set一样, 重新实现equals和hashCode方法

### HashSet需要注意的点

```java
a.在不指定长度时,哈希表中的数组默认长度为16,HashMap创建出来,一开始没有创建长度为16的数组;
b.什么时候创建的长度为16的数组呢?在第一次put的时候,底层会创建长度为16的数组
c.哈希表中有一个数据加[加载因子]->默认为0.75(加载因子)->代表当元素存储到百分之75的时候要扩容了->2倍
d.如果对个元素出现了哈希值一样,内容不一样时,就会在同一个索引上以链表的形式存储,当链表长度达到8并且当前数组长度>=64时,链表就会改成使用红黑树存储
如果后续删除元素,那么在同一个索引位置上的元素个数小于6,红黑树会变回链表
e.加入红黑树目的:查询快
```

### 外面笔试时可能会问到的变量

```java
default_initial_capacity:HashMap默认容量  16
default_load_factor:HashMap默认加载因子   0.75f
threshold:扩容的临界值   等于   容量*0.75 = 12  第一次扩容
treeify_threshold:链表长度默认值,转为红黑树:8
min_treeify_capacity:链表被树化时最小的数组容量:64
```

### TreeSet

```java
1.概述:TreeSet是Set的实现类
2.特点:
  a.对元素进行排序
  b.无索引
  c.不能存null
  d.线程不安全
  e.元素唯一
3.数据结构:红黑树      
```

### TreeMap

```java
1.概述:TreeMap是Map的实现类
2.特点:
  a.对key进行排序
  b.无索引
  c.key唯一
  d.线程不安全
  e.不能存null
3.数据结构:红黑树      
```

## 40. File类

#### File静态成员

这两个静态成员是为了解决Window系统和Linux系统中, 路径的分隔符不同的问题 ( 分别为'\\' 和 '/'), 在两个系统中路径分隔符都是 ' ; ' 但是我们同样给了一个静态变量用于表示

```java 
static String pathSeparator:与系统有关的路径分隔符，为了方便，它被表示为一个字符串。
static String separator:与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串。  
```

```java
private static void file02() {
        String path1 = "E:\\Idea\\io";
        System.out.println(path1);
        System.out.println("==================");

        //要求代码写完,一次编写,到处运行
        String path2 = "E:"+File.separator+"Idea"+File.separator+"io";
        System.out.println(path2);
    }

    private static void file01() {
        //static String pathSeparator:与系统有关的路径分隔符，为了方便，它被表示为一个字符串。
        String pathSeparator = File.pathSeparator;
        System.out.println("pathSeparator = " + pathSeparator); //  ;
        //static String separator:与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串。
        String separator = File.separator;
        System.out.println("separator = " + separator); //  \
    }
```

#### File的构造方法

> 简单来说, 三种构造方法就是
>
> 1. (文件夹路径, 文件名)
> 2. (写入了文件夹路径的File对象, 文件名)
> 3. (文件路径)

```java
File(String parent, String child) 根据所填写的路径创建File对象
     parent:父路径
     child:子路径
File(File parent, String child)  根据所填写的路径创建File对象
     parent:父路径,是一个File对象
     child:子路径
File(String pathname)  根据所填写的路径创建File对象
     pathname:直接指定路径   
```

```java
public static void main(String[] args) {
        //File(String parent, String child) 根据所填写的路径创建File对象
        //parent:父路径
        //child:子路径
        File file1 = new File("E:\\Idea\\io", "1.jpg");
        System.out.println("file1 = " + file1);
        //File(File parent, String child)  根据所填写的路径创建File对象
        //parent:父路径,是一个File对象
        //child:子路径
        File parent = new File("E:\\Idea\\io");
        File file2 = new File(parent, "1.jpg");
        System.out.println("file2 = " + file2);
        //File(String pathname)  根据所填写的路径创建File对象
        //pathname:直接指定路径
        File file3 = new File("E:\\Idea\\io\\1.jpg");
        System.out.println("file3 = " + file3);
    }
```

> 细节:
>
> 我们创建File对象的时候,传递的路径可以是不存在的,但是传递不存在的路径后, 在执行操作的(比如写入)时候会报错

#### File的获取方法

```java
String getAbsolutePath() -> 获取File的绝对路径->带盘符的路径
String getPath() ->获取的是封装路径->new File对象的时候写的啥路径,获取的就是啥路径
String getName()  -> 获取的是文件或者文件夹名称
long length() -> 获取的是文件的长度 -> 文件的字节数   
```

#### File的创建方法

> 讲的是使用File类创建文件夹或者文件

```java
boolean createNewFile()  -> 创建文件
        如果要创建的文件之前有,创建失败,返回false
        如果要创建的文件之前没有,创建成功,返回true
    
boolean mkdirs() -> 创建文件夹(目录)既可以创建多级文件夹,还可以创建单级文件夹
        如果要创建的文件夹之前有,创建失败,返回false
        如果要创建的文件夹之前没有,创建成功,返回true
```

```java
/*boolean createNewFile()  -> 创建文件
        如果要创建的文件之前有,创建失败,返回false
        如果要创建的文件之前没有,创建成功,返回true*/
        File file1 = new File("E:\\Idea\\io\\1.txt");
        System.out.println("file1.createNewFile() = " + file1.createNewFile());

        /*boolean mkdirs() -> 创建文件夹(目录)既可以创建多级文件夹,还可以创建单级文件夹
        如果要创建的文件夹之前有,创建失败,返回false
        如果要创建的文件夹之前没有,创建成功,返回true*/
        File file2 = new File("E:\\Idea\\io\\haha\\heihei\\hehe");
        System.out.println("file2.mkdirs() = " + file2.mkdirs());
```

#### File类的删除方法

```java
boolean delete()->删除文件或者文件夹
    -> 返回的是否删除成功

注意:
  1.如果删除文件,不走回收站
  2.如果删除文件夹,必须是空文件夹,而且也不走回收站    
```

```java
private static void file03() {
    //boolean delete()->删除文件或者文件夹
    //File file1 = new File("E:\\Idea\\io\\1.txt");
    File file1 = new File("E:\\Idea\\io\\haha");
    System.out.println("file1.delete() = " + file1.delete());
}
```

#### File类的判断方法

```java
boolean isDirectory() -> 判断是否为文件夹 
boolean isFile()  -> 判断是否为文件
boolean exists()  -> 判断文件或者文件夹是否存在    
```

#### File的遍历方法

```java
String[] list() -> 遍历指定的文件夹,返回的是String数组 
File[] listFiles()-> 遍历指定的文件夹,返回的是File数组 ->这个推荐使用
    
细节:listFiles方法底层还是list方法
     调用list方法,遍历文件夹,返回一个Stirng数组,遍历数组,将数组中的内容一个一个封装到File对象中,然后再将File对象放到File数组中
```

#### 相对路径和绝对路径

- 在idea中写的相对路径,其实就是从模块名开始写

## 41. IO流

### IO流分类

```java
字节流:万能流,一切皆字节    
	字节输出流:  OutputStream 抽象类
    字节输入流:  InputStream 抽象类

字符流:专门操作文本文档
    字符输出流:Writer 抽象类
    字符输入流:Reader 抽象类
```

### OutputStream中的子类[FileOutputStream]

```java
 1.概述:字节输出流,OutputStream 是一个抽象类
        子类: FileOutputStream
 2.作用:往硬盘上写数据
     
 3.构造:
   FileOutputStream(File file) 
   FileOutputStream(String name)
       
 4.特点:
   a.指定的文件如果没有,输出流会自动创建
   b.每执行一次,默认都会创建一个新的文件,覆盖老文件 
       
 5.方法:
   void write(int b)  一次写一个字节
   void write(byte[] b)  一次写一个字节数组
   void write(byte[] b, int off, int len)  一次写一个字节数组一部分 
             b:写的数组
             off:从数组的哪个索引开始写
             len:写多少个
   void close()  -> 关闭资源              
```

```java
1.字节流的续写追加:
  FileOutputStream(String name, boolean append) 
                   append:true -> 会实现续写追加,文件不覆盖了
                       
2.换行:
  a.windows: \r\n -> 占2个字节   \n
  b.linux: \n
  c.mac os : \r
```

### InputStream子类[FileInputStream]的介绍以及方法的使用

```java
1.概述:字节输入流 InputStream ,是一个抽象类
      子类:FileInputStream
          
2.作用:读数据,将数据从硬盘上读到内存中来
    
3.构造:
  FileInputStream(File file)
  FileInputStream(String path)  
      
4.方法:
  int read()   一次读一个字节,返回的是读取的字节
  int read(byte[] b)  一次读取一个字节数组,返回的是读取的字节个数
  int read(byte[] b, int off, int len)  一次读取一个字节数组一部分,返回的是读取的字节个数
  void close()  关闭资源        
```

> 问题1:一个流对象,读完之后,就不要再读了;除非重新new一个新的对象
>
> 问题2:流关闭之后,流对象不能继续使用了

### 读取-1的问题

```java
每个文件末尾都有一个 "结束标志"
    
read()方法读到了文件的结束标志, 会返回-1
```

### 字节流实现图片复制代码实现

```java
简单来说就是边read, 边write
```

### 字符流

####  字节流的问题

不适用边读边看的情景,  实时显示中文字符流, 因为一个中文有多个字节, 会出现错误显示的情况

#### FileReader

>  字符流主要是用来操作的文本文档, 但是复制操作不要用字符流, 使用字节流

```java
1.概述:字符输入流 -> Reader -> 是一个抽象类
      子类:FileReader
2.作用:将文本文档中的内容读取到内存中来
3.构造:
  FileReader(File file)
  FileReader(String path)
4.方法:
  int read() -> 一次读取一个字符,返回的是读取字符对应的int值 
  int read(char[] cbuf) -> 一次读取一个字符数组,返回的是读取个数  
  int read(char[] cbuf, int off, int len) -> 一次读取一个字符数组一部分,返回的是读取个数
           cbuf:读取的数组
           off:从数组的哪个索引开始读
           len:读多少个
  void close() -> 关闭资源    
```

#### FileWriter

```java
1.概述:字符输出流 -> Writer -> 抽象类
  子类:FileWriter
2.作用:将数据写到文件中
3.构造:
  FileWriter(File file) 
  FileWriter(String fileName)     
  FileWriter(String fileName, boolean append) -> 追加,续写 
4.方法:
  void write(int c) -> 一次写一个字符 
  void write(char[] cbuf) 一次写一个字符数组 
  void write(char[] cbuf, int off, int len) 一次写一个字符数组一部分  
  void write(String str) 直接写一个字符串
  void flush()  :将缓冲区中的数据刷到文件中    
  void close()  关流
      
5.注意:FileWriterr底层自带一个缓冲区,我们写的数据会先保存到缓冲区中,所以我们需要将缓冲区中的数据刷到文件中      
```

> flush():将缓冲区中的数据刷到文件中,后续流对象还能继续使用
> close():先刷新后关闭,后续流对象不能使用了

#### IO异常的处理方式

```java
FileWriter fw = null;
try{
    fw = new FileWriter("module21\\3.txt");
    fw.write("你好");

}catch (Exception e){
    e.printStackTrace();
}finally {
    //如果fw不为null,证明new出来了所以需要close;相反不需要close
    if (fw!=null){
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```

> 需要注意的点, 就在finally中close, 但是需要先判断是不是null, 也就是对象有没有成功new出来的情况

#### JDK7之后io异常处理方式

```java
1.格式:
  try(IO对象){
      可能出现异常的代码
  }catch(异常类型 对象名){
      处理异常
  }
2.注意:
  以上格式处理IO异常,会自动关流
```

```java
public class Demo02Exception {
    public static void main(String[] args) {
        try(FileWriter fw = new FileWriter("module21\\4.txt");){
            fw.write("你好");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

```

### 缓冲字节流

```
出现的理由
	1. FileInputStream和FileOutputSream读取和写入字节流, 都是直接在内存的读写
	获取到了就会读取/写入 -> 造成了频繁的IO
	2. 而Buffer会在读/写的时候, 将字节流先存储在缓冲区数组(8192), 过程变成了
	读 : 磁盘 -> 内存
	写 : 获取 -> 内存 -> 磁盘
	最后一次性将内容IO出去
创建方法
	1. 先new FileinputStream fis
	2. new BufferedInputStream(fis)
    
使用方法
	和基本流一样使用
```

```java
package IOBufferModule;

import java.io.*;

public class Buffer {
    public static void main(String[] args) throws IOException {
        method1();
        method2();
    }

    private static void method2() throws IOException{
//         使用缓冲流
        long start = System.currentTimeMillis();

        FileInputStream fis = new FileInputStream("com.java.advanced/src/IOBufferModule/1.html");
        FileOutputStream fos = new FileOutputStream("com.java.advanced/src/IOBufferModule/2.html");

        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int len;
        while((len = bis.read()) != -1) {
            bos.write(len);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);

        bos.close();
        bis.close();
    }

    private static void method1() throws IOException {
//        使用基本流
        long start = System.currentTimeMillis();

        FileInputStream fis = new FileInputStream("com.java.advanced/src/IOBufferModule/1.html");
        FileOutputStream fos = new FileOutputStream("com.java.advanced/src/IOBufferModule/2.html");

        int len;
        while ((len = fis.read()) != -1) {
            fos.write(len);
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);

        fis.close();
        fos.close();
    }
}

```

> 细节:
>
> ​	1. 使用缓冲流的时候最后为什么不需要关闭基本流
>
> ​		缓冲流的close底层会自动关闭基本流
>
>  2. 缓冲流底层有数组, 都是在内存之间进行读写, 那么缓冲流读写的过程是怎么样的
>
>     硬盘 -> 输入流缓冲区 -> len变量 -> 输出流缓冲区 -> 磁盘

### 字符缓冲输入流\_BufferedReader

```
Reader : 
创建 :
	和BufferedInputStream一样, 通过传递子类, new出来的
使用 : 和FileReader一样. 多了个特殊方法
特殊方法 : readLine() -> 读取一行

Writer : 
创建 :
	和BufferedInputStream一样, 通过传递子类, new出来的
使用 : 和FileWriter一样. 多了个特殊方法
特殊方法 : newLine() -> 换行
```

```java
package com.java.buffer;

import java.io.*;

public class DemoBufferedWriter {
    public static void main(String[] args) throws IOException {
        write();
        read();
    }

    private static void read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("module01/src/com/java/buffer/1.txt"));

        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
        br.close();
    }

    private static void write() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("module01/src/com/java/buffer/1.txt"));
        writer.write("床前明月光");
        writer.newLine();
        writer.write("疑是地上霜");
        writer.newLine();

        writer.close();
    }

}

```

### 转换流

**InputStreamReader**

```
和FileReader一样使用, 可以按照指定的编码规则去读数据
构造:
	InputStreamReader(InputStream in, String charsetName)
											charsetName:指定编码, 不区分大小写
```

**OutputStreamWriter**

```
和FileWriter一样使用, 可以按照指定的编码规则去读数据
构造:
	OutputStreamWriter(OutputStream out,String charsetName)
											charsetName:指定编码, 不区分大小写
```

### 序列化流

1. 作用

   - 我们想让数据存储以后不是可以直接读, 比如用户信息, 我们并不希望可以直接通过点开文件就能直接读, 于是我们需要一种加密手段, 就像C中的二进制文件一样
   - 这种加密手段就是序列化

2. 序列化

   - 序列化 -> `ObjectOutputStream`, 反序列化 -> `ObjectInputStream`

   - 想要将对象序列化的文件中, 被序列化的对象需要实现Serializable接口

   ```java
   package com.java.objectOutputStream;
   
   import java.io.*;
   
   public class demoObjectOutputStream {
       public static void main(String[] args) throws IOException, ClassNotFoundException {
   //        method01();
           method02();
       }
   
       private static void method02() throws IOException, ClassNotFoundException {
           // 反序列化
           ObjectInputStream ois =
                   new ObjectInputStream(new FileInputStream("module01/src/com/java/objectOutputStream/demoObjectOutputStream.txt"));
   
           Person p = (Person) ois.readObject();
           System.out.println(p);
           ois.close();
       }
   
       private static void method01() throws IOException {
           // 序列化
           ObjectOutputStream oos =
                   new ObjectOutputStream(new FileOutputStream("module01/src/com/java/objectOutputStream/demoObjectOutputStream.txt"));
   
           Person p1 = new Person("张三", 23);
           oos.writeObject(p1);
           oos.close();
       }
   
   
   }
   ```

   ### 反序列化时出现的问题

   1. 序列化之后, 修改了对象, 但是没有重新序列化, 直接反序列化了, 就会出现了序列号冲突的问题, 本质上是一种不匹配的问题, 修改前和修改后的对象, 系统分配的序列号不同

      - 解决方法 -> 直接在对象里指定序列号
        ```java
        为对象加上属性
            public static final long serialVersionUID = 43L;
        ```

      > 这样只能解决由访问权限发生变化带来的序列号变化

   2. 循环获取多个序列化对象的时候循环次数和序列化的对象的个数不同的问题
      - 解决方法 : 将数据打包 -> 将数据放在集合中, 再将整个集合序列化 -> 将集合反序列化, 从集合中获取对象


# 42. Junit

## 42.1 各个注解的运行时机与限制

- `@Test` : 
  - 限制
    - 只能修饰无参无返回方法
    - 不能修饰静态方法
- `@Before`
  - 时机 
    - 每个`@Test`方法运行前都会运行一次的代码块
  - 限制
    - 和`@Test`一致
- `@After`
  - 时机
    - 每个`@Test`方法运行后都会运行一次的代码块
- `@BeforeClass`
  - 时机
    - 所有`@Test`运行之前, 会运行一次的代码块
  - 限制
    - 只能修饰静态方法

## 42.2 对于限制的解释

- 为什么要有这些修饰的限制, 可以从生命周期和修饰的级别的角度说明

- 修饰的级别

  - ```
    |-@BeforeClass => 类级别的初始化方法
    |------|-@Before => 方法级别的初始化方法
    |------|------|-@Test => 测试方法
    |------|-@After => 方法级别的清理
    |------|-@Before
    |------|------|-@Test
    |------|-@After
    |-@AfterClass => 类级别的清理
    ```

- 生命周期

  - 为了保证测试的隔离性, 每个测试用例会生成新的测试实例, 在里面执行测试代码
  - `@BeforeClass`在所有测试实例创建之前执行一次
  - `@Before, @Test, @After` 都在测试实例中被创建和执行
  - `@AfterClass`在所有测试实例执行完毕后执行一次

- 测试类中的属性

  - 假设测试类中有个属性int counter 初始化为 1;
  - 则对于每个新的测试实例, 新建的实例中的counter初始都是1
  - 所以`@Before, @Test, @After`需要是非静态的方法, 从而使得它修饰的函数能访问测试实例中属性
  - 但是对于`@BeforeClass`这个类级别的修饰, 如果我想有一个对于所有的测试实例都共享的数据, 这个时候很明显它只能是静态数据, 因为所有的测试实体都是被新创建的, 非静态的数据是无法相互之间传递的

# 43. 类加载器

## 43.1 概念介绍

类加载器是用来干什么的

- 情景 : 我们如果需要使用类, 需要将类从class文件中加载到内存中, 现在该由谁来加载类? 加载类的时机什么? 为什么需要这个工具?

- 由谁来加载类
  - 类加载器, 并不是直接由JVM从class文件中加载类, 而是由类加载器ClassLoader将类从磁盘中加载到内存中
- 为什么需要这个工具
  - 显然, 在最开始的时候将所有的类一股脑的加载下来并不是一个效率的做法, 就像C语言中直接引入所有的头文件一样愚蠢, 对于资源有极大的浪费
- 加载类的时机是什么
  - 在类被使用时候ClassLoader从class文件中加载到内存中

## 43.2 类加载器的层级和运行流程

- 情景 : Java将类和类加载器分为三个层级, 但是现在我们有了一个类, 我们如何知道我们该由哪个类加载器加载
  - BootStrap ClassLoader :  启动类加载器加载的系统性质的类
  - Extension ClassLoader : 拓展类加载器加载和处理安全加密类相关的拓展类
  - Application ClassLoader : 用户自己创建的类
- 运行流程
  1. 类 => AppLoader => ExtLoader => BootLoader
  2. 由最后一级BootLoader先寻找, 如果没有找到这个类, 这个类不是BootLoader管的, 返回信息
  3. 现在由ExtLoader寻找, 如果没有找到, 返回没有找到的信息
  4. 现在由AppLoader寻找
  5. BootLoader => ExtLoader => AppLoader => Class
- 这样的运行顺序保证了一定是底层系统性质的类覆盖上层的类, 保证了核心库的安全性
- 如果有一个类被查找到了, 会被它的那个层级的Loader缓存下来, 下次再需要访问的时候, 直接从换从中获取, 而不是重新从磁盘中读取
- 三个类加载器之间有逻辑上的层级关系和父子关系, 但是没有实际上的继承关系, 他们都继承自同意父类`ClassLoader`

## 43.3 获取ClassLoader

如何获取Classloader呢

- 通过`类.class.getClassLoader()`
- 通过`类.class.getClassLoader().getParent()`获取当前的类加载器的父类加载器

- 无法获取BootStrapLoader层级的类加载器, 返回的是null

```java
/**
 * 说明类的加载机制
 */
public class DemoClassLoader {
    @Test
    public void testBootstrapLoader(){
        //测试启动类加载器
        ClassLoader classLoader = String.class.getClassLoader();
        System.out.println("classLoader = " + classLoader);
        // 启动类的加载器是用c语言写的, 无法直接获取

        ClassLoader classLoader1 = DemoClassLoader.class.getClassLoader().getParent().getParent();
        System.out.println("classLoader1 = " + classLoader1);
    }

    @Test
    public void testExtensionLoader(){
        //测试拓展类加载器
        ClassLoader classLoader = DemoClassLoader.class.getClassLoader().getParent();
        System.out.println("classLoader = " + classLoader);
    }

    @Test
    public void testApplicationLoader(){
        //测试应用类加载器
        ClassLoader classLoader = DemoClassLoader.class.getClassLoader();
        System.out.println("classLoader = " + classLoader);
    }
}
```

# 44. 反射

## 44.1. 反射的意义

- 情景 : 现在我们需要开发大型的或者说集成度很高的框架供多元的使用者使用, 这个时候我们无法得知使用者的传进来的类, 和其中有哪些方法, 但是我们又确实需要知道这些信息, 比如这个类的类型, 这个类中的某些方法, 这个时候我们就能通过反射获取这些 "标签"
- 反射能做到的事, 获取类的类型, 类的成员变量, 类的成员方法, 类的构造方法

- 反射让代码更灵活并且拓展了代码泛用性, 但是因为是动态读取的, 在性能上并不是很好

## 44.2 反射获取内容

### 44.2.1 获取对象的Class类

首先获取目标类的Class<?>对象, 这个类中包含这个类的元数据(定义) : 类名, 父类, 实现的接口, 类修饰符, 方法, 字段等

该对象有三种获取方式

- 直接通过类名获取, MyClass.class获取
- myObject.getClass() : 通过对象实例获取
- Class.forName("com.example.MyClass"): 通过类的全限定名获取

```java
@Test
public void testGetClass() throws ClassNotFoundException {
    // 1. 通过getClass方法获取Class对象
    Person person = new Person();
    Class<? extends Person> aClass1 = person.getClass();
    System.out.println("aClass1 = " + aClass1);

    // 2. 通过每个类的.class静态成员, 这个是jvm为所有类型提供的一个成员
    Class<Person> aClass2 = Person.class;
    System.out.println("aClass2 = " + aClass2);

    // 3. static Class<?> forName获取
    Class<?> aClass3 = Class.forName("com.javaSE.Person");
    System.out.println("aClass3 = " + aClass3);
}
```

### 44.2.2 最通用的获取Class类的方式

通过类名.class获取

### 44.3.1 获取构造方法

#### 1. 获取所有公有的构造方法

- 流程

  1. 构建`Constructor[]`数组, 用于接收从`class.getConstructors()`的构造方法对象
  2. 通过`for`循环遍历获取

- code
  ```java
  @Test
  public void testGetConstructors(){
      Class<?> aClass = Person.class;
  
      Constructor<?>[] constructors = aClass.getConstructors();
      for (Constructor<?> constructor : constructors) {
          System.out.println(constructor);
      }
  }
  ```

#### 2. 获取特定的构造方法

- 流程
  1. 通过`Constructor`类型接收`class.getConstructor(Type p1....)`的返回
  2. 通过传递的参数类型来指定获取的构造函数比如填入()就是空参构造, 如果填入`(String.class)`就是用于获取参数列表是只传递了一个String的构造函数
  3. 通过构造方法, 能构建出新的实例, 通过调用`newInstance(参数...)`方法

- code
  ```java
  @Test
  public void testGetConstructor() throws Exception {
      Class<?> aClass = Person.class;
  
      Constructor<?> constructor = aClass.getConstructor(String.class, Integer.class);
      System.out.println(constructor);
  
      Person p = (Person) constructor.newInstance("fang",20);
      System.out.println("p = " + p);
  }
  ```

#### 3. 获取私有的方法

- 流程

  1. 将上面两者的`getConstructor`修改为`getDeclaredConstructor(s)`即可
  2. 如果还要能使用, 还需要`.setAccessible(true)`来解除私有的权限

- code
  ```java
  @Test
  public void testGetPrivateConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
      // 获取私有构造
      Class<?> aClass = Person.class;
  
      Constructor<?>[] dc = aClass.getDeclaredConstructors();
      for (Constructor<?> constructor : dc) {
          System.out.println(constructor);
      }
  
      // 解除私有构造的权限, 使之能使用私有构造
      Constructor<?> constructor = aClass.getDeclaredConstructor(String.class);
      constructor.setAccessible(true);
      Person p = (Person) constructor.newInstance("fung");
      System.out.println(p);
  }
  ```

### 44.4 通用的结构

- 接下来介绍获取(使用)成员方法, 获取和构建成员变量, 都是一样的结构, 关注的方面都是接下来的几个方面, 只会给出代码
  - 获取所有public
  - 获取特定的public
  - 获取所有的方法(包含private)
  - 获取特定的private
  - 以及用于接收的类型
  - 如何使用获取的内容

### 44.5 获取成员方法

- 使用`Method`接收

- 获取所有的public => `aClass.getMethods()`

- 获取特定的方法 => `aClass.getMethod("方法名", 这个方法需要传递的参数列表...)`

- 获取所有的方法 => `aClass.getDeclaredMethods()`

- 获取特定的方法(可以是private) => `aClass.getDeclaredMethod("方法名", 这个方法需要传递的参数列表...)`

- 如何使用

  - 使用Object接收返回的结果, 因为不知道类型
  - 调用`Method.invoke(对象, 参数列表)`来执行方法
  - 如果是私有方法同样需要调用`setAccessiable(true)`来解除私有的权限

-  code 
  ```java
  /**
       * 通过Class使用public成员方法
       */
  @Test
  public void testGetMethods() throws Exception{
      Class<Person> aClass = Person.class;
  
      // 获取所有的public方法
      Method[] methods = aClass.getMethods();
      for (Method method : methods) {
          System.out.println(method);
      }
  
      // 获取指定的public方法
      Method setName = aClass.getMethod("setName", String.class);
      System.out.println(setName);
      Method method = aClass.getMethod("getName");
      System.out.println("method = " + method);
      Person p = aClass.newInstance();
      Object o1 = setName.invoke(p, "fung");
      Object o = method.invoke(p);
      System.out.println("o1 = " + o1);
      System.out.println("o = " + o);
  }
  
  /**
       * 通过Class使用private方法
       */
  @Test
  public void testGetPrivateMethods() throws Exception{
      // 获取所有方法
      Class<Person> aClass = Person.class;
  
      Method[] declaredMethods = aClass.getDeclaredMethods();
      for (Method declaredMethod : declaredMethods) {
          System.out.println(declaredMethod);
      }
  
      // 获取指定的private方法并使用
      Person p = aClass.newInstance();
      Method privateMethod = aClass.getDeclaredMethod("eat");
      privateMethod.setAccessible(true);
      privateMethod.invoke(p);
  }
  ```

### 44.6 成员变量

- 获取所有public => `aClass.getFiedls()`
- 获取特定的public => `aClass.getField("字段名")`
- 获取所有的方法(包含private) => `aClass.getDeclaredFields()`
- 获取特定的private => `aClass.getDeclaredField("字段名")`
- 以及用于接收的类型 => `Field`
- 如何使用获取的内容
  - 通过`Field.set(对象实例, 值)`的形式赋值
  - 通过`Filed.get(对象)`的形式获取值

- code
  ```java
  /**
       * 通过Class获取成员变量
       */
  @Test
  public void testGetField() throws Exception{
      Class<Person> aClass = Person.class;
      Person p = aClass.newInstance();
  
      Field[] pubFields = aClass.getFields();
      Field[] allFields = aClass.getDeclaredFields();
      for (Field pubField : pubFields) {
          System.out.println("pubField = " + pubField);
      }
      for (Field allField : allFields) {
          System.out.println("allField = " + allField);
      }
  
      System.out.println("<==================>");
  
      Field name = aClass.getDeclaredField("name");
      name.setAccessible(true);
      System.out.println("name = " + name);
      name.set(p,"fang");
      Object o = name.get(p);
      System.out.println(o);
      Field publicField = aClass.getField("publicField");
      publicField.set(p,"test");
      Object o1 = publicField.get(p);
      System.out.println(o1);
  }
  ```

  





