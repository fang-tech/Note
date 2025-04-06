# 类字节码里有什么?

> 在初入JVM之际, 并且计算机的基础不够的前提下, 深入地了解JVM不会是一个明智的选择, 会在细节中迷失, 在旅途的开始, 更值得关注的是自子而下的结构以及各个部分的作用, 它们共同决定了什么
## 字节码文件的作用是什么

> 什么是字节码文件? 就是.class结尾的文件

- class文件是java代码和cpu能执行的指令之间的中间层
    - java代码经过编译器以后, 会被编译成class文件
    - class文件再被交给JVM读取执行, 而JVM会根据具体的平台将字节码翻译成具体的指令

- JVM已经不再支持Java了, 衍生出来了很多的基于JVM的编译语言
    - Kotlin : 通过kotlinc编译器 -> 字节码文件
    - Groovy : 通过grooxy编译器 -> 字节码文件
## Class文件的结构属性

> 我们只会分析一个简单的案例, 从而知道一个class文件大概有什么内容

### 属性结构介绍

- 魔数和class文件版本 : 每个class文件的前四个字节被称为魔数, 值为(0xcafebabe), 它的作用是确定这个文件是一个class文件, 起到一个标识符的作用, 再后两位分别是JDK的小版本号, 大版本号
- 常量池 : class文件的资源仓库, 存储的资源有 : 变量的属性, 类型, 名称; 方法的属性,, 类型, 名称等
- 访问标志 : 表示该类的属性和访问类型, 是接口还是类, 访问类型是不是public, 类型是否被标记为final
- 类索引, 父类索引, 接口索引 : class文件靠类索引, 父类索引, 接口索引这三项来确定这个类的继承关系
- 字段表属性 : 用于描述接口或类中声明的变量, 比如变量的作用域(public,...), 是否是静态变量, 是不是final, 数据类型等
- 方法表属性 : 描述的是方法的类型作用域等
- 属性表属性 : 描述的是特殊的属性. 比如字段表或方法表中的特殊的属性等

### 一个简单的例子

```java
public class Main {
    private int m;
    private String[][] str;

    public int inc() {
        return m + 1;
    }
}
```

- 反编译以后的结果
```class
Classfile /G:/Desktop/just4learn/src/main/java/com/func/JVM/Main.class
  Last modified 2025年3月31日; size 315 bytes
  SHA-256 checksum 542e6bc916453b34e2baebf6d02c704a8984922a0a7e42ebc902d174266d1109
  Compiled from "Main.java"
public class com.func.JVM.Main
  minor version: 0
  major version: 61
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #8                          // com/func/JVM/Main
  super_class: #2                         // java/lang/Object
  interfaces: 0, fields: 2, methods: 2, attributes: 1
Constant pool:
   #1 = Methodref          #2.#3          // java/lang/Object."<init>":()V
   #2 = Class              #4             // java/lang/Object
   #3 = NameAndType        #5:#6          // "<init>":()V
   #4 = Utf8               java/lang/Object
   #5 = Utf8               <init>
   #6 = Utf8               ()V
   #7 = Fieldref           #8.#9          // com/func/JVM/Main.m:I
   #8 = Class              #10            // com/func/JVM/Main
   #9 = NameAndType        #11:#12        // m:I
  #10 = Utf8               com/func/JVM/Main
  #11 = Utf8               m
  #12 = Utf8               I
  #13 = Utf8               str
  #14 = Utf8               [[Ljava/lang/String;
  #15 = Utf8               Code
  #16 = Utf8               LineNumberTable
  #17 = Utf8               inc
  #18 = Utf8               ()I
  #19 = Utf8               SourceFile
  #20 = Utf8               Main.java
{
  private int m;
    descriptor: I
    flags: (0x0002) ACC_PRIVATE

  private java.lang.String[][] str;
    descriptor: [[Ljava/lang/String;
    flags: (0x0002) ACC_PRIVATE

  public com.func.JVM.Main();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 14: 0

  public int inc();
    descriptor: ()I
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: getfield      #7                  // Field m:I
         4: iconst_1
         5: iadd
         6: ireturn
      LineNumberTable:
        line 19: 0
}
SourceFile: "Main.java"
```

### 字节码文件信息

- 前三行都是一些基础的元信息, 简单易懂
- public class com.func.JVM.Main : 说明该类的全限定类名
- minor version : 0 小的版本号是0
- major version : 61 大的版本号是17()

