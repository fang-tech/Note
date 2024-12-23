---
tags:
  - JavaWeb
  - backend
  - Servlet
  - 含有八股内容
  - Java
---
# Servlet
## 1. Servlet简介
- 一个运行在tomcat这样的服务器软件容器中的程序, 能够接受响应请求, 协同调度功能, 响应数据的一个Web应用控制器
- 是运行在服务端(Tomcat)的Java小程序, 从代码层面来讲就是一系列的接口, 是sun公司定义访问动态资源的规范
## 2. Servlet开发流程

> 步骤1 : 先完成前端发送请求的form
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<form method="get" action="userServlet">
    用户名:<input type="text" name="username" /> <br>
    <input  type="submit" value="校验"/>
</form>

</body>
</html>
```

> 步骤 2 : 完成Java业务逻辑, 处理接收与返回
- 在Java代码中完成业务, Step :
    1. 实现Servlet接口, 可以通过继承HttpServlet实现
    2. 重写service方法, 其中的参数是`(HttpServletRequest req, resp)`
    3. 获取请求中的参数
    4. 业务逻辑处理
    5. 设置响应内容
```java
package com.f.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
        String username = req.getParameter("username");

        if ("funtianyu".equals(username)){
            resp.getWriter().write("NO");
        } else {
            resp.getWriter().write("YES");
        }

    }
}

```

> 步骤 3 : 在Web.xml中完成路径的映射和补全html中的action中的url路径

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--给UserServlet起别名, 用于做映射-->
    <servlet>
        <servlet-name>userServlet</servlet-name>
        <servlet-class>com.f.servlet.UserServlet</servlet-class>
    </servlet>

    <!--做路径映射-->
    <servlet-mapping>
        <servlet-name>userServlet</servlet-name>
        <url-pattern>/userServlet</url-pattern>
    </servlet-mapping>
</web-app>
```

> 通过设置servlet标签, 将一个serv-name和Java业务逻辑代码绑定
> 再通过servlet-mapping标签, 将一个serv-name和访问路径绑定
> 设置完访问路径后再将这个路径填入html中的action

## 3. Servlet注解方法配置
- 主要是用于简化繁琐的web.xml的配置, 可以在Servlet对象上通过添加注解的方式, 便捷配置Servlet
```java
@WebServlet(
    name="xxx",  // servlet-name
    urlPartterns == value == "url", // url-parttern
    loadOnStartup = int
)
```

## 4. Servlet生命周期
### 4.1 简介与测试
- 一个Servlet对象有四个阶段
    - 构造 => 在访问到对应的URL的时候执行 : 1次
    - 初始化 => 构造完毕以后就执行初始化 : 1次
    - 执行服务 => 在每次请求的时候 : 无数次
    - 销毁 => 在关闭tomcat的时候最后调用 : 1次

> 测试代码
```java
package com.f.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LifeCycle extends HttpServlet {
    public LifeCycle(){
        System.out.println("构造方法");
    }
    @Override
    public void init() throws ServletException {
        System.out.println("初始化方法");
    }

    @Override
    public void destroy() {
        System.out.println("销毁方法()");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service()");
    }
}
```

> \<load-on-startup>参数 : 如果值是正整数, 那么这个Servlet对象会在Tomcat启动的时候就执行构造和初始化, 如果有多个对象设定了这个值, 则会根据按大小, 从小到大以此执行
```xml
    <servlet>
        <servlet-name>lifeCycle</servlet-name>
        <servlet-class>com.f.servlet.LifeCycle</servlet-class>

        <load-on-startup>100</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>lifeCycle</servlet-name>
        <url-pattern>/lifeCycle</url-pattern>
    </servlet-mapping>
```

### 4.2 总结
- Servlet对象在容器中是单例的
- 容器可以处理并发的用户请求, 每个请求在容器中都会开启一个线程
- 所以多个线程可能同时使用相同的Servlet对象, 故我们不要轻易在定义一些容易经常发生修改的成员变量, 会有线程冲突
## 5. Servlet继承结构
### 5.1 继承结构和如何查阅源码
> 继承结构 : HttpServlet => GenericServlet => Servlet(interface)

> 如何查阅源码
- 从结构开始, 去查阅它的实现类中对于各个方法的实现, 并理解在这一层实现的功能
### 5.2 Servlet.class
> Servlet源码
```java
public interface Servlet {
    void init(ServletConfig var1) throws ServletException;

    ServletConfig getServletConfig();

    void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

    String getServletInfo();

    void destroy();
}
```

### 5.3 GenericServlet.class
> `init(ServletConfig var1)的重写`
```java
// 有参的实现
public void init(ServletConfig config) throws ServletException {
        this.config = config; 
        this.init();
    }

// 无参的实现
public void init() throws ServletException {
    }
```
- 实现的内容
    - 设置config, 并执行无参的init
    - 无参的`init()`用于重写, 这样我们就不用重新写加载config这个操作了
---
> ` ServletConfig getServletConfig();`
```java
public ServletConfig getServletConfig() {
        return this.config;
    }
```
- 简单的返回config参数
---
> ` void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;`
```java
 public abstract void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;
```
- 没有实现
---
> `String getServletInfo();`
```java
public String getServletInfo() {
        return "";
    }
```
- 返回空
---
> `void destroy();`
```java
public void destroy() {
    }
```
-  消极实现
---
> 总结
- 实现了基础的配置和初始化, service留作下一层实现
### 5.4 HttpServlet.class
> ` public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException`
```java
public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest)req;
            response = (HttpServletResponse)res;
        } catch (ClassCastException var6) {
            throw new ServletException(lStrings.getString("http.non_http"));
        }

        this.service(request, response);
    }
```
- 完成了对传入参数从父类 => 子类的类型转变
- 并将调用用于接收子类类型的service方法
---
>` protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException `

```java
protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        long lastModified;
        if (method.equals("GET")) {
            lastModified = this.getLastModified(req);
            if (lastModified == -1L) {
                this.doGet(req, resp);
            } else {
                long ifModifiedSince;
                try {
                    ifModifiedSince = req.getDateHeader("If-Modified-Since");
                } catch (IllegalArgumentException var9) {
                    ifModifiedSince = -1L;
                }

                if (ifModifiedSince < lastModified / 1000L * 1000L) {
                    this.maybeSetLastModified(resp, lastModified);
                    this.doGet(req, resp);
                } else {
                    resp.setStatus(304);
                }
            }
        } else if (method.equals("HEAD")) {
            lastModified = this.getLastModified(req);
            this.maybeSetLastModified(resp, lastModified);
            this.doHead(req, resp);
        } else if (method.equals("POST")) {
            this.doPost(req, resp);
        } else if (method.equals("PUT")) {
            this.doPut(req, resp);
        } else if (method.equals("DELETE")) {
            this.doDelete(req, resp);
        } else if (method.equals("OPTIONS")) {
            this.doOptions(req, resp);
        } else if (method.equals("TRACE")) {
            this.doTrace(req, resp);
        } else {
            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[]{method};
            errMsg = MessageFormat.format(errMsg, errArgs);
            resp.sendError(501, errMsg);
        }

    }
```
- 主要功能, 验证方法, 并执行对应的doxxx方法
---
> `doXXX()方法`
```java
// dorGet
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = lStrings.getString("http.method_get_not_supported");
        this.sendMethodNotAllowed(req, resp, msg);
    }
// doPost
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = lStrings.getString("http.method_post_not_supported");
        this.sendMethodNotAllowed(req, resp, msg);
    }
```
- 无论传入的内容返回405界面
---
> 总结
- 默认的service方法会无论方法都去执行返回405界面
- 所以我们需要重写service方法, 这样才能正常显示
- 但是如果我们重写service方法,  我们会失去service的一些功能, 还有种做法是重写doGet等do方法

## 6. ServletConfig
### 6.1 ServletConfig的使用
> Servlet是什么?
- 为Servlet提供初始参数配置的对象, 每个Servlet对象都有一个自己ServletConfig对象
- 会在Servlet的init方法中将config传入
---
> 基本的使用
- 使用步骤 : 
    - 在web.xml或者@WebServlet注解中传入参数
    - 在Servlet中获取
-  四个方法

| 方法名                  | 作用                                                         |
| ----------------------- | ------------------------------------------------------------ |
| getServletName()        | 获取\<servlet-name>HelloServlet\</servlet-name>定义的Servlet名称 |
| getServletContext()     | 获取ServletContext对象                                       |
| getInitParameter()      | 获取配置Servlet时设置的『初始化参数』，根据名字获取值        |
| getInitParameterNames() | 获取所有初始化参数名组成的Enumeration对象                    |
> 示例
```java
@WebServlet(
        name="ServletA",
        urlPatterns = "/servletA",
        initParams = {@WebInitParam(name="paramA", value = "valueA"), @WebInitParam(name="paramB", value = "valueB")}
)
public class ServletA extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = getServletName();
        String value = getInitParameter("paramA");
        System.out.println("servletName = " + name);
        System.out.println("paramsA :" + value);

        Enumeration<String> parameterNames = getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String pname = parameterNames.nextElement();
            String pvalue = getInitParameter(pname);

            System.out.println(pname + " : " + pvalue);
        }
    }
}
```

> 使用web.xml配置的时候
```xml
  <servlet>
       <servlet-name>ServletA</servlet-name>
       <servlet-class>com.atguigu.servlet.ServletA</servlet-class>
       <!--配置ServletA的初始参数-->
       <init-param>
           <param-name>param1</param-name>
           <param-value>value1</param-value>
       </init-param>
       <init-param>
           <param-name>param2</param-name>
           <param-value>value2</param-value>
       </init-param>
   </servlet>

    <servlet-mapping>
        <servlet-name>ServletA</servlet-name>
        <url-pattern>/servletA</url-pattern>
    </servlet-mapping>
```
### 6.2 ServletConfig
> 用来干什么的?
- 是所有的在同一个容器中的Servlet对象所可以共同使用的ServletConfig, 像是全局的config
> 示例
```java
@WebServlet(
        name = "servletB",
        urlPatterns = "/servletB"
)
public class ServletB extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取ServletContext对象
        ServletContext servletContext = this.getServletContext();

        String valueA = servletContext.getInitParameter("name1");
        System.out.println("name1 : " + valueA);

        Enumeration<String> names = servletContext.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = servletContext.getInitParameter(name);
            System.out.println(name + " = " + value);
        }
    }
}
```
### 6.3 ServletContext其他重要的API
> 获取一个指向项目部署位置下的某个文件/目录的磁盘真实路径的API

 `servletContext.getRealPath(filename/dircname)`
 - 实现了动态获取项目运行的实际路径的功能

> 获取项目的上下文路径

`String contextPath = servletContext.getContextPath();`
- 可以解决后端的实现中的路径问题

> 域对象相关的API
- 域对象 : 用于存储和传递数据的对象,不同的域对象代表不同的共享数据的范围
- ServletContext代表应用是最大的域, 可以在本应用实现数据的共享和传递
- webapp的三大域对象是应用域, 会话域, 请求域

| API                                         | 功能解释       |
| ------------------------------------------- | ---------- |
| void setAttribute(String key,Object value); | 向域中存储/修改数据 |
| Object getAttribute(String key);            | 获得域中的数据    |
| void removeAttribute(String key);           | 移除域中的数据    |
## 7. HttpServletRequest

### 7.1 HttpServletRequest简介

> HttpServletRequest是什么

- HttpServletRequest是一个接口,其父接口是ServletRequest
    
- HttpServletRequest是Tomcat将请求报文转换封装而来的对象,在Tomcat调用service方法时传入
    
- HttpServletRequest代表客户端发来的请求,所有请求中的信息都可以通过该对象获得
    
### 7.2 HttpServletRequest常见API

> HttpServletRequest怎么用

- 获取请求行信息相关(方式,请求的url,协议及版本)
    
| API                           | 功能解释            |
| ----------------------------- | --------------- |
| StringBuffer getRequestURL(); | 获取客户端请求的url     |
| String getRequestURI();       | 获取客户端请求项目中的具体资源 |
| int getServerPort();          | 获取客户端发送请求时的端口   |
| int getLocalPort();           | 获取本应用在所在容器的端口   |
| int getRemotePort();          | 获取客户端程序的端口      |
| String getScheme();           | 获取请求协议          |
| String getProtocol();         | 获取请求协议及版本号      |
| String getMethod();           | 获取请求方式          |

- 获得请求头信息相关
    

|API|功能解释|
|---|---|
|String getHeader(String headerName);|根据头名称获取请求头|
|Enumeration<String> getHeaderNames();|获取所有的请求头名字|
|String getContentType();|获取content-type请求头|

- 获得请求参数相关
    

|API|功能解释|
|---|---|
|String getParameter(String parameterName);|根据请求参数名获取请求单个参数值|
|String[] getParameterValues(String parameterName);|根据请求参数名获取请求多个参数值数组|
|Enumeration<String> getParameterNames();|获取所有请求参数名|
|Map<String, String[]> getParameterMap();|获取所有请求参数的键值对集合|
|BufferedReader getReader() throws IOException;|获取读取请求体的字符输入流|
|ServletInputStream getInputStream() throws IOException;|获取读取请求体的字节输入流|
|int getContentLength();|获得请求体长度的字节数|

- 其他API
    

|API|功能解释|
|---|---|
|String getServletPath();|获取请求的Servlet的映射路径|
|ServletContext getServletContext();|获取ServletContext对象|
|Cookie[] getCookies();|获取请求中的所有cookie|
|HttpSession getSession();|获取Session对象|
|void setCharacterEncoding(String encoding) ;|设置请求体字符集|

## 8. HttpServletResponse

### 8.1 HttpServletResponse简介

> HttpServletResponse是什么

- HttpServletResponse是一个接口,其父接口是ServletResponse
    
- HttpServletResponse是Tomcat预先创建的,在Tomcat调用service方法时传入
    
- HttpServletResponse代表对客户端的响应,该对象会被转换成响应的报文发送给客户端,通过该对象我们可以设置响应信息
    
### 8.2 HttpServletResponse的常见API

> HttpServletRequest怎么用

- 设置响应行相关
    

|API|功能解释|
|---|---|
|void setStatus(int code);|设置响应状态码|

- 设置响应头相关
    

|API|功能解释|
|---|---|
|void setHeader(String headerName, String headerValue);|设置/修改响应头键值对|
|void setContentType(String contentType);|设置content-type响应头及响应字符集(设置MIME类型)|

- 设置响应体相关
    

|API|功能解释|
|---|---|
|PrintWriter getWriter() throws IOException;|获得向响应体放入信息的字符输出流|
|ServletOutputStream getOutputStream() throws IOException;|获得向响应体放入信息的字节输出流|
|void setContentLength(int length);|设置响应体的字节长度,其实就是在设置content-length响应头|

- 其他API
    

|API|功能解释|
|---|---|
|void sendError(int code, String message) throws IOException;|向客户端响应错误信息的方法,需要指定响应码和响应信息|
|void addCookie(Cookie cookie);|向响应体中增加cookie|
|void setCharacterEncoding(String encoding);|设置响应体字符集|

> MIME类型

- MIME类型,可以理解为文档类型,用户表示传递的数据是属于什么类型的文档
    
- 浏览器可以根据MIME类型决定该用什么样的方式解析接收到的响应体数据
    
- 可以这样理解: 前后端交互数据时,告诉对方发给对方的是 html/css/js/图片/声音/视频/... ...
    
- tomcat/conf/web.xml中配置了常见文件的拓展名和MIMIE类型的对应关系
    
- 常见的MIME类型举例如下
    

| 文件拓展名                           | MIME类型                 |
| ------------------------------- | ---------------------- |
| .html                           | text/html              |
| .css                            | text/css               |
| .js                             | application/javascript |
| .png /.jpeg/.jpg/... ...        | image/jpeg             |
| .mp3/.mpe/.mpeg/ ... ...        | audio/mpeg             |
| .mp4                            | video/mp4              |
| .m1v/.m1v/.m2v/.mpe/... ...<br> | video/mpeg             |
## 9. 请求转发和响应重定向

### 9.1 概述

> 什么是请求转发和响应重定向

- 请求转发和响应重定向是web应用中间接访问项目资源的两种手段,也是Servlet控制页面跳转的两种手段
    
- 请求转发通过HttpServletRequest实现,响应重定向通过HttpServletResponse实现
    
- 请求转发生活举例: 张三找李四借钱,李四没有,李四找王五,让王五借给张三
    
- 响应重定向生活举例:张三找李四借钱,李四没有,李四让张三去找王五,张三自己再去找王五借钱
    
### 9.2 请求转发(含八股)

> 请求转发特点(背诵)

- 请求转发通过HttpServletRequest对象获取请求转发器实现
    
- 请求转发是服务器内部的行为,对客户端是屏蔽的
    
- 客户端只发送了一次请求,客户端地址栏不变
    
- 服务端只产生了一对请求和响应对象,这一对请求和响应对象会继续传递给下一个资源
    
- 因为全程只有一个HttpServletRequset对象,所以请求参数可以传递,请求域中的数据也可以传递
    
- 请求转发可以转发给其他Servlet动态资源,也可以转发给一些静态资源以实现页面跳转
    
- 请求转发可以转发给WEB-INF下受保护的资源
    
- 请求转发不能转发到本项目以外的外部资源
    

> 请求转发测试代码

![[images/1682323740343.png]]

- ServletA
    

 ```java
 @WebServlet("/servletA")  
 public class ServletA extends HttpServlet {  
     @Override  
     protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
         //  获取请求转发器  
         //  转发给servlet  ok  
         RequestDispatcher  requestDispatcher = req.getRequestDispatcher("servletB");  
         //  转发给一个视图资源 ok  
         //RequestDispatcher requestDispatcher = req.getRequestDispatcher("welcome.html");  
         //  转发给WEB-INF下的资源  ok  
         //RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/view1.html");  
         //  转发给外部资源   no  
         //RequestDispatcher requestDispatcher = req.getRequestDispatcher("http://www.atguigu.com");  
         //  获取请求参数  
         String username = req.getParameter("username");  
         System.out.println(username);  
         //  向请求域中添加数据  
         req.setAttribute("reqKey","requestMessage");  
         //  做出转发动作  
         requestDispatcher.forward(req,resp);  
     }  
 }
```

- ServletB
    

 ```java
 @WebServlet("/servletB")  
 public class ServletB extends HttpServlet {  
     @Override  
     protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
         // 获取请求参数  
         String username = req.getParameter("username");  
         System.out.println(username);  
         // 获取请求域中的数据  
         String reqMessage = (String)req.getAttribute("reqKey");  
         System.out.println(reqMessage);  
         // 做出响应  
         resp.getWriter().write("servletB response");          
     }  
 }
```

- 打开浏览器,输入以下url测试
    

 http://localhost:8080/web03_war_exploded/servletA?username=atguigu

### 9.3 响应重定向(含八股)

> 响应重定向特点(背诵)

- 响应重定向通过HttpServletResponse对象的sendRedirect方法实现
    
- 响应重定向是服务端通过302响应码和路径,告诉客户端自己去找其他资源,是在服务端提示下的,客户端的行为
    
- 客户端至少发送了两次请求,客户端地址栏是要变化的
    
- 服务端产生了多对请求和响应对象,且请求和响应对象不会传递给下一个资源
    
- 因为全程产生了多个HttpServletRequset对象,所以请求参数不可以传递,请求域中的数据也不可以传递
    
- 重定向可以是其他Servlet动态资源,也可以是一些静态资源以实现页面跳转
    
- 重定向不可以到给WEB-INF下受保护的资源
    
- 重定向可以到本项目以外的外部资源
    

> 响应重定向测试代码

![[images/1682323740343.png]]

- ServletA
    

 ​  
 ```java
@WebServlet("/servletA")  
 public class ServletA extends HttpServlet {  
     @Override  
     protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
         //  获取请求参数  
         String username = req.getParameter("username");  
         System.out.println(username);  
         //  向请求域中添加数据  
         req.setAttribute("reqKey","requestMessage");  
         //  响应重定向  
         // 重定向到servlet动态资源 OK  
         resp.sendRedirect("servletB");  
         // 重定向到视图静态资源 OK  
         //resp.sendRedirect("welcome.html");  
         // 重定向到WEB-INF下的资源 NO  
         //resp.sendRedirect("WEB-INF/views/view1");  
         // 重定向到外部资源  
         //resp.sendRedirect("http://www.atguigu.com");  
     }  
 }
```

- ServletB
    

 ```java
@WebServlet("/servletB")  
 public class ServletB extends HttpServlet {  
     @Override  
     protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
         // 获取请求参数  
         String username = req.getParameter("username");  
         System.out.println(username);  
         // 获取请求域中的数据  
         String reqMessage = (String)req.getAttribute("reqKey");  
         System.out.println(reqMessage);  
         // 做出响应  
         resp.getWriter().write("servletB response");  
 ​  
     }  
 }
```
## 10. 乱码问题
> 归根结底就是字符集的匹配问题
###  10.1 乱码问题
> 乱码问题产生的根本原因是什么

1. 数据的编码和解码使用的不是同一个字符集
2. 使用了不支持某个语言文字的字符集

> 各个字符集的兼容性



![[images/1682326867396.png]]

+ 由上图得知,上述字符集都兼容了ASCII
+ ASCII中有什么? 英文字母和一些通常使用的符号,所以这些东西无论使用什么字符集都不会乱码



### 10.1.1 HTML乱码问题

> 设置项目文件的字符集要使用一个支持中文的字符集

+ 查看当前文件的字符集

![[images/1682325817829.png]]

+ 查看项目字符集 配置,将Global Encoding 全局字符集,Project Encoding 项目字符集, Properties Files 属性配置文件字符集设置为UTF-8

![[images/1682326229063.png]]

> 当前视图文件的字符集通过<meta charset="UTF-8"> 来告知浏览器通过什么字符集来解析当前文件

``` html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    中文
</body>
</html>
```

### 10.1.2 Tomcat控制台乱码

> 在tomcat10.1.7这个版本中,修改 tomcat/conf/logging.properties中,所有的UTF-8为GBK即可

+ 修改前

![[images/1681443202115.png]]

+ 修改后

![[images/1681443273573.png]]

+ 重启测试

![[images/1681443314432.png]]

![[images/1682325615922.png]]

> sout乱码问题,设置JVM加载.class文件时使用UTF-8字符集

+ 设置虚拟机加载.class文件的字符集和编译时使用的字符集一致

![[images/1695189588009.png]]



### 10.1.3 请求乱码问题

#### 10.1.3.1 GET请求乱码

> GET请求方式乱码分析

+ GET方式提交参数的方式是将参数放到URL后面,如果使用的不是UTF-8,那么会对参数进行URL编码处理
+ HTML中的 <meta charset='字符集'/> 影响了GET方式提交参数的URL编码
+ tomcat10.1.7的URI编码默认为 UTF-8
+ 当GET方式提交的参数URL编码和tomcat10.1.7默认的URI编码不一致时,就会出现乱码

> GET请求方式乱码演示

+ 浏览器解析的文档的<meta charset="GBK" /> 

![[images/1682385870660.png]]

+ GET方式提交时,会对数据进行URL编码处理 ,是将GBK 转码为 "百分号码"

![[images/1682385997927.png]]

+ tomcat10.1.7 默认使用UTF-8对URI进行解析,造成前后端使用的字符集不一致,出现乱码

![[images/1682386110151.png]]

> GET请求方式乱码解决

+ 方式1  :设置GET方式提交的编码和Tomcat10.1.7的URI默认解析编码一致即可 (推荐)

![[images/1682386298048.png]]



![[images/1682386374464.png]]



+ 方式2 : 设置Tomcat10.1.7的URI解析字符集和GET请求发送时所使用URL转码时的字符集一致即可,修改conf/server.xml中 Connecter 添加 URIEncoding="GBK"  (不推荐)

![[images/1682386551684.png]]

![[images/1682386611945.png]]

#### 10.1.3.2 POST方式请求乱码

> POST请求方式乱码分析

+ POST请求将参数放在请求体中进行发送
+ 请求体使用的字符集受到了<meta charset="字符集"/> 的影响
+ Tomcat10.1.7 默认使用UTF-8字符集对请求体进行解析
+ 如果请求体的URL转码和Tomcat的请求体解析编码不一致,就容易出现乱码

> POST方式乱码演示

+ POST请求请求体受到了<meta charset="字符集"/> 的影响

![[images/1682387258428.png]]

+ 请求体中,将GBK数据进行 URL编码

![[images/1682387349916.png]]

+ 后端默认使用UTF-8解析请求体,出现字符集不一致,导致乱码

![[images/1682387412704.png]]

> POST请求方式乱码解决

+ 方式1 : 请求时,使用UTF-8字符集提交请求体 (推荐)

![[images/1682387836615.png]]

![[images/1682387857587.png]]

+ 方式2 : 后端在获取参数前,设置解析请求体使用的字符集和请求发送时使用的字符集一致 (不推荐)

![[images/1682388026978.png]]





### 10.1.3 响应乱码问题

> 响应乱码分析

+ 在Tomcat10.1.7中,向响应体中放入的数据默认使用了工程编码 UTF-8
+ 浏览器在接收响应信息时,使用了不同的字符集或者是不支持中文的字符集就会出现乱码

> 响应乱码演示

+ 服务端通过response对象向响应体添加数据



![[images/1682388204239.png]]

+ 浏览器接收数据解析乱码

![[images/1682388599014.png]]



> 响应乱码解决

+ 方式1 : 手动设定浏览器对本次响应体解析时使用的字符集(不推荐)
    + edge和 chrome浏览器没有提供直接的比较方便的入口,不方便

+ 方式2: 后端通过设置响应体的字符集和浏览器解析响应体的默认字符集一致(不推荐)

![[images/1682389063225.png]]



方式3: 通过设置content-type响应头,告诉浏览器以指定的字符集解析响应体(推荐)

![[images/1682389263627.png]]



![[images/1682389317234.png]]
## 11. 工程开发实践
> 说明 :  这部分内容, 并不做实际的实现, 只针对其中用到的部分新的jar包和API和整体的结构以及规范说明, 因为有需要做的项目, 实现他的简陋的项目, 浪费时间
### 11.1 项目结构
#### 11.1.1 Module层
> 实现的内容
- 对于数据库中的数据的增删改查
    - dao层 => 实现对数据的增删改查操作类
        - impl层 : 对于接口的实现
        - 接口的定义
        - BaseDao : 基础类
- 实体类
    - pojo层 => 对于实体类的实现
- 服务
    - service => 对于服务的实现
        - 相关接口的定义
        - impl
            - 对于服务的实现
---
#### 11.1.2 Controller层
> 实现的内容
- 接收前端请求和给出响应
    - BaseController => 基础操作的实现
    - Controller类
---
#### 11.1.3 View层
> 实现的内容
- 页面的显示传递以及接收后端的数据
    - static等
### 11.2 代码说明
#### 11.2.1 pojo层与Lombok
- 需要实现序列化接口
- 避免繁琐的getter setter等的设置, 使用Lombok可以集成实现
```java
@AllArgsConstructor // 全参构造
@NoArgsConstructor // 无参构造
@Data // getter setter equals hashcode toString
public class Sysuser implements Serializable
```

#### 11.2.2 加密工具类的使用
```java
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public final class MD5Util {
    public static String encrypt(String strSrc) {
        try {
            char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错!!!")
        }
    }
}
```
