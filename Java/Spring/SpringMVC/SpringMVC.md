# SpringMVC

## 1. RESTful与注解上的细节

### 1.1 类上的ResultMapping注解

- 类上的ResultMapping注解, 会让类中方法的resultMapping从类上的路径开始
- 不过这个时候注意返回的html路径问题, 访问的根路径是templates文件夹, 如果在里面有子文件夹, 注意写全文件夹

- User
```java
@RequestMapping("/product")
@Controller
public class UserController {
    @RequestMapping("/detail")
    public String toDetail(){
        return "/product/detail";
    }
}
```

- Product
```java
@RequestMapping("/user")
@Controller
public class ProductController {
    @RequestMapping("/detail")
    public String toDetail(){
        return "/user/detail";
    }
}
```

### 1.2 value属性详解

#### value接受的是String数组

也就是允许多个url映射到同一个控制器上
```java
@RequestMapping("/user")
@Controller
public class ProductController {
    @RequestMapping({"/detail", "/detail1"})
    public String toDetail(){
        return "/user/detail";
    }
}
```

#### Ant风格

> 其实就是路径支持模糊匹配

- \* : 代表任何0 ~ N个任意字符
- \** : 代表任何0 ~ N个任意字符, 包括路径分隔符\\ (只能放在url末尾, 并且左右两边不能有除了\\以外的任意字符)
- ? : 代表一个任意字符

- \?
```java
    @RequestMapping("/x?z/detail")
    public String toDetail(){
        System.out.println("模糊匹配页面正在解析");
        return "/ant";
    }
```

- \*
```java
    @RequestMapping("/x*z/detail")
    public String toDetail(){
        System.out.println("模糊匹配页面正在解析");
        return "/ant";
    }
```

- \**
```java
    @RequestMapping("/xz/detail/**")
    public String toDetail(){
        System.out.println("模糊匹配页面正在解析");
        return "/ant";
    }
```

### 1.3 RESTful

> 实际上是一种将提交表单的信息放在URL上的一种规范, 和传统的login?username=123&passwd=231的形式不一样, RESTful直接将信息用路径分隔符分隔开 login/123/231, 这是一种更现代和常用的方法

- 使用步骤
    1.在ReauestMapping上添加用{}包裹起来的占位符, 
    2.在方法的形参上添加@PathVatiable("占位符"), 以此将表单中的值传递给 参数

```java
    @RequestMapping("/table/{username}/{passwd}")
    public String toTable(@PathVariable("username") String username,
                          @PathVariable("passwd") String passwd){
        System.out.println(username);
        System.out.println(passwd);
        return "RESTful";
    }
```

### 1.4 表单提交

- 可以指定Mapping的method属性来指定表单提交的方式
- SpringMVC也提供了更简洁的方式来实现, 只需要指定对应的mapping就行

```java
    @PostMapping("/login")
    public String doPost(){
        return "login";
    }
```

```html
<form th:action="@{/login}" method="get">
    用户名 : <input type="text" name="username"/><br/>
    密码 :  <input type="text" name="passwd"/> <br/>
            <input type="submit" value="登录"/>
</form>
```

- **GetMapping**：要求前端必须发送get请求
    
- **PutMapping**：要求前端必须发送put请求
    
- **DeleteMapping**：要求前端必须发送delete请求
    
- **PatchMapping**：要求前端必须发送patch请求


>  如果方法错误, 会返回405请求方法错误
### 1.5 GET和POST

#### 什么时候用POST, 什么时候用GET

- 如果需要像服务器提交数据, 则使用POST
- 需要从服务器获取数据, 则使用GET方法
- 如果需要上传大数据量的内容, 只能使用POST方法, 因为他有请求体这个可以承载大数据量的载体
- 需要上传敏感数据的时候, 也推荐使用POST方法, 它不会将表单回显在URL上

#### GET方法的特殊之处

- GET方法会先走浏览器的缓存, 如果没有才会走服务器
    - 这个时候如果不想走缓存, 可以通过在URL最后添加上时间戳来解决
- 大部分不指明的请求都是GET请求, 比如获取网页图标之类的
- POST不支持缓存


### 1.6 RequestMapping注解的Param属性

> HTTP code 400 : 请求参数的格式不正确导致的, 接下来就会出现

- 通过指定RequestMapping中的params参数, 能实现对于提交内容的格式的限制, 下面是四种常用的用法
- 需要注意的是, 下面的四种情况, 相互之间是有交集的, 有交集的时候的行为不可预测, 故不要写出这样的代码

```java
    // 只有两个参数都填入的时候才符合格式
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET}, params = {"username", "passwd"})
    public String testParam(){
        System.out.println("两个参数都填入了");
        return "login";
    }

    // username 需要为空, passwd不能为空
    @RequestMapping(value =  "/login", method = {RequestMethod.POST, RequestMethod.GET}, params = {"!username", "passwd"})
    public String testParam2(){
        System.out.println("只有密码填入的时候");
        return "login";
    }

    // username 需要=admin, passwd为root的时候
    @RequestMapping(value =  "/login", method = {RequestMethod.POST, RequestMethod.GET}, params = {"username=admin", "passwd=root"})
    public String testParam3(){
        System.out.println("admin用户登录");
        return "login";
    }
    // username 需要!=admin, passwd为root的时候
    @RequestMapping(value =  "/login", method = {RequestMethod.POST, RequestMethod.GET}, params = {"username!=admin", "passwd"})
    public String testParam4(){
        System.out.println("非admin用户登录");
        return "login";
    }
```

### 1.7 RequestMapping注解的headers属性

#### 认识headers属性

headers和params原理相同，用法也相同。 当前端提交的请求头信息和后端要求的请求头信息一致时，才能映射成功。 请求头信息怎么查看？在chrome浏览器中，F12打开控制台，找到Network，可以查看具体的请求协议和响应协议。在请求协议中可以看到请求头信息，例如： ![image.png](https://cdn.nlark.com/yuque/0/2024/png/21376908/1710402265257-e2b13b8d-52e7-4088-842a-4246be3e866a.png#averageHue=%23fcfbfa&clientId=u9e0c3730-20d1-4&from=paste&height=366&id=u0b591958&originHeight=366&originWidth=987&originalType=binary&ratio=1&rotation=0&showTitle=false&size=32052&status=done&style=shadow&taskId=ue61420fa-9ede-48ed-a4cf-230b74daaa8&title=&width=987) 请求头信息和请求参数信息一样，都是键值对形式，例如上图中：

- Referer: [http://localhost:8080/springmvc/](http://localhost:8080/springmvc/) 键是Referer，值是[http://localhost:8080/springmvc/](http://localhost:8080/springmvc/)
    
- Host: localhost:8080 键是Host，值是localhost:8080
    

![标头.jpg](https://cdn.nlark.com/yuque/0/2023/jpeg/21376908/1692002570088-3338946f-42b3-4174-8910-7e749c31e950.jpeg#averageHue=%23f9f8f8&clientId=uc5a67c34-8a0d-4&from=paste&height=78&id=DCl7T&originHeight=78&originWidth=1400&originalType=binary&ratio=1&rotation=0&showTitle=false&size=23158&status=done&style=shadow&taskId=u98709943-fd0b-4e51-821c-a3fc0aef219&title=&width=1400)

#### headers属性的用法

和params属性是一样的用法

注意：如果前端提交的请求头信息，和后端要求的请求头信息不一致，则出现404错误！！！

## 2.  从请求中获取值

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
</head>
<body>
<h3>用户注册</h3>
<hr>
<form th:action="@{/register}" method="post">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    性别：
        男 <input type="radio" name="sex" value="1">
        女 <input type="radio" name="sex" value="0">
        <br>
    爱好：
        抽烟 <input type="checkbox" name="hobby" value="smoke">
        喝酒 <input type="checkbox" name="hobby" value="drink">
        烫头 <input type="checkbox" name="hobby" value="perm">
        <br>
    简介：<textarea rows="10" cols="60" name="intro"></textarea><br>
    <input type="submit" value="注册">
</form>
</body>
</html>

```

### 2.1 在servlet中

在servlet中, 获取值需要从request的param中获取

```java
@PostMapping(value="/register")
public String register(HttpServletRequest request){
    // 通过当前请求对象获取提交的数据
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String sex = request.getParameter("sex");
    String[] hobbies = request.getParameterValues("hobby");
    String intro = request.getParameter("intro");
    System.out.println(username + "," + password + "," + sex + "," + Arrays.toString(hobbies) + "," + intro);
    return "success";
}
```

### 2.2 通过RequestParam获取

```java
    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String passwd,
            @RequestParam("sex") String sex,
            @RequestParam("hobby") String[] hobby,
            @RequestParam("intro") String intro,
            @RequestParam("age") int age
    ){
        System.out.println("username = " + username);
        System.out.println("passwd = " + passwd);
        System.out.println("sex = " + sex);
        System.out.println("hobby = " + Arrays.toString(hobby));
        System.out.println("intro = " + intro);
        System.out.println("age = " + age);
        return "success";
    }
```

### 2.3 RequestParam注解的required属性和default属性

顾名思义 : 
- required = true的时候, 这个参数不能没有提交, 和在RequeatMapping中的param属性一样, 如果为false, 则可以为空, 这个时候对应的形参值是0
- default = value : 如果这个参数没有传入或者为空字符串的时候, 填入的值

### 2.4 RequestParam是可以省略的

- 如果形参的名字和表单中的参数名是相同的, 这个时候就能省略
- 如果是spring 6, 需要有额外的设置
    - 需要在pom.xml上加上以下内容
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.12.1</version>
            <configuration>
                <source>21</source>
                <target>21</target>
                <compilerArgs>
                    <arg>-parameters</arg>
                </compilerArgs>
            </configuration>
        </plugin>
    </plugins>
</build>
```


- 实验代码
```java
@PostMapping("/register")
    public String register(
            String username,
            String passwd,
            String sex,
            String[] hobby,
            String intro,
            Integer age
    ){
        System.out.println("username = " + username);
        System.out.println("passwd = " + passwd);
        System.out.println("sex = " + sex);
        System.out.println("hobby = " + Arrays.toString(hobby));
        System.out.println("intro = " + intro);
        System.out.println("age = " + age);
        return "success";
    }
```

### 2.5 通过传递javabean用于接收request传回的参数

- 也是需要javabean的属性名是和表单的key名对应才能成功赋值
- 经过实验
    - 实际实例顺序为先实例化对象(无论有参无参), 再通过setter赋值
    - 也就是javabean对象必须提供有参构造或者setter中的一种

- 实验代码
```java
public class User {
    private String username;
    private String password;
    private String sex;
    private String[] hobby;
    private String intro;

    public void setUsername(String username) {
        System.out.println("走的setter方法");
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public User(String username, String password, String sex, String[] hobby, String intro) {
        System.out.println("走了有参构造");
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.hobby = hobby;
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", hobby=" + Arrays.toString(hobby) +
                ", intro='" + intro + '\'' +
                '}';
    }
}

    @PostMapping("/register")
    public String register(User user){
        System.out.println("user = " + user);
        return "success";

```

### 2.6 获取请求中hearder和cookie中的值

- 设置cookie
```html
<script type="text/javascript">
    function sendCookie(){
        document.cookie= "id=12312312; expires=Thu";
        document.location = "/springmvc/register";
    }
</script>
<button onclick="sendCookie()">向服务器发送Cookie</button>
```

- 通过@RequestHeader获取头信息, 里面的value是需要获取的头的信息的key
- 通过@CookieValue获取cookie中的信息, 里面的value是需要获取的cookie的key

- 测试代码
```java
    @RequestMapping(value = "/register" , method = {RequestMethod.GET, RequestMethod.POST})
    public String register(User user,
                           @RequestHeader("Referer") String Referer,
                           @CookieValue("id") String cookie_id
    ){
        System.out.println("user = " + user);
        System.out.println("Referer = " + Referer);
        System.out.println("cookie = " + cookie_id);
        return "success";
    }
```

## 3. domain in SpringMVC

### 3.1 Three domain in Servlet

- Request domain : only live in one HttpRequest Object Instance
- Session Domain : live in one Session, begin in the Browser opening, end of Browser close
    - So at this domain you can save cookie
- Application Domain : live with the Server, with begin, with end

- All domain Objects always have three methods
```java
// 向域中存储数据
void setAttribute(String name, Object obj);

// 从域中读取数据
Object getAttribute(String name);

// 删除域中的数据
void removeAttribute(String name);
```

### 3.2 在SpringMVC中的请求域

- 可以通过设置映射器的接收为ModelMap, Map, Model三种类型的时候, 这三种类型都能当作HttpRequest来设置和获取请求域中的数据

- 其中Map方法特殊一点, 其他的都还是Servlet中Attribute那一套
```java
    @RequestMapping("/testDomainForModel")
    public String toView(Model model){
        model.addAttribute("testRequestScope", "这是通过Model加载到Request的请求域里的内容");
        return "view";
    }
    @RequestMapping("/testDomainForModelMap")
    public String toView(Map<String, Object> map){
        map.put("testRequestScope", "这是通过map加载到Request请求域里的内容");
        return "view";
    }
    @RequestMapping("/testDomainForMap")
    public String toView(ModelMap modelMap){
        modelMap.addAttribute("testRequestScope", "这是通过ModelMap加载到Request的请求域里的内容");
        return "view";
    }
```

### 3.3 通过ModelAndView请求域中的数据

- 这个时候需要在方法中实例化ModelAndView
- addObject -> 添加请求域中的数据
- setViewName -> 添加视图
- 返回ModelAndView , 这个时候返回的就不是String了

```java
    @RequestMapping("/testDomainForModelAndView")
    public ModelAndView toView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testRequestScope", "这是通过ModelAndView加载到Request请求域里的内容");
        modelAndView.setViewName("view");
        return modelAndView;
    }
```

###  3.4 Session域对象

> thymeleaf显示session中的内容

- 通过${session.\<session-key>}

```html
<div th:text="${session.testSessionScope}"></div>
<div th:text="${session.x}"></div>
<div th:text="${session.y}"></div>
```


> 使用Servlet的原生API解决

1. 传入HttpSession对象
2. 在其中setAttribute

```java
    @RequestMapping("/testSessionScope")
    public String testSession(HttpSession session){
        session.setAttribute("testSessionScope", "value of x");
        return "view";
    }
```


> 通过ModelMap类

这个时候需要在类的@Controller上面添加上@SessionAttributes({\<session-key1>, \<session-key2>})的注解, 用于提前声明ModelMap中的哪些值是session域中的对象

```java
@SessionAttributes({"x", "y"})
@Controller
public class RequestDomainController {
    @RequestMapping("/testSessionWithMap")
    public String testSessionWithMap(ModelMap map){
        map.addAttribute("x", "value of x");
        map.addAttribute("y", "value of y");

        return "view";
    }
}
```

### 3.5 Application域对象

> 通过${application.\<application-key>}的形式获取参数

> 一般就是采用Servlet原生API实现

1. 方法添加形参HttpServletRequest request
2. 通过request的getServletContext()方法获取对象
3. 向其中存取数据 (数据的key不能有-在中间)


```java
    @RequestMapping("/testApplication")
    public String testApplication(HttpServletRequest request){
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("applicationkey","application-value");

        return "view";
    }
```

## 4. View

### 4.1 视图支持配置

- 一直要复制的springmvc文件中就是为了实现对于视图的配置

```xml
<!--视图解析器-->
<bean id="thymeleafViewResolver" class="org.thymeleaf.spring6.view.ThymeleafViewResolver">
    <!--作用于视图渲染的过程中，可以设置视图渲染后输出时采用的编码字符集-->
    <property name="characterEncoding" value="UTF-8"/>
    <!--如果配置多个视图解析器，它来决定优先使用哪个视图解析器，它的值越小优先级越高-->
    <property name="order" value="1"/>
    <!--当 ThymeleafViewResolver 渲染模板时，会使用该模板引擎来解析、编译和渲染模板-->
    <property name="templateEngine">
        <bean class="org.thymeleaf.spring6.SpringTemplateEngine">
            <!--用于指定 Thymeleaf 模板引擎使用的模板解析器。模板解析器负责根据模板位置、模板资源名称、文件编码等信息，加载模板并对其进行解析-->
            <property name="templateResolver">
                <bean class="org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver">
                    <!--设置模板文件的位置（前缀）-->
                    <property name="prefix" value="/WEB-INF/templates/"/>
                    <!--设置模板文件后缀（后缀），Thymeleaf文件扩展名不一定是html，也可以是其他，例如txt，大部分都是html-->
                    <property name="suffix" value=".html"/>
                    <!--设置模板类型，例如：HTML,TEXT,JAVASCRIPT,CSS等-->
                    <property name="templateMode" value="HTML"/>
                    <!--用于模板文件在读取和解析过程中采用的编码字符集-->
                    <property name="characterEncoding" value="UTF-8"/>
                </bean>
            </property>
        </bean>
    </property>
</bean>
```

- 其中最重要的内容就是指定了View是Thymeleaf的
- 这样的配置, 有利于OCP开闭原则

### 4.2 SpringMVC 支持的常见视图

Spring MVC支持的常见视图包括：

1. InternalResourceView：内部资源视图（Spring MVC框架内置的，专门为`JSP模板语法`准备的）
2. RedirectView：重定向视图（Spring MVC框架内置的，用来完成重定向效果）
3. ThymeleafView：Thymeleaf视图（第三方的，为`Thymeleaf模板语法`准备的）
4. FreeMarkerView：FreeMarker视图（第三方的，为`FreeMarker模板语法`准备的）
5. VelocityView：Velocity视图（第三方的，为`Velocity模板语法`准备的）
6. PDFView：PDF视图（第三方的，专门用来生成pdf文件视图）
7. ExcelView：Excel视图（第三方的，专门用来生成excel文件视图）

### 4.3 实现视图机制的核心接口

> 前面的大多数内容都是对于View视图相关功能的实现的说明, 这里仅做了解, 给出相关文件链接
> [[第5章 视图View#实现视图机制的核心接口]]

### 4.4 逻辑视图名到物理视图名

- 逻辑视图名 : return的视图名
- 物理视图名 : 实际视图文件的完整可寻路径
- 这些转化也是在springmvc中进行了配置

```xml
<bean id="thymeleafViewResolver" class="org.thymeleaf.spring6.view.ThymeleafViewResolver">
    <property name="characterEncoding" value="UTF-8"/>
    <property name="order" value="1"/>
    <property name="templateEngine">
        <bean class="org.thymeleaf.spring6.SpringTemplateEngine">
            <property name="templateResolver">
                <bean class="org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver">
                    <property name="prefix" value="/WEB-INF/templates/"/>
                    <property name="suffix" value=".html"/>
                    <property name="templateMode" value="HTML"/>
                    <property name="characterEncoding" value="UTF-8"/>
                </bean>
            </property>
        </bean>
    </property>
</bean>
```


`<property name="prefix" value="/web-inf/templates/"/>` : 这行指明要为逻辑视图名添加上前缀 /web-inf/templates/
`<property name="suffix" value=".html"/>` : 这行指明要为逻辑视图名添加上后缀 .html
- 所以如果逻辑视图名是 "view", 则最后的物理视图名是 "/web-inf/templates/view.html"

### 4.5 重定向和转发

- 转发 : 浏览器向服务端发送了一个请求以后, 服务端在内部自发地将请求传递给另一个地址
    - 所以这个时候浏览器上显示的地址是不会发生变化的
    - 通过这种方式我们是能够访问到WEB-INF中隐藏的资源的
- 重定向 : 同样是浏览器向服务端发送了一个请求以后, 服务端告诉浏览器, 你接下来再去访问这个地址
    - 所以这个时候能实现跨域, 因为实际上就是重新填写了一次URL
    

#### forward : 实现转发

- 简单来说只需要在return的内容上加上 forward:\<target>

```java
@Controller
public class ForwardController {
    @RequestMapping("/a")
    public String toA(){
        return "forward : /b";
    }

    @RequestMapping("/b")
    public String toB() {
        System.out.println("实现了转发");
        return "b";
    }
}
```

#### redirect

- 和forward一样, 将forward改成redirect就行

```java
@RequestMapping("/a")
public String toA(){
    return "redirect:/b";
}

@RequestMapping("/b")
public String toB() {
    System.out.println("实现了转发");
    return "b";
}
```

### 4.6 mvc namespace下的一些配置

> 需要引入的配置

需要在命名空间添加
```xml
xmlns:mvc="http://www.springframework.org/schema/mvc"

http://www.springframework.org/schema/mvc
http://www.springframework.org/schema/mvc/spring-mvc.xsd
```

- 这些配置都是配置在springmvc.xml文件中的
#### view-controller

- 这个功能主要是为了简化一些静态页面的配置, 这些页面的Controller代码往往只是一个return,  通过这个代码的配置能直接实现URL到页面的配置

- 使用这个之前需要额外配置\<mvc:annotation-driven/>
```xml
<mvc:annotation-driven/>
<mvc:view-controller path="/" view-name="index"/>
```

#### 静态资源相关配置

- 静态资源一般并不是放在WEB-INF中的, 而是直接放在webapp里面的

- 但是我们现在直接访问静态资源会报错
    - 因为我们之前在web.xml中的配置
```xml
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

- 我们让所有jsp之外的请求都走DispatchServlet, 这个时候访问静态资源也会走这条路径, 就会404报错
- 可以通过开启Servlet处理静态资源的功能

```xml
<!--开启默认Servlet处理-->
<mvc:default-servlet-handler>
```

- 这个时候就能正常将请求交给Servlet处理, 这里的逻辑是先交给DispatchServlet处理, 发现它处理不了以后, 就会交给Servlet处理


> 也可以使用mvc:resources标签配置静态资源

```xml
<!-- 开启注解驱动 -->
<mvc:annotation-driven />

<!-- 配置静态资源处理 -->
<mvc:resources mapping="/static/**" location="/static/" />
```

- 表示但凡路径的开始是/static, 那么就会去从/static文件夹中找
 