---
tags:
  - frontend
  - JavaWeb
  - XML
  - Java
---
# XML
> XML是EXtensible Markup Language的缩写, 翻译过来就是课拓展标记语言, 基本语法也是标签
- HTML实际上是XML加上HTML的约束后形成的语言, 算是XML的子集
- XML可以通过添加约束的方式, 规定配置文件中可以写什么和怎么写
## 1. 常见配置文件类型
1. properties文件, 例如druid的连接池使用的就是properties文件作为配置文件
2. XML文件, 如Tomcat
3. YAML文件, 如SprintBoot
4. json文件,  常用来做文件传输
### 1.1 properties配置文件
> 示例
```.properties
atguigu.jdbc.url=jdbc:mysql://localhost:3306/atguigu
atguigu.jdbc.driver=com.mysql.cj.jdbc.Driver
atguigu.jdbc.username=root
atguigu.jdbc.password=root
```
> 语法规范
- 由键值对组成
- 连接符是=号
- 每一行必须顶格写
### 1.2 XML文件
> 示例
```xml
<?xml version="1.0" encoding="UTF-8"?>
<students>
    <student>
        <name id = "1">张</name>
        <aeg>18</aeg>
    </student>
    <student>
        <name id = "2">Lee</name>
        <age>111</age>
    </student>
</students>  
```
> XML基本语法
- 第一行是默认的文档声明, 基本不需要改动, 但是必须是第一行顶格写
- 根标签
    - 只能有一个
- 属性
    - 属性必须有值
    - 属性值必须加引号, 单双都行
> XML约束
- 编写XML文件的时候, 需要根据XML约束中的规定来编写XML配置文件, 同时这些规定也能提示如何编写
- 主要有DTD, Schema两种
- Schema约束, 要求一个XML文档中, 所有标签, 所有属性, 都必须在约束中有明确的定义
### 1.3 DOM4J进行XML解析
#### 1.3.1 DOM4J使用步骤
1. 创建SAXReader对象
2. 将XML文件读取为inputSteam流[[javaAdvance#41. IO流]]
3. 将输入流传入SAXReader的read方法, 传回来的是Element节点对象
4. 获取Element的子节点, 获取节点名, 获取属性值, 获取指定的节点, 获取所有节点
#### 1.3.2 API介绍
1. 创建SAXReader对象

```java
SAXReader saxReader = new SAXReader();
```

2. 解析XML获取Document对象: 需要传入要解析的XML文件的字节输入流

```java
Document document = reader.read(inputStream);
```

3. 获取文档的根标签

```java
Element rootElement = documen.getRootElement()
```

4. 获取标签的子标签

```java
//获取所有子标签
List<Element> sonElementList = rootElement.elements();
//获取指定标签名的子标签
List<Element> sonElementList = rootElement.elements("标签名");
```

5. 获取标签体内的文本

```java
String text = element.getText();
```

6. 获取标签的某个属性的值

```java
String value = element.attributeValue("属性名");
```

#### 1.3.3 code
```java
package tomcat.com;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.List;

public class XML {
    public static void main(String[] args) throws Exception {
        SAXReader saxReader= new SAXReader();
        InputStream inputStream = new FileInputStream("src/tomcat/com/demo01.xml");
        Document xml = saxReader.read(inputStream);

        Element rootElement = xml.getRootElement();
        List<Element> sonElementList = rootElement.elements();

        // 指定标签名的子标签
        List<Element> sonElement = rootElement.elements("name");

        for (Element e : sonElement) {
            String text = e.getText();
            System.out.println(text);
        }

        for (Element e : sonElementList) {
            Element s = e.element("name");
            String text = s.getText();
            String attribute = s.attributeValue("id");
            System.out.println(text);
            System.out.println("id = " + attribute);
        }

    }
}

```


