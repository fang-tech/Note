## 1. JDK和JRE

```java
1. jdk : (Java Development Kit) : Java开发工具包
   javac : 编译工具
   jdb 调试工具
   java 运行工具
   jhat 内存分析工具
   ...
2. jre  (Java runtime Environment):java运行环境, 包含jvm以及后面开发用到的核心类库
   
3. jdk包含了jre包含了jvm
```

## 2. 编译和运行

```java
1. 将文件后缀改为java
    使用javac + 文件名.java编译java文件, 并生成可运行文件(.class文件)
    
2. 运行文件
    java + 文件名(没有后缀, 默认识别.class文件)
```

## 3. 文档注释

```java
使用/***/正常写文档注释后, JDK提供工具快速生成总结了所有文档注释的可视化界面文件
    
1. 书写文档注释
2. 使用javadoc -d api -suthor -version <filename>
```

## 4. java中的class的特殊命名规则

> 1. public类的类名需要和文件名一致
> 2. 非public类的类名可以不一致
> 3. 一个java文件中可以有多个class, 但是只有一个public class且它的命名和文件命名一致
> 4. 尽量保证不要在一个java文件中写多个class

## 5. println和print的区别

```java
println -> 输出后自带一个换行符
print -> 输出以后不带换行符
```

## 6. 数据类型在java中的内存占用

| 数据类型 | 内存占用 |
| :------: | :------: |
|   byte   |  1 byte  |
|  short   |  2 byte  |
|   int    |  4 byte  |
|   long   |  8 byte  |
|  float   |  4 byte  |
|  double  |  8 byte  |
|   char   |  2 byte  |
| boolean  |  1 byte  |

> java中的数据类型和c不一样, 并不会随着运行环境的不同而变化内存占用量

## 7. 命名规则

``` java
类 : 大驼峰命名 -> 每个单词首字母大写
方法和变量 : 小驼峰 -> 从第二个单词的首字母大写
```

## 8. IDEA package命名规范

```java
com.package.a
对于工程的结构的管理
```

## 9. 常见快捷键

| 快捷键               | 功能                                   |
| -------------------- | -------------------------------------- |
| `Alt+Enter`          | 导入包, 自动修正                       |
| `Ctrl+Y`             | 删除光标所在行                         |
| `Ctrl+D`             | 复制光标所在行的内容, 插入光标位置下面 |
| `Ctrl +Alt+L`        | 格式化代码                             |
| `Ctrl+Shift+/`       | 选中代码注释, 多行注释                 |
| `Alt+Shift+上下箭头` | 移动当前行的代码                       |

## 10. 三元运算符

```java
boolean表达式?表达式1:表达式2
先判断, 如果是true走?后面的表达式1, 否则走表达式2
```

## 11. 输出\_Scanner

> 概述: 相当于CPP的scanf
>
> 作用: 获取键盘输入
>
> 位置: java.util
>
> 使用: 
>
>  1. 导包
>
>  2. 创建对象
>     Scanner 变量名 = new Scanner(System.in)
>
>  3. 使用
>
>     Scanner变量.next() : 获取数据, 以空字符为结尾
>
>     nextLine(), 以换行符为结尾

## 12. Random随机数

```java
Random 变量名 = new Random()
变量名.nextInt()
 变量名.nextInt(10) -> 0~9
```

## 13. switch case

```java
switch (case):
	case 1:
		Statement;
		break;
	default:
		Statement;
		break;
```

## 14. 数组

```java
int[] data = {};
int[] data = new int[length];
//CPP
int data[] = {};

//获取数组的长度
data.length
    
```

## 15. 方法

> 1. 在java中的, 我们需要将所有的方法(函数)封装在类中, 如果我们还是想像函数式编程一样编辑出不依赖于某个类的方法, 给出public static

## 16.  匿名对象

```java
// 正常形式
Person p = new Person();
p.method;

// 匿名对象
new Person().method; 
// 用于只调用一个方法的时候
```

## 17. 对象的内存分配

> 1. 内存空间分为三部分
>    - 栈区
>    - 堆区
>    - 方法区
> 2. 我们创建一个对象, 并且调用其中方法的过程, 内存的过程上是:
>    1. 我们创建的对象会被存放在方法区, 并以class地址值的形式提供索引
>    2. 我们的语句被放在栈区中按顺序运行
>    3. new语句让我们在堆区中申请了一块内存用于存放实例化的对象, 并且将这个对象的地址赋给变量
>    4. 堆区中实例化的对象分为两部分
>       - 属性值 -> Type datas
>       - class地址值 -> 用于索引对象的方法
> 3. java的内存分配机制其实和CPP基本一致, 提供指针以供索引方法(函数)
> 4. java并不是没有指针, 而是没有引入指针的概念, 将指针隐藏了起来, 以提供更自动化的内存管理机制, 隐藏指针也是在其自动管理机制下必然的一个结果, 因为这个时候指针已经是一个不必要的类型了, 我们将不直接对内存进行操作, 内存的分配和销毁, 我们都由垃圾回收器自动管理

## 18. 局部变量和成员变量

> 1. 定义位置不同 
>    - 局部变量在类之外
>    - 成员变量定义在类中
> 2. 初始化值不同
>    - 成员变量是有初始值的, 所以我们可以直接使用
>    - 局部变量必须先初始化, 初始化之后才能使用

## 19. JavaBean

JavaBean是Java的一种编写类的思想和规范

1. 类必须是具体的(非抽象的)和公共的, public class 类名
2. 并且具有无参数的构造方法
3. 成员变量私有化, 并提供操作成员变量的set和get方法
4. 一般放在com.project.entity(pojo)模块下

## 20. JavaBean和数据库

JavaBean在未来的业务中, 都需要和数据库对应

1. 类	 -> 表
2. 属性 -> 列
3. 对象 -> 表中的每一行数据

## 21. 可变参数

Java中我们允许不规定接受某种类型的参数数量, 本质上是传数组

```java
public class Demo{
    public int add(int...args){
        int result
        for (int val:args){
            result+=args;
        }
		return result;
    }
}
```

> 可变参数必须放在参数列表的最后



