---
tags:
  - frontend
  - JavaWeb
  - Tomcat
  - Deployment
---
^Tomcat
# Tomcat
## 1. Tomcat的作用
- 一个服务器软件, 用于部署一个完整的Web应用
## 2. Tomcat服务器

### 2.1 简介
> Tomcat是Apache 软件基金会（Apache Software Foundation）的Jakarta 项目中的一个核心项目，由Apache、Sun 和其他一些公司及个人共同开发而成。最新的Servlet 和JSP 规范总是能在Tomcat 中得到体现，因为Tomcat 技术先进、性能稳定，而且免费，因而深受Java 爱好者的喜爱并得到了部分软件开发商的认可，成为目前比较流行的Web 应用服务器。
### 2.2 安装

> 版本

- 版本：企业用的比较广泛的是8.0和9.0,目前比较新正式发布版本是Tomcat10.0, Tomcat11仍然处于测试阶段。
 - **10和之前的版本使用的API接口不同**, 所以8和9之间是可以移植的, 但是10和之前的无法相互移植, 虽然移植Tomcat是一件很蠢的事情
- JAVAEE 版本和Servlet版本号对应关系 [Jakarta EE Releases](https://jakarta.ee/release/)
    

|**SERVLET** VERSION|EE VERSION|
|---|---|
|6.1|Jakarta EE ?|
|6.0|Jakarta EE 10|
|5.0|Jakarta EE 9/9.1|
|4.0|JAVA EE 8|
|3.1|JAVA EE 7|
|3.1|JAVA EE 7|
|3.0|JAVAEE 6|

- Tomcat 版本和Servlet版本之间的对应关系
    

|**SERVLET** VERSION|**TOMCAT** VERSION|**JDK** VERSION|
|---|---|---|
|6.1|11.0.x|17 and later|
|6.0|10.1.x|11 and later|
|5.0|10.0.x (superseded)|8 and later|
|4.0|9.0.x|8 and later|
|3.1|8.5.x|7 and later|
|3.1|8.0.x (superseded)|7 and later|
|3.0|7.0.x (archived)|6 and later (7 and later for WebSocket)|

> 下载

- Tomcat官方网站：[http://tomcat.apache.org/](http://tomcat.apache.org/ "http://tomcat.apache.org/")
> 安装

1. 正确安装JDK并配置JAVA_HOME(以JDK17为例 [https://injdk.cn](https://injdk.cn/)中可以下载各种版本的JDK)
2. 解压tomcat到非中文无空格目录
3. 点击bin/startup.bat启动
4. 处理dos窗口日志中文乱码问题: 修改conf/logging.properties,将所有的UTF-8修改为GBK
## 3. Tomcat目录及测试
- bin：该目录下存放的是二进制可执行文件，如果是安装版，那么这个目录下会有两个exe文件：tomcat10.exe、tomcat10w.exe，前者是在控制台下启动Tomcat，后者是弹出GUI窗口启动Tomcat；如果是解压版，那么会有startup.bat和shutdown.bat文件，startup.bat用来启动Tomcat，但需要先配置JAVA_HOME环境变量才能启动，shutdawn.bat用来停止Tomcat；
    
- conf：这是一个非常非常重要的目录，这个目录下有四个最为重要的文件：
    
    - **server.xml：配置整个服务器信息。例如修改端口号。默认HTTP请求的端口号是：8080**
        
    - tomcat-users.xml：存储tomcat用户的文件，这里保存的是tomcat的用户名及密码，以及用户的角色信息。可以按着该文件中的注释信息添加tomcat用户，然后就可以在Tomcat主页中进入Tomcat Manager页面了；
        
        ```xml
           <tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
              version="1.0">	
                	<role rolename="admin-gui"/>
                	<role rolename="admin-script"/>
                	<role rolename="manager-gui"/>
                	<role rolename="manager-script"/>
                	<role rolename="manager-jmx"/>
                	<role rolename="manager-status"/>
                	<user 	username="admin" 
                			password="admin" 
                			roles="admin-gui,admin-script,manager-gui,manager-script,manager-jmx,manager-status"
                	/>
            </tomcat-users>
         ```
        
        web.xml：部署描述符文件，这个文件中注册了很多MIME类型，即文档类型。这些MIME类型是客户端与服务器之间说明文档类型的，如用户请求一个html网页，那么服务器还会告诉客户端浏览器响应的文档是text/html类型的，这就是一个MIME类型。客户端浏览器通过这个MIME类型就知道如何处理它了。当然是在浏览器中显示这个html文件了。但如果服务器响应的是一个exe文件，那么浏览器就不可能显示它，而是应该弹出下载窗口才对。MIME就是用来说明文档的内容是什么类型的！
        
    - context.xml：对所有应用的统一配置，通常我们不会去配置它。
        
- lib：Tomcat的类库，里面是一大堆jar文件。如果需要添加Tomcat依赖的jar文件，可以把它放到这个目录中，当然也可以把应用依赖的jar文件放到这个目录中，这个目录中的jar所有项目都可以共享之，但这样你的应用放到其他Tomcat下时就不能再共享这个目录下的jar包了，所以建议只把Tomcat需要的jar包放到这个目录下；
    
- logs：这个目录中都是日志文件，记录了Tomcat启动和关闭的信息，如果启动Tomcat时有错误，那么异常也会记录在日志文件中。
    
- temp：存放Tomcat的临时文件，这个目录下的东西可以在停止Tomcat后删除！
    
- **webapps：存放web项目的目录，其中每个文件夹都是一个项目**；如果这个目录下已经存在了目录，那么都是tomcat自带的项目。其中ROOT是一个特殊的项目，在地址栏中访问：[http://127.0.0.1:8080](http://127.0.0.1:8080/)，没有给出项目目录时，对应的就是ROOT项目.[http://localhost:8080/examples](http://localhost:8080/examples)，进入示例项目。其中examples"就是项目名，即文件夹的名字。
    
- work：运行时生成的文件，最终运行的文件都在这里。通过webapps中的项目生成的！可以把这个目录下的内容删除，再次运行时会生再次生成work目录。当客户端用户访问一个JSP文件时，Tomcat会通过JSP生成Java文件，然后再编译Java文件生成class文件，生成的java和class文件都会存放到这个目录下。
    
- LICENSE：许可证。
    
- NOTICE：说明文件。
## 4. WEB项目的标准结构
> 一个标准的可以用于发布的WEB项目标准结构如下

![[1.png]]
- app  本应用根目录
    + static 非必要目录,约定俗成的名字,一般在此处放静态资源 ( css  js  img)
    + WEB-INF  必要目录,必须叫WEB-INF,受保护的资源目录,浏览器通过url不可以直接访问的目录
        + classes     必要目录,src下源代码,配置文件,编译后会在该目录下,web项目中如果没有源码,则该目录不会出现
          + lib             必要目录,项目依赖的jar编译后会出现在该目录下,web项目要是没有依赖任何jar,则该目录不会出现
          + web.xml   必要文件,web项目的基本配置文件. 较新的版本中可以没有该文件,但是学习过程中还是需要该文件 
    + index.html  非必要文件,index.html/index.htm/index.jsp为默认的欢迎页
^  Tomcat部署
## 5. IDEA部署-运行项目
### 2.6.1 IDEA关联本地Tomcat

> 可以在创建项目前设置本地tomcat,也可以在打开某个项目的状态下找到settings

![[images/1681457611053.png]]

> 找到 Build,Execution,Eeployment下的Application Servers ,找到+号

![[images/1681457711914.png]]

> 选择Tomcat Server

![[images/1681457800708.png]]

> 选择tomcat的安装目录

![[images/1681457879937.png]]

> 点击ok

![[images/1681457921094.png]]

> 关联完毕

![[images/1681458031957.png]]

### 2.6.2 IDEA创建web工程

> 推荐先创建一个空项目,这样可以在一个空项目下同时存在多个modules,不用后续来回切换之前的项目,当然也可以忽略此步直接创建web项目

![[images/1681458194939.png]]
![[images/1681458273381.png]]

> 检查项目的SDK,语法版本,以及项目编译后的输出目录

![[images/1681458343921.png]]

![[images/1681458393871.png]]

> 先创建一个普通的JAVA项目

![[images/1681458485837.png]]
> 检查各项信息是否填写有误

![[images/1681458599545.png]]

> 创建完毕后,为项目添加Tomcat依赖

![[images/1681458857830.png]]



![[images/1681458897017.png]]

![[images/1681458939400.png]]

> 选择modules,添加  framework support



![[images/1681458672258.png]]

> 选择Web Application 注意Version,勾选  Create web.xml

![[images/1681459007273.png]]

> 删除index.jsp ,替换为 index.html

![[images/1681459080873.png]]



![[images/1681459147133.png]]

> 处理配置文件

+ 在工程下创建resources目录,专门用于存放配置文件(都放在src下也行,单独存放可以尽量避免文件集中存放造成的混乱)
+ 标记目录为资源目录,不标记的话则该目录不参与编译

![[images/1681461443278.png]]

+ 标记完成后,显示效果如下

![[images/1681461513406.png]]

> 处理依赖jar包问题

+ 在WEB-INF下创建lib目录
+ 必须在WEB-INF下,且目录名必须叫lib!!!
+ 复制jar文件进入lib目录

![[images/1681461788411.png]]

+ 将lib目录添加为当前项目的依赖,后续可以用maven统一解决

![[images/1681461846178.png]]

![[images/1681461881121.png]]

+ 环境级别推荐选择module 级别,降低对其他项目的影响,name可以空着不写



![[images/1681461923761.png]]

+ 查看当前项目有那些环境依赖

![[images/1681463867295.png]]

![[images/1681462179671.png]]

+ 在此位置,可以通过-号解除依赖

![[images/1681462247973.png]]

### 2.6.3 IDEA部署-运行web项目

> 检查idea是否识别modules为web项目并存在将项目构建成发布结构的配置

+ 就是检查工程目录下,web目录有没有特殊的识别标记

![[images/1681462523901.png]]

+ 以及artifacts下,有没有对应 _war_exploded,如果没有,就点击+号添加

![[images/1681462584524.png]]

> 点击向下箭头,出现 Edit Configurations选项

![[images/1681462645070.png]]

> 出现运行配置界面

![[images/1681462710108.png]]



> 点击+号,添加本地tomcat服务器

![[images/1681462754191.png]]

> 因为IDEA 只关联了一个Tomcat,红色部分就只有一个Tomcat可选

![[images/1681462798933.png]]

> 选择Deployment,通过+添加要部署到Tomcat中的artifact

![[images/1681463011546.png]]

> applicationContext中是默认的项目上下文路径,也就是url中需要输入的路径,这里可以自己定义,可以和工程名称不一样,也可以不写,但是要保留/,我们这里暂时就用默认的

![[images/1681463049807.png]]

> 点击apply 应用后,回到Server部分. After Launch是配置启动成功后,是否默认自动打开浏览器并输入URL中的地址,HTTP port是Http连接器目前占用的端口号

![[images/1681463212587.png]]

> 点击OK后,启动项目,访问测试

+ 绿色箭头是正常运行模式
+ "小虫子"是debug运行模式

![[images/1681463386274.png]]

+ 点击后,查看日志状态是否有异常

![[images/1681463361795.png]]

+ 浏览器自动打开并自动访问了index.html欢迎页

![[images/1681520068936.png]]

> 工程结构和可以发布的项目结构之间的目录对应关系

![[images/1681464081226.png]]

> IDEA部署并运行项目的原理

+ idea并没有直接进将编译好的项目放入tomcat的webapps中
+ idea根据关联的tomcat,创建了一个tomcat副本,将项目部署到了这个副本中
+ idea的tomcat副本在C:\用户\当前用户\AppData\Local\JetBrains\IntelliJIdea2022.2\tomcat\中
+ idea的tomcat副本并不是一个完整的tomcat,副本里只是准备了和当前项目相关的配置文件而已
+ idea启动tomcat时,是让本地tomcat程序按照tomcat副本里的配置文件运行
+ idea的tomcat副本部署项目的模式是通过conf/Catalina/localhost/*.xml配置文件的形式实现项目部署的

![[images/1681521240438.png]]
