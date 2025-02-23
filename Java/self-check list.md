# MyBatis

## 使用逆向生成以后, 报binding相关错误

### properties文件错误

- **properties文件中不要加双引号 !!!**

- 示例正确文件
```properties
spring.application.name=sb3-005-springMVC


spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.zaxxer.hikari.HikariDataSource

# 指定mapper文件
mybatis.mapper-locations=classpath:mapper/*.xml

# 驼峰映射
mybatis.configuration.map-underscore-to-camel-case=true
```


