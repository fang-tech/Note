# Java类文件结构

## 一个简单的例子

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

## 魔数

.class文件的前四个字节 : `cafe babe`

## 版本号

紧跟着魔数的四个字节就是副版本号和主版本号`0000 0037`

## 常量池

紧接着版本号之后就是常量池, 里面存储了字段, 方法, 类, 接口的符号引用和常量

### 常量类型字面量

| Tag                   | tag对应的十六进制编码 | 类型              |
| --------------------- | --------------------- | ----------------- |
| CONSTANT_Integer_info | 0x03                  | int 类型字面量    |
| CONSTANT_Float_info   | 0x04                  | float 类型字面量  |
| CONSTANT_Long_info    | 0x05                  | long 类型字面量   |
| CONSTANT_Double_info  | 0x06                  | double 类型字面量 |

如`public final long ong = Long.MAX_VALUE;`, 在字节码中就是`05 7f ff ff ff ff ff ff ff`

### 字符串类型字面量

通过tag length stringArray的形式记录数据

| Tag                  | tag对应的十六进制编码 | 类型         |
| -------------------- | --------------------- | ------------ |
| CONSTANT_String_info | 0x08                  | 字符串字面量 |
| CONSTANT_Uft8_info   | 0x01                  | 字符串       |

CONSTANT_Uft8_info的直接存储stringArray, 也代表代码中的字符串常量比如"hello"

![CONSTANT_Uft8_info](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/jvm/class-file-jiegou-ae4f38c9-68fe-40ad-91c6-3e7fd360de05.png)

而CONSTANT_String_info存储索引, 通过索引找对应的CONSTANT_Uft8_info

s = "hello"

![img](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/jvm/class-file-jiegou-85af064d-5dc6-4187-b4f3-3501ccfc99b3.png)

### 方法类字段类型字面量

通过下面这种格式来记录数据

如果是

对于CONSTANT_Fieldref_info, CONSTANT_Classref_info, CONSTANT_Methodref_info

```txt
CONSTANT_*ref_info {
  u1 tag;
  u2 class_index;
  u2 name_and_type_index;
}
```

- tag : 标识符, CONSTANT_Fieldref_info为`0x09`, CONSTANT_Methodref_info 为`0x0a`, CONSTANT_InterfaceMethodref_info为`0x0b`
- class_index : 是这个字段 | 方法 | 接口方法所在的类信息的索引
- name_and_type_index : 为CONSTANT_NameAndType_info的常量池索引, 拿CONSTANT_Fieldref_info来说就是字段名和字段类型, 拿CONSTANT_Methodref_info就是方法名, 方法参数和返回值类型.

| tag                              | 十六级进制编码 | 类型               |
| -------------------------------- | -------------- | ------------------ |
| CONSTANT_MethodHandle_info       | 0x0f           | 方法句柄           |
| CONSTANT_MethodType_info         | 0x10           | 方法类型           |
| CONSTANT_InvokeDynamic_info      | 0x12           | 动态调用点         |
| CONSTANT_Fieldref_info           | 0x09           | 字段               |
| CONSTANT_Methodref_info          | 0x0a           | 普通方法           |
| CONSTANT_InterfaceMethodref_info | 0x0b           | 接口方法           |
| CONSTANT_Class_info              | 0x07           | 类或接口的全限定名 |

## 访问标记

紧跟着常量池后面的就是访问标记, 用来记录这个类的访问信息, 是不是final, 是不是abstract等访问标记

| 标记位 | 标识符         | 描述                                      |
| :----- | :------------- | :---------------------------------------- |
| 0x0001 | ACC_PUBLIC     | public 类型                               |
| 0x0010 | ACC_FINAL      | final 类型                                |
| 0x0020 | ACC_SUPER      | 调用父类的方法时，使用 invokespecial 指令 |
| 0x0200 | ACC_INTERFACE  | 接口类型                                  |
| 0x0400 | ACC_ABSTRACT   | 抽象类类型                                |
| 0x1000 | ACC_SYNTHETIC  | 标记为编译器自动生成的类                  |
| 0x2000 | ACC_ANNOTATION | 标记为注解类                              |
| 0x4000 | ACC_ENUM       | 标记为枚举类                              |
| 0x8000 | ACC_MODULE     | 标记为模块类                              |

比如一个public enum类型的访问标记就是0x4031

- 0x4000 : 枚举类
- 0x0010 : final类型
- 0x0020 : 现代JVM的标准标志
- 0x00001 : public访问修饰符

## 类索引, 父类索引, 接口索引

这部分用来确定类的继承关系, this_class为当前类索引, super_class为父类的索引, interface_class为接口

这三个会分别存储指向CONSTANT_Class_info的索引

## 字段表

一个类中的所有字段会存储在字段表中

```txt
field_info {
    u2 access_flag;
    us name_index;
    u2 description_index;
}
```

- access_flag为字段的访问标记
- name_index指向常量池中的CONSTANT_Utf8_info
- descrition_index为字段的描述类型索引, 也指向常量池中的CONSATNT_Utf8_info, 针对不同的数据类型, 有不同规则的描述信息
  - 对于基本数据类型而言, 会使用一个字符来表示, 比如I对应的就是int, B对应的就是byte
  - 对于引用数据类型来说, 使用`L ***;`来表示, 比如字符串类型就是`Ljava/lang/String;`
  - 对于数组来说, 会用一个前置的`[`表示, 比如一个String数组就是`[Ljava/lang/String;`

## 方法表

和字段表类似, 用来存储方法的信息, 比如方法名, 方法参数, 方法签名等

![img](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/jvm/class-file-jiegou-cbe6d025-84a5-4fea-821b-a4234f47c6cd.png)

## 属性表

记录额外信息, 比如常量值属性, 记录的就是final字段的编译时常量值, Code属性 : 方法的字节码指令, 操作数栈大小, 局部变量大小等

![img](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/jvm/class-file-jiegou-53f73e24-f060-45d2-8e29-34263c31847b.png)

![img](https://cdn.tobebetterjavaer.com/tobebetterjavaer/images/jvm/class-file-jiegou-e76339a8-0aab-418b-9722-4b3c8591693c.png)
