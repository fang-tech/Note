# JDBC

## 基础篇

### 2. JDBC的概述

java只实现了连接数据库的接口的规范, 而没有定义详细的实现, 详细的实现交给实际的数据库场上实现, 所以想通过Java连接特定的数据库, 实际上需要下载对应厂商的jar包

### 3. Quick-start

```java
// 1. 指明使用的数据库, 为其创建驱动
Class.forName("com.mysql.cj.jdbc.Driver");

// 2. 建立与数据库的连接
String url = "jdbc:mysql://localhost:3306/atguigu";
String username = "root";
String password = "root";
Connection connection = DriverManager.getConnection(url, username, password);

// 3. 创建承载语句的对象
Statement statement = connection.createStatement();

// 4. 编写SQL语句, 并执行, 同时接收返回
String sql = "SELECT * FROM t_emp";
ResultSet resultSet = statement.executeQuery(sql);

// 5. 处理查询结果
while (resulteSet.next()) {
    // 获取查询的结果
    int empId = resultSet.getInt("emp_id");
    String empName = resultSet.getString("emp_name");
    double empSalary = resultSet.getDouble("emp_salary");
    int empAge = resultSet.getInt("emp_age");
    System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
}

// 6. 释放资源
resulteSet.close();
statement.close();
connection.close();
```

### 4. 对于API的讲解

#### 4.1 Driver -> 注册驱动

`Class.forName("com.mysql.cj.jdbc.Driver");`, 可以替换为`Class.forName(new Driver());`, 原本的写法填入的参数是资源路径, 我们可以直接传入驱动对象

> 也可以直接省略不写, 因为从JDK6开始后, 都会自动注册驱动

- 驱动程序用于和数据库进行通讯

#### 4.2 Connection -> 代表和数据库的一次连接

- 用于事务级的管理, 能执行`rollback`和`commit`
- 建立连接需要指明url, username, password
  - url = "jdbc:mysql://ip:port/database?字段1=...&字段2=..."
- 使用后记得关闭, 避免资源泄露

#### 4.3 Statement

Statement可以通过Connection对象创建

但是会有sql注入的问题

- 这个问题发生于想动态查询的时候

- ```JAVA
  // 4. 编写SQl语句, 并执行, 以及接收返回
  Scanner scanner = new Scanner(System.in);
  String name = scanner.nextLine();
  String sql = "SELECT * FROM t_emp WHERE emp_name = '" + name+ "'";
  ResultSet resultSet = statement.executeQuery(sql);
  ```

- 这个时候输入的name如果是abc' or '1' = '1, 会在WHERE条件处构建一个永真句, 从而查询到所有内容

#### 4.4 PreparedStatement

Staatement的子类, 独有的特性

- 预编译SQL语句 => 重复执行的时候, 不用每次都重新编译运行, 提高运行效率
- 能够防止SQL注入

使用上的特点

- 不支持无参构造
- 通过?占位符说明哪个位置需要传参
- 再在后续set, 将参数传给指定位置的占位符

```java
String sql = "SELECT * FROM t_emp WHERE emp_name = ?";
PreparedStatement preparedStatement = connection.prepareStatement(sql);

preparedStatement.setString(1,name);
ResultSet resultSet = preparedStatement.executeQuery();


```

> 如果这个时候传入 "abc' or  '1' = '1 ", 原语句会被修改, 其中的特殊字符会被做转义, 从而只作为字符串的内容传入, 比如这句会变为 "abc\\' or \\'1\\' = \\'1"
> 同时传入的时候set的是String, 会自动加上 '' 包裹

#### 4.5 ResultSet

用于接收查询语句返回的多行结果, 可以通过getInt(getDouble...)等方法从结果集中获取数据

关键方法 : `ResultSet.next()` 返回的是`boolean`类型, 在还没有执行这个语句的最初, ResultSet指向的是`第-1行`, 该方法会先判定是否有下一行, 如果有, 移动到下一行, 用于给使用者获取下一行的数据,

用于获取一行的数据

### 5. 用PreparedStatement执行CRUD

#### 5.1 查询单行单列

#### 5.2 查询单行多列

#### 5.3 查询多行多列

#### 5.4 插入删除修改数据

将执行查询的`executeQuery()`修改为`executeUpdate()`

这个时候接收的返回int result的内容是收到影响的行数, 如果是0, 说明失败

## 进阶篇

### 7. JDBC拓展

#### 7.1 实体类和ORM

#### 7.2 主键回显

  由于执行插入语句即`preparedStatement.executeUpdate()`, 返回的只是受影响的行数, 但是我们现在有个使用的情景是, 我们插入数据的时候, 并不会指定主键的值, 那么这个时候, 实体类其中Id即主键号该怎么获取 => 主键回显, 具体代码

```java
// 声明预编译对象的时候, 添加参数, 表明需要返回主键值
PreparedStatement preparedStatement = 
    DriverManager.preparedStatement(sql,Statement.RETURN_GENERATED_KEYS);

// 在成功执行插入语句后
if (result > 0) {
    sout ("成功");
    resultSet = preparedStatement.getGaneratedKeys();
    if (resultSet.next()) {
        int empId = resultSet.getInt(1);
        employee.setId(empId);
    }
} else {
    sout("失败");
}
```

#### 7.3 批量操作

- 原因 : 如果我现在需要插入一万条数据, 按照之前的写法, 我们需要重复执行executeUpdate一万次, 从而插入一万条数据, 这个时候会与数据库连接一万次, 网络上的往返也有一万次, 效率极低

- 解决策略 : 缓存, 建立buffer, 每次添加数据后, 并不是将直接`executeUpdate()`而是`AddBatch()`, 将数据添加到批处理的缓存区, 最后执行一次`executeBatch()`将语句发送往数据库\

- 操作 :
  ```java
  // step 1 : 将在与数据库建立连接的url最后的添加参数的位置加入?rewriteBatchedStatements=true
  DriverManager.getConnection("jdbc:mysql:///atguigu?rewriteBatchedStatements=true"
                              , "root", "root");
  // step 2 : 将原来的executeUpdate的位置换为addBatch()
  preparedStatement.addBatch();
  // step 3 : 最后在所有语句的最后写入executeBatch()
  preparedStatement.executeBatch();
  ```

> 需要执行批量操作的SQL语句最后不能有 ";"

