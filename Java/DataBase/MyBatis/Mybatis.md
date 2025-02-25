# MyBatis
## 1. HelloWord - 第一个程序

> 创建mybatis-config.xml 配置文件, 用于加载mybatis配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING" />
    </settings>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--sql映射文件创建好之后，需要将该文件路径配置到这里-->
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```

> 创建数据库和表

... 省略

> 创建查询resource.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace先随意写一个-->
<mapper namespace="car">
    <!--insert sql：保存一个汽车信息-->
    <insert id="insertCar">
        insert into t_car
            (id,car_num,brand,guide_price,produce_time,car_type)
        values
            (null,'102','丰田mirai',40.30,'2014-10-05','氢能源')
    </insert>
</mapper>
```

> 创建第一个程序

执行步骤
1. 创建SqlSessionFactoryBuilder(), 用于创建SqlSessionFactory
2.  通过SqlSessionFactory创建Session
3. Session执行对应id的SQL
4. 接受返回并提交
5. 执行错误则回滚
6. 在finally中关闭连接

```java
public class HelloWord {
    public static void main(String[] args) {
        SqlSession sqlSession = null;

        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            sqlSession = sqlSessionFactory.openSession();
            int count = sqlSession.insert("insertCar");
            System.out.println("插入了" + count + "条数据");
            sqlSession.commit();
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
```

> 集成日志框架

1. 添加logback依赖
2. 添加相关配置文件到类的根路径下, 文件命名可以为`logback.xml`或者`logback-config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration debug="false">
    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>
    <!-- 按照每天生成日志文件 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${LOG_HOME}/TestWeb.log.%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--日志文件保留天数-->
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
        <!--日志文件最大的大小-->
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>100MB</MaxFileSize>
        </triggeringPolicy>
    </appender>

    <!--mybatis log configure-->
    <logger name="com.apache.ibatis" level="TRACE"/>
    <logger name="java.sql.Connection" level="DEBUG"/>
    <logger name="java.sql.Statement" level="DEBUG"/>
    <logger name="java.sql.PreparedStatement" level="DEBUG"/>

    <!-- 日志输出级别,logback日志级别包括五个：TRACE < DEBUG < INFO < WARN < ERROR -->
    <root level="DEBUG">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
    </root>

</configuration>
```

> 将繁琐的创建session的过程包装为一个工具类

```java
public class SqlSessionUtil {
    private static SqlSessionFactory sqlSessionFactory;

    // 类加载的时初始化SqlSessionFactor对象
    static {
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            sqlSessionFactory =  sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSession openSession(){
        return sqlSessionFactory.openSession(true);
    }
}
```

## 2. 使用MyBatis完成CRUD

### 2.1 Create

- 使用`SqlSession.insert("id", map/object)`接口

> 有两种实现方式, MyBatis支持使用Map和对象两种方式传入数据

-  Map

```java
    public void testMapInsert(){
        HashMap map = new HashMap<>();
        map.put("carNum", "111");
        map.put("brand", "奔驰E300L");
        map.put("guidePrice", 70.3);
        map.put("produceTime", "2020-10-12");
        map.put("carType", "燃油车");

        SqlSession sqlSession = null;

        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            sqlSession = sqlSessionFactory.openSession();
            int count = sqlSession.insert("insertCar", map);
            System.out.println("count = " + count);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
```

- Object

> 使用对象的话, 如果是需要将对象中数据传入, 则对象需要有get方法
> 如果需要通过Mybatis创建对象, 则其中需要有set方法

```java        
        Car car = new Car();
        car.setBrand("奔驰E");
        car.setCarNum("124");
        car.setCarType("燃油车");
        car.setGuidePrice(123.2);
        car.setProduceTime("2023-12-05");

        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.insert("insertCar", car);
        System.out.println("count = " + count);

    }
```

- xml

```xml
    <insert id="insertCar">
        insert into t_car
            (car_num,brand,guide_price,produce_time,car_type)
        values
            (#{carNum},#{brand}, #{guidePrice}, #{produceTime}, #{carType})
    </insert>
```

### 2.2 Update

> 后续都只演示两种方式中, 使用Object传入数据的方式

> 使用`SqlSession.update("id", map/object)`接口

- java

```java
        @Test
    public void testUpdate(){
        Car car = new Car();
        car.setId(7);
        car.setBrand("奔驰");
        car.setCarType("电车");
        car.setProduceTime("2022-01-23");
        car.setGuidePrice(12.3);
        car.setCarNum("126");

        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.update("updateById", car);
        System.out.println("count = " + count);

    }
```

- xml

```xml
    <update id="updateById">
        UPDATE t_car SET
            car_num = #{carNum}, brand = #{brand}, guide_price = #{guidePrice},
            produce_time = #{produceTime}, car_type = #{carType}
        where id = #{id}
    </update>
```

### 2.3 Delete

> 使用`SqlSession.delete("id", map/object)`接口

- java

```java
    public void testDelete(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.delete("deleteByCarNum", "124");
        System.out.println("count = " + count);

    }
```

- xml

```xml
    <delete id="deleteByCarNum">
        DELETE FROM t_car WHERE car_num = #{carNum}
    </delete>
```

### 2.4 Read

> 需要特别注意的时候, 因为返回的数量不同, 所以对于selete, 有两个接口分别用于返回单个结果的查询语句和返回List的查询语句

> 同时对于xml而言, 也需要额外指定resultType这个属性, 用于获取类的字节码

> 同时注意数据库中字段命名的方式和Java程序中往往不一样, 所以对查询出来的内容做重命名

```java
    @Test
    public void testSelectList(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        List<Object> cars = sqlSession.selectList("selectCarAll");
        cars.forEach(car -> System.out.println(car));
    }
    @Test
    public void testQueryOne(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Object car = sqlSession.selectOne("selectCarById", 1);
        System.out.println("car = " + car);
    }
```

- xml

```xml
    <select id="selectCarById" resultType="com.func.mybatis.crud.Car">
        SELECT id, brand, car_num carNum, guide_price guidePrice, produce_time produceTime, car_type carType
        FROM t_car
        WHERE id = #{id}
    </select>
    <select id="selectCarAll" resultType="com.func.mybatis.crud.Car">
        SELECT id, brand, car_num carNum, guide_price guidePrice, produce_time produceTime, car_type carType
        FROM t_car
    </select>
```

### 2.5 名称空间

- 如果多个resource文件中的sql语句id一样, 这个时候通过添加namespace来进行区分
## 3. Mybatis核心配置文件详解

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/powernode"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
        <mapper resource="CarMapper2.xml"/>
    </mappers>
</configuration>
```

- configuration：根标签，表示配置信息。
- environments：环境（多个），以“s”结尾表示复数，也就是说mybatis的环境可以配置多个数据源。
   - default属性：表示默认使用的是哪个环境，default后面填写的是environment的id。**default的值只需要和environment的id值一致即可**。
- environment：具体的环境配置（**主要包括：事务管理器的配置 + 数据源的配置**）
   - id：给当前环境一个唯一标识，该标识用在environments的default后面，用来指定默认环境的选择。
- transactionManager：配置事务管理器
   - type属性：指定事务管理器具体使用什么方式，可选值包括两个
      - **JDBC**：使用JDBC原生的事务管理机制。**底层原理：事务开启conn.setAutoCommit(false); ...处理业务...事务提交conn.commit();**
      - **MANAGED**：交给其它容器来管理事务，比如WebLogic、JBOSS等。如果没有管理事务的容器，则没有事务。**没有事务的含义：只要执行一条DML语句，则提交一次**。
- dataSource：指定数据源
   - type属性：用来指定具体使用的数据库连接池的策略，可选值包括三个
      - **UNPOOLED**：采用传统的获取连接的方式，虽然也实现Javax.sql.DataSource接口，但是并没有使用池的思想。
         - property可以是：
            - driver 这是 JDBC 驱动的 Java 类全限定名。
            - url 这是数据库的 JDBC URL 地址。
            - username 登录数据库的用户名。
            - password 登录数据库的密码。
            - defaultTransactionIsolationLevel 默认的连接事务隔离级别。
            - defaultNetworkTimeout 等待数据库操作完成的默认网络超时时间（单位：毫秒）
      - **POOLED**：采用传统的javax.sql.DataSource规范中的连接池，mybatis中有针对规范的实现。
         - property可以是（除了包含**UNPOOLED**中之外）：
            - poolMaximumActiveConnections 在任意时间可存在的活动（正在使用）连接数量，默认值：10
            - poolMaximumIdleConnections 任意时间可能存在的空闲连接数。
            - 其它....
      - **JNDI**：采用服务器提供的JNDI技术实现，来获取DataSource对象，不同的服务器所能拿到DataSource是不一样。如果不是web或者maven的war工程，JNDI是不能使用的。
         - property可以是（最多只包含以下两个属性）：
            - initial_context 这个属性用来在 InitialContext 中寻找上下文（即，initialContext.lookup(initial_context)）这是个可选属性，如果忽略，那么将会直接从 InitialContext 中寻找 data_source 属性。
            - data_source 这是引用数据源实例位置的上下文路径。提供了 initial_context 配置时会在其返回的上下文中进行查找，没有提供时则直接在 InitialContext 中查找。
- mappers：在mappers标签中可以配置多个sql映射文件的路径。
- mapper：配置某个sql映射文件的路径
   - resource属性：使用相对于类路径的资源引用方式
   - url属性：使用完全限定资源定位符（URL）方式
## 4.1 environment
mybatis-003-configuration
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--默认使用开发环境-->
    <!--<environments default="dev">-->
    <!--默认使用生产环境-->
    <environments default="production">
        <!--开发环境-->
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/powernode"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
        <!--生产环境-->
        <environment id="production">
            <transactionManager type="JDBC" />
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="car">
    <insert id="insertCar">
        insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values(null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
    </insert>
</mapper>
```
```java
package com.powernode.mybatis;

import com.powernode.mybatis.pojo.Car;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void testEnvironment() throws Exception{
        // 准备数据
        Car car = new Car();
        car.setCarNum("133");
        car.setBrand("丰田霸道");
        car.setGuidePrice(50.3);
        car.setProduceTime("2020-01-10");
        car.setCarType("燃油车");

        // 一个数据库对应一个SqlSessionFactory对象
        // 两个数据库对应两个SqlSessionFactory对象，以此类推
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 使用默认数据库
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        int count = sqlSession.insert("insertCar", car);
        System.out.println("插入了几条记录：" + count);

        // 使用指定数据库
        SqlSessionFactory sqlSessionFactory1 = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "dev");
        SqlSession sqlSession1 = sqlSessionFactory1.openSession(true);
        int count1 = sqlSession1.insert("insertCar", car);
        System.out.println("插入了几条记录：" + count1);
    }
}
```
执行结果：
![FF0CD72D-D26C-4ccb-9202-593CDA8C53D8.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1660117624656-64973cf7-6700-43da-96fa-5960dfc5045b.png#clientId=uf25371ce-2df6-4&from=paste&height=520&id=ube5d1c7d&originHeight=520&originWidth=1627&originalType=binary&ratio=1&rotation=0&showTitle=false&size=122019&status=done&style=none&taskId=ub5627b32-0df7-4e4d-9968-dc704671e6c&title=&width=1627)
![B8C1BB12-6CB0-4f08-A96F-560B6268C8F8.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1660117692466-ecd6317f-5868-4858-a33c-0c7ec68044ac.png#clientId=uf25371ce-2df6-4&from=paste&height=499&id=ubeb926c4&originHeight=499&originWidth=756&originalType=binary&ratio=1&rotation=0&showTitle=false&size=32276&status=done&style=none&taskId=u8350f504-6e73-4222-8172-415d1cf2331&title=&width=756)

## 4.2 transactionManager
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="dev">
        <environment id="dev">
            <transactionManager type="MANAGED"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/powernode"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```
```java
@Test
public void testTransactionManager() throws Exception{
    // 准备数据
    Car car = new Car();
    car.setCarNum("133");
    car.setBrand("丰田霸道");
    car.setGuidePrice(50.3);
    car.setProduceTime("2020-01-10");
    car.setCarType("燃油车");
    // 获取SqlSessionFactory对象
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config2.xml"));
    // 获取SqlSession对象
    SqlSession sqlSession = sqlSessionFactory.openSession();
    // 执行SQL
    int count = sqlSession.insert("insertCar", car);
    System.out.println("插入了几条记录：" + count);
}
```
当事务管理器是：JDBC

- 采用JDBC的原生事务机制：
   - 开启事务：conn.setAutoCommit(false);
   - 处理业务......
   - 提交事务：conn.commit();

当事务管理器是：MANAGED

- 交给容器去管理事务，但目前使用的是本地程序，没有容器的支持，**当mybatis找不到容器的支持时：没有事务**。也就是说只要执行一条DML语句，则提交一次。
## 4.3 dataSource
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="UNPOOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/powernode"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```
```java
@Test
public void testDataSource() throws Exception{
    // 准备数据
    Car car = new Car();
    car.setCarNum("133");
    car.setBrand("丰田霸道");
    car.setGuidePrice(50.3);
    car.setProduceTime("2020-01-10");
    car.setCarType("燃油车");
    // 获取SqlSessionFactory对象
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config3.xml"));
    // 获取SqlSession对象
    SqlSession sqlSession = sqlSessionFactory.openSession(true);
    // 执行SQL
    int count = sqlSession.insert("insertCar", car);
    System.out.println("插入了几条记录：" + count);
    // 关闭会话
    sqlSession.close();
}
```
当type是UNPOOLED，控制台输出：
![D58DF616-529B-41c7-8C0F-8A931A380497.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1660125487038-29390827-4e20-431a-8d62-f56a40bf0349.png#averageHue=%2333302f&clientId=uf25371ce-2df6-4&from=paste&height=275&id=u12a918bf&originHeight=275&originWidth=1637&originalType=binary&ratio=1&rotation=0&showTitle=false&size=50045&status=done&style=none&taskId=ud5ec24dc-2635-4d39-9ac2-ee47e88b08f&title=&width=1637)
修改配置文件mybatis-config3.xml中的配置：
```xml
<dataSource type="POOLED">
```
Java测试程序不需要修改，直接执行，看控制台输出：
![8DFDAF99-AA22-41cd-99C7-227A4667A0EB.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1660125701333-b14f63d8-c442-4211-ad77-884f21162357.png#averageHue=%2334302f&clientId=uf25371ce-2df6-4&from=paste&height=435&id=u52280792&originHeight=435&originWidth=1628&originalType=binary&ratio=1&rotation=0&showTitle=false&size=93482&status=done&style=none&taskId=ub425fbf6-8f9b-4955-8fa5-425a4fd2abb&title=&width=1628)
通过测试得出：UNPOOLED不会使用连接池，每一次都会新建JDBC连接对象。POOLED会使用数据库连接池。【这个连接池是mybatis自己实现的。】
```xml
<dataSource type="JNDI">
```
JNDI的方式：表示对接JNDI服务器中的连接池。这种方式给了我们可以使用第三方连接池的接口。如果想使用dbcp、c3p0、druid（德鲁伊）等，需要使用这种方式。
这种再重点说一下type="POOLED"的时候，它的属性有哪些？
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/powernode"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
                <!--最大连接数-->
                <property name="poolMaximumActiveConnections" value="3"/>
                <!--这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直失败且不打印日志），默认值：20000 毫秒（即 20 秒）。-->
                <property name="poolTimeToWait" value="20000"/>
                <!--强行回归池的时间-->
                <property name="poolMaximumCheckoutTime" value="20000"/>
                <!--最多空闲数量-->
                <property name="poolMaximumIdleConnections" value="1"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```
poolMaximumActiveConnections：最大的活动的连接数量。默认值10
poolMaximumIdleConnections：最大的空闲连接数量。默认值5
poolMaximumCheckoutTime：强行回归池的时间。默认值20秒。
poolTimeToWait：当无法获取到空闲连接时，每隔20秒打印一次日志，避免因代码配置有误，导致傻等。（时长是可以配置的）
当然，还有其他属性。对于连接池来说，以上几个属性比较重要。
最大的活动的连接数量就是连接池连接数量的上限。默认值10，如果有10个请求正在使用这10个连接，第11个请求只能等待空闲连接。
最大的空闲连接数量。默认值5，如何已经有了5个空闲连接，当第6个连接要空闲下来的时候，连接池会选择关闭该连接对象。来减少数据库的开销。
需要根据系统的并发情况，来合理调整连接池最大连接数以及最多空闲数量。充分发挥数据库连接池的性能。【可以根据实际情况进行测试，然后调整一个合理的数量。】
下图是默认配置：
![9970FDF3-6A6A-4530-84A3-B72FE189124E.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1660126871013-88f83ea4-94e9-4088-bdb4-06a4fd73866a.png#averageHue=%233d4042&clientId=uf25371ce-2df6-4&from=paste&height=348&id=u3a10ea73&originHeight=348&originWidth=640&originalType=binary&ratio=1&rotation=0&showTitle=false&size=22150&status=done&style=none&taskId=u43582ddf-d276-4250-aefb-35db3c377e4&title=&width=640)
在以上配置的基础之上，可以编写java程序测试：
```java
@Test
public void testPool() throws Exception{
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config3.xml"));
    for (int i = 0; i < 4; i++) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Object selectCarByCarNum = sqlSession.selectOne("selectCarByCarNum");
    }
}
```
```xml
<select id="selectCarByCarNum" resultType="com.powernode.mybatis.pojo.Car">
  select id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType from t_car where car_num = '100'
</select>
```
![E2C814E9-489D-4f4a-94F6-2ED5F6E56F54.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1660128646511-8df1006a-dd85-48d1-9b88-ce7969d9b546.png#averageHue=%2335302f&clientId=uf25371ce-2df6-4&from=paste&height=748&id=u2b331339&originHeight=748&originWidth=1507&originalType=binary&ratio=1&rotation=0&showTitle=false&size=165804&status=done&style=none&taskId=ud490fe5a-777b-41eb-9b83-a702e843fe3&title=&width=1507)
## 4.4 properties
mybatis提供了更加灵活的配置，连接数据库的信息可以单独写到一个属性资源文件中，假设在类的根路径下创建jdbc.properties文件，配置如下：
```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/powernode
```
在mybatis核心配置文件中引入并使用：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--引入外部属性资源文件-->
    <properties resource="jdbc.properties">
        <property name="jdbc.username" value="root"/>
        <property name="jdbc.password" value="root"/>
    </properties>

    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--${key}使用-->
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```
编写Java程序进行测试：
```java
@Test
public void testProperties() throws Exception{
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config4.xml"));
    SqlSession sqlSession = sqlSessionFactory.openSession();
    Object car = sqlSession.selectOne("selectCarByCarNum");
    System.out.println(car);
}
```
**properties两个属性：**
**resource：这个属性从类的根路径下开始加载。【常用的。】**
**url：从指定的url加载，假设文件放在d:/jdbc.properties，这个url可以写成：file:///d:/jdbc.properties。注意是三个斜杠哦。**
注意：如果不知道mybatis-config.xml文件中标签的编写顺序的话，可以有两种方式知道它的顺序：

- 第一种方式：查看dtd约束文件。
- 第二种方式：通过idea的报错提示信息。【一般采用这种方式】
## 4.5 mapper
mapper标签用来指定SQL映射文件的路径，包含多种指定方式，这里先主要看其中两种：
第一种：resource，从类的根路径下开始加载【比url常用】
```xml
<mappers>
  <mapper resource="CarMapper.xml"/>
</mappers>
```
如果是这样写的话，必须保证类的根下有CarMapper.xml文件。
如果类的根路径下有一个包叫做test，CarMapper.xml如果放在test包下的话，这个配置应该是这样写：
```xml
<mappers>
  <mapper resource="test/CarMapper.xml"/>
</mappers>
```
第二种：url，从指定的url位置加载
假设CarMapper.xml文件放在d盘的根下，这个配置就需要这样写：
```xml
<mappers>
  <mapper url="file:///d:/CarMapper.xml"/>
</mappers>
```
**mapper还有其他的指定方式，后面再看！！！**

## 5. 在WEB中应用Mybatis

### 5.1 实现 

在代码或者官方文档中查看, 这里不再赘述

### 5.2 Mybatis对象作用域以及事务问题

#### 5.2.1 对象作用域
##### SqlSessionFactoryBuilder

这个对象一旦创建了SqlSessionFactory以后就不再被需要, 故最好的作用域就是局部作用域, 创建后就销毁, 以释放XML资源

##### SqlSessionFactory

一旦被创建后就应该在应用运行期间一直存在, 重复创建SqlSessionFactory被视作一种坏习惯. 最佳作用域就是应用作用域, 最简单的实现方式就是使用单例模式或者静态单例模式

##### SqlSession

每个线程都应该有他自己的SqlSession实例, 该实例是不线程安全的, 所以是不能被共享的, 最佳作用域是请求或方法作用域, 每次收到一个HTTP请求, 就可以打开一个SqlSession, 返回一个响应以后, 就关闭他, 关闭操作必须执行, 并且很重要

##### 事务问题

如果需要实现事务的提交, 以保证Sql语句都成功或失败执行, 该怎么做 ? 

1. 修改SqlSessionUtil, 创建线程本地SqlSession变量, 以此保证能在Dao中执行sql语句, 能在Service中commit
2. ThreadLocal\<?\> : 线程本地变量, 会为每个线程独立创建一个对应变量的副本, 以供整个线程使用

```java
public class SqlSessionUtil {
    private static SqlSessionFactory sqlSessionFactory;

    private static ThreadLocal<SqlSession> local = new ThreadLocal<>();

    static {
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSession openSession(){
        SqlSession sqlSession = local.get();
        if (sqlSession == null) {
            sqlSession = sqlSessionFactory.openSession();
            local.set(sqlSession);
        }
        return sqlSession;
    }

    public static void close(SqlSession sqlSession){
        if (sqlSession != null) {
            sqlSession.close();
        }
        local.remove();
    }
}
```

## 6. javassist生成类

> 用于解决Dao层重复的sql代码, 可以为类动态添加方法

### 6.1 第一个程序

1. 创建类池 `ClassPool = ClassPool.getDeault()`
2. 从类池中创建类 `pool.makeClass("package name")`
3. 为类创建方法, 需要指定返回类型, 方法名, 形参列表, 所属类 `CtMethod = new`
4. 为方法添加权限修饰符 `ctMethod.setModifiters()`
5. 为方法添加方法体 `ctClass.addMethod(ctMethod)`
6. 将方法添加到类上`ctClass.addMethod(ctMethod)`
7. 获取类的实例 newInstance()
8. 执行方法

```java
    public void testJavassist() throws CannotCompileException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // 获取类池
        ClassPool pool = ClassPool.getDefault();

        // 创建类
        CtClass ctClass = pool.makeClass("com.func.bank.test.Test");
        // 为类创建方法
        // 返回类型, 方法名, 形式参数列表, 所属类
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, ctClass);
        // 为方法添加权限修饰符
        ctMethod.setModifiers(Modifier.PUBLIC);
        // 为方法添加方法体
        ctMethod.setBody("{System.out.println(\"Hello world\");}");
        // 为类添加方法
        ctClass.addMethod(ctMethod);
        //调用方法j
        Class<?> aClass = ctClass.toClass();
        Object o = aClass.newInstance();
        Method method = o.getClass().getDeclaredMethod("execute");
        method.invoke(o);
    }
}
```

### 6.2 使用Javassist动态生成DaoImpl

```java
package com.func.javassist;

import com.func.bank.pojo.Account;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.*;
import java.util.Arrays;


public class GenerateDaoByJavassist {
    /**
     * 创建dao层的接口的代理对象, 实现其中的增删改查
     * @param sqlSession 会话实例
     * @param daoInterface 需要被代理的接口
     * @return 接口的代理类
     */
    public static Object getMapper(SqlSession sqlSession, Class daoInterface){
        // 生成代理类
        ClassPool classPool = ClassPool.getDefault();
            // 拼接代理类的完整包名
        CtClass ctClass = classPool.makeClass(daoInterface.getPackageName() + ".impl." + daoInterface.getSimpleName() + "Impl");
            // 将接口类放入类池
        CtClass ctInterface = classPool.makeClass(daoInterface.getName());
            // 代理类代理接口类
        ctClass.addInterface(ctInterface);

        // 获取接口中的所有方法并实现
        Method[] methods = daoInterface.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            // 拼接方法的签名
            StringBuilder methodStr = new StringBuilder();
            // 返回类型, 直接获取的是Class<?>, 不是类型名
            String returnType = method.getReturnType().getName();
            methodStr.append(returnType);
            methodStr.append(" ");
            // 拼接方法名
            String methodName = method.getName();
            methodStr.append(methodName);
            methodStr.append(" ");
            // 拼接参数列表
            methodStr.append("(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                String typeName = parameterType.getName();
                methodStr.append(typeName);
                methodStr.append(" arg");
                methodStr.append(i);
                if (i != parameterTypes.length - 1) {
                    methodStr.append(", ");
                }
            }
            methodStr.append(") {");

            // 拼接方法体
                // 获取对应需要执行的Sql语句的type
            // 这行代码导致以后namespace必须是接口的全限定接口名, sqlId必须是方法名
            String sqlId = daoInterface.getName() + "." + methodName;
            // 获取SQLCommandType
            String sqlCommandType = sqlSession.getConfiguration().getMappedStatement(sqlId).getSqlCommandType().name();

            if ("SELECT".equals(sqlCommandType)) {
                // 获取session对象
                methodStr.append("org.apache.ibatis.session.SqlSession sqlSession = com.func.bank.utils.SqlSessionUtil.openSession();");
                // 执行SQL语句
                methodStr.append("Object obj = sqlSession.selectOne(\""+ sqlId + "\"," + "arg0);");
                methodStr.append("return (" + returnType + ") obj;");
            } else if ("UPDATE".equals(sqlCommandType)) {
                // 获取session对象
                methodStr.append("org.apache.ibatis.session.SqlSession sqlSession = com.func.bank.utils.SqlSessionUtil.openSession();");
                // 执行SQL语句
                methodStr.append("int count = sqlSession.update(\""+ sqlId + "\"," + "arg0);");
                methodStr.append("return count;");
            }
            methodStr.append("}");
            System.out.println(methodStr);

            try {
                // 将方法添加到类中
                CtMethod ctMethod = CtMethod.make(methodStr.toString(), ctClass);
                ctMethod.setModifiers(Modifier.PUBLIC);
                ctClass.addMethod(ctMethod);
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            // 创建实体类
            Class<?> aClass = ctClass.toClass();
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
            Object o = declaredConstructor.newInstance();
            return o;
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
```

## 7. MyBatis中的接口代理机制

> 很不幸的消息 : 第六章中的对于Dao层重复代码的抽象, 在Mybatis中已经实现了

- 但是这个时候一般不写Dao作为标注, 而是Mapper, 也是为了能说明这是MyBatis实现的Dao层接口

```java
AccountDao accountDao = (AccountDao)sqlSession.getMapper(AccountDao.class)
```

## 8. 一些MyBatis使用技巧

> 主要是一些便捷的设置和SQL拼接上的说明

### 8.1 IDEA设置模板文件

- setting -> Editor -> File and Code Templates

- mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <properties resource="jdbc.properties"/>

    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--一定要注意这里的路径哦！！！-->
    </mappers>
</configuration>
```

- sqlMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace = >

</mapper>
```

### 8.2 \#{}和${}

> 前者就是我们经常使用的那个, 它和后者的区别在于底层的实现, 前者是PreparedStatement, 后者是Statement, 也就是说, 这两者不能混用, 带有前者的是预编译后再传值, 能预防sql注入, 后者先传值后编译, 存在sql注入

> ${}的用法, 其实就是sql拼接的应用
#### 8.2.1 两者的区别

- 在#{value}中填入的值, 最后在SQL语句中是以'value'的形式存在的
    - 执行后生成的sql语句中带有 ? 占位符, 用于接收传入的值
- 在${value}中填入的值, 最后在SQL中是直接以value的形式存在的
    - 执行后生成的sql语句是没有占位符的完全的sql语句

#### 8.2.2 ${}的特殊用法

##### 拼接表名

- 实际开发中, 存在分库分表, 这个时候往往会有需要通过拼接表名
- 通过查询的sql语句, 很容易知道, 这个时候不能通过#{}去填入表名, 这样表名会带'', 所以只能通过${}拼接表名

##### 批量删除

- 通过sql语句中的in(list)实现删除, 这个时候也是不能传入#{}, 会带有''

##### 模糊查询

- 与上同理, 但这个时候, 由模糊查询的多种形式, 其实两种方式都可以

- ${} 

```xml
<select id="selectLikeByBrand" resultType="Car">
    select *
    from t_car
    where 
    brand like '%${brand}%'
</select>
```

- #{}

```xml
<select id="selectLikeByBrand" resultType="Car">
    select *
    from t_car
    where 
    brand like concat('%', #{brand}, '%')
</select>
```

### 8.3 别名

> 一般就用于给resultType做简化, 不然每次都需要从项目根路径开始填起

- 在mybatis-config中\<configuration>下
- 别名不区分大小写

##### 指定单个

```xml
    <typeAliases>
        <typeAlias type="com.func.mybatis.crud.Car" alias="Car"/>
    </typeAliases>
```

- 也可以不填alias, 这个时候默认是类的SimpleName()

##### 指定整个包

```xml
<!-- 在properties下面 -->
<typeAliases>
  <package name="com.powernode.mybatis.pojo"/>
</typeAliases>
```

- 这个时候会自动为这个包下的类取别名, 名字就是简类名

### 8.4 mappers

- resource：从类路径中加载
    - 从类路径开始加载
        
- url：从指定的全限定资源路径中加载
    - 绝对路径
        
- class：使用映射器接口实现类的完全限定类名
    - SQL映射文件(Mapper.xml文件)和mapper接口放在同一个目录下
    - SQL映射文件的名字也必须和mapper接口名字的完全相同
        
- package：将包内的映射器接口实现全部注册为映射器

### 8.5 插入数据的时候自动获取主键

> 主键往往是自增的, 这个时候我们往往给主键设置的值都是null, 这个时候我们就在向数据库插入数据以后, 需要反向从数据库中获取id赋值给实体对象

1. 在映射文件中, 在其中的insert标签中添加**useGeneratedKeys**属性, 值为"true"即可, 并指定主键的列名 **keyProperty**="id"

```java
<insert id="insertUseGeneratedKeys" useGeneratedKeys="true" keyProperty="id">
  insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values(null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
</insert>
```

## 9. 使用Mapper执行语句以后的特殊处理

### 9.1 多参数

- 现在我们想通过name和sex共同查询, 多参数的传入就是个问题了如果还是和之前一样直接传值, 会出错

- 错误例子

```java
/**
 * 根据name和sex查询
 * @param name
 * @param sex
 * @return
 */
List<Student> selectByNameAndSex(String name, Character sex);
```

```java
@Test
public void testSelectByNameAndSex(){
    List<Student> students = mapper.selectByNameAndSex("张三", '女');
    students.forEach(student -> System.out.println(student));
}
```

```xml
<select id="selectByNameAndSex" resultType="student">
  select * from t_student where name = #{name} and sex = #{sex}
</select>
```

![A386812C-F031-4f63-A6CE-174B7948CA31.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1660641021618-ce3ac913-fe10-45f5-9760-3e51ef2dd864.png#clientId=u7e0d60f6-6020-4&from=paste&height=237&id=u010e6c4a&originHeight=237&originWidth=1632&originalType=binary&ratio=1&rotation=0&showTitle=false&size=43805&status=done&style=shadow&taskId=ub6538fa2-2ec6-4a63-b094-3face43e433&title=&width=1632)

分析报错, 我们可以知道, 问题是name参数没有找到, 能找到的可用参数只用\[arg1, arg0, param1, param2\]

- 之前我们碰到了这个问题吗, 没有的话, 为什么 ?
    - 以前是通过将传入的参数, 存储到Map中, 再将map传入, 但是现在Dao层是MyBaties自动实现的, 所以这个方法行不通了

> 解决方法 1

- 根据报错信息, 直接将select语句中的参数改为arg0, arg1 (param1, param2) 即可

```xml
<select id="selectByNameAndSex" resultType="student">
  <!--select * from t_student where name = #{name} and sex = #{sex}-->
  <!--select * from t_student where name = #{arg0} and sex = #{arg1}-->
  <!--select * from t_student where name = #{param1} and sex = #{param2}-->
  select * from t_student where name = #{arg0} and sex = #{param2}
</select>
```

- 原理, MyBatis底层会创建一个map集合, 以arg0,/param1为key, 以上方法上的参数为value

```java
Map<String,Object> map = new HashMap<>();
map.put("arg0", name);
map.put("arg1", sex);
map.put("param1", name);
map.put("param2", sex);

// 所以可以这样取值：#{arg0} #{arg1} #{param1} #{param2}
// 其本质就是#{map集合的key}
```

### 9.2 @Param 注解

> 这就是第二种解决方案

第一种解决方案, 实际上使用了MyBatis自己创建的map, 我们也可以自己创建map

- 在Mapper的参数上, 指定这个变量的在Map中的key
```java
    /**
     * 根据name和age查询
     * @param name
     * @param age
     * @return
     */
    List<Student> selectByNameAndAge(@Param(value="name") String name, @Param("age") int age);
```

- 映射文件以{Key}中 key 去map中查找值
```xml
<select id="selectByNameAndAge" resultType="student">
  select * from t_student where name = #{name} and age = #{age}
</select>
```

## 10. MyBatis查询语句专题

### 10.1 一些常见报错的解释

- TooManyResultException
![8D921E96-56B6-48a2-A605-0F27EE2D38C6.png](https://cdn.nlark.com/yuque/0/2022/png/21376908/1660816549528-b600f5a9-81b4-4725-87c7-b933ee60ca39.png#clientId=u85e451ff-5650-4&from=paste&height=320&id=u1669660d&originHeight=320&originWidth=1357&originalType=binary&ratio=1&rotation=0&showTitle=false&size=51856&status=done&style=shadow&taskId=u2164854e-7c28-4d8b-bd6d-8495d4a6566&title=&width=1357)
> 解释 : 只是用了Object接收List, 非集合对象, 无法接收多个返回结果

### 10.2 使用Map集合接收返回结果

- 使用Map\<String, Object> 作为返回值
- 这个时候就会将返回结果以键值对的形式存储到返回的Map中
- 这个时候的select的返回类型, 需要设定为 "map", 这个是MyBatis内置的别名

### 10.3 使用List\<Map> 作为返回结果

- 这个就对应着前面的返回多个Object的时候需要使用List<>接收是一样的情况
- `resultType`同样设置为map

### 10.4 使用Map\<String, Map>

- 使用这个存储结构, 一般是将id作为外层Map的key, 这样方便查找

- 实现

1. 创建Mapper, 这里需要指定map的key是查找到的对象的什么字段, 一般就是指定id作为key

```java
public interface CarMapper {
    /**
     * 设定map的key是id, value是Car
     * @return
     */
    @MapKey("id")
    Map<Long, Map<String, Object>> selectAll();
}
```

```java
CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    @Test
    public void testMapSelect(){
        Map<Long, Map<String, Object>> map = mapper.selectAll();
        System.out.println(map);
    }
```

```xml
        <select id="selectAll" resultType="map">
        SELECT id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType
        from t_car
    </select>
```

### 10.5 resultMap结果映射

- 主要是为了解决查询结果的列名和对象的字段名不同的问题
- 这个问题一般是用数据库中的列名命名风格往往是\_, 下划线式, 但是java中的属性的命名一般是驼峰命名

- 三种解决方式
    - 在查询语句中使用as给列起别名
    - 使用resultMap进行结果映射, 在\<mapper>标签中
    - 开启驼峰命名自动映射, 在\<settings>标签中
    
- 使用resultMap映射

```xml
<mapper namespace = "com.func.mybatis.select.CarMapper">
    
    <resultMap id="carResult" type="car">
        <id property="id" column="id"/>
        <result property="carNum" column="car_num"/>
        <result property="guidePrice" column="guide_price"/>
        <result property="produceTime" column="produce_time"/>
        <result property="carType" column="car_type"/>
    </resultMap>

    <select id="selectAllByResultMap" resultMap="carResult" resultType="car">
        SELECT * from t_car
    </select>
</mapper>
```

```java
public interface CarMapper {
    List<Car> selectAllByResultMap();
    /**
     * 设定map的key是id, value是Car
     * @return
     */
    @MapKey("id")
    Map<Long, Map<String, Object>> selectAll();
}
```

- 自动映射

```xml
    <!--在properties后面-->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
```

## 11. 动态SQL

- 其实之前也介绍过动态SQL的实现, 通过\${}拼接, 但是这种实现, 过于繁琐, 如果我们想在查询过程中动态加上某个where条件, 通过${}拼接实现, 是很繁琐且奇怪的实现方式
- MyBatis为我们提供了实现便捷实现动态SQL的功能

### 11.1 if标签

- 该标签的主要功能是测试test中的语句, 如果满足条件, 才会为sql添加value中的内容

```xml
    <select id="selectDynamic" resultType="car">
        SELECT * from t_car WHERE
        <if test="brand != null and brand != ''">
            brand LIKE #{brand}"%" 
        </if>
        <if test="carType != null and carType != ''">
            and car_type = #{carType} 
        </if>
        <if test="carNum != null and carNum != ''">
            and car_num = #{carNum}
        </if>
    </select>
```

```java
    List<Car> selectDynamic(@Param("carNum") String carNum, @Param("carType") String carType, @Param("brand") String brand);


        List<Car> cars = mapper.selectDynamic("102", "氢能源", "丰");
        System.out.println(cars);
```

> 但是使用这个查询有个问题, 如果第一句或最后一句为空, 则会有多余的and出现

- 我们可以在 where的后面添加上 0 = 0 这样的恒等式来解决前面的多余的and , MyBatis也考虑到了这一点, 给了更便捷的消去前面的多余的and的方法, where标签

### 11.2 where标签

- 功能 : 让where子句更加智能, 如果条件都为空的时候, 能保证不会生成where子句, 自动去除 **前面** 的多余的and和or

```xml
    <select id="selectDynamic" resultType="car">
        SELECT * from t_car
        <where>
            <if test="brand != null and brand != ''">
                brand LIKE #{brand}"%"
            </if>
            <if test="carType != null and carType != ''">
                and car_type = #{carType}
            </if>
            <if test="carNum != null and carNum != ''">
                and car_num = #{carNum}
            </if>
        </where>
    </select>
```

> 但是问题是, 它并不能去除句末的多余的and或or

### 11.3 trim标签

- 更加智能的, 能指定在句前添加内容和去除多余前缀或多余后缀的标签
    - prefix : trim标签中的语句前添加内容
    - suffix : 在trim标签中的语句后添加内容
    - prefixOverrides : 前缀去掉的内容
    - suffixOverrides : 后缀去掉的内容
    
```xml
    <select id="selectDynamic" resultType="car">
        SELECT * from t_car
        <trim prefix="where" prefixOverrides="and|or" suffixOverrides="and|or">
            <if test="brand != null and brand != ''">
                brand LIKE #{brand}"%"
            </if>
            <if test="carType != null and carType != ''">
                and car_type = #{carType}
            </if>
            <if test="carNum != null and carNum != ''">
                and car_num = #{carNum}
            </if>
        </trim>
    </select>
```

### 11.4 set标签

- 主要用在update操作上, 以实现只更新我们填入了的字段

```xml
<update id="updateWithSet">
  update t_car
  <set>
    <if test="carNum != null and carNum != ''">car_num = #{carNum},</if>
    <if test="brand != null and brand != ''">brand = #{brand},</if>
    <if test="guidePrice != null and guidePrice != ''">guide_price = #{guidePrice},</if>
    <if test="produceTime != null and produceTime != ''">produce_time = #{produceTime},</if>
    <if test="carType != null and carType != ''">car_type = #{carType},</if>
  </set>
  where id = #{id}
</update>
```

### 11.5 choose when otherwise

- 三个标签是一起使用的
- 能实现递进式的查询, 如果有brand属性, 就按照brand属性查, 如果没有就按照price查, 如果还没有就按照type查

```xml
<choose>
  <when></when>
  <when></when>
  <when></when>
  <otherwise></otherwise>
</choose>
```

> 相当于 if else default

 - **只会有一个分支被执行**

```xml
    <select id="selectWithChoose" resultType="car">
        SELECT * from t_car
        <where>
            <choose>
                <when test="brand != null and brand != ''">
                    brand = #{brand}
                </when>
                <when test="guidePrice != null and guidePrice != ''">
                    and guide_price = #{guidePrice}
                </when>
                <otherwise>
                    and car_type = #{carType}
                </otherwise>
            </choose>
        </where>
    </select>
```


```java
    List<Car> selectWithChoose(@Param("guidePrice") String guidePrice, @Param("carType") String carType, @Param("brand") String brand);
```

### 11.6 foreach标签

- 用于动态生成批量操作, 比如

```mysql
delete from t_car where id in(1,2,3);
delete from t_car where id = 1 or id = 2 or id = 3;

// 或者

insert into t_car values
  (null,'1001','凯美瑞',35.0,'2010-10-11','燃油车'),
  (null,'1002','比亚迪唐',31.0,'2020-11-11','新能源'),
  (null,'1003','比亚迪宋',32.0,'2020-10-11','新能源')
```

这样的重复格式的语句

#### 批量删除

- 使用in来删除

```xml
    <delete id="deleteWithForeach">
        delete from t_car WHERE id in
        <foreach collection="ids" item="id" open="(" close=")" separator=", ">
            #{id}
        </foreach>
    </delete>
```

- 属性解释
    - collection：集合或数组
    - item：集合或数组中的元素
    - separator：分隔符
    - open：foreach标签中所有内容的开始
    - close：foreach标签中所有内容的结束

```java
    int deleteWithForeach(@Param("ids") Integer[] ids);
```

- 通过 or 删除

```xml
    <delete id="deleteWithForeach">
        delete from t_car WHERE
        <foreach collection="ids" item="id" separator="or">
            id = #{id}
        </foreach>
    </delete>
```

#### 批量增加

```xml
    <insert id="insertBatchForeach">
        INSERT INTO t_car VALUES 
        <foreach collection="cars" item="car" separator=",">
            (null, #{car.carNum}, #{car.brand}, #{car.guidePrice}, #{car.produceTime}, #{car.carType})
        </foreach>
    </insert>
```

```java
        Car car1 = new Car("1022", "兰博基尼", 100.0,"1998-10-11", "燃油车" ,  null);
        Car car2 = new Car("1022", "兰博基尼", 100.0,"1998-10-11", "燃油车" ,  null);
        Car car3 = new Car("1022", "兰博基尼", 100.0,"1998-10-11", "燃油车" ,  null);
        List<Car> cars = Arrays.asList(car1, car2, car3);
        System.out.println(mapper.insertBatchForeach(cars));
        SqlSessionUtil.openSession().commit();
```

```java
    int insertBatchForeach(@Param("cars") List<Car> cars);
```

### 11.7 sql标签和include标签

- sql用于声明sql片段
- include用于添加sql到sql语句中
- 主要是用于代码复用

```xml
<sql id="carCols">id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType</sql>

<select id="selectAllRetMap" resultType="map">
  select <include refid="carCols"/> from t_car
</select>

<select id="selectAllRetListMap" resultType="map">
  select <include refid="carCols"/> carType from t_car
</select>

<select id="selectByIdRetMap" resultType="map">
  select <include refid="carCols"/> from t_car where id = #{id}
</select>
```

## 12. 高级映射和延迟加载

- 本章节的主要是讲解级联查询, 及多表联查, 还有mybatis的延迟加载

> pojo类

- Student
```java
@Data
public class Student {
    private Integer sid;
    private String sname;
    private Clazz clazz;
}
```

- Clazz
```java
@Data
public class Clazz {
    private Integer cid;
    private String cname;
}
```

### 12.1  多对一

- 需求 : t_student表中有cid这一个属性, 我们希望查出来对应的clazz, 并将其赋值给Student类中的Clazz属性

- 测试类
```java
    StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
    @Test
    public void testMapping() {
        Student student = mapper.selectBySid(2);
        System.out.println(student);
    }
```

#### 级联属性映射

> 这种查询方式, 其实就是直接用sql语句实现多表联查的直接搬运, 通过join语句, 查出来两个表的属性, 然后再在resultmap中将查出来的对应的结果赋值给clazz类返回

- SQL
```xml
    <!--SQL-->
    <select id="selectBySid" resultType="Student" resultMap="studentResultMap">
        SELECT s.*, c.*
        from t_student s JOIN t_clazz c
            ON c.cid = s.cid
        WHERE sid = #{sid}
    </select>
```

- ResultMap
```java
    <resultMap id="studentResultMap" type="Student">
        <id column="sid" property="sid"/>
        <result property="sname" column="sname"/>
        <result property="clazz.cid" column="cid"/>
        <result property="clazz.cname" column="cname"/>
    </resultMap>
```

- StudentMapper
```java
public interface StudentMapper {
    Student selectBySid(@Param("sid") Integer sid);
}
```
#### 通过配置association

> 这种方式是对上面的多表联查的语法糖, 有更清晰的结构, 再resultMap中有体现返回的是Clazz类, 并且为其中的属性赋值的过程

- SQL ==> 不变

- ResultMap
```java
    <resultMap id="studentResultMap" type="Student">
        <id column="sid" property="sid"/>
        <result property="sname" column="sname"/>
        <association property="clazz" javaType="Clazz">
            <id property="cid" column="cid"/>
            <result property="cname" column="cname"/>
        </association>
    </resultMap>
```

#### 分步查询

> 将原先的查询语句分割成了两个子模块, 主查询语句, 子查询语句, 相比于第二种做法这里更加模块化, 能提高代码的复用性

- 实现步骤 :
    - 在association的基础上, 添加column, property, javaType, select四个属性
        - javaType : 返回的类型
        - select : 用于查询返回需要类型的sql语句的sqlId, 必须是全限定的id
        - column : 主select语句中的列, 用于关联子表, 也是select语句查询传入的参数
            - property : 类中对应的属性名

- ClazzMapper
```xml
<mapper namespace = "com.func.mybatis.mapper.ClazzMapper">

    <select id="selectByCid" resultType="Clazz">
        SELECT *
        from t_clazz
        where cid = #{cid}
    </select>

</mapper>
```

- StudentSQL
```xml
    <select id="selectBySid" resultMap="studentResultMap" resultType="Student">
        select *
        from t_student
        where sid = #{sid}
    </select>
```

- ResultMap
```xml
    <resultMap id="studentResultMap" type="Student">
        <id column="sid" property="sid"/>
        <result property="sname" column="sname"/>
        <association property="clazz" javaType="Clazz"
                    select="com.func.mybatis.mapper.ClazzMapper.selectByCid"
                    column="cid">
        </association>
    </resultMap>
```

- 模块化和复用性的体现
    - 可以直接复用已经有的ClazzMapper中的根据cid查询Clazz对象的方法
    - 代码更加清晰
    - 并且有利于之后实现懒加载

### 12.2 多对一的懒加载

> 懒加载(延迟加载) : 即按需加载, 只有在用到对应的属性的时候, 才加载对应的查询语句, 比如在这里, 如果我们没有用到clazz这个属性, 那么ClazzMapper中的selectByCid就不会被调用查询, 只有用到的时候才会调用
#### 非全局设置

- 在association中的fetchType = "lazy" 即可
```xml
    <resultMap id="studentResultMap" type="Student">
        <id column="sid" property="sid"/>
        <result property="sname" column="sname"/>
        <association property="clazz" javaType="Clazz"
                    select="com.func.mybatis.mapper.ClazzMapper.selectByCid"
                    column="cid"
                    fetchType="lazy">
        </association>
    </resultMap>
```

#### 全局懒加载

在核心配置文件`mybatis-config.xml`中添加

```xml
    <settings>
        <setting name="lazyLoadingEnabled" value="true"/>
    </settings>
```

加载了全局懒加载以后, 如果想让某个语句不是懒加载, 可以通过设置 association的fetchType属性为eager即可

```xml
        <association property="clazz" javaType="Clazz"
                    select="com.func.mybatis.mapper.ClazzMapper.selectByCid"
                    column="cid"
                    fetchType="eager">
        </association>
```

### 12.3 一对多

> 也就是一个对象中包含多个其他对象的时候

- Clazz
```java
public class Clazz {
    private Integer cid;
    private String cname;
    private List<Student> stus;
}
```

#### 普通查询

> 也就是和多对一的时候一样的, 直接将SQL中多表联查的步骤搬到mybatis中

- 这里使用的不再是association而是collection, 使用方式和colletion基本一致, 不过将javaType换成了ofType

- SQL
```xml
    <select id="selectStusByCid" resultType="Clazz" resultMap="resultMapper">
        SELECT c.*, s.*
        FROM t_clazz c
        JOIN t_student s 
        ON c.cid = s.cid
        WHERE c.cid = #{cid}
    </select>
```

- ResultMap
```xml
    <resultMap id="resultMapper" type="Clazz">
        <id property="cid" column="cid"/>
        <result property="cname" column="cname"/>
        <collection property="stus" ofType="Student">
            <id property="sid" column="sid"/>
            <result property="sname" column="sname"/>
        </collection>
    </resultMap>
```

- test
```java
    public void testCollection(){
        Clazz clazz = clazzMapper.selectStusByCid(1001);
        System.out.println(clazz);
    }
```

#### 分步查询

> 和多对一的情况下的分步查询基本一致

- StudentMapper新增的方法
```java
    Student selectByCid(@Param("cid") Integer cid);
```
- StudentMapper.xml新增的查询语句
```xml
    <select id="selectByCid" resultType="Student">
        SELECT *
        FROM t_student
        WHERE cid = #{cid}
    </select>
```

- Sql
```xml
    <select id="selectStusByCid" resultMap="resultMapper" resultType="Clazz">
        SELECT *
        FROM t_clazz
        WHERE cid = #{cid}
    </select>
```
- ResultMap
```xml
    <resultMap id="resultMapper" type="Clazz">
        <id property="cid" column="cid"/>
        <result property="cname" column="cname"/>
        <collection property="stus" ofType="Student"
                    select="com.func.mybatis.mapper.StudentMapper.selectByCid"
                    column="cid"/>
    </resultMap>
```
- 新增的接口方法
```java
    Clazz selectStusByCid(@Param("cid") Integer cid);
```
## 13. Mybatis的缓存

> 缓存 : cache , 通过减少IO的次数, 来提高程序执行的效率

- mybatis的缓存 : 将select查询的结果, 放到缓存 (内存中去) , 下次再次执行这条SQL语句的时候, 直接从缓存中读取而不再查数据库, 减少了IO和执行繁琐的查找算法的时间
- 缓存分为
    - 一级缓存 : 将查到的数据存储到SqlSession中
    - 二级缓存 : 将查到的数据存储到SqlSessionFactory中
    - 集成第三方的缓存 : 如EhCache
- 无论哪级缓存, 在数据库发生变化以后, 缓存都会作废

### 13.1 一级缓存

- 一级缓存是默认开启的, 不需要任何配置, 只要用同一个SqlSession查询同一条DQL语句就会走缓存
-  因为缓存是存在SqlSession中的, 所以更换了Session以后, 也会重新查询

```java
    @Test
    public void testFirstCache() throws Exception{
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        System.out.println(mapper.selectStusByCid(1001));
        // 完全一样的语句
        System.out.println(mapper.selectStusByCid(1001)); // 只这次执行并没有执行SQL语句
        // 改变查询内容
        System.out.println(mapper.selectStusByCid(1002));
        //  更换SqlSession
        ClazzMapper mapper1 = sqlSessionFactory.openSession().getMapper(ClazzMapper.class);
        System.out.println(mapper1.selectStusByCid(1001));
        // 对数据库进行增删改查
        System.out.println(mapper.insertClazz("高三六班", 1003));
        sqlSession.commit();
        // 再次进行查询
        System.out.println(mapper.selectStusByCid(1001));
    }
```

### 13.2 二级缓存

- 二级缓存的范围是SqlSessionFactory
- 使用二级缓存需要满足以下的条件
    1. `<setting name="cacheEnabled" value="true">`全局性开启或关闭所有映射器配置文件中已配置的任何缓存, 默认为true
    2. 在使用二级缓存的SqlMapper.xml文件中添加配置 `<cache />`
    3. 使用二级缓存的实体类对象必须是可序列化的, 也就是必须实现Serializable接口
    4. SqlSession对象关闭或提交以后, 一级缓存中的数据才会被写入二级缓存, 此时二级缓存才是可用的
    
- ClazzMapper.xml
```xml
<mapper namespace = "com.func.mybatis.cache.mapper.ClazzMapper">

    <!--开启二级缓存-->
    <cache/>

    <resultMap id="resultMapper" type="Clazz">
        <id property="cid" column="cid"/>
        <result property="cname" column="cname"/>
        <collection property="stus" ofType="Student"
                    select="com.func.mybatis.cache.mapper.StudentMapper.selectByCid"
                    column="cid"/>
    </resultMap>

    <select id="selectStusByCid" resultMap="resultMapper" resultType="Clazz">
        SELECT *
        FROM t_clazz
        WHERE cid = #{cid}
    </select>

    <insert id="insertClazz">
        INSERT INTO t_clazz
            VALUES (#{cid}, #{cname})
    </insert>

    <select id="selectAll" resultType="Clazz">
        select * from t_clazz
    </select>

</mapper>
```

- Clazz.java
```java
@Data
public class Clazz implements Serializable{
    private Integer cid;
    private String cname;
    private List<Student> stus;
}
```

- Test
```java
    public void testSecondCache() throws Exception{
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        List<Clazz> clazzes = mapper.selectAll();
        System.out.println(clazzes);
        sqlSession.close();
        ClazzMapper mapper1 = sqlSessionFactory.openSession().getMapper(ClazzMapper.class);
        List<Clazz> clazzes1 = mapper1.selectAll(); // 直接返回了在缓存中的结果
        System.out.println(clazzes1); 
    }
```

- cache的属性
    1. eviction：指定从缓存中移除某个对象的淘汰算法。默认采用LRU策略。
        1. LRU：Least Recently Used。最近最少使用。优先淘汰在间隔时间内使用频率最低的对象。(其实还有一种淘汰算法LFU，最不常用。)
        2. FIFO：First In First Out。一种先进先出的数据缓存器。先进入二级缓存的对象最先被淘汰。
        3. SOFT：软引用。淘汰软引用指向的对象。具体算法和JVM的垃圾回收算法有关。
        4. WEAK：弱引用。淘汰弱引用指向的对象。具体算法和JVM的垃圾回收算法有关。
            
    2. flushInterval：
        1. 二级缓存的刷新时间间隔。单位毫秒。如果没有设置。就代表不刷新缓存，只要内存足够大，一直会向二级缓存中缓存数据。除非执行了增删改。
    3. readOnly：
        1. true：多条相同的sql语句执行之后返回的对象是共享的同一个。性能好。但是多线程并发可能会存在安全问题。
        2. false：多条相同的sql语句执行之后返回的对象是副本，调用了clone方法。性能一般。但安全。
    4. size：
        1. 设置二级缓存中最多可存储的java对象数量。默认值1024。
        
        
### 13.3 Mybatis集成EhCaChe

> 用于替代mybatis自带的二级缓存, 一级缓存是不能替代的, 算是第三方的缓存组件

- 导入依赖
```xml
        <!--mybatis集成ehcache的组件-->
        <dependency>
          <groupId>org.mybatis.caches</groupId>
          <artifactId>mybatis-ehcache</artifactId>
          <version>1.2.2</version>
        </dependency>
```

- 在项目的根路径下添加配置文件echcache.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">
    <!--磁盘存储:将缓存中暂时不使用的对象,转移到硬盘,类似于Windows系统的虚拟内存-->
    <diskStore path="e:/ehcache"/>
    <!--defaultCache：默认的管理策略-->
    <!--eternal：设定缓存的elements是否永远不过期。如果为true，则缓存的数据始终有效，如果为false那么还要根据timeToIdleSeconds，timeToLiveSeconds判断-->
    <!--maxElementsInMemory：在内存中缓存的element的最大数目-->
    <!--overflowToDisk：如果内存中数据超过内存限制，是否要缓存到磁盘上-->
    <!--diskPersistent：是否在磁盘上持久化。指重启jvm后，数据是否有效。默认为false-->
    <!--timeToIdleSeconds：对象空闲时间(单位：秒)，指对象在多长时间没有被访问就会失效。只对eternal为false的有效。默认值0，表示一直可以访问-->
    <!--timeToLiveSeconds：对象存活时间(单位：秒)，指对象从创建到失效所需要的时间。只对eternal为false的有效。默认值0，表示一直可以访问-->
    <!--memoryStoreEvictionPolicy：缓存的3 种清空策略-->
    <!--FIFO：first in first out (先进先出)-->
    <!--LFU：Less Frequently Used (最少使用).意思是一直以来最少被使用的。缓存的元素有一个hit 属性，hit 值最小的将会被清出缓存-->
    <!--LRU：Least Recently Used(最近最少使用). (ehcache 默认值).缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存-->
    <defaultCache eternal="false" maxElementsInMemory="1000" overflowToDisk="false" diskPersistent="false"
                  timeToIdleSeconds="0" timeToLiveSeconds="600" memoryStoreEvictionPolicy="LRU"/>
</ehcache>
```

- SqlMapper.xml文件下的cache, type选择EhcacheCache
```xml
    <!--开启二级缓存-->
    <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```

- 使用和自带的二级缓存是一样的

## 14. 逆向工程

未来有需求的时候再看吧 [[Java/MyBatis/官方文档/document/MyBatis#十五、MyBatis的逆向工程]]

## 15. pageHelper

### 配置

- 添加依赖
```xml
        <!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.3.2</version>
        </dependency>
```

- 在mybatis-config.xml中配置插件 : 位置在typeAliases后面
```xml
    <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
    </plugins>
    
```

### 使用

- 使用PageHelper开启分页, 开启以后, 这个时候查出来的cars结果集已经是分页后的结果了
- 同时如果想在开启分页以后获取特定页的数据, 再在之后获取pageinfo就行
```java
        PageHelper.startPage(2, 2);

        List<Car> cars = mapper.selectAll();
//        System.out.println(cars);

        PageInfo<Car> pageInfo = new PageInfo<>(cars, 5);
        System.out.println(pageInfo);
```