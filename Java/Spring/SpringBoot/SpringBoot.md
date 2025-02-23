# 1. 加载外部配置

## 1.1 application.properties

这是springboot默认的加载的外部配置, 是java常用的properties类型的文件, 采用键值对的形式存储值, 通过object.filed的方式创建对象

```properties
spring.application.name=sb3-003-external-config
myapp.username=jack
myapp.email=jack@123.com
myapp.age=30
```

## 1.2 bean对象获取配置文件中的值
- 通过@Value("${key:defult}")的形式获取配置文件中的值

```java
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Value("${myapp.username}")
    private String username;
    @Value("${myapp.email}")
    private String email;
    @Value("${myapp.age}")
    private Integer age;
    @Value("${myapp.passwd:123456}")
    private String passwd;
    @Override
    public void printInfo(){
        String str = String.join(",", username, email, String.valueOf(age), passwd);
        System.out.println(str);
    }
}
```

## 1.3 springboot扫描配置文件中的顺序

- 使用springboot initializr初始化的项目, application.properties文件存放在resource也就是项目的根路径下, 这个路径实际上是优先级最低的路径, 最容易被覆盖的路径

- 扫描的顺序
    1. current directory: ./config/ : 这个路径是项目的根路径, 或是命令行的所在的当前目录, 会在当前目录的config文件夹中找, 这个是优先级最高的位置
        1. 如果没有找到application.properties会继续找application.yml如果两个文件都没找到才会去找接下来的文件夹
    2. current directory: ./: 在当前目录中找
    3. classpath: /config/: 如果在工作目录没有找到, 就回去类路径中找, 首先会在resource的config文件夹中找, 先从类路径的 /config/中找
    4. classpath: / : 如果没有在类路径的config文件夹中找到了, 就会从类路径中找
    
## 1.4 使用YAML加载配置文件

- YAML是大小写敏感的配置文件, 键和值之间使用一个冒号和空格分隔
- 递进关系采用换行 + 空格分隔

- properties文件中这么配置
```properties
myapp.name=123
myapp.count=1
```
- 那么yml文件中就需要这么配置
```yml
myapp: 
    name: 123
    count: 1
```

- yml 使用#注解
- yml中的数组
```yaml
import: [element1, element2]

# 第二种方式
import:
    - element1
    - element2
```

## 1.5 配置文件合并

> 在配置的内容越来越多的时候, 显然将所有的内容都放在一个文件中是一种不合适的选择, 我们这个时候就有了分文件管理的需求, 就像mabatis中的mapper, 我们并不是将所有的mapper都放在一个文件, 而是在中心配置文件中将所有的配置文件import

### 1.5.1 properties配置方法

使用 `spring.config.import` 将需要的配置文件导入, 配置文件之间使用逗号隔开

- core
```properties
spring.application.name=sb3-003-external-config
spring.config.import=classpath:application-mysql.properties.properties, classpath:application-redis.properties.properties
```

- application-mysql
```properties
spring.datasource.username=root
spring.datasource.password=root
```

- application-redis
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6322
```

### 1.5.2 yaml配置方法

使用 `spring: config : import: ` 方法

- core
```yaml
spring:
  config:
    import:
      - classpath:application-mysql.properties.properties
      - classpath:application-redis.properties.properties
```

- application-mysql
```yaml
spring:
  datasource:
    username: root
    password: 789789
```

- application-redis
```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

### 1.5.3 测试代码
```java
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private String port;
```

## 1.6 多环境切换

> 这个功能用于切换不同情境下的不同配置文件


一般来说, 有四种方式环境文件

1. 开发环境的配置文件, 一般叫 `application-dev`
2. 测试环境的配置文件 : `application-test`
3. 预生产环境的配置文件 :  `application-preprod`
4. 生产环境的配置文件 : `application-prod`

有两种方式指定环境
1. 在application.properties文件中添加上`spring.profiles.active=prod`
2. 也可以在运行jar包的时候, 添加上`--spring.profiles.active=prod`命令行参数

```yaml
spring:
  profiles:
      active: mysql

spring:
  datasource:
    username: root
    password: 789789
```

- 需要额外说明的是, 实际上这个的命名是随意的, 只要是application-name, 后面的name都可以被视作一种环境而被springboot识别

## 1.7 将配置信息绑定到bean上

### 简单bean的绑定

- 现在我有一个yaml配置文件, 现在需要将他绑定到一个bean实例上

```yml
app:
  name: jack
  age: 30
  email: jack@123.com
```

> 说明 

- @Configuration注解用于将这个类纳入IoC容器管理
    - 其中的参数用于指明不需要代理类的生成, 这样能提高效率
    - 也可以使用@Component注解来将这个类纳入IoC容器管理
- ConfigurationProperties(prefix) 用于指明这个类它的前缀, 也就是这个类是配置文件中的哪个类

- java
```java
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "app")
@Data
public class MyApp {
    private String name;
    private Integer age;
    private String email;
}
```
### 嵌套bean绑定

- yaml
```yaml
app:
  name: jack
  age: 30
  email: jack@123.com
  address:
    city: BJ
    street: ChaoYang
    zipcode: 123456
```

- java
```java
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "app")
@Data
public class MyApp {
    private String name;
    private Integer age;
    private String email;
    private Address address;

}


@Configuration
@ConfigurationProperties(prefix = "address")
@Data
public class Address {
    private String zipcode;
    private String city;
    private String  street;
}
```

### 另外的两种将类纳入IoC容器管理的方式

> 这两种方式都是在SpringBoot程序的入口, 带有@SpringBootApplication的类上注解的

- @ConfigurationPropertiesScan
```java
@ConfigurationPropertiesScan(basePackages = "org.springboot.sb3003externalconfig.bean")
@SpringBootApplication
```

- @EnableConfiguration
```java
@EnableConfigurationProperties(MyApp.class)
@SpringBootApplication
```

### 将配置绑定在bean的Map/List/Array属性上

> 其实还是和正常的bean是一样的处理方式, 只不过需要额外地创建对应的子类

- 现在我们需要将这些yml配置信息绑定到一个bean配置类上
```yml
#数组
names:
  - jackson
  - lucy
  - lili

#List集合
products: 
  - name: 西瓜
    price: 3.0
  - name: 苹果
    price: 2.0

#Map集合
vips:
  vip1:
    name: 张三
    age: 20
  vip2:
    name: 李四
    age: 22
```

```java
@ConfigurationProperties
@Data
public class AppBean {
    private String[] names;
    private List<Product> products;
    private Map<String, Vip> vips;
}

@Data
class Product {
    private String name;
    private double price;
}

@Data
class Vip {
    private String name;
    private Integer age;
}
```

### 将配置信息绑定到外部类上

- 需求并不总是是我们要将配置信息绑定到一个我们创建的可以修改的类上, 这个类很可能是由第三方提供的字节码文件, 并不可以修改

> 情景模拟

- 配置文件
```yaml
address:
  city: TJ
  street: XiangYangLu
  zipcode: 11111111
```

- 类
```java
@Data
public class Address {
    private String zipcode;
    private String city;
    private String  street;
}
```

- 现在我们怎么将配置信息绑定到Address类上, 并创建相应的bean, 使用@Bean和@ConfigurationProperties组合

```java
@Configuration
public class ApplicationConfig {
    @Bean
    @ConfigurationProperties(prefix = "address")
    public Address getAddress(){
        return new Address();
    }
}
```

- 需要额外注意, 这个工厂方法上面需要有@Configuration注解, 以纳入容器管理

- 测试代码
```java
    @Autowired
    private Address address;
    @Test
    void contextLoads() {
        System.out.println(address);
    }
```

### 如何将外部的, 非application中的配置信息加载到配置类上

@Configuration : 将这个类纳入容器管理
@ConfigurationProperties : 将配置文件中的值赋值给Bean对象
@PropertySource : 指定额外的配置文件

- 在`resources`目录下新建`a`目录，在`a`目录下新建`b`目录，`b`目录中新建`group-info.properties`文件，进行如下的配置：
```yaml
group.name=IT
group.leader=LaoDu
group.count=20
```

- java
```java@Configuration
@ConfigurationProperties(prefix = "group")
@PropertySource("classpath:a/b/group-info.properties")
@Data
public class Group {
    private String name;
    private String leader;
    private Integer count;
}
```

## 1.8 @ ImportResource注解

> 现在我们有了三种配置Bean的方式
>     1. 通过xml文件配置
>     2. 通过@Service,  @Controller, @Component, @Configuration等注解配置
>     3. 通过@Bean + @ConfigurationProperties注解配置
> 我们已经介绍了后两种在springboot中的体现, @ImportResource就是实现第一种的方式

- 使用方法 : 通过在程序入口类(带有@SpringBootApplication的类上)注解, 引入xml文件, 从而创建bean

```java
@ImportResource("classpath:/config/applicationContext.xml")
@SpringBootApplication
public class Sb3003ExternalConfigApplication
```

- xml文件
```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="person" class="org.springboot.sb3003externalconfig.bean.Person">
        <property name="name" value="jackson"/>
        <property name="age" value="20"/>
    </bean>
</beans>
```

- Person类
```java
@Data
public class Person {
    private String name;
    private Integer age;
}
```

- 这种方式能在外部只提供了xml文件的时候综合项目, 创建Bean的方式

## 1.9 Environment

> Spring其实已经提供了我们访问应用程序环境信息的接口Environment , 这里使用也是通过这个类获取环境信息

Environment对象封装的信息有 :
    1. Active Profiles : 当前激活的配置文件列表, 主要是用于获取环境配置文件的相关信息 ( 如开发环境, 测试环境等 )
    2. System Properties : 系统属性, 通常是操作系统级别的属性, 比如操作系统名称, java版本等
    3. System Environment Variables : 系统环境变量, 通常是由操作系统提供的, 可以启动应用程序时设定的特定的值
    4. Command Line arguments : 应用程序启动的时候传递给主方法的参数
    5. Properties Sources : 包含了从不同源加载的所有属性

- 示例代码
```java
    @Autowired
    // 注入的方式获取对象
    private Environment environment;

    void contextLoads() {
        // 直接使用环境对象
        String[] activeProfiles = environment.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            System.out.println("activeProfile = " + activeProfile);
        }

        //  获取配置信息
        String city = environment.getProperty("address.city");
        System.out.println("city = " + city);

    }
```

## 1.10 面向切面编程

在SpringBoot中的面向切面编程和在Spring中的基本一致, 只是导入依赖的形式, 这里是引入一个starter启动器, 接下来给出示例以后主要说明和传入如的参数JoinPoint和ProceedJoinPoint相关的内容
- 详细的内容说明, 参照[[Spring#12. 面向切面编程AOP]]

### 引入AoP编程

- 添加上相关的starter即可
```xml
<!--aop启动器-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### 示例

> 实现AoP的流程
>     1. 创建切面类
>         1. 为切面类添加@Component注解, 将类纳入IoC容器管理
>         2. 添加@Aspect注解, 表明这是一个切面类
>     2. 添加代理方法
>     3. 为方法编织切入点



- 示例代码

```java
@Component
@Aspect
public class LogAspect {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");

    @Before("execution(* org.springboot.sb3003externalconfig.service..*(..))")
    public void sysLog(ProceedingJoinPoint joinPoint){
        System.out.println("切面编织成功");
        StringBuilder log = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String strNow = formatter.format(now);
        log.append(strNow);
        log.append(":");
        log.append(joinPoint.getSignature().getName());
        log.append("(");
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            log.append(args[i]);
            if (i < args.length -1)
                log.append(", ");
        }
        log.append(")");
        System.out.println(log);
    }
}
```


### 重点 : ProceedJoinPoint和JoinPoint

这两个类都是切面类中的方法可以传入的参数, 其中ProceedJoinPoint是JoinPoint的子类, JoinPoint是个接口
- 通过JoinPoint, 我们能获取到和执行的方法相关的参数, 比如方法名, 类名等
- 而ProceedJoinPoint进一步实现了proceed()方法, 从而能在方法中运行原方法
    - 该类是只能在@Arround类型的方法中使用, 其他的情景使用会导致切面错误, 无法正常产生代理类, 但是现在并不会出现GPT所说的编译错误, 运行和编译的时候并不会主动报错

# MyBatis集成

## 原本怎么集成和使用MyBatis 

- 首先创建mybatis-config.xml文件, 用来存放核心配置
    - 关键配置有数据库连接是否池化
    - DataSource
        - database url
        - database driver
        - database name
        - database password
    - mappers resource
    - enable underscore-to-camel-case
- 创建mapper.xml文件, 里面添加CRUD方法
- 实体类
- mapperDao接口

## springboot中的集成

### 核心配置文件

> springboot中不需要再额外创建xml文件进行配置, 直接在application.properties中配置即可

```properties
# mybatis数据源配置
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
```

### 接口Mapper类

- 无变化

### mapper.xml文件

- 这个文件现在能通过MyBatisX插件简化开发过程
- 在下载了以后, 在接口的方法上alt + enter上就能生成mapper的框架部分, 只需要自己填好sql语句即可

### 驼峰映射和mapper.xml文件的导入

> 同样是在application.properties文件中配置

```properties
# 告诉MyBatis Mapper.xml文件的位置
mybatis.mapper-locations=classpath:mapper/*.xml

# 开启驼峰自动映射
mybatis.configuration.map-underscore-to-camel-case=true
```

### 添加Mapper的扫描

 > 最后在SpringBoot的入口程序上添加如下的注解, 来完成Mapper接口的扫描
 
 ```java
 @MapperScan("org.springboot.sb3004ssm.repository")
@SpringBootApplication
public class Sb3004SsmApplication
```

## Lombok

### Lombok常用注解

- @Data之类的就不额外说明了

#### @Value

> 这个注解是用来创建不可修改类的, 简单来说就是创建各个属性都被final关键字修饰的类

```java
@Value
public class Person {
    private String name;
    private Integer age;
    private Long id;
}
```

- 这个类这个时候只有getter, hascode, toString, equal方法, 没有setter方法, 通过这个方式实现不可修改

#### @Builder

> 这是GoF23中设计模式之一, 本质上是为了解决参数很多的构造方法在创建对象的时候的繁杂, 我们想让这些参数可以以此分开来添加到类中, 而不是在构造方法的时候一口气注入, 同时这种方式又有别于已经创建以后再setter, 这是在创建之前进行的行为

- 没有使用注解的时候手敲的代码
    - **注意这个时候的constructor是私有的**

```java
package org.springboot.sb3004ssm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.io.PrintWriter;

@Data
public class Person {
    private String name;
    private Integer age;
    private Long id;

    private Person(String name, Integer age, Long id) {
        this.age = age;
        this.id = id;
        this.name = name;
    }

    public static PersonBuilder builder(){
        return new PersonBuilder();
    }

    public static class PersonBuilder {
        private String name;
        private Integer age;
        private Long id;
        public PersonBuilder name(String name){
            this.name = name;
            return this;
        }

        public PersonBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public PersonBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Person build(){
            return new Person(this.name, this.age, this.id);
        }
    }

    public static void main(String[] args) {
        PersonBuilder builder = Person.builder();
        Person fang = builder.name("fang").age(212).id(2L).build();
        System.out.println(fang);
    }
}
```

- 使用了注解的方案
```java
@Builder
@Data
public class Person {
    private String name;
    private Integer age;
    private Long id;
    public static void main(String[] args) {
        PersonBuilder builder = Person.builder();
        Person fang = builder.name("fang").age(212).id(2L).build();
        System.out.println(fang);
    }
}
```

- 不难从IDEA的structure中看出来, Builder还提供了@Data, 和一个私有的全参构造方法

#### @Singular

> 这个方式是辅助Builder设计模式使用的, 是为了解决如果类中有List类型, 我们怎么实现一个一个添加的问题

```java
@Builder
@Data
public class Person {
    private String name;
    private Integer age;
    private Long id;
    @Singular("addMate")
    private List<String> mate;
    public static void main(String[] args) {
        PersonBuilder builder = Person.builder();
        Person fang = builder.name("fang").age(212).id(2L).addMate("test").addMate("mate").build();
        System.out.println(fang);
    }
}
```

#### @Slf4j

> 这其实是一类日志注解, 这个注解会为我们自动创建日志类, 这样在类中我们就能直接调用日志类

实际上我们有三种选择, 这个根据实际的需求选择
-  @Slf4j
- @Log4j
- @Log4j2

- 这几个注解的实质实现就是为我们在类中创建一个日志类, 那么这里有的前提就是我们已经导入了框架以及日志的具体实现, 并完成了对日志框架的配置

- 这里是按照教程使用的Slf4j日志框架和logback日志实现
```xml
    <!--Slf4j日志规范-->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.16</version>
    </dependency>
    <!--Slf4j日志实现：logback-->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.5.11</version>
    </dependency>
```

- TestCode
```java
@Slf4j
@Builder
@Data
public class Person {
    private String name;
    private Integer age;
    private Long id;
    @Singular("addMate")
    private List<String> mate;
    public static void main(String[] args) {
        log.info("实验成功");
        PersonBuilder builder = Person.builder();
        Person fang = builder.name("fang").age(212).id(2L).addMate("test").addMate("mate").build();
        System.out.println(fang);
    }
}
```


## MyBatis逆向

> MyBatis逆向指的是, 通过数据库的表反向生成Mapper接口文件, 实体类以及Mapper.xml文件, 这样就不再需要自己写繁琐的CRUD, 只需要建表就能一键完成剩下的内容

### 安装插件和准备数据源

- 在IDEA的plugins中搜索安装Free MyBatis Tool插件
- 在IDEA中连接上数据库和需要的库
- 右键表, 这个时候能看到MyBatis-Generator的选项
- 在里面配置好路径和名称即可, 接下来就会生成对应的内容
### 测试程序

- testcode
```java
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Sb3004SsmApplication.class, args);
		PersonMapper personMapper = applicationContext.getBean("personMapper", PersonMapper.class);
		System.out.println(personMapper);

		System.out.println(personMapper.selectByPrimaryKey(2));
		Person person = new Person(null, "fangh", "123445", "1292-02-13");
		System.out.println("插入语句 : " + personMapper.insert(person));
		int count = personMapper.deleteByPrimaryKey(1);
		System.out.println(count);

		personMapper.selectAll().forEach(person1 -> {
			System.out.println(person1);
		});

		applicationContext.close();
	}
```

- 到此MyBatis的所有整合都已经完成了