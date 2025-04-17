# 1. 数据库概述

## 数据库相关概念

**DB: 数据库**

**DBMS: 数据库管理系统 (Database Management System)**

**SQL: 结构化查询语言 (Structured Query Language)**

## 关系型数据库设计规则

- E - R (entity - relationship, 实体-联系) 模型中有三个主要概念是: `实体集`, `属性`, `联系集`

### 表的关联关系

- 一对一关联
  - 一般用于将一张表拆成两张表, 一张存储常用的, 一张存储不常用的

- 一对多关联
- 多对一关联
- 自我引用

# 2. MySQL环境搭建

## 启动和关闭服务

- 使用命令行工具

```bash
# 启动SQL服务
net start MySQL服务名(MySQL80)

# 关闭SQL服务
net stop MySQL服务名(MySQL80)
```

## 登录

```bash
mysql -h 主机名(localhost) -P 3306 -u root -p密码
```

> client和server在同一台机器上的时候, host其实就是127.0.0.1或者localhost, 所以-hlocalhost可以不写, 同样的, 如果端口号没有做修改, -P 3006也可以不写

## 原来就有的库

- information_schema
  - 是MySQL自带的数据库, 主要保存MySQL数据库服务器的系统信息, 比如数据库的名称, 数据库表的名称, 字段名称, 存取权限, 数据文件所在的文件夹和系统使用的文件夹等
- performance_schema
  - 是MySQL系统自带的数据库, 可以用来监控MySQL的各类性能指标
- sys
  - 主要作用是以一种更容易被理解的形式展示MySQL数据库服务器的各类性能指标, 帮助系统管理员和数据库管理人员监控MySQL的技术性能
- mysql
  - 数保存了MySQL数据库服务器运行时需要的系统信息, 比如数据文件夹, 当前使用的字符集, 约束检查信息等

## 数据库的简单使用

1. 创建自己的数据库

```sql
create database 数据库的名称;
```

> 该名称不能和存在的数据库重名

2. 查看所有的数据库

```sql
show databases;
```

> 数据库的名称是不区分大小写的

3. 使用数据库

 ```sql
 use 数据库名;
 ```

> 输入同样是不区分大小写的

4. 查看某个库的所有表格

```sql
show tables; #  use 到某个数据库中

show tables from 数据库名称;
```

5. 创建新的表格

```sql
create table <table-name>(
	name type,
    name type
);
```

> 最后的变量是不能加上" , ", 加上后会报错

6. 查看一个表的数据

```sql 
select * from <table-name>;
```

7. 添加一条记录

```sql
insert into student value(1, '张三');
insert into student value(2, '李四');
```

8. 查看表的创建信息

```sql
show create table <table-name>\G;

#	查看student表的详细创建信息
show create table student\G;
```

9. 删除表格

```sql
drop table <table-name>;
```

10. 删除数据库

```sql
drop database <database-name>;
```

## 编码问题的解决

1. 查看编码命令

```sql
show variable like 'character_%';
show variable like 'collation_%';
```

2. 修改mysql的数据目录下的my.ini配置文件

```sql
[mysql] #大概在63行左右，在其下添加 ... 
default-character-set=utf8 #默认字符集 
[mysqld] # 大概在76行左右，在其下添加 ... character-set-server=utf8
collation-server=utf8_general_ci
```

3. 重启服务

> 修改失败
>
> 在8.0版本之前, 默认字符集为latin1, utf8指向utf8mb3. 从8.0开始, 数据库的默认编码为utf8mb4

# 3. 基础的SELECT语句

### SQL分类

SQL语言在功能上分为三大类

- DDL ( Data Definnition Languages - 数据定义语言) , 这些语言定义了不同的数据库, 表, 视图, 索引等数据库对象, 还可以用来创建, 删除和修改数据库和数据表的结构
  - 主要语句有 `CREATE`, `DROP`, `ALTER`
- DML ( Data Manipulation Languages - 数据操作语言 ) , 用于添加, 删除, 更新和查询数据库记录, 并检查数据完整性
  - 主要语句关键词有`INSERT`, `DELETE`, `UPDATE`, `SELECT`
  - SELECT是SQL语言的基础, 最为重要
- DCL ( Data Control Languages - 数据控制语言 ) , 用于定义数据库, 表, 字段, 用户的访问权限和安全级别
  - 主要的语句关键字包括`GRANT`, `REVOKE`, `COMMIT`, `ROLLBACK`, `SAVEPOINT`等.

### SQL语言的规则和规范

- 列的别名, 尽量使用双引号 "" , 而且不建议省略as

- 关键字不能被缩写和分行

#### 大小规范

- MySQL 在Windows 环境是大小写不敏感的

- MySQL 在Linux环境是大小写敏感的
  - 数据库名, 表名, 表的别名, 变量名是严格区分大小写的
  - 关键字, 函数名, 列名(字段名), 列的别名是忽略大小写的
- 推荐的统一书写规范
  - 数据库名, 表名, 表的别名, 字段名, 字段别名等都小写
  - SQL关键字, 函数名, 绑定变量等都大小写

#### 注释

```SQL
#注释文字 (MySQL特有的方式) 
-- 注释文字 (--后必须有一个空格) 
/* 多行注释 */
```

#### 命名规则

- 数据库, 表名不得超过30个字符, 变量名限制为29个
- 特殊字符里只有\_
- 不能有任何形式的重名现象
- 保持字段名和类型的一致性, 在命名字段并其指定数据类型的时候一定要保证一致性

```sql
CREATE TABLE `order`()
# 其中order使用``飘号, 因为order和系统关键字或系统函数名等预定义标识符重名了
```

```sql
SELECT id AS "编号" from t_stu;
```

> 起别名的时候, as可以省略
>
> 如果字段别名中没有空格, 可以省略 " "

#### 数据导入指令

```sql
mysql> source <path\**.sql>
```

## 基本的SELECT语句

#### SELECT....

```sql
SELECT 1; # 没有任何字句
SELECT 9/2;

# 会返回一个一行一列的结果集, 常用来测试数据库链接是否正常, 是否可以正常使用
```

#### SELECT ... FROM

- 语法 :
  ```sql
  SELECT	标识选择哪些列
  FROM	标识从哪个表中选择
  ```

- 选择全部列 :
  ```sql
  SELECT *
  FROM departments;
  ```

> 一般情况下,  除非需要获取和使用表中的所有字段, 最好不要使用 \* 通配符, 会查询到不必要的信息降低程序的运行效率, 优势是, 当不知道,所需要的列的名称的时候, 可以通过它们获取

#### 列的别名

- 重命名一个列, 便于计算
- 紧跟列名, 也可以在列名和别名之间加入关键字AS, 别名使用双引号, 以便在别名中包含空格或特殊的字符并区分大小写

- 举例
  ```sql
  SELECT last_name AS name, commission_pct comm
  FROM employees;
  ```

  ```sql
  SELECT last_name "Name", salary*12 "Annual Salary"
  FROM employees;
  ```

#### 去除重复行

默认情况下, 查询会返回全部行, 包括重复行.

```sql
SELECT department_id
FROM employees;
```

使用关键字DISTINCT去除重复行

```sql
SELECT DISTINCT department_id
FROM employees;
```

对于双查询, 会有注意点:

```sql
SELECT DISTINCT department_id, salary
FROM employees;
```

> 1. DISTINCT 需要放在所有的列名的前面
> 2. DISTINCT其实是对后面所有的列名的组合去重

#### 空值参与运算

- 所有运算符或列值遇到null值, 运算的结果都为null

####  查询常数

用于增加一个固定的常数列, 可以用这个列作为表的标记

```sql
SELECT '尚硅谷' AS corporation, last_name FROM employees;
```

## 显示表结构

使用DESCRIBE 或 DESC 命令, 表示表结构

```sql
DESCRIBE employees;
# 或
DESC employees;
```

 其中, 各个字段的含义分别解释如下:

- Field: 表示字段名称
- Type: 表示字段类型
- Null: 表示该列是否可以存储NULL值
- Key: 表示该列是否已经编制索引, PRI表示该列是表主键的一部分, UNI表示该列是UNIQUE索引的一部分, MUL表示在列中某个给定值允许出现多次
- Default: 表示该列是否有默认值, 如果有, 那么值是多少
- Extra: 表示可以获取的与给定列有关的附加信息, 例如AUTO_INCREAMENT

## 过滤数据

```sql
SELECT <Field1>, <Field2>
FROM <table-name>
WHERE <condition>
```

> WHERE语句需要在FROM语句后面

# 4. 运算符

- 在Java中, +的左右两边如果有字符串, 那么表示字符串的拼接. 但是在MySQL中 + 只表示数值相加. 如果遇上非数值型, 会先尝试转成数值, 如果转失败, 则会以0为计算
- 一个数除以整数后, 不管是否能除尽, 结果都为一个浮点数
- 除不尽的时候, 小数位数保留四位
- 一个数除以0为NULL

## 比较运算符

### 比较结果为NULL

当普通的运算符, 有一个操作数为NULL的时候, 运算的结果为NULL

### 安全等于运算符

安全等于运算符 ( <=> ) 与等于运算符 (=) 的作用是相似的, 唯一的区别是 '<=>' 可以用来对NULL进行判断. 当两个操作数都为NULL的时候, 返回1

### 空运算符

判断一个值是否为NULL, 是则返回1

```sql
SELECT NULL IS NULL, ISNULL(NULL);
```

### 非空运算符

与之相对的, 就有非空运算符(IS NOT NULL)

### 最小值运算符

语法格式为 : LEAST(值1, 值2, ..., 值n).

```sql
SELECT LEAST('a', 'b', 'c'), LEAST(1, 2,3);
```

### 最大值运算符

与最小值运算符相对的就是最大值运算符GREATEST(value1, value2, ...,value_n)

### BETWEEN AND运算符

使用格式 : 

```sql
SELECT D FROM TABLE WHERE C BETWEEN A AND B;
```

```sql
SELECT last_name, salary
FROM employees
WHERE salary BETWEEN 2500 AND 3000;
```

### IN运算符

用于判定给定的值是否是IN列表中的一个值

```sql
SELECT 'a' IN('a', 'b');
```

```sql
SELECT NULL IN(...) # 返回值一定是NULL
```

### NOT IN 运算符

### LIKE 运算符

主要用于匹配字符串, 通常用于模糊匹配, 如果给定的值或匹配条件为NULLL , 则返回条件为NULL

```sql
"%": 匹配0个或多个字符
"_": 只能匹配一个字符
```

```sql
SELECT first_name
FROM employees
WHERE first_name LIKE 'S%';

SELECT last_name
FROM employees
WHERE last_name LIKE '_o%';
```

### ESCAPE

回避特殊符号可以使用转义符

- 使用情景 : 我现在需要查名字里带有\_的人, 这个时候, 我们如何表达\_符号就是个问题

1. 使用\作为转移符号的情况 -> 这种情况下, 不需要使用ESCAPE

```sql
SELECT job_id
FROM jobs
WHERE job_id LIKE 'IT\_%';
```

2. 没有使用\作为转义符号, 这个时候需要使用ESCAPE声明转义字符

```sql
SELECT job_id
FROM jobs
WHERE job_id LIKE 'IT$_%' ESCAPE '$';
```

### REGEXP运算符

同样是用来匹配字符串的, 语法格式为`expr REGEXP 匹配条件`. 如果expr满足匹配条件, 返回1; 如果不满足, 则返回0. 若expr或匹配条件任意一个为NULL, 则结果为NULL

匹配规则

```
(1)'^'匹配以该字符后面的字符开头的字符串
(2)'$'匹配以该字符前面的字符结尾的字符串
(3)'.'匹配任何一个单字符
(4)"[...]"匹配在方括号内的任何字符. 例如, "[abc]"匹配"a"或"b"或"c". 
为了命名字符的范围, 使用一个'-'. "[a-z]"匹配任何字母,"[0-9]"匹配任何数字
(5)'*'匹配零个或多个在它前面的字符, 例如, "x*"匹配任何数量的'x"字符, 如[0-9]*表示匹配任何数量数字
```

```sql
SELECT 'ssgaga' REGEXP '^s', 'shsada' REGEXP 'a$', 'shsaga' REGEXP 'hs';

SELECT 'atguigu' REGEXP 'gu.gu', 'atguigu' REGEXP '[ab]';
# 结果全为1
```

## 逻辑运算符

| 运算符     | 作用     | 示例           |
| ---------- | -------- | -------------- |
| NOT 或 !   | 逻辑非   | SELECT NOT A   |
| AND 或 &&  | 逻辑与   | SELECT A AND B |
| OR 或 \|\| | 逻辑或   | SELECT A OR B  |
| XOR        | 逻辑异或 | SELECT A XOR B |

> !NULL => NULL
>
> AND前后有NULL的时候返回NULL
>
> OR当有一个值为NULL, 但另一个值为非0的时候, 返回1, 两个都为NULL的时候返回NULL



> AND和OR可以一起使用, AND的优先级高于OR

## 位运算符

和CPP中的一致, 不赘述

# 5. 排序和分页

## 1. 排序数据

### 排序规则

- 使用 `ORDER BY`子句排序

  - ASC (ascend) :升序
  - DESC(descend) :降序

  - `ORDER BY`子句在SELECT语句的结尾

### 单列排序

```sql
SELECT last_name, job_id, department_id, hire_date
FROM employees
ORDER BY hire_date;
```

```sql
SELECT 
  last_name,
  job_id,
  department_id,
  hire_date 
FROM
  employees 
ORDER BY hire_date DESC;
```

> 1. 最后的降序还是升序模式, 在ORDER BY语句的末尾说明
>
> 2. 默认升序

### 多列排序

```sql
SELECT last_name, department_id, salary
FROM employees
ORDER BY hire_date, salary DESC;
```

> 可以使用不再SELECT列表中的列排序
>
> 在对多列进行排序的时候, 首先排序的第一列必须有相同的值

## 2. 分页

### 问题背景

背景1: 查询返回的数据太多了, 查看起来很不方便

背景2: 表里又四条数据, 但是我们只想显示2, 3条数据

### 实现规则

- MySQL中使用LIMIT实现分页

- 格式 :
  ```sql
  LIMIT [位置偏移量, ] 行数
  ```

  第一个"位置偏移量参数"指示MysQL从哪一行开始显示, 是一个可选参数, 如果不指定, 默认从第一条开始; 第二个参数"行数"只是返回的记录条数

- 举例
  ```sql
  -- 前十条记录
  SELECT *
  FROM employees
  LIMIT 0,10;
  
  -- 或者
  SELECT *
  FROM employees
  LIMIT 10;
  
  -- 11至20条记录
  SELECT *
  FROM employees
  LIMIT 10,10;
  ```

> MySQL8.0中可以使用"LIMIT 3 OFFSET 4", 意思是获取从第五条记录开始后面的三条记录, 和"LIMIT 4, 3;"返回的结果相同

- 分页显示公式: **(当前页数-1)\*每页条数, 每页条数 **
  ```sql
  SELECT *
  FROM <table>
  LIMIT(Pageno - 1)*PageSize, PageSize;
  ```

> LIMIT子句必须放在整个SELECT语句的最后

- 好处:
  1. 减少数据表的网络传输量 
  2. 也可以提升查询效率
  3. SELECT不需要扫描整张表, 只需要检索到一条符合条件的记录即可返回

## 作业

题目 :

```sql
#1. 查询员工的姓名和部门号和年薪，按年薪降序,按姓名升序显示 
#2. 选择工资不在 8000 到 17000 的员工的姓名和工资，按工资降序，显示第21到40位置的数据 
#3. 查询邮箱中包含 e 的员工信息，并先按邮箱的字节数降序，再按部门号升序
```

答案 :

```sql
SELECT last_name, department_id, salary * 12 AS annual_salary
FROM employees
ORDER BY annual_salary DESC, last_name ASC;
```

```sql
SELECT last_name, salary
FROM employees
WHERE salary NOT BETWEEN 8000 AND 17000
ORDER BY salary DESC
LIMIT 20,20;
```

```sql
SELECT last_name,email,department_id
FROM employees
# where email like "%e%"
WHERE email REGEXP '[e]'
ORDER BY LENGTH(email) DESC, department_id ASC;
```

# 6. 多表查询

多表查询, 也称关联查询, 指两个或更多个表一起完成查询操作

前提条件: 这些一起查询的表之间是有关系的 (一对一, 一对多) , 它们之间一定是有关联字段的, 这个关联字段可能建立了外键, 也可能没有建立外键, 比如: 员工表和部门表, 这两个表依靠 "部门编号" 进行关联.

## 1. 多表连接 (案例)

### 案例说明

```sql
#案例: 查询员工的姓名及其部门
SELECT last_name, department_name
FROM employees, departments;
# 返回的行数有2889行
```

分析错误情况:

```sql
SELECT COUNT(employee_id) FROM employees; 
#输出107行 
SELECT COUNT(department_id)FROM departments; 
#输出27行 
SELECT 107*27 FROM dual;
# 107*27 = 2889
```

该错误称为笛卡尔积的错误

### 笛卡尔积

笛卡尔积的作用就是可以把任意表进行连接, 即使这两张不相关.

最后的结果就是两张表的内容, 两两组合

### 案例分析和问题解决

- 笛卡尔积的错误会在下面条件下产生

  - 省略了多个表的连接条件 (或关联条件)
  - 连接条件无效
  - 所有表中的所有行相互连接

- 为了避开笛卡尔积, 可以**在WHERE加入有效的连接条件**

- 加入连接条件后, 查询语法:

  ```sql
  SELECT table1.coolumn, table2.column
  FROM table1, table2
  WHERE table1.column1 = table2.column2; # 连接条件
  ```

## 2. 多表查询分类讲解

### 分类1: 等值连接 vs 非等值连接

#### 等值连接

简单来说, 就是利用两张不同的表中相等的关联字段连接在一起的就是等值连接, 一般表现形式为一张表的外键和另一张表的主键等值连接 

```sql
SELECT employees.`employee_id`, employees.`last_name`
	,employees.`department_id`, departments.`department_id`,
	departments.`department_name`
FROM employees, departments
WHERE employees.`department_id` = departments.`department_id`;
```

拓展1: 表的别名

- 使用别名可以简化查询

- 列明前使用表名前缀可以提高查询效率

  ```sql
  SELECT e.employee_id, e.last_name, e.department_id, 
  	d.department_id, d.location_id 
  FROM employees e , departments d
  WHERE e.department_id = d.department_id;
  ```

> 需要说明的是, 如果我们使用了表的别名, 在查询字段, 过滤条件中就只能使用别名进行替代
>
> 不能使用原来的表名, 否则就会报错

> `阿里开发规范`:
>
> [`强制`]: 对于数据库中表记录的查询和变更, 只要涉及多个表, 都需要在列名前加上表的别名(或表名) 进行限定

拓展2: 连接多个表

> 连接n个表, 至少需要n-1个连接条件

```sql
SELECT e.`last_name`, d.`department_name`, t.`city`
FROM employees e, departments d, locations t
WHERE e.`department_id` = d.`department_id` AND d.`location_id` = t.`location_id`;
```

#### 非等值连接

连接的`WHERE`条件不再是相等, 而是别的逻辑判断

```sql
SELECT e.`last_name`, e.`salary`, j.`grade_level`
FROM employees e, job_grades j
WHERE e.`salary` BETWEEN j.`lowest_sal` AND j.`highest_sal`;
```

### 分类2 : 自连接 vs 非自连接

- 当table1和table2本质上是同一张表, 只是用取别名的方式虚拟成两张表以代表不同的意义. 然后两个表再进行内连接, 外连接等查询

题目 : 查询employees表, 返回"Xxx works for Xxx"

```sql
SELECT CONCAT(worker.`last_name`, ' works for ', manager.`last_name`)
FROM employees worker, employees manager
WHERE	worker.`employee_id` = manager.`employee_id`;
```

> 其实就是我们会为了方便理解, 将一张表虚拟成两张表以代表不同的含义, 然后这两个表, 再通过id等进行等值连接, 最后查询

### 分类3 : 内连接 vs 外连接

- 内连接: 合并具有同一列的两个以上的表的行, **结果集中不包含一个表与另一个表不匹配的行**

- 外连接: 两个表在连接过程中, 除了返回满足连接条件的行以外**还返回左 (或右) 表中布满足条件的行, 这种连接成为左 (右) 连接**. 没有匹配的行时, 结果表中的相应的列为空(NULL)
- 如果是左外连接, 则连接条件中的左边的表也称为`主表`, 右边的表称为`从表`
- 如果是右外连接, 则连接条件中的右边的表也称为`主表`, 左边的表称为`从表`

> 内连接 : 返回满足连接条件的内容, 不满足连接条件的内容不会被返回, 和用WHERE实现的是一致的
>
> 外连接 : 不满足连接条件的内容同样会被返回, 其不满足的部分会填上NULL

#### SQL92: 使用(+)创建连接

- 在SQL92中采用(+) 代表从表所在的位置, 即左或右外连接中, (+)表示哪个是从表

- Oracle 对 SQL92 支持较好，而 MySQL 则不支持 SQL92 的外连接。
  ```sql
  #左外连接 
  SELECT last_name,department_name 
  FROM employees ,departments 
  WHERE employees.department_id = departments.department_id(+); 
  #右外连接 
  SELECT last_name,department_name 
  FROM employees ,departments 
  WHERE employees.department_id(+) = departments.department_id;
  ```

- 而且在 SQL92 中，只有左外连接和右外连接，没有满（或全）外连接。

#### SQL99语法实现多表查询

#### 基本语法

- 使用JOIN...ON子句创建连接的语法结构:

  ```sql
  SELECT table1.column, table2.column, table3.column
  FROM table1
  	JOIN table2 ON table1 AND table2 的连接条件
  		JOIN table3 ON table2 AND table3 的连接条件
  ```

  嵌套逻辑类似FOR循环

  ```python
  for t1 in table1:
  	for t2 in table2: 
          	if condition1:
  				for t3 in table3: 
                      if condition2: 
                          	output t1 + t2 + t3
  ```

- 语法说明:
  - **可以使用ON子句指定额外的连接条件**
  - 这个连接条件是与其他条件分开的
  - **ON 子句使语句具有更高的易读性**
  - 关键字 JOIN, INNER JOIN, CORSS JOIN的含义是一样的, 都表示内连接

#### 内连接的实现

```sql
SELECT 字段列表
FROM <table-a> INNER JOIN <table-b>
ON 关联条件
WHERE .....;
```

题目1 :

```sql
SELECT e.`employee_id`, e.`last_name`, e.`department_id`,
	d.`department_id`, d.`location_id`
FROM 	employees e JOIN departments d
ON 	(e.`department_id` = d.`department_id`);
```

题目2 :

```sql
SELECT e.`last_name`, d.`department_name`, l.`city`
FROM employees e
INNER JOIN departments d
ON e.`department_id` = d.`department_id`
INNER JOIN locations l
ON d.`location_id` = l.`location_id`;
```

#### 外连接

##### 左外连接

- 语法 :
  ```sql
  # 实现查询结果是A
  SELECT 字段列表
  FROM <table-a> LEFT JOIN <table-b>
  ON 关联条件
  WHERE 等其他子句;
  ```

- 举例: 
  ```sql
  SELECT e.`last_name`, d.`department_name`, d.`department_id`
  FROM employees e
  LEFT JOIN departments d
  ON e.department_id = d.`department_id`;
  ```

> 左(右) 外连接的特点就是, 从主表(左\右表)取到的字段名是不会为NULL的, 但是从表的内容可以为NULL

##### 右外连接

和左外连接是一样的, 就是换了主表的方向

> LEFT JOIN 和 RIGHT JOIN 只存在SQL99及以后的标准中, 在SQL92中不存在, 只能用 (+) 表示

#### 满外连接 ( FULL OUTER JOIN)

- 满外连接的结果 = 左右表匹配的数据 + 左表没有匹配到的数据 + 右表没有匹配到的数据
- MySQL不支持FULL JOIN, 但是可以用`LEFT JOIN UNION RIGHT JOIN`代替

## 7. UNUON的使用

**合并查询的结果**利用UNION关键字, 可以给出多条SELECT语句, 并将它们的结果组合成单个结果集. 合并时, 两个表对应的列数和数据类型必须相同, 并且相互对应. 各个SELECT语句之间使用UNION或UNION ALL关键字分隔

语法格式:

```sql
SELECT column,... FROM <table1>
UNION [ALL]
SELECT column,... FROM <table2>
```

- UNION 操作符返回两个查询的结果集的并集, 去掉重复的记录
- UNION ALL操作符返回两个查询的结果集的并集, 不去除重复

> 执行UNION ALL语句时所需要的资源比UNION语句少, 如果明确知道合并数据后的结果数据不存在重复数据, 或不需要去除重复的数据, 尽量使用UNION ALL, 提高数据查询的效率

举例 : 查询部门编号>90或邮箱包含a的员工信息

```sql
SELECT e.`last_name`, e.`salary`
FROM employees e
WHERE e.`department_id` > 90 OR e.`email` REGEXP "[a]"
ORDER BY e.salary DESC;
```

```sql
SELECT e.`last_name`, e.`salary`
FROM employees e
WHERE e.`department_id` > 90
UNION 
SELECT e.`last_name`, e.`salary`
FROM employees e
WHERE e.`email` REGEXP "[a]"
ORDER BY salary DESC;
```

## 8. 7种SQL JOINS的实现

![JOINS](.\imgs\JOINS.png)

实现七种集合关系

```sql
# 中图 A∩B
SELECT employee_id, last_name, department_name
FROM employees e JOIN departments d
ON e.`department_id` = d.`department_id`;
```

```sql
# 左上图
SELECT employee_id, last_name, department_name
FROM employees e LEFT JOIN departments d
ON e.`department_id` = d.`department_id`;
```

```sql
# 右上图
SELECT employee_id, last_name, department_name
FROM employees e RIGHT JOIN departments d
ON e.`department_id` = d.`department_id`;
```

```sql
# 左中图 A - A∩B
SELECT employee_id, last_name, department_name
FROM employees e LEFT JOIN departments d
ON e.`department_id` = d.`department_id`
WHERE d.`department_id` IS NULL;
```

```sql
# 右中图: B-A∩B
SELECT employee_id, last_name, department_name
FROM employees e RIGHT JOIN departments d
ON e.`department_id` = d.`department_id`
WHERE e.`department_id` IS NULL;
```

```sql
# 左下图: 满外连接
# 左中图 + 右上图  A∪B
SELECT employee_id, last_name, department_name
FROM employees e RIGHT JOIN departments d
ON e.`department_id` = d.`department_id`
WHERE e.`department_id` IS NULL
UNION ALL 
SELECT employee_id, last_name, department_name
FROM employees e LEFT JOIN departments d
ON e.`department_id` = d.`department_id`;
```

```sql
# 右下图
# 左中图 + 右真图 A∪B- A∩B
SELECT employee_id, last_name, department_name
FROM employees e LEFT JOIN departments d
ON e.`department_id` = d.`department_id`
WHERE d.`department_id` IS NULL
UNION ALL
SELECT employee_id, last_name, department_name
FROM employees e RIGHT JOIN departments d
ON e.`department_id` = d.`department_id`
WHERE e.`department_id` IS NULL;
```

## 9. SQL99语法新特性

### 自然连接

SQL99 在SQL92 的基础上提供了一些特殊语法, 比如 `NATURAL JOIN`用来表示自然连接, 我们可以把自然连接理解为SQL92中的等值连接. 会帮你自动查询两张连接表中的 **所有相同字段** , 然后进行 **等值连接**

```sql
SELECT employee_id, last_name, department_name
FROM employees e NATURAL JOIN departments d;
```

### USING连接

当我们进行连接的时候, SQL99还支持使用USING指定数据表里的 **同名字段** 进行等值连接. 但是只能配合JOIN一起使用 : 

```sql
SELECT employee_id, last_name, department_name
FROM employees e JOIN departments d
USING (department_id);
```

## 10. 章节小结

我们要 **控制连接表的数量**. 多表连接就相当于嵌套for循环一样, 非常消耗资源, 会让SQL查询性能下降得很严重, 因此不要连接不必要得表. 在许多DBMS中, 也都会有最大连接表的限制

> [强制]  超过三个表禁止join. 需要join的字段, 数据类型保持绝对一致; 多表关联查询时, 保证被关联的字段需要有索引
>
> 即使双表 join 也能注意表索引, SQL性能.

## 作业

```sql
【题目】 
# 1.显示所有员工的姓名，部门号和部门名称。 
# 2.查询90号部门员工的job_id和90号部门的location_id 
# 3.选择所有有奖金的员工的 last_name , department_name , location_id , city
# 4.选择city在Toronto工作的员工的 last_name , job_id , department_id , department_name 
# 5.查询员工所在的部门名称、部门地址、姓名、工作、工资，其中员工所在部门的部门名称为’Executive’ 
# 6.选择指定员工的姓名，员工号，以及他的管理者的姓名和员工号，结果类似于下面的格式 employees Emp# manager Mgr# kochhar 101 king 100 
# 7.查询哪些部门没有员工 
# 8. 查询哪个城市没有部门 
# 9. 查询部门名为 Sales 或 IT 的员工信息
```

```sql
# 1 
SELECT e.`last_name`, e.`department_id`, d.`department_name`
FROM employees e JOIN departments d
ON e.`department_id` = d.`department_id`;
```

```sql
# 2
SELECT e.`job_id`, d.`location_id`
FROM employees e JOIN departments d
ON e.`department_id` = d.`department_id`
WHERE e.`department_id` = 90;
```

```sql
# 3
SELECT e.`last_name`, d.`department_name`, l.`location_id`, l.`city`
FROM employees e, departments d, locations l
WHERE e.`department_id` = d.`department_id`
AND d.`location_id` = l.`location_id`
AND e.`commission_pct` IS NOT NULL;

SELECT e.`last_name`, d.`department_name`, l.`location_id`, l.`city`
FROM employees e
LEFT JOIN departments d 
ON e.`department_id` = d.`department_id`
LEFT JOIN locations l
ON d.`location_id` = l.`location_id`
WHERE e.`commission_pct` IS NOT NULL;
```

```sql
# 4
SELECT e.`last_name`, e.`employee_id`, d.`department_id`, d.`department_name`, l.`city`
FROM employees e
LEFT JOIN departments d 
ON e.`department_id` = d.`department_id`
LEFT JOIN locations l
ON d.`location_id` = l.`location_id`
WHERE l.`city` = 'Toronto';
```

```sql
# 5
SELECT d.`department_name`, l.`street_address`, e.`last_name`, e.`job_id`, e.`salary`
FROM employees e
LEFT JOIN departments d 
ON e.`department_id` = d.`department_id`
LEFT JOIN locations l
ON l.`location_id` = d.`location_id`
WHERE d.`department_name` = 'Executive';
```

```sql
# 6
SELECT worker.`last_name` employees, worker.`employee_id` AS 'Emp#', mgr.last_name manager, mgr.employee_id "Mgr#"
FROM employees worker
LEFT JOIN employees mgr
ON worker.`manager_id` = mgr.`employee_id`;
```

```sql
# 7
SELECT d.`department_name`
FROM departments d
LEFT JOIN employees e
ON d.`department_id` = e.`department_id`
WHERE e.`department_id` IS NULL;
```

# 7. 单行函数

## 1. 函数的理解

本章及下一章讲解的是SQL的内置函数

> DBMS 之间的差异性很大, 远大于同一个语言不同版本之间的差异. 实际上, 只有很少的函数是被DBMS同时支持的. 大部分DBMS会有自己特定的函数, 这就意味着采用SQL函数的代码可移植性是很差的, 因此在使用函数的时候需要特别注意

### 函数的分类

函数分为 `单行函数`, `聚合函数 (分组函数)`

单行函数

- 操作数据对象
- 接受参数返回一个结果
- 只对一行进行变换
- 每行返回一个结果
- 可以嵌套
- 参数可以是一列或一个值

## 2. 数值函数

### 基本函数

| 函数                  | 用法                                                         |
| --------------------- | ------------------------------------------------------------ |
| ABS(x)                | 返回x的绝对值                                                |
| SIGN(X)               | 返回X的符号. 正数返回1, 负数返回-1, 0返回0                   |
| PI()                  | 返回圆周率的值                                               |
| CEIL(x), CEILING(x)   | 返回大于或等于某个值的最小整数 : 向上取整                    |
| FLOOR(x)              | 返回小于或等于某个值的最大整数 : 向下取整                    |
| LEAST(e1, e2, e3....) | 返回列表中的最小值                                           |
| GREATEST(e1,e2,e3...) | 返回列表中的                                                 |
| MOD(x,y)              | 返回x除以y后的余数                                           |
| RAND()                | 返回0 ~ 1的随机值                                            |
| RAND(x)               | 返回0~1的随机值, 其中x的值用作种子值, 相同的x值会产生相同的随机数 |
| ROUND(x)              | 返回一个x进行了四舍五入后, 最接近于x的整数                   |
| ROUND(x,y)            | 返回一个对x进行了四舍五入后, 最接近x的值, 保留到小数点后y位  |
| TRUNCATE(x,y)         | 返回数字x截断为y位小数的结果                                 |
| SQRT(x)               | 返回x的平方根. 当x的值为负数时, 返回NULL                     |

举例 :

```sql
SELECT 
ABS(-2), ABS(1), SIGN(-2), SIGN(0), PI(), CEIL(9.3), FLOOR(9.3), CEIL(-9.3), FLOOR(-9.3), MOD(12,5);
```

```sql
SELECT
LEAST(1,2,23,4,5), GREATEST(1,23,4,4,52,151), 
RAND(), RAND(2), RAND(2), RAND(3), ROUND(PI()),ROUND(PI(), 2);
```

```sql
SELECT TRUNCATE(PI(), 5), PI(), SQRT(3), SQRT(-3);
```

###  角度和弧度互换函数

| 函数       | 用法                              |
| ---------- | --------------------------------- |
| RADIANS(x) | 将角度转换弧度, 其中, x为角度值   |
| DEGREES(x) | 将弧度转换为角度, 其中, x为弧度值 |

```sql
SELECT RADIANS(180), DEGREES(PI());
```

### 三角函数

| 函数       | 用法                                           |
| ---------- | ---------------------------------------------- |
| SIN(x)     | 返回x的正弦值, 其中, x为弧度制                 |
| ASIN(x)    | 反三角函数, 如果x的值不在-1到1之间, 则返回NULL |
| COS(x)     | 余弦函数                                       |
| ACOS(x)    | 反余弦函数, 如果x的值不在-1到1之间, 则返回NULL |
| TAN(x)     | 返回x的正切值, 即返回正切值为x的值             |
| ATAN(x)    | 返回反正切函数的值                             |
| ATAN2(m,n) | 返回两个参数的反正切值                         |
| COT(x)     | 返回余切值, 其中, x为弧度制                    |

> 三角函数其中的参数都是弧度制参数

针对ATAN2(m,n)的说明, 例如, 有两个点,point(x1,y1)和point(x2,y2), 使用ATAN函数计算反正切值为ATAN((y2-y1)/(x2-x1)),  使用ATAN2(M,N)计算反正切值则为(y2-y1, x2-x1). 由使用方式可以看出, 当x2-x1等于0的时候, ATAN(x)函数会报错, 而ATAN2(m,n)函数则仍可以计算

```sql
SELECT
SIN(RADIANS(30)), DEGREES(ASIN(1)), TAN(RADIANS(45)), DEGREES(ATAN(1)), DEGREES(ATAN2(1,1));
```

### 指数与对数

| 函数                 | 用法                  |
| -------------------- | --------------------- |
| POW(x,y), POWER(x,y) | 返回x的y次方          |
| EXP(x)               | e^x                   |
| LN(x), LOG(x)        | 返回以e为底的x的对数  |
| LOG10(x)             | 返回以10为底的x的对数 |
| LOG2(x)              | 返回以2为底的x的对数  |

### 进制之间的转换

| 函数          | 用法                     |
| ------------- | ------------------------ |
| BIN(X)        | 返回X的二进制编码        |
| HEX(x)        | 十六进制                 |
| OCT(x)        | 八进制                   |
| CONV(x,f1,f2) | 返回f1进制数变成f2进制数 |

CONV(10,2,8) -> 将二进制数(10)转化为八进制数

## 3. 字符串函数

| 函数                   | 用法                                                         |
| ---------------------- | ------------------------------------------------------------ |
| LENGTH(s)              | 返回字符串s的字节数, 和字符集有关                            |
| CONCAT(s1,s2,s3...)    | 连接s1,s2,.....,sn为一个字符串函数                           |
| UPEER(s)               | 所有字母大写                                                 |
| LOWER(s)               | 所有字母小写                                                 |
| REVERSE(s)             | 返回s反转后的字符串                                          |
| NULLIF(value1, value2) | 比较两个字符串, 如果value1和value2相等, 则返回NULL, 否则返回value1 |
| FIND_IN_SET(s1,s2)     | 返回字符串s1在字符串s2中出现的位置, s2以逗号为分隔           |
| FILED(s,s1,...)        | 返回字符串s在字符串列表中第一次出现的位置                    |

> 注意 : MySQL中, 字符串的位置是从1开始的

```sql
SELECT FIELD('mm', 'hello', 'msm', 'ammma'), FIND_IN_SET('mm','hello,mm,amma');
```

```sql
SELECT NULLIF('mysql','mysql'), NULLIF('mysql', '');
```

## 4. 日期和时间函数

### 获取日期, 时间

| 函数                                                         | 用法                           |
| ------------------------------------------------------------ | ------------------------------ |
| **CURDATE()**, CURRENT_DATE()                                | 返回当前日期, 只包含年, 月, 日 |
| **CURTIME()**, CURRENT_TIME()                                | 返回当前时间, 只包含时分秒     |
| **NOW()** / SYSDATE() / CURRENT_TIMESTAMP() / LOCALTIME() / LOCALTIMESTAMP() | 返回当前系统日期和时间         |
| UTC_DATE()                                                   | 返回UTC (世界标准时间) 日期    |
| UTC_TIME()                                                   | 返回UTC (世界标准时间) 时间    |

```sql
SELECT
CURDATE(), CURTIME(), NOW(), UTC_DATE(), UTC_TIME();
```

> 可以通过 + 0, 将时间类型转化为数字, 比如2021-10-25 + 0 -> 20211025, 以此可以比较时间?

### 日期和时间戳的转换

| 函数                     | 用法                                      |
| ------------------------ | ----------------------------------------- |
| UNIX_TIMESTAMP()         | 以UNIX时间戳的形式返回当前时间            |
| UNIX_TIMESTAMP(date)     | 将时间date以UNIX时间戳的形式返回          |
| FROM_UNIXTIME(timestamp) | jiangUNIX时间戳的时间转换为普通格式的时间 |

```sql
SELECT
UNIX_TIMESTAMP(), FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()));
```

### 获取月份, 星期, 星期数, 天数等函数

| 函数                                    | 用法                            |
| --------------------------------------- | ------------------------------- |
| YEAR(date) / MONTH(date) / DAY(date)    | 返回具体的日期值                |
| HOUR(time)/ MINUTE(time) / SECOND(time) | 返回具体的时间                  |
| MONTHNAME(date)                         | 返回月份: January               |
| DAYNAME(date)                           | 返回星期几: MONDAY...           |
| WEEKDAY(date)                           | 返回周几, 周一是0, 周二是1...   |
| QUARTER(date)                           | 返回日期对应的季度, 范围为1 ~ 4 |
| WEEK(date) , WEEKOFYEAR(date)           | 返回一年中的第几周              |
| DAYOFYEAR(date)                         | 返回一年中的第几天              |
| DAYOFMONTH(date)                        | 返回日期位于所在月份的第几天    |
| DAYOFWEEK(date)                         | 返回周几, 注意周日是1, 周一是2  |

### 日期的操作函数

| 函数                    | 用法                                       |
| ----------------------- | ------------------------------------------ |
| EXTRACT(type FROM date) | 返回指定日期中特定的部分, type指定返回的值 |

![TYPEOFDATE](.\imgs\TYPEOFDATE.png)

```sql
SELECT EXTRACT(MINUTE FROM NOW());
```

### 时间和秒钟转换的函数

| 函数                 | 用法                                                         |
| -------------------- | ------------------------------------------------------------ |
| TIME_TO_SEC(time)    | 将time转化为秒并返回结果值, 转化公式为: `小时*3600 + 分钟*60 + 秒` |
| SEC_TO_TIME(seconds) | 将seconds描述转化为包含小时, 分钟和秒的时间                  |

```sql
SELECT TIME_TO_SEC(CURTIME()), CURTIME();
```

### 计算日期和时间的函数

| 函数                                                         | 用法                                           |
| ------------------------------------------------------------ | ---------------------------------------------- |
| DATE_ADD(datetime, INTERVAL expr type), ADDDATE(date, INTERVAL expr type) | 返回与给定日期时间相差INTERVAL时间段的日期时间 |
| DATE_SUB(date, INTERVAL expr type), SUBDATE(date, INTERRVAL expr type) , SUBDATE(....) | 返回与date相差INTERVAL时间间隔的日期           |

![INTERVALTYPE](.\imgs\INTERVALTYPE.png)

```sql
SELECT DATE_ADD(NOW(), INTERVAL 1 DAY) AS col1, DATE_ADD('2021-10-21 23:32:12', INTERVAL 1 SECOND) AS col2,
ADDDATE('2021-10-21 23:32:12',INTERVAL 1 SECOND) AS col3, 
DATE_ADD('2021-10-21 23:32:12',INTERVAL '1_1' MINUTE_SECOND) AS col4, 
DATE_ADD(NOW(), INTERVAL -1 YEAR) AS col5, #可以是负数 
DATE_ADD(NOW(), INTERVAL '1_1' YEAR_MONTH) AS col6; #需要单引号
```

```sql
SELECT DATE_SUB('2021-01-21',INTERVAL 31 DAY) AS col1, 
SUBDATE('2021-01-21',INTERVAL 31 DAY) AS col2, 
DATE_SUB('2021-01-21 02:01:01',INTERVAL '1 1' DAY_HOUR) AS col3 FROM DUAL;
```

> ADDDATE是向前 "+" 上指定的时间
>
> SUBDATE是向后 "-" 上指定的时间

| 函数                           | 用法                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| ADDTIME(time1, time2)          | 返回time1加上time2的时间, 当time2为一个数字时, 代表的是`秒`, 可以为负数 |
| SUBTIME(time1, time2)          | 返回time1减去time2后的时间, 当time2为一个数字的时候,代表的是`秒`, 可以为负数 |
| DATEDIFF(time1, time2)         | 返回date1 - date2 的日期间隔天数                             |
| TIMEDIFF(time1, time2)         | 返回time1 - time 的时间间隔                                  |
| FROM_DAYS(n)                   | 返回从0000年1月1日起, n天以后的日期                          |
| TO_DAYS(date)                  | 返回date距离0000年1月1日的天数                               |
| LAST_DAY(date)                 | 返回date所在月份的最后一天的日期                             |
| MAKEDATE(year, n)              | 针对给定年根与所在年份中的天数返还一个日期                   |
| MAKETIME(hour, minute, second) | 将给定的小时, 分钟和秒组合成时间并返回                       |
| PERIOD_ADD(time, n)            | 返回time加上n后的时间                                        |

```sql
SELECT 
ADDTIME(NOW(), 20), NOW(), SUBTIME(NOW(), 30) , SUBTIME(NOW(), '1:1:3'), 
DATEDIFF(NOW(), '2021-10-01'), TIMEDIFF(NOW(), '2021-10-01 22:10:20');
```

```sql
mysql> SELECT SUBTIME(NOW(), '-1:-1:-1');
+----------------------------+
| SUBTIME(NOW(), '-1:-1:-1') | 
+----------------------------+
| 2019-12-15 22:25:11 		 | 
+----------------------------+
```

```sql
mysql> SELECT MAKEDATE(2020,32); 
+-------------------+
| MAKEDATE(2020,32) |
+-------------------+
| 2020-02-01 		| 
+-------------------+
```

举例 : 查询七天内新增用户的数量由多少

```sql
SELECT COUNT(*) AS num
FROM new_user
WHERE TO_DAYS(NOW()) - TO_DAYS(regist_time) <= 7;
```

### 日期的格式化与解析

| 函数                               | 方法                                       |
| ---------------------------------- | ------------------------------------------ |
| DATE_FORMAT(date, fmt)             | 按照字符串fmt格式化日期date                |
| TIME_FORMAT(time, fmt)             | 按照字符串fmt格式化时间time                |
| GET_FORMAT(date_type, format_type) | 返回日期字符串的显示格式                   |
| STR_TO_DATE(str, fmt)              | 按照字符串fmt对str进行解析, 解析为一个日期 |

上述`非GET_FORMAT`函数中fmt参数常用的格式符:

| 格式符 | 说明                                     | 格式符 | 说明                                                     |
| ------ | ---------------------------------------- | ------ | -------------------------------------------------------- |
| %Y     | 4位数字表示数字                          | %y     | 两位数字表示年份                                         |
| %M     | 月名表示月份                             | %m     | 两位数字表示月份(01,02,03)                               |
| %b     | 缩写的月名                               | %c     | 数字表示月份 (1,2,3..)                                   |
| %D     | 英文后缀表示月中的天数(1st, 2nd, 3rd...) | %d     | 两位数字表示月中的天数(01, 02..)                         |
| %e     | 数字形式表示月中的天数                   |        |                                                          |
| %H     | 两位数字表示小时, 24小时制 (01,02...)    | %h或%l | 两位数字表示小时, 12小时制                               |
| %i     | 两位数字表示分钟                         | %S和%s | 两位数字表示秒                                           |
| %W     | 一周中的星期名称                         | %a     | 星期的缩写                                               |
| %w     | 数字表示周 (Sun = 0)                     |        |                                                          |
| %j     | 以3位数字表示年终的天数(001, 002...)     | %U     | 以数字表示年中的第几周, (1,2,3..), 其中Sun为周中的第一天 |
| %u     | 以数字表示年中的第几周                   |        |                                                          |
| %T     | 24小时制                                 | %r     | 12小时制                                                 |
| %p     | AM或PM                                   | %%     | 表示%                                                    |

GET_FORMAT函数中date_type和format_type参数的取值如下:

![tpye](E:\Desktop\imgs\tpye.png)

```sql
SELECT DATE_FORMAT(NOW(), '%H:%i:%s');
-- 21:18:03
```

```sql
SELECT STR_TO_DATE('09/01/2009', '%m/%d/%Y');

str_to_date('09/01/2009', '%m/%d/%Y')
2009-09-01
```

```sql
SELECT GET_FORMAT(DATE, 'USA');

# %m.%d.%Y

SELECT DATE_FORMAT(NOW(), GET_FORMAT(DATE, 'USA'));
```

```sql
SELECT STR_TO_DATE('2020-01-01 00:00:00', '%Y-%m-%d');
# 2020-01-01
```

## 5. 流程控制函数

流程处理函数可以根据不同的条件, 执行不同的处理流程, 类似与CPP中的条件控制语句

| 函数                                                         | 用法                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| IF(value, value1, value2)                                    | 如果value的值为TRUE, 返回value1, 否则返回value2 : 就像 ? :语句 |
| IFNULL(value1, value2)                                       | 如果value1不为NULL返回value1,否则返回value2                  |
| CASE WHEN 条件1 THEN 结果1 WHEN 条件2 THEN 结果2...[ELSE resultn] END | 相当于Java的if ... **else if** ... else ...                  |
| CASE expr WHEN 常量1 THEN 值1 WHEN 常量值2 THEN 值1 ... [ELSE值n] END | 相当于Java的switch ... case ...                              |

```sql
SELECT IF(1 > 0, '正确', '错误');
```

```sql
SELECT IFNULL(NULL, 'Hello');
```

```sql
SELECT CASE
	WHEN 1 > 0
	THEN '1>0'
	WHEN 2 > 0
	THEN '2>0'
	ELSE '3>0'
	END;
```

```sql
SELECT CASE 2
	WHEN 1 THEN  '我是1'
	WHEN 2 THEN  '我是2'
	ELSE '你是谁'
	END;
```

```sql
SELECT employee_id, salary, CASE WHEN salary >= 15000 THEN '高薪'
			WHEN salary >= 10000 THEN '潜力股'
			WHEN salary >=8000   THEN '屌丝'
			ELSE '草根' END '描述'
FROM employees;
```

```sql
SELECT CASE WHEN 1 > 0 THEN 'yes'
		WHEN 1 <= 0 THEN 'no'
		ELSE 'unknow'
		END;
```

```sql
SELECT employee_id, 12 * salary * (1 + IFNULL(commission_pct, 0))
FROM employees;
```

```sql
SELECT last_name, job_id, salary,
	CASE job_id WHEN 'IT_PROG' THEN 1.10*salary
		    WHEN 'ST_CLERK'THEN 1.15*salary
		    WHEN 'SA_REP'  THEN 1.20*salary
		    ELSE salary	   END  "REVISD_SALARY"
FROM employees;
```

## 6. 加密和解密函数

可以保证数据库的安全

| 函数                         | 用法                                                         |
| ---------------------------- | ------------------------------------------------------------ |
| PASSWORD(str)                | 加密str, 41位长的字符串, 加密结果不可逆, 常用于用户密码的加密 |
| MD5(str)                     | 返回字符串str的md5加密后的值, 也是一种加密方式, 若参数为NULL, 则会返回NULL |
| SHA(str)                     | 从原明文密码str计算并返回加密后的密码字符串, 当参数为NULL时, 返回NULL. `SHA加密算法比MD5更加安全` |
| ENCODE(value, password_seed) | 返回使用password_seed作为加密密码加密value                   |
| DECODE(value, password_seed) | 返回使用password_seed作为加密密码解密value                   |

```sql
SELECT PASSWORD('mysql'), PASSWORD(NULL);
# PASSWORD()函数在5.7版本以后已经弃用了
```

```sql
SELECT MD5('123'), SHA('maysql');
```

```sql
SELECT AES_DECRYPT(AES_ENCRYPT('str','str'),'str');
# ENCODE已经被弃用
```

## 7. MySQL信息函数

内置查看MySQL信息的函数

| 函数                                                  | 用法                                                 |
| ----------------------------------------------------- | ---------------------------------------------------- |
| VERSION()                                             | 返回当前MySQL的版本号                                |
| CONNECTIUON_ID()                                      | 返回当前MySQL服务器的连接数                          |
| DATABASE(), SCHEMA()                                  | 返回MySQL命令当前所在的数据库                        |
| USER(), CURRENT_USER(), SYSTEM_USER(), SESSION_USER() | 返回当前连接MySQL的用户名, 返回格式为"主机名@用户名" |
| CHARsET(value)                                        | 返回字符集value自变量的字符集                        |
| COLLATION(value)                                      | 返回字符串value的比较规则                            |

```sql
SELECT VERSION(), CONNECTION_ID(), DATABASE(), USER(), CHARSET('abc'), COLLATION('abc');
```

## 8. 其他函数

一些难以分类但是重要的函数

| 函数                           | 用法                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| FORMAT(value, n)               | n表示`四舍五入`后保留到小数点后n位                           |
| INET_ATON(ipvalue)             | 将以点分隔的IP地址转化为一个数字                             |
| INET_NTOA(value)               | 将数字形式的IP地址转化为以点为分隔的IP地址                   |
| BENCHMARK(n, expr)             | 将表达式expr重复执行n次, 用于测试MySQL处理expr表达式所耗费的时间 |
| CONVERT(value USING char_code) | 将value所使用的字符编码修改为char_code                       |

```sql
SELECT FORMAT(123.123, 2), 
INET_ATON('192.168.21'), INET_NTOA(INET_ATON('192.168.21')), 
BENCHMARK(12000, MD5('mysql'));
```

```sql
SELECT CHARSET('mysql'), CHARSET(CONVERT('mysql' USING 'utf8'));
```

## 作业

```sql
【题目】 # 1.显示系统时间(注：日期+时间) 
# 2.查询员工号，姓名，工资，以及工资提高百分之20%后的结果（new salary） 
# 3.将员工的姓名按首字母排序，并写出姓名的长度（length） 
# 4.查询员工id,last_name,salary，并作为一个列输出，别名为OUT_PUT 
# 5.查询公司各员工工作的年数、工作的天数，并按工作年数的降序排序 
# 6.查询员工姓名，hire_date , department_id，满足以下条件：雇用时间在1997年之后，department_id 为80 或 90 或110, commission_pct不为空 
# 7.查询公司中入职超过10000天的员工姓名、入职时间 
# 8.做一个查询，产生下面的结果 <last_name> earns <salary> monthly but wants <salary*3>
# 9.使用case-when，按照下面的条件： 
job grade 
AD_PRES A 
ST_MAN B 
IT_PROG C 
SA_REP D 
ST_CLERK E
```

```sql
# 1
SELECT NOW();

# 2
SELECT last_name, employee_id, salary, salary * 1.2 AS "new salary"
FROM employees;
# 3
SELECT last_name, LENGTH(last_name) AS LENGTH
FROM employees
ORDER BY last_name;

# 4
SELECT CONCAT(employee_id, ',',  last_name, ',',  salary) AS OUT_PUT
FROM employees;

# 5
SELECT last_name, YEAR(NOW()) - YEAR(hire_date) years, DATEDIFF(NOW(), hire_date)
FROM employees
ORDER BY years DESC;

# 6
SELECT last_name, hire_date, department_id
FROM employees
# where year(hire_date) > 1997 
WHERE DATE_FORMAT(hire_date,'%Y') >= '1997'
AND department_id IN (80, 90, 110) 
AND commission_pct IS NOT NULL;

# 7
SELECT last_name, hire_date, DATEDIFF(NOW(), hire_date)
FROM employees
WHERE DATEDIFF(NOW(), hire_date) > 10000;

# 8
SELECT CONCAT('<', last_name, '> ', 'earns <', TRUNCATE(salary,0), '> monthly but wants <', salary*3, '>') AS "Dream Salary"
FROM employees;

# 9
SELECT last_name, job_id, CASE job_id
	WHEN 'AD_PRES' THEN 'A'
	WHEN 'ST_MAN'  THEN 'B'
	WHEN 'IT_PROG' THEN 'C'
	WHEN 'SA_REP'  THEN 'D'
	WHEN 'ST_CLERK'THEN 'E'
	END AS "Grade"
FROM employees;
```

# 8. 聚合函数

对数据进行汇总的函数

## 聚合函数介绍

- **什么是聚合函数**

聚合函数作用于一组数据, 并对一组数据返回一个值

- **聚合函数类型**
  - AVG()
  - SUM()
  - MAX()
  - MIN()
  - COUNT()
- 聚合函数不能嵌套使用

### AVG, SUM

可以对数值类数据使用

比如, 不能对varchar()类型的使用

### MIN和MAX函数

可以对任意数据类型的数据使用

可以比较日期

### COUNT函数

- COUNT(\*)返回表中记录总数, 适用于**任何数据类型**

```sql
SELECT COUNT(*)
FROM employees
WHERE department_id = 90;
```

- COUNT(expr)返回expr不为空的记录总数

```sql
SELECT COUNT(commission_pct)
FROM employees
WHERE department_id = 90;
```

- 问题: 用count(\*), count(1), count(列名)谁好呢?

  count(\*), count(1)比count(列名)

- 问题: 能不能使用count(列名)替换count(\*)?

  不要使用 count(列名)来替代 `count(*)` ， `count(*)` 是 SQL92 定义的标准统计行数的语法，跟数

  据库无关，跟 NULL 和非 NULL 无关。

  说明：count(*)会统计值为 NULL 的行，而 count(列名)不会统计此列为 NULL 值的行。

## GROUP BY

### 基本使用

**可以使用GROUP BY子句将表中的数据分为若干组**

```sql
SELECT column, group_function(column)
FROM <table>
[WHERE condition]
[GROUP BY group_by_expression]
[GROUP BY column]
```

> WHERE一定放在FROM后面

```sql
SELECT department_id, AVG(salary)
FROM employees
GROUP BY department_id;
```

包含在 GROUP BY子句中的列不必包含在SELECT列表中

```sql
SELECT  AVG(salary)
FROM employees
GROUP BY department_id;
```

### 使用多个列分组

```sql
SELECT department_id, job_id, SUM(salary)
FROM employees
GROUP BY department_id, job_id;
```

### GROUP BY中使用WITH ROLLUP

使用这个关键字以后, 能计算查询出的所有记录的综合, 即统计记录数量

```sql
SELECT department_id,job_id , AVG(salary)
FROM employees
GROUP BY department_id, job_id WITH ROLLUP;
```

> 表示'小计', 我们有一个销售数据表，包含年份、季度和销售额，
>
> 年份   季度   总销售额
>
> 2023   Q1     1000
> 2023   Q2     1200
> 2023   Q3     1100
> 2023   Q4     1300
> 2023   NULL   4600    -- 2023年的小计
> 2024   Q1     1100
> 2024   Q2     1300
> 2024   NULL   2400    -- 2024年的小计
> NULL   NULL   7000    -- 总计
>
> 年度小计 : 当季度列为NULL时, 对应行显示该年度的总销售额
>
> 总计行 : 当年份和季度都为NULL时, 显示所有年份和季度的总销售额

> 当使用ROLLUP时, 不能同时使用ERDER BY子句进行结果排序, 即两个语句是互斥语句

## HAVING语句

### 基本使用

过滤分组: HAVING子句

适用情况:

1. 行已经被分组
2. 使用了聚合函数
3. 满足HAVING子句中条件的分组将被显示
4. HAVING 不能单独使用, 必须要跟 GROUP BY一起使用

```sql
SELECT department_id, MAX(salary)
FROM employees
GROUP BY department_id
HAVING	MAX(salary)>10000;
```

> 其实就是针对GROUP BY + WHERE后面条件语句不能使用聚合函数的补充

### WHERE和HAVING的对比

**区别1 : WHERE可以直接使用表中的字段作为筛选条件, 但不能使用分组中的计算函数作为筛选条件; HAVING 必须要和GROUP BY配合使用, 可以把分组计算的函数和分组作为筛选条件**

在查询语法结构中, WHERE语句在GROUP BY之前, 无法对分组结果进行筛选. 另外, WHERE排除的记录不再包括在分组中

**区别2 : 如果需要通过连接从关联表中获取需要的数据, WHERE是先筛选后连接, 而HAVING是先连接后筛选**

|        | 优点                         | 缺点                                   |
| ------ | ---------------------------- | -------------------------------------- |
| WHERE  | 先筛选数据再关联, 执行效率高 | 不能对分组中的计算函数进行筛选         |
| HAVING | 可以使用分组中的计算函数     | 在最后的结果集中进行筛选, 执行效率较低 |

**开发中的选择:**

两者并不是相互排斥的, 我们可以在一个查询中同时使用WHERE和HAVING. 包含分组统计函数的条件使用HAVING, 普通条件使用WHERE

## SELECT的执行过程

### 查询的结构

```sql
# 方式1:
SELECT ...
FROM ...
WHERE 多表的连接条件
AND  不包含组函数的过滤条件
GROUP BY ...
HAVING 包含组函数的过滤条件
ORDER BY ... ASC/DESC
LIMIT ...
```

```sql
# 方式2:
SELECT ...
FROM ... JOIN ...
ON 多表连接的条件
JOIN ...
ON ...
WHERE 不包含组函数的过滤条件
AND/OR 不包含组函数的过滤条件
GROUP BY ...
HAVING 包含组函数的过滤条件
ORDER BY .... ASC?DEsC
LIMIT ...
```

### SELECT 的执行顺序

**1. 关键字的顺序是不能颠倒的**

```sql
SELECT ... FROM ... WHERE ... GROUP BY ... HAVING ... ORDER BY ... LIMIT
```

**2. SELECT语句的执行顺序**

```sql
FROM -> WHERE -> GROUP BY -> HAVING -> SELECT 字段 -> DISTINCT -> ORDER BY -> LIMIT
```

在SELECT语句执行这些步骤的时候, 每个步骤都会产生一个`虚拟表`, 然后将这个虚拟表传入下一个步骤中作为输入, 需要注意的是, 这些步骤隐含在SQL的执行过程中, 对于我们来说是不可见的

### SQL的执行原理

SELECT 是先执行 FROM 这一步的。在这个阶段，如果是多张表联查，还会经历下面的几个步骤：

​	1. 首先先通过 CROSS JOIN 求笛卡尔积，相当于得到虚拟表 `vt（virtual table）1-1`； 

​	2. 通过 ON 进行筛选，在虚拟表 `vt1-1` 的基础上进行筛选，得到虚拟表 `vt1-2`； 

​	3. 添加外部行。如果我们使用的是左连接、右链接或者全连接，就会涉及到外部行，也就是在虚拟

表 `vt1-2` 的基础上增加外部行，得到虚拟表 `vt1-3`。

当然如果我们操作的是两张以上的表，还会重复上面的步骤，直到所有表都被处理完为止。这个过程得

到是我们的原始数据。

当我们拿到了查询数据表的原始数据，也就是最终的虚拟表 `vt1` ，就可以在此基础上再进行 WHERE 阶 

段 。在这个阶段中，会根据 vt1 表的结果进行筛选过滤，得到虚拟表 `vt2` 。

然后进入第三步和第四步，也就是 GROUP 和 HAVING 阶段 。在这个阶段中，实际上是在虚拟表` vt2` 的

基础上进行分组和分组过滤，得到中间的虚拟表 `vt3` 和 `vt4` 。

当我们完成了条件筛选部分之后，就可以筛选表中提取的字段，也就是进入到 SELECT 和 DISTINCT 

阶段 。

首先在 SELECT 阶段会提取想要的字段，然后在 DISTINCT 阶段过滤掉重复的行，分别得到中间的虚拟表

`vt5-1` 和 `vt5-2` 。

当我们提取了想要的字段数据之后，就可以按照指定的字段进行排序，也就是 ORDER BY 阶段 ，得到

虚拟表` vt6` 。

最后在` vt6 `的基础上，取出指定行的记录，也就是 LIMIT 阶段 ，得到最终的结果，对应的是虚拟表

`vt7 `。

当然我们在写 SELECT 语句的时候，不一定存在所有的关键字，相应的阶段就会省略。

同时因为 SQL 是一门类似英语的结构化查询语言，所以我们在写 SELECT 语句的时候，还要注意相应的

关键字顺序，**所谓底层运行的原理，就是我们刚才讲到的执行顺序**

## 作业

```sql
【题目】 
#1.where子句可否使用组函数进行过滤? 
#2.查询公司员工工资的最大值，最小值，平均值，总和 
#3.查询各job_id的员工工资的最大值，最小值，平均值，总和 
#4.选择具有各个job_id的员工人数 
# 5.查询员工最高工资和最低工资的差距（DIFFERENCE） 
# 6.查询各个管理者手下员工的最低工资，其中最低工资不能低于6000，没有管理者的员工不计算在内 
# 7.查询所有部门的名字，location_id，员工数量和平均工资，并按平均工资降序 
# 8.查询每个工种、每个部门的部门名、工种名和最低工资
```

```sql
# 2
SELECT MAX(salary), MIN(salary), AVG(salary), SUM(salary)
FROM employees;
```

```sql
# 3
SELECT job_id, MAX(salary), MIN(salary), AVG(salary), SUM(salary)
FROM employees
GROUP BY job_id;
```

```sql
# 4
SELECT job_id, MAX(salary), MIN(salary), AVG(salary), SUM(salary)
FROM employees
GROUP BY job_id;
```

```sql
# 5
SELECT MAX(salary) - MIN(salary) DIFFERENCE
FROM employees;
```

```sql
# 6
SELECT MAX(salary) - MIN(salary) DIFFERENCE
FROM employees;
```

```sql
# 7
SELECT d.`department_name`, d.`location_id`, COUNT(employee_id), AVG(e.`salary`)
FROM employees e RIGHT JOIN departments d
ON e.`department_id` = d.`department_id`
GROUP BY department_name, location_id
ORDER BY MIN(e.`salary`) DESC;
```

> 当SELECT 语句中同时有聚合列和非聚合列的时候一定要有GROUP BY
>
> 因为这个时候, 非聚合列的一行数据是无法和聚合列的一行数据对应的

```sql
# 8
SELECT e.`job_id`, d.`department_name`, MIN(salary)
FROM employees e RIGHT JOIN departments d
ON e.`department_id` = d.`department_id`
GROUP BY  d.`department_name`, e.`job_id`;
```

# 9. 子查询

## 需求分析与问题解决

### 实际问题

谁的工资比 Abel 高 ? => Abel 的工资是多少

```sql
# 自连接
SELECT e2.`salary`, e1.`salary`
FROM employees e1, employees e2
WHERE e1.`last_name` = 'Abel'
AND e2.`salary` > e1.salary
```

```sql
# 子查询
SELECT salary
FROM employees
WHERE salary > (
	SELECT salary
	FROM employees
	WHERE last_name = 'Abel'
	);
```

### 子查询的基本使用

- 子查询的基本语法结构
  ```sql
  SELECT select_list
  FROM <table>
  WHERE expr operator
  				(SELECT select_list
                  FROM <table>);
  ```

- 子查询 (内查询) 在主查询之前一次执行完成
- 子查询的结果被主查询 (外查询) 使用
- **注意事项**
  - 子查询要包含在括号内
  - 将子查询放在比较条件的右侧
  - 单行操作符对应单行子查询, 多行操作符对应多行子查询

### 子查询的分类

**分类方式1:**

按查询的结果返回一条还是多条记录, 将子查询分为`单行子查询`, `多行子查询`

**分类方式2:**

按内查询是否被执行多次, 将子查询划分为`相关(或关联)子查询`和`不相关(或非关联)子查询`

子查询只是作为条件进行执行, 只运行一次, 这种查询叫做不相关子查询

采用循环的方式, 先从外部查询开始, 每次都传入子查询进行查询, 然后再将结果反馈给外部, 这种嵌套的执行方式就是相关子查询

## 单行子查询

**题目: 查询工资大于149号员工工资的员工的信息**

```sql
SELECT last_name, salary
FROM employees
WHERE salary > (
	SELECT salary
	FROM employees
	WHERE employee_id = 149); 
```

**题目: 返回job_id与141号员工相同, salary比143员工多的员工姓名, job_id和工资**

```sql
SELECT last_name, job_id, salary
FROM employees
WHERE job_id = (
	SELECT job_id
	FROM employees
	WHERE employee_id = 141)
AND salary > (
	SELECT salary
	FROM employees
	WHERE employee_id = 143);
```

**题目 : 查询与141号或174员工的manager_id和department_id相同的其他员工的employee_id, manager_id, department_id**

不成对比较 : 

```sql
SELECT employee_id, manager_id, department_id
FROM employees
WHERE manager_id IN
	(SELECT manager_id
		FROM employees
		WHERE employee_id IN (141,174))
AND department_id IN
	(SELECT department_id
		FROM employees
		WHERE employee_id IN (141,174));
```

成对比较 :

```sql
SELECT employee_id, manager_id, department_id
FROM employees
WHERE (manager_id, department_id) IN
	(SELECT manager_id, department_id
		FROM employees
		WHERE employee_id IN (141, 174))
AND employee_id NOT IN (141, 174);
```

### HAVING 中的子查询

- 首先执行子查询
- 向主查询中的HAVING子句返回结果

**题目: 擦汗寻最低工资大于50号部门最低工资的部门id和其他最低工资**

```sql
SELECT department_id, MIN(salary)
FROM employees
GROUP BY department_id
HAVING MIN(salary) >
	(SELECT MIN(salary)
		FROM employees
		WHERE department_id = 50);
```

### CASE中的子查询

**题目：显式员工的employee_id,last_name和location。其中，若员工department_id与location_id为1800** 

**的department_id相同，则location为’Canada’，其余则为’USA’**

```sql
SELECT employee_id, last_name, 
	(CASE department_id
	WHEN (SELECT department_id
		FROM departments
		WHERE location_id = 18000)
	THEN 'Canada'
	ELSE 'USA' END) location	
FROM employees
```

### 子查询中的空值问题

```sql
SELECT last_name, job_id 
FROM employees 
WHERE job_id = 
    (SELECT job_id 
        FROM employees 
        WHERE last_name = 'Haas');
```

> **子查询不返回任何行**

### 非法使用子查询

```sql
SELECT employee_id, last_name 
FROM employees 
WHERE salary = 
    (SELECT MIN(salary) FROM employees 
     GROUP BY department_id);
```

> 错误代码 : 1241
> Subquery returns more than 1 row

> 多行子查询使用单行比较符

## 多行子查询

- 也称为集合比较子查询
- 内查询返回多行
- 使用多行比较操作符

### 多行比较操作符

| 操作符 | 含义                                                       |
| ------ | ---------------------------------------------------------- |
| IN     | 等于列表中的任何一个                                       |
| ANY    | 需要和单行比较操作符一起使用, 和子查询返回的某个值进行比较 |
| ALL    | 需要和单行比较操作符一起使用, 和子查询返回的所有值比较     |
| SOME   | 实际上式ANY的别名, 作用相同, 一般常使用ANY                 |

> ANY操作符: ANY表示"与子查询返回的任何一个值比较"。如果比较结果有一个为真，则整个表达式为真。
>
> ALL操作符: ALL表示"与子查询返回的所有值比较"。只有当比较结果对所有值都为真时，整个表达式才为真。

**题目：返回其它job_id中比job_id为‘IT_PROG’部门任一工资低的员工的员工号、姓名、job_id 以及salary**

```sql
SELECT employee_id, last_name, job_id, salary
FROM employees
WHERE salary < ANY
    (SELECT salary
        FROM employees
        WHERE job_id = 'IT_PROG')
AND job_id <> 'IT_PROG';
```

**题目：返回其它job_id中比job_id为‘IT_PROG’部门所有工资都低的员工的员工号、姓名、job_id以及salary**

```sql
SELECT employee_id, last_name, job_id, salary
FROM employees
WHERE salary < ALL
    (SELECT salary
        FROM employees
        WHERE job_id = 'IT_PROG')
AND job_id <> 'IT_PROG';
```

**题目：查询平均工资最低的部门id**

```sql
SELECT department_id
FROM employees
GROUP BY department_id 
HAVING AVG(salary) <= ALL(
        SELECT AVG(salary)
        FROM employees
        GROUP BY department_id);
```

## 相关子查询执行流程

相关子查询就是, 子查询中引用了外部表, 比如, 我们想查员工中工资大于本部门平均工资的员工, department_id = e.`department_id`,

 我们每计算一行的时候, 外部查询产生的结果集都会发生变化, 这个时候对应的子查询也需要重复计算一次, 因为它引用了外部表

```sql
SELECT column1, column2, ...
FROM <table1> outer
WHERE column1 operator
					(SELECT column1, column2
                    	FROM <table2>
                    	WHERE expr1 = 
                    				outer.expr2)
```

> 这里的outer是\<table1> 的别名

**题目：查询员工中工资大于本部门平均工资的员工的last_name,salary和其department_id**

使用相关子查询

```sql
SELECT last_name, salary, department_id
FROM employees e
WHERE e.salary > 
            (SELECT AVG(salary)
            FROM employees
            WHERE  department_id = e.`department_id`);
```

FROM中使用子查询

```sql
SELECT last_name, salary, e1.department_id
FROM employees e1, (SELECT department_id, AVG(salary) dept_avg_sal FROM employees GROUP BY department_id) e2
WHERE e1.`department_id` = e2.department_id
AND e2.dept_avg_sal < e1.`salary`;
```

> FROM型的子查询: 子查询是作为FROM的一部分, 子查询要用()引起来, 并且要给这个子查询取别名, 把它当成一张"临时的虚拟的表"来使用

在ORDER BY中使用子查询:

**题目：查询员工的id,salary,按照department_name 排序**

```sql
SELECT employee_id, salary
FROM employees e
ORDER BY (
          SELECT department_name
          FROM departments d
          WHERE e.`department_id` = d.`department_id`
) DESC;
```

**题目：若employees表中employee_id与job_history表中employee_id相同的数目不小于2，输出这些相同**

**id的员工的employee_id,last_name和其job_id** 

```sql
SELECT e.employee_id, last_name, e.job_id
FROM employees e 
WHERE 2 <= (SELECT COUNT(*) num FROM job_history j WHERE j.`employee_id` = e.`employee_id`);
```

### EXISTS 与 NOT EXISTS关键字

- 关联子查询通常也会和 EXISTS 操作符一起使用, 用来检查在子查询中是否存在满足条件的行
- **如果在子查询中不存在满足条件的行:**
  -  返回FALSE
  - 继续在子查询中查找
- **如果在子查询中存在满足条件的行:**
  - 不在子查询中继续查找
  - 条件返回TRUE
- NOT EXISTS关键字表示如果不存在某种条件, 则返回TRUE, 否则返回FALSE

**题目：查询公司管理者的employee_id，last_name，job_id，department_id信息**

方式一:

```sql
SELECT employee_id, last_name, job_id, department_id
FROM employees e1
WHERE EXISTS (SELECT *
            FROM employees e2
            WHERE e1.`employee_id` = e2.`manager_id`);
```

方式二:

```sql
SELECT e1.employee_id, e1.last_name, e1.job_id, e1.department_id
FROM employees e1 JOIN employees e2
WHERE e1.`manager_id` = e2.`employee_id`
```

方式三:

```sql
SELECT e1.employee_id, e1.last_name, e1.job_id, e1.department_id
FROM employees e1 
WHERE employee_id IN (
                SELECT DISTINCT manager_id
                FROM employees
                );
```

**题目：查询departments表中，不存在于employees表中的部门的department_id和department_name** 

```sql
SELECT department_id, department_name
FROM departments d
WHERE NOT EXISTS (SELECT 1
                    FROM employees e
                    WHERE e.`department_id` = d.`department_id`);
```

### 相关更新

```sql
UPDATE <table1> alias1
SET		column = (SELECT expression
                 FROM <table2> <alias2>
                 WHERE alias1.column = alias2.column)
```

使用相关子查询一个表中的数据更新另一个表的数据

**题目：在employees中增加一个department_name字段，数据为员工对应的部门名称**

```sql
# 1)
ALTER TABLE employees
ADD(department_name VARCHAR2(14));

# 2)
UPDATE employees e
SET  department_name = (SELECT department_name
                        FROM departments d
                        WHERE e.`department_id` = d.`department_id`);
```

### 相关删除

....

## 思考题

自连接和子查询哪个更好

自连接方式好

题目中可以使用子查询，也可以使用自连接。一般情况建议你使用自连接，因为在许多 DBMS 的处理过

程中，对于自连接的处理速度要比子查询快得多。

可以这样理解：子查询实际上是通过未知表进行查询后的条件判断，而自连接是通过已知的自身数据表

进行条件判断，因此在大部分 DBMS 中都对自连接处理进行了优化。

## 作业

> 每个派生表都必须有自己的别名
>
> 派生表(Derived table): 这是指在SQL查询中通过子查询创建的临时表。它不是数据库中实际存在的表,而是查询过程中临时生成的结果集。

```sql
# 1
SELECT last_name, salary
FROM employees e
WHERE department_id = 
            (SELECT department_id
              FROM employees
              WHERE last_name = 'Zlotkey');
```

```sql
# 2
SELECT salary, employee_id, last_name
FROM employees e
WHERE salary > (
            SELECT AVG(salary)
            FROM employees
            );
```

```sql
# 3
SELECT last_name, job_id, salary
FROM employees e
WHERE salary > ALL (
                SELECT salary
                FROM employees
                WHERE job_id = 'SA_MAN');
```

```sql
# 4
SELECT employee_id, last_name 
FROM employees e
WHERE department_id IN 
            (SELECT DISTINCT department_id
            FROM employees
            WHERE  last_name REGEXP '[u]');
```

```sql
# 5
SELECT employee_id
FROM employees
WHERE department_id = ANY(
            SELECT department_id
            FROM departments
            WHERE location_id = 1700);
```

```sql
# 6
SELECT last_name, salary, manager_id
FROM employees
WHERE manager_id IN (
            SELECT DISTINCT employee_id
            FROM employees
            WHERE last_name = 'King');
```

```sql
# 7
SELECT last_name, salary
FROM employees
WHERE salary = (
            SELECT MIN(salary)
            FROM employees);
```

```sql
# 8
SELECT department_id ,department_name
FROM departments
WHERE department_id = (
        SELECT department_id 
        FROM employees
        GROUP BY department_id
        HAVING AVG(salary) <= ALL 
                    (SELECT AVG(salary)
                        FROM employees
                        GROUP BY department_id)
            );
                
# 方式2: 
SELECT department_id ,department_name
FROM departments
WHERE department_id = (
        SELECT department_id 
        FROM employees
        GROUP BY department_id
        HAVING AVG(salary) = 
                    (SELECT MIN(dept_avgsal)
                        FROM (
                            SELECT AVG(salary) dept_avgsal
                            FROM employees
                            GROUP BY department_id) avg_sal
                            )
            );
        
# 方式3:
SELECT d.*
FROM departments d, (
        SELECT department_id, AVG(salary) avg_sal
        FROM employees
        GROUP BY department_id
        ORDER BY avg_sal
        LIMIT 0,1) dept_avg_sal
WHERE d.`department_id` = dept_avg_sal.department_id;
```

```sql
# 9
SELECT d.*, (SELECT AVG(salary) FROM employees e WHERE e.department_id = d.department_id) 
FROM departments d
WHERE department_id = (
        SELECT DISTINCT department_id
        FROM employees e
        WHERE e.`department_id` = d.`department_id`
        GROUP BY department_id
        HAVING AVG(e.`salary`) = (
            SELECT AVG(salary) avgasl
            FROM employees
            GROUP BY department_id
            ORDER BY avgasl
            LIMIT 0,1));
            
# 方式2: 
SELECT d.*, (SELECT AVG(salary) FROM employees e WHERE e.department_id = d.department_id) 
FROM departments d
WHERE department_id = (
        SELECT department_id
        FROM employees e
        GROUP BY department_id
        HAVING AVG(e.`salary`) = (
            SELECT MIN(dept_avgsal)
            FROM (
                SELECT AVG(salary) dept_avgsal
                FROM employees
                GROUP BY department_id
                )  avg_sal
            ) 
        );
```

```sql
# 10
SELECT job_id
FROM employees
WHERE job_id = (
        SELECT job_id
        FROM employees
        GROUP BY job_id
        HAVING AVG(salary) = (
            SELECT MAX(job_avgsal)
            FROM (
                SELECT AVG(salary) job_avgsal
                FROM employees
                GROUP BY  job_id
            ) avg_sal
        )
    );
```

```sql
# 11
SELECT department_id
FROM employees
WHERE department_id IS NOT NULL
GROUP BY department_id
HAVING AVG(salary) > (
        SELECT AVG(salary)
        FROM employees);
```

```sql
# 12
SELECT department_id
FROM employees
WHERE department_id IS NOT NULL
GROUP BY department_id
HAVING AVG(salary) > (
        SELECT AVG(salary)
        FROM employees);
```

```sql
# 13
SELECT MIN(salary)
FROM employees
GROUP BY department_id
HAVING department_id = (
        SELECT department_id
        FROM employees
        GROUP BY department_id
        HAVING MAX(salary) = (
                SELECT MIN(max_sal) 
                FROM (
                    SELECT MAX(salary) max_sal
                    FROM employees
                    GROUP BY department_id
                ) max_sal
            )
        );
```

```sql
# 14
SELECT last_name, department_id, email, salary
FROM employees
WHERE employee_id IN (
    SELECT manager_id
    FROM employees
    WHERE department_id = (
        SELECT DISTINCT department_id
        FROM employees
        WHERE manager_id IS NOT NULL
        GROUP BY department_id
        HAVING department_id = (
            SELECT department_id
            FROM employees
            GROUP BY department_id
            ORDER BY AVG(salary) DESC
            LIMIT 0,1
        )
    )
);

# 方式2:
SELECT last_name, department_id, email, salary
FROM employees e
WHERE  employee_id IN (
    SELECT DISTINCT manager_id
    FROM employees e, (
        SELECT department_id, AVG(salary) avg_sal
        FROM employees
        GROUP BY department_id
        ORDER BY avg_sal DESC
        LIMIT 0,1
        )dept_avg_sal
    WHERE dept_avg_sal.department_id = e.department_id
);
```

```sql
# 18
SELECT employee_id, last_name, salary
FROM employees e
WHERE salary > (
    SELECT AVG(salary) avg_sal
    FROM employees e1
    WHERE e.`department_id` = e1.`department_id`
    GROUP BY department_id
)
```

# 创建和管理表

## 基础知识

### 一条数据的存储过程

- 创建数据库 -> 确认字段 -> 创建数据表  -> 插入数据
- 从系统架构来看 : `数据库服务器` -> `数据库`  -> `数据表的行与列`

### 标识符命名规则

- 数据库名, 表名不得超过30个字符, 变量名限制为29个
- 不能有重名现象
- 保证字段名和类型的一致性

### 数据类型

| 类型             | 类型举例                                                     |
| ---------------- | ------------------------------------------------------------ |
| 整数类型         | TINYINT, SMALLINT, MEDIUMINT, INT(或INTERGER), BIGINT        |
| 浮点类型         | FLOAT, DOUBLE                                                |
| 定点数类型       | DECIMAL                                                      |
| 位类型           | BIT                                                          |
| 日期时间类型     | YEAR, TIME, DATE, DATETIME, TIMESTAMP                        |
| 文本字符串类型   | CHAR, VARCHAR, TINYTEXT, TEXT, MEDIUMTEXT, LONGTEXT          |
| 枚举类型         | ENUM                                                         |
| 集合类型         | SET                                                          |
| 二进制字符串类型 | BINARY, VARBINARY, TINYBLOB, MEDIUMBLOB, LONGBLOB            |
| JSON类型         | JSON对象, JSON数组                                           |
| 空间数据类型     | 单值：GEOMETRY、POINT、LINESTRING、POLYGON；集合：MULTIPOINT、MULTILINESTRING、GEOMETRYCOLLECTION |

常用数据类型

| 数据类型      | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| INT           | 从-2^31 ~ 2^31-1的整数类型, 存储大小位4个字节                |
| CHAR(size)    | 定长字符类型, 若未指定, 默认为1个字符, 最大长度为2           |
| VARCHAR(size) | 可变长字符数据, 根据字符串实际长度保存, 必须指定长度         |
| FLOAT(M,D)    | 单精度, 占用4个字节, M=整数位+小数位, D=小数位. D<=M<=255, 0<=D<=30, 默认M+D<=6 |
| DOUBLE(M,D)   | 双精度, 占用8个字节, D<=M<=255, 0<=D<=30, 默认M+D<=15        |
| DECIMAL(M,D)  | 高精度小数, 占用M+2个字节, D<=M<=65, 0<=D<=30, 最大取值范围和DOUBLE相同 |
| DATE          | 日期类型, 格式'YYYY-MM-DD'                                   |
| BLOB          | 二进制形式的长文本数据, 最大可达4G                           |
| TEXT          | 长文本数据, 最大可达4G                                       |

## 创建和管理数据库

### 创建数据库

```sql
# 创建数据库
CREATE DATABASE 数据库名;

# 创建数据库, 并指定使用的字符集
CREATE DATABASE 数据库名 CHARACTER SET 字符集;

# 判断数据库是否存在,如果不存在则创建数据库
CREATE DATABASE IF NOT EXISTS 数据库名;
```

> 注意: DATABASE不能改名, 一些可视化工具可以改名, 它是建立新库, 把所有的表复制到新库, 再删去原先的库

### 使用数据库

- 查看当前所有的数据库
  ```sql
  SHOW DATABASES;
  ```

- 查看当前正在使用的数据库
  ```sql
  SELECT DATABASE():
  ```

- 查看指定库下所有的表

  ```sql
  SHOW TABLES FROM 数据库名;
  ```

- 查看数据库的创建信息
  ```sql
  SHOW CREATE DATABASE 数据库名;
  # 或者
  SHOW CREATE DATABASE 数据库名\G
  ```

- 使用/切换数据库
  ```sql
  USE 数据库名;
  ```

> 操作表和数据之前需要指定数据库, 否则就要对所有对象加上"数据库名"

### 修改数据库

- 更改数据库字符集
  ```sql
  ALTER DATABASE 数据库名 CHARACTER SET 字符集; # 比如: gbk, utf8等
  ```

### 删除数据库

```sql
DROP DATABASE 数据库名;

# 推荐
DROP DATABASE IF EXISTS 数据库名;
```

## 创建表

### 创建方式

- 创建表的前提 :
  - CRAETE TABLE权限
  - 存储空间
- 语法格式:

```sql
CREATE TABLE [IF NOT EXISTS] 表名(
	字段1, 数据类型 [约束条件] [默认值],
    ....
    [表约束条件]
);
```

- 必须指定:
  - 表名
  - 字段名, 数据类型, 长度
- 可选指定
  - 约束条件
  - 默认值

```sql
CREATE TABLE IF NOT EXISTS emp(
    emp_id INT,
    emp_name VARCHAR(20),
    salary DOUBLE,
    birthday DATE
);
DESC emp;a
```

> 可以将id字段设置为INT(11), 这里的11实际上是INT类型指定的显示类型, 默认为11, 创建时可以使用INT(2)指定显示宽度
>
> 这个属性在8.x版本后不再被推荐使用, 少用

```sql
CREATE TABLE IF NOT EXISTS dept(
    -- int类型, 自增
    deptno INT(2) AUTO_INCREMENT,
    dname VARCHAR(14),

    -- 主键
    PRIMARY KEY (deptno)
);
DESC dept;
```

### 创建方式2

-  使用AS subquery选项, 将创建表和插入数据结合起来
- 指定的列和子查询种的列要一一对应
- 通过列名和默认值定义列

```sql
CREATE TABLE emp1 AS SELECT * FROM employees;

CREATE TABLE emp2 AS SELECT * FROM employees WHERE 1=2; -- 创建的emp2是空表
```

```sql
CREATE TABLE dept80 
AS
SELECT employee_id, last_name, salary*12 ANNSAL, hire_date 
FROM employees 
WHERE department_id = 80;
```

### 查看数据表结构

```sql
SHOW CREATE TABLE 表名\G

DESC 表名;
```

使用SHOW CREATE TABLE语句不仅能看到表创建时的详细语句, 还可以查看存储引擎和字符编码

## 修改表

使用ALTER TABLE语句能做到

- 向已有的表中添加列
- 修改现有表中的列
- 删除现有表中的列
- 重命名现有表中的列

### 追加一个列

```sql
ALTER TABLE 表名 ADD [column] 字段名 字段类型 [FIRST|AFTER字段名] ;
```

```sql
ALTER TABLE DEPT80
ADD job_id varchar(15);	
```

### 修改一个列

- 修改列的数据类型, 长度, 默认值和位置

```sql
ALTER TABLE 表名, MODIFY [COLUMN] 字段名1 字段类型 [DEFAULT] [FIRST|AFTER 字段名2]
```

```sql
ALTER TABLE dept80
MODIFY salary DOUBLE(9,2) DEFAULT 1000; 
```

- 对默认值的修改, 只影响今后对表的修改
- 这种方式还能修改列的约束

### 重命名一个列

```sql
ALTER TABLE 表名
CHANGE 列名 新列名 新数据类型
```

```sql
ALTER TABLE dept80
CHANGE last_name dept_name VARCHAR(15);
```

### 删除一个列

```sql
ALTER TABLE 表名 DROP [COLUMN] 字段名
```

```sql
ALTER TABLE dept80
DROP COLUMN salary;
```

> COLUMN是可选项, 可以写也可以不写

## 重命名表

- 方式一 : 使用RENAME
  ```sql
  RENAME TABLE emp
  TO myemp;
  ```

- 方式二:
  ```sql
  ALTER TABLE dept
  RENAME  [TO] detail_dept;
  ```

## 删除表

- 当一张数据表`没有与其他任何数据表形成关联关系`时, 可以将当前数据表直接删除

- 数据和结构都被删除

- 所有正在运行的相关事务被提交

- 所有相关索引被删除
  ```sql
  DROP TABLE [IF EXISTS]  数据表1, 数据表2 ,....
  ```

- 删除语句不能回滚

## 清空表

- TRUNCATE TABLE语句
  - 删除表中的所有数据
  - 释放表的存储空间

```sql
TRUNCATE TABLE detail_dept
```

- TRUNCATE语句不能回滚, 而使用DELETE语句删除数据, 可以回滚

```sql
SET autocommit = FALSE; 
DELETE FROM emp2; 
#TRUNCATE TABLE emp2; 
SELECT * FROM emp2; 
ROLLBACK; 
SELECT * FROM emp2;
```

> 阿里开发规范:
>
> [参考] TRUNCATE TABLE 比 DELETE 速度快, 且使用的系统和事务日志资源少, 但是TRUNCATE无事务, 且不触发 TRIGGER, 有可能造成事故, 故不建议在开发代码中使用次语句

## 内容拓展

### 阿里巴巴开发手册之MySQL字段命名

- [强制] 表名字段名, 必须小写字母和数字, 禁止出现数字开头, 禁止两个下划线中间只出现数字
- [强制] 禁止使用保留字
- [强制] 表必备三字段: id, gmt_create, gmt_modified
  - 其中id必为主键, 类型为BIGINT UNSIGNED, 单表时自增, 步长为1. gmt_create, gmt_modified 类型必须为DATETIME类型, 前者现在时表示主动式创建, 后者过去分词表示被动式更新
- [推荐] 表的命名最好是遵守 "业务名称\_表的作用"
- [推荐] 库名和应用名称尽量一致

### 如何理解清空表, 删除表等操作需谨慎

MySQL删除表的时候, 不会有任何确认信息, 因此执行删除操作时应当慎重, 在删除表钱, 最好对表的数据进行备份, 这样能有利于数据的恢复, 避免造成无法挽回的后果

在使用**ALTER TABLE** 进行表的基本修改操作时, 在执行操作过程之前, 也应该确保对数据进行**完整的备份**, 因为数据的改变时**无法撤销**的. 

### MySQL8新特性-DDL原子化

DDL操作要么成功, 要么回滚

比如我现在要删除book1, book2两张表, 但是后一张表是不存在的, 我现在执行`DROP TABLE book1, book2`, MySQL5.7就会执行后报错, 但是仍然删除了book1, 而MySQL8.x就会保留book1, 因为这是一次失败的操作, 会直接回滚

## 作业

```sql
# 1
CREATE DATABASE test02_market;

SELECT DATABASE();

USE test02_market;

# 2
CREATE TABLE customers(
    c_num INT,
    c_name VARCHAR(50),
    c_contact VARCHAR(50),
    c_city VARCHAR(50),
    c_birth DATE
);

# 3
ALTER TABLE customers
MODIFY c_contact VARCHAR(50) AFTER c_birth;

# 5
ALTER TABLE customers
CHANGE c_contact c_phone VARCHAR(50);

# 6 
ALTER TABLE customers
ADD c_gender CHAR(1) AFTER c_name;

# 7
ALTER TABLE customers
RENAME customers_info;

SHOW TABLES;

DESC customers_info;
```

# 数据处理之增删改

## 插入数据

### 方式1: VALUES的方式增加数据

这种语法一次只能向表中插入一条数据, 即插入一行数据

情况1:  为表的**所有字段**按**默认顺序**插入数据

```sql
INSERT INTO 表名
VALUES (value1, value2)
```

值列表中需要为表的**每一个字段指定值**, 并且值的顺序必须和数据表中字段定义时的顺序相同

```sql
INSERT INTO departments
VALUES (280, 'Pub', 100, 1700);
```

```sql
INSERT INTO departments
VALUES (290, 'Finance', NULL, NULL);
```

情况2 : 为表的**指定字段**插入数据

```sql
INSERT INTO 表名 (column1, column2...)
VALUES (value1, ....)
```

只为表的指定字段插入数据, INSERT语句只向部分字段插入值, 其他未指定的字段会填充上默认值

在INSERT子句中随意列出列名, 但是一旦列出, VALUES中要插入的value1,...valuen需要和column1,column2,...一一对应, 如果类型不同, 将无法插入

```sql
INSERT INTO departments(department_id, department_name) 
VALUES (80, 'IT');
```

情况3: **同时插入多条数据**

在VALUES子句上加上逗号即可

```sql
INSERT INTO table_name
VALUES 
(value1 [,value2, …, valuen]), 
(value1 [,value2, …, valuen]),
……
(value1 [,value2, …, valuen]);
```

或者

```sql
INSERT INTO table_name(column1 [, column2, …, columnn]) 
VALUES 
(value1 [,value2, …, valuen]), 
(value1 [,value2, …, valuen]),
……
(value1 [,value2, …, valuen]);
```

```sql
mysql> INSERT INTO emp(emp_id,emp_name)
-> VALUES (1001,'shkstart'),
-> (1002,'atguigu'),
-> (1003,'Tom');
```

在使用INSERT插入多行数据的时候, MySQL会返回一些在执行单行插入的时候, 没有的额外的信息 

- Records: 表名插入的记录条数
- Duplicates: 表明插入时被忽略的记录, 原因是这些记录包含了重复的主键
- Warnings: 表明有问题的数据值, 例如发生了数据类型的转换

> 多行的INSERT语句在处理的过程中的 **效率更高**, 因为MySQL执行单条INSERT语句插入多行数据比使用多条INSERT语句快, 所以在插入多条记录时最好选择使用单挑INSERT语句的方式插入

### 方式2: 将查询结果插入到表中

```sql
INSERT INTO 表名
(tar_column1, ...)
SELECT 
(src_column1, ...)
FROM 源表名
[WHRER condition]
```

- 子查询的值列表应与INSERT子句中的列名对应

```sql
INSERT INTO dept80 (employee_id, hire_date, job_id)
SELECT employee_id, hire_date, job_id
FROM employees;
```

## 更新数据 (修改数据)

```sql
UPDATE table_name
SET column1=value1, column2=value2,....
[WHERE condition]
```

- 可以一次更新多条数据
- 如果需要回滚数据, 需要保证在DML之前, 进行设置: SET AUTOCOMMIT = FALSE
- 使用WHERE指定需要更新的数据

```sql
UPDATE employees
SET department_id = 70
WHERE employee_id = 113;
```

- 如果忽略WHERE子句, 则表中的所有数据将被更新

- **更新中的数据完整性错误**

  ```sql
  UPDATE employees 
  SET department_id = 55 
  WHERE department_id = 110;
  ```

  - 外键约束冲突
  - 检查约束冲突
  - 触发器问题
  - 这些都是在更新数据的时候, 会导致数据完整性冲突的因素

## 删除数据

```sql
DELETE FROM <table_name> [WHERE <condition>];
```

table_name指定要执行删除操作的表, 如果没有WHERE子句, DELETE将删除表中的所有记录

```sql
DELETE FROM departments
WHERE department_name = 'Finance';
```

- 删除中的数据完整性错误

```sql
DELETE FROM departments
WHERE		department_id = 60;
```

```sql
错误代码
错误代码： 1451
Cannot delete or update a parent row: 
a foreign key constraint fails (`atguigudb`.`employees`, CONSTRAINT `emp_dept_fk` 
                                FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`))
```

> 说明: You cannot delete a row that contains a primary key that is used as a foreign key in another table

## 计算列

某一列的值是通过别的列计算得来的, 例如a列值为1, b列值为2, 那么c列值不需要手动赋值, 可以由c=a+b计算得来, 这样的c列就是计算列

MySQL8.0中, CREATE TABLE和ALTER TABLE都支持计算列

```sql
CREATE TABLE tb1(
	id INT,
    a INT,
    b INT,
    c INT HENERATED ALWAYS AS (a + b) VIRTUAL
)
```

## 作业

```sql
CREATE DATABASE test01_library;

USE test01_library;

CREATE TABLE books(
    id INT,
    b_name VARCHAR(50),
    b_authors VARCHAR(100),
    price FLOAT,
    pubdate YEAR,
    note VARCHAR(100),
    num INT
);

INSERT INTO books
VALUES (1, 'Tal of AAA', 'Dickes', 23, '1995', 'novel', 11);

INSERT INTO books (id, b_name, b_authors, price, pubdate, note, num)
VALUES (2, 'EmmaT', 'Jane lura', 35, '1993', 'joke', 22);

INSERT INTO books
VALUES 
    (3, 'Stroy of Jane', 'Jane Tim', 40, '2001', 'novel', 0),
    (4, 'Lovey Day', 'George Byron', 20 , '2005', 'novel', 30),
    (5, 'Old land', 'Honore Blade', 30, '2010', 'law', 0),
    (6, 'The Battle', 'Upton Sara', 30, '1999', 'medicine', 40),
    (7, 'Rose Hood', 'Richard haggard', 28, '2008', 'cartoon', 28);
    
UPDATE books
SET price = price + 5
WHERE note = 'novel';

SELECT *
FROM books;

UPDATE books
SET price = 40, note='drama'
WHERE b_name = 'EmmaT';

DELETE FROM books
WHERE num = 0;

SELECT b_name
FROM books
WHERE b_name REGEXP '[a]';

SELECT COUNT(*), SUM(num)
FROM books
WHERE b_name REGEXP '[a]';

SELECT *
FROM books
WHERE note = 'novel'
ORDER BY price DESC;

SELECT *
FROM books
ORDER BY num DESC, note ASC;

SELECT COUNT(1)
FROM books
GROUP BY note;

SELECT SUM(num)
FROM books
GROUP BY note
HAVING SUM(num) > 30;

SELECT *
FROM books
LIMIT 5, 5;

# 1
SELECT SUM(num)
FROM books
GROUP BY note
HAVING SUM(num) >= ALL(
        SELECT SUM(num)
        FROM books
        GROUP BY note);

# 2 
SELECT MAX(sum_num)
FROM (SELECT SUM(num) sum_num
       FROM books
       GROUP BY note) sum_nums;
       
# 3
SELECT SUM(num) sum_num
FROM books
GROUP BY note
ORDER BY sum_num DESC
LIMIT 0,1;

SELECT b_name
FROM books
WHERE  LENGTH(REPLACE(b_name, ' ', '')) >= 10;


SELECT b_name, note, (CASE note
            WHEN 'novel' THEN '小说'
            WHEN 'law'  THEN '法律'
            WHEN 'medicine' THEN '医药'
            WHEN 'cartoon'  THEN '卡通'
            WHEN 'joke' THEN '笑话'
            END) type_cn
FROM books;
```

# MySQL数据类型精讲

## 整型

### 可选属性

#### M

M -> 显示宽度, 这个属性已经是不再被推荐使用的属性

```sql
CREATE TABLE test01_int (
	x INT(M))
```

这个属性不影响存储, 只影响显示

#### UNSIGNED

无符号

```sql
CREATE TABLE test01_int (
	x INT UNSIGNED
)
```

#### ZEROFILL

0填充, 某列有ZEROFILL属性, 也会自动加上UNSIGNED这个属性

表示显示不够M位的时候, 用0在左边填充, 超过了M位, 就不会填充了

也就是说只有 **ZEROFILL 和 M 两个属性连用的时候才有意义**

### 各个类型的整型的适用场所和字节数

| 类型名       | 字节数 | 适用场景                                                     |
| ------------ | ------ | ------------------------------------------------------------ |
| TINYINT      | 1      | 一般用于枚举类型, 适用于系统设置取值范围很小, 且取值固定的场景 |
| SMALLINT     | 2      | 少用, 用于小范围的数据统计, 比如工厂的固定资产库存数等       |
| MEDIUMINT    | 3      | 少用, 用于较大整数的计算，比如车站每日的客流量等。           |
| INT, INTEGER | 4      | 用的最多, 一般不需要考虑越界问题                             |
| BIGINT       | 8      | 特别大的整数才需要用到, 比如双十一的交易数, 大型门户网站的点击数.... |

> 选择策略 : 优先保证稳定性 -> 优先确保这个范围不会超, 其次考虑大小

## 浮点类型

| 类型   | 占用字节 |
| ------ | -------- |
| FLOAT  | 4        |
| DOUBLE | 8        |

- 无符号的属性在浮点类型上已经要被弃用了, 因为它并不能扩大取值范围, 只会让取值范围变成正常浮点数的正数的那一半

### M, D属性

用于指定浮点数的精度, 这种语法是非标准语法, 其他数据库不一定支持, 涉及到数据迁移, 就尽量不要这么写

- FLOAT(M, D) ->
  - M指明整数位 + 小数位
  - D指明小数位
  - (3, 2) -> 整数位加上小数位一共是个3位数, 小数位是0位数 -> 实际存储范围就是 (-9.99 ~ 9.99)
- 会影响存储
  - 存储的时候, 整数部分超出了范围是会报错的
  - 小数部分超出了范围, 会进行四舍五入, 如果舍入后, 整数部分没有超出范围就不报错, 只是警告, 相反就会报错

- 这个属性也不推荐使用了

## 定点数

底层是用字符串存储, 很精确, 可以用 == 判断

| 数据类型                 | 字节数  |
| ------------------------ | ------- |
| DECIMAL(M,D),DEC,NUMERIC | M+2字节 |

- DECIMAL最大取值范围与DOUBLE一致, 但是有效数据范围由M和D决定

- 当DECIMAL类型不指定精度和标度的时候, 默认为DECIMAL(10, 0), 数据精度超出范围的时候, MySQL做出的舍入和浮点数一样

### 适用范围的讨论

- 浮点数更适用于数字取值范围大,  可以容忍微小误差的科学计算场景
- 适用于精度有要求极高的场景 (涉及金额计算的场景)

## 位类型

| 二进制类型 | 长度 | 长度范围 | 占用空间        |
| ---------- | ---- | -------- | --------------- |
| BIT(M)     | M    | 1<=M<=64 | 约为(M+7)/8字节 |

在使用SELECT语句查询的时候, 可以使用BIN(), HEC()函数, 读取出二进制数据和十六进制数据

## 日期与时间类型

- YEAR 类型通常用来表示年
- DATE 类型通常用来表示年、月、日
- TIME 类型通常用来表示时、分、秒
- DATETIME 类型通常用来表示年、月、日、时、分、秒
- TIMESTAMP 类型通常用来表示带时区的年、月、日、时、分、秒

#### ![date](E:\Desktop\imgs\date.png)



### YEAR

以四位字符串或数字格式表示YEAR类型, 格式为YYYY, 最小值为1901, 最大值为2155

### DATE类型

- 没有时间部分

- 格式为`YYYY-MM-DD`, 需要**三个字节**的存储空间

- 以 `YYYY-MM-DD` 格式或者 `YYYYMMDD` 格式表示的字符串日期，其最小取值为1000-01-01，最大取值为

  9999-12-03。YYYYMMDD格式会被转化为YYYY-MM-DD格式。

- 以 `YY-MM-DD` 格式或者 `YYMMDD` 格式表示的字符串日期，此格式中，年份为两位数值或字符串满足

  YEAR类型的格式条件为：当年份取值为00到69时，会被转化为2000到2069；当年份取值为70到99

  时，会被转化为1970到1999。

- 使用`NOW()`会插入系统日期

### TIME类型

- 三个字节

- 不包含日期
- 使用`NOW()`会插入系统时间

#### 插入格式

1. 可以使用带有冒号的字符串，比如' D HH:MM:SS' 、' HH:MM:SS '、' HH:MM '、' D HH:MM '、' D HH '或' SS '格式，都能被正确地插入TIME类型的字段中。其中D表示天，其最小值为0，最大值为34。如果使用带有D格式的字符串插入TIME类型的字段时，D会被转化为小时，计算格式为D*24+HH。当使用带有冒号并且不带D的字符串表示时间时，表示当天的时间，比如12:10表示12:10:00，而不是00:12:10。 -> **也可以存入D, 但是实际上会发生转化**
2. 可以使用不带有冒号的字符串或者数字，格式为' HHMMSS '或者 HHMMSS 。如果插入一个不合法的字符串或者数字，MySQL在存储数据时，会将其自动转化为00:00:00进行存储。比如1210，MySQL会将最右边的两位解析成秒，表示00:12:10，而不是12:10:00。 -> **不合法的时间会取其中正常的, 然后用0填充, 而且默认从右往左取正常值**

### DATETIME

- **8个字节**

#### 存储的格式条件

1. 以` YYYY-MM-DD HH:MM:SS` 格式或者 `YYYYMMDDHHMMSS` 格式的字符串插入DATETIME类型的字段时，最小值为1000-01-01 00:00:00，最大值为9999-12-03 23:59:59。 以`YYYYMMDDHHMMSS`格式的数字插入DATETIME类型的字段时，会被转化为`格式。
2. 以 `YY-MM-DD HH:MM:SS` 格式或者 `YYMMDDHHMMSS` 格式的字符串插入DATETIME类型的字段时，两位数的年份规则符合YEAR类型的规则，00到69表示2000到2069；70到99表示1970到1999。

3. 使用函数 `CURRENT_TIMESTAMP()` 和 `NOW()` ，可以向DATETIME类型的字段插入系统的当前日期和时间。

## TIMESTAMP类型

- 取值范围小, 包含时区信息
- **4个字节**
- 会根据用户时区的不同, 显示不同的结果
- 存储数据的时候需要对当前时间所在的时区进行转换, 查询数据的时候, 再将时间转换回当前的时区. 

### 开发中经验

- 用的最多的就是DATETIME, 存储的信息最完整, 范围也大
- 存注册时间, 商品发布时间等, 不建议使用DATETIEM存储, 使用`时间戳`, DATETIME虽然直观, 但是不便于计算

## 文本字符类型

### CHAR 和 VARCHAR

| 类型    | 特点     | 长度 | 长度范围        | 存储空间             |
| ------- | -------- | ---- | --------------- | -------------------- |
| CAHR    | 固定长度 | M    | 0 <= M <= 255   | M个字节              |
| VARCHAR | 可变长度 | M    | 0 <= M <= 65535 | (实际长度 + 1)个字节 |

#### 那种情况使用CHAR 或 VARCHAR 更好

情况1 : 存储很短的信息, 这个时候, 使用CHAR, 因为 VARCHAR还要额外占用一个字节

情况2 : 固定长度的, 比如uuid作为主键, 那使用char应该更合适

情况3 : 十分频繁更改的列, 因为varchar每次存储需要额外的计算

情况4 : 视引擎而定

- `MyISAM`数据存储引擎和数据列: 最好使用固定长度(CHAR), 这样使整个表静态化, 加快数据检索
- `MEMORY`存储引擎和数据列 : 两个都一样
- `InnoDB`存储引擎 : 建议使用VARCHAR, **主要影响性能的因素是数据行使用的存储量**, 而char平均占用的空间多余varchar

### TEXT类型

![TEXT](E:\Desktop\imgs\TEXT.png)

- 由于实际存储的长度不确定, MySQL不允许 TEXT 类型做主键

#### 开发中的经验

**TEXT文本类型，可以存比较大的文本段**，搜索速度稍慢，因此如果不是特别大的内容，建议使用CHAR， VARCHAR来代替。还有TEXT类型不用加默认值，加了也没用。**而且text和blob类型的数据删除后容易导致“空洞”，使得文件碎片比较多**，所以频繁使用的表不建议包含TEXT类型字段，建议单独分出去，单独用一个表

## ENUM类型

| 类型 | 长度 | 长度范围        | 占用的存储空间 |
| ---- | ---- | --------------- | -------------- |
| ENUM | L    | 1 <= L <= 65535 | 1或2个字节     |

- 当ENUM类型包含1～255个成员时，需要1个字节的存储空间；
- 当ENUM类型包含256～65535个成员时，需要2个字节的存储空间。
- ENUM类型的成员个数的上限为65535个。

```sql
CREATE TABLE test01_enum(
	season ENUM ('春', '夏', '秋', '冬', 'unknow')
);
```

```sql
# 允许按照角标的方式获取指定索引位置的枚举值 
INSERT INTO test01_enum 
VALUES('1'),(3);

# 忽略大小写 
INSERT INTO test_enum 
VALUES('UNKNOW');

# 插入不存在的值的时候的报错 : Data truncated for column 'season' at row 1 
INSERT INTO test_enum 
VALUES('ab');

# 当ENUM类型的字段没有声明为NOT NULL时，插入NULL也是有效的 
INSERT INTO test_enum 
VALUES(NULL);
```

## SET类型

- 集合类型

```sql
CREATE TABLE test_set( 
    s SET ('A', 'B', 'C') 
);
```

```sql
INSERT INTO test_set (s) VALUES ('A'), ('A,B'); 

#插入重复的SET类型成员时，MySQL会自动删除重复的成员 
INSERT INTO test_set (s) VALUES ('A,B,C,A'); 

#向SET类型的字段插入SET成员中不存在的值时，MySQL会抛出错误。 
INSERT INTO test_set (s) VALUES ('A,B,C,D'); SELECT * FROM test_set;
```

## 二进制字符串类型

MySQL中的二进制字符串类型主要存储一些二进制数据，比如可以存储图片、音频和视频等二进制数据。

### BINARY与VARBINARY类型

这两个类似于CHAR和VARCHAR的关系, 一个是固定的, 一个是变长的

| 类型         | 特点     | 值的长度            | 占用空间  |
| ------------ | -------- | ------------------- | --------- |
| BINARY(M)    | 固定长度 | M (0 <= M <= 255)   | M个字节   |
| VARBINARY(M) | 可变长度 | M (0 <= M <= 65535) | M+1个字节 |

- BINARY类型如果未指定(M), 表示只能存储一个字节
- VARBINARY必须指定(M)

#### BLOB类型

- 二进制大对象, 可以容纳可变数量的数据
- 和INT一样, 分有TINYBLOB, BLOB, MEDIUMBLOB, LONGBLOB四种类型
- 可以用来存储一个二进制大对象, 比如图片, 音频, 视频等
- 实际工作中, 往往不会在MySQL数据库中使用BLOB类型存储大对象数据, 通常会将图片, 音频, 视频文件存储到服务器的磁盘上, 在MySQL中存储访问路径

#### TEXT和BLOB的使用注意事项

1. BLOB和TEXT值, 在执行了大量的删除和更新操作后. 删除这种大值后很容易留下来数据空洞, 而后面填入数据空洞的数据的大小不同, 会出现碎片化, 建议定期对这类表 `OPIMIZE TABLE`进行碎片整理
2. 不要通过SELECT \*这种方式查询这类大的数据类型, 会导致资源的大幅度浪费, 针对大文本字段进行模糊查询, MySQL提供了`前缀索引`
3. 把BLOB或TEXT列`分离到单独的表`中, 如果将这些数据列移动到另一张表后, 可以使原先的表中的数据列转化为固定长度的数据列格式. 这会`减少主表中的碎片`, 获得到固定长度数据行的性能优势. 而且还会在我们SELECT \* 主表的时候, 不会通过网络传输大量的BLOB或TEXT值

## JSON类型

将整个JSON文件的内容, 作为一个数据对象存储

```sql
INSERT INTO test_json (js) 
VALUES 
('{"name":"songhk", "age":18, "address":{"province":"beijing", "city":"beijing"}}');
```

检索的时候, 需要查询某个具体的值的时候可以通过"->", "->>"符号获取

```sql
SELECT js -> '$.name' AS NAME

-> "songhk"
```

## 空间类型

没用的东西

## 小结及选择建议

- 整数 -> `INT`
- 小数 -> `DICIMAL`
- 日期和时间 -> `TEXT`
- 字符串的选择
  - 如果存储的数据范围超过DECIMAL范围, 建议将数据拆分为整数部分和小数部分分开存储
  - 存储的字符串长度几乎相等 -> CHAR
  - VARCHAR长度不要超过 5000, 存储长度大于此值, 定义字段类型为 TEXT, 并独立出来一张表, 用主键来对应, 避免影响其它字段索引效率

# 13. 约束

## 约束概述

### 为什么需要约束

- 为了保证数据的完整性, 对表数据进行的额外的条件限制
  - `实体完整性 ` -> 同一张表中不能存在两条完全相同无法区分的记录
  - `域完整性` ->年龄 0 ~ 120
  - `引用完整性` -> 员工所在部门, 在部门表中要能找到那个部门
  - `用户自定义完整性` -> 用户唯一, 密码不能为空等

### 约束的创建时机

- 在创建表的时候 ( CREATE TABLE )
- 表创建后通过 ( ALTER TABLE) 修改规定约束

### 约束的分类

- 根据约束数据里的限制
  - 单列约束: 只约束一列
  - 多列约束: 可约束多行数据
- 约束的作用范围
  - 列级约束: 只作用在一个列上, 跟在列的定义后面
  - 表级约束: 可以作用在多个列上, 不与表一起定义, 单独定义
- 根据约束起的作用:
  - `NOT NULL` 非空约束, 规定某个字段不能为空
  - `UNIQUE` 唯一约束, 规定某个字段在整张表中式唯一的
  - `PRIMARY KEY`主键约束 (非空且唯一)
  - `FOREIGN KEY` 外键约束
  - `CHECK` 检查约束
  - `DEFAULT` 默认值约束

> MySQL不支持check约束, 但可以使用check约束, 但是没有任何效果

- 查看某个表已有的约束

  ```sql
  # information_schema数据库名 (系统库)
  # table_constraints表名称 (专门存储各个表的约束)
  SELECT * FROM information_schema.table_constrains
  WHERE table_name = '<table-name>'
  ```

## 非空约束 `NOT NULL`

#### 添加非空约束

1. 建表时

```sql
CREATE TABLE <table-name>(
	字段名 数据类型 NOT NULL
)
```

2. 建表后的修改, 加上约束

```sql
ALTER TABLE <table-name>
MODIFY 字段名 数据类型 NOT NULL
```

#### 删除约束

1. 设置为NULL
   ```sql
   ALTER TABLE <table-name>
   MODIFY 字段名 数据类型 NULL
   # 设置为NULL说明允许为NULL
   ```

2. 去掉NOT NULL
   ```sql
   ALTER TABLE <table-name>
   MODIFY 字段名 数据类型
   ```

## 唯一性约束 `UNIQUE`

### 特点

- 约束后的列, 值没有重复
- 可以时某一列的值唯一, 也可以是多个列组合的值唯一
- 允许列值为空
- 创建唯一约束的时候, 如果不给唯一约束命名, 就默认和列名相同

### 复合唯一约束

```sql
unique key(字段列表) 
#字段列表中写的是多个字段名，多个字段名用逗号分隔，表示那么是复合唯一，即多 个字段的组合是唯一的
```

### 添加唯一约束

1. 建表时

```sql
CREATE TABLE <table-name>(
	字段名 数据类型,
    字段名 数据类型 UNIQUE,
    字段名 数据类型 UNIQUE KEY
);

CREATE TABLE <table-name>(
	字段名 数据类型,
    ...,
    [constraint 约束名] UNIQUE KEY(字段名)
)
```

```sql
CREATE TABLE USER(
    id INT NOT NULL,
    -- 列级约束语法
    NAME VARCHAR(25), UNIQUE
    PASSWORD VARCHAR(16),
    -- 表级约束语法
    CONSTRAINT uk_name_pwd UNIQUE(NAME, PASSWORD)
);
```

> 用户名和密码的组合不能重合

2. 建表后指定唯一键约束

```sql
# 列级约束的添加
ALTER TABLE <table-name>
MODIFY 字段名 字段类型 UNIQUE;

# 表级约束的添加
ALTER TABLE <table-name>
ADD UNIQUE KEY(字段名称);
# or
ALTER TABLE <table-name>
ADD CONSTRAINT uk_name_ped UNIQUE(NAME, PASSWORD)
```

> 列级的就是对一个列的修改
>
> 表级的就是添加一个列了

### 删除唯一约束

- 添加唯一性约束的列上会自动创建唯一索引
- 删除唯一性约束只能通过删除唯一索引的方式删除
- 删除时需要指定唯一索引名, 唯一索引名和唯一约束名一样
- 如果创建唯一约束时未指定名称, 如果是单列, 就默认和列名相同, 如果是组合列, 那就默认和()中排在第一个的列名相同, 也可以是自定义唯一性约束名
- 主键的索引名都是 **PRIMARY**, 就算自己命名了主键约束也没用
- **不要修改主键字段的值**, 因为主键是数据的唯一标识, 修改了以后会破坏数据的完整性

```sql
SELECT * FROM information_schema.table_constraints 
WHERE table_name = '<table-name>'
# 查看有哪些约束
```

```sql
ALTER TABLE USER
DROP INDEX uk_name_pwd;
```

> 可以通过show index from 表名称; 来查看到索引

##  主键约束 `PRIMARY KEY`

### 特点

- 每个表有且仅有一个主键
- 主键 <=> 唯一性约束 + 非空约束
- 主键有列级的主键 -> 在创建列的时候添加后缀, 表级主键 -> 新建一列, 用于写约束
- 主键有复合主键和单列主键
  - 复合主键
    - 每个属性都非空
    - 组合不重复
- 一张表只能有一个主键

### 创建主键约束

和唯一性约束的创建模式相同

```sql
# 列级
CREATE TABLE <table-name>(
	id INT PEIMARY KEY
)

# 表级
CREATE TABLE <table-name>(
	id INT,
    CONSTRAINT pk_id PRIMARY KEY(id)
)
CREATE TABLE <table-name>(
	id INT,
    PRIMARY KEY(id)
)
```

- 复合主键和唯一性约束相同

### 添加主键

- 同样和唯一性主键相同
  - 列级主键就当MODIFY一列就行
  - 表级主键就当ADD一列就行

### 删除主键约束

因为一张表只有一个主键, 所以不需要指定索引

```sql
ALTER TABLE <table-name>
DROP PRIMARY KEY;
```

## 自增 `AUTO_INCREMENT`

### 特性

- 只有键列 (主键列, 唯一键列) 后可加上`AUTO_INCREMENT`
- 如果指定键列的值为0或NULL的时候, 就会执行自增长, 自增长的值为`max(当前列) + 1`, 如果指定其他值, 那么就会是其他值
- 一个表最多只有一个自增长列
- 自增长的列数据类型必须是整数类型

### 创建自增长约束

在键值列后添加后缀

### 添加自增长约束

当成一个列修改他的属性即可

`ALTER TABLE <table-name> MODIFY`

### 删除自增长约束

和删除NOT NULL约束一样, 直接覆盖就行

### MySQL8新特性-自增长持久化

- MySQL5.7版本, 我INSERT数据后, 假设id为主键它的值为1,2,3,4, 我删除4后, 再插入, 主键序列为1,2,3,5. 删除后重启数据库, 这个时候插入数据, 得到的序列却会是1,2,3,4
  - 这是因为在5.7中, 计数器的维护是在内存中, 重启后就会重置
- 而在8后, 会符合预期的获得1,2,3,5, 因为自增主键的计数器持久化到`重做日志`

## FOREIGN KEY 约束

### 特点

- 分为主表和从表, 从表references主表
- 从表的外键列, 必须参考主表的主键列或唯一约束键列
- 创建的时候, 如果需要在创建表的时候指定外键, 得先创建主表, 从表才能在创建的时候指定外键
  - 如果是后续添加外键, 则不会有创建顺序上的限制
- 创建时, 如果不给外键约束命名, 默认名不是列名, 而是自动产生的一个外键名
- 删除的时候, 需要先删除从表中依赖的主表的数据, 才能删除主表中对应的数据
- 一个表可以有多个外键
- 从表的外键列与主表的被参照的列的名字可以相同或不同, 但是数据类型必须一样, 逻辑意义一致(NOT NUL等)
- 删除外键约束的时候, 还需要手动删除外键索引

### 创建外键

```sql
CREATE TABLE <table-name>(
	id INT PRIMARY KEY,
    CONSTRAINT fk_id_name FOREIGN KEY (从表的字段) REFERENCES 主表名(主表的字段)
)
```

### 添加外键

```sql
ALTER TABLE 从表名
ADD
[CONSTRAINT 约束名] FOREIGN KEY (从表的字段) REFERENCES 主表名(主表的字段) [on update xx][on delete xx];
```

### 删除外键约束

```sql
# 先查看约束名和删除外键的约束
SELECT * FROM information_schema.table_constraints WHERE table_name = "表名";

ALTER TABLE 从表名 DROP FOREIGN KEY 外键名

# 查看索引名和删除索引
SHOW INDEX FROM 表名称;

ALTER TABLE 表名称 DROP INDEX 索引名;
```

### 约束等级

- `Cascade`: 父表上update/delete记录时, 同步update/delete从表的匹配记录
- `Set null`: 父表上update/delete记录时, 将子表匹配记录的列设为null, 这个时候, 从表的外键列不能为NOT NULL
- `No action`: 如果子表中有匹配的记录, 则不允许对父表对应键进行update/delete操作
- `Restrict`: 和No action一致
- `Set default`: 父表有变更的时候, 子表将外键列设置为一个默认的值, 但Innodb不能识别
- 没有指定, 默认为Restrict方式
- 对于外键约束, 最好采用: `ON UPDATE CASCADE ON DELETE`的方式

### 开发规范

1. \[强制]: 不得使用外键与级联, 一切外键概念必须在应用层解决

## CHECK约束

### 特点

- 在5.7版本是没有用的属性
- 8版本后开始有效

### 使用

```sql
CREATE TABLE <table-name>(
	age INT CHECK(age > 20),
    gender CHAR(5) check (gender IN('男' OR '女'))
)

# 如果插入不合规的值, 就会报错
```

## DEFAULT约束

```sql
# 用于指定默认值
CREATE TABLE <table-name>(
	字段名 数据类型 default 默认值
)
```

## 面试

1. 为什么要在建表的时候, 加上not null default 或 defualt 0
   - 不想让表中出现null值
2. 为什么不想要null
   - 不好比较
   - 效率不高, 影响索引
3. 并不是每个表可以选择任意存储引擎
   - 外键约束不能跨引擎使用

# 14. 视图

> 视图的使用就当一张正常的虚拟表就行

## 视图的意义

- 相当于对于数据和操作的封装, 
  - 对于数据的封装 -> 使数据具有隔离性, 我们可以通过视图, 让用户只能访问到有限的, 我们想提供的数据
  - 对于操作的封装 -> 就像函数一样, 我们把一些复杂的频繁使用的查询, 直接封装为视图, 下次需要查询的时候, 直接调用视图就能获取了
- 视图在执行逻辑上, 也像一个无传参函数, 每次调用视图就是会执行视图里定义的语句, 然后给出返回, 所以视图的返回结果是会根据基表动态变化的, 因为实质上, 就是进行了查询

## 创建视图

```sql
CREATE VIEW 视图名称(字段名)
AS 查询语句

# 如果创建视图的时候指定了字段名, 那SELECT返回的结果的字段名, 就会按照设置字段名别名给出, 
# 如果没有, 就会默认按照SELECT语句中的字段名
```

- 使用情景 : 利用视图进行格式化

我们经常需要输出某个格式的内容, 我们就可以把他封装为一个视图

```sql
CREATE VIEW emp_depart
AS
SELECT CONCAT(last_name, '(', department_name ,')') AS emp_dept
FROM employees e JOIN departments d
WHERE e.departemnt_id = d.department_id
```

- 我们也可以基于视图创建视图

```sql
CREATE VIEW emp_dept_ysalary 
AS
SELECT emp_dept.ename,dname,year_salary 
FROM emp_dept INNER JOIN emp_year_salary 
ON emp_dept.ename = emp_year_salary.ename;
```

## 查看视图

- 就和查看TABLE时一样的

- 查看视图的属性信息
  ```sql
  SHOW TABLE STATUS LIKE '视图名称'\G
  ```

- 查看视图的详细定义信息
  ```sql
  SHOW CREATE VIEW 视图名称;
  ```

## 通过视图更新数据

- 在且仅在视图只是隔离了部分原表的数据的时候可以UPDATE, DELETE
- 在且仅在视图包含了基表中所有被定义为非空又未指认默认值的列, 可以执行INSERT操作
- 形式化的定义 : 视图中的行和底层基本表中的行之间必须存在`一对一`的关系, 才能执行更新操作

> 通过视图来更新数据是一种不推荐的行为, 因为视图作为虚拟表, 主要是为了方便查询, 所以没必要将不属于它的职责强加

## 修改视图

1. 使用`CREATE OR REPLACE VIEW`子句**覆盖式**地修改视图
   ```sql
   CREATE OR REPLACE VIEW <view-name>
   (字段...)
   AS
   SELECT ...
   ```

2. `ALTER VIEW`

   ```sql
   ALTER VIEW 视图名称
   AS
   查询语句
   ```

> 别名要和子查询中的各列一一对应

## 删除视图

- 删除只是删除视图的定义, 不会删除基表的数据

- 语法
  ```sql
  DROP VIEW IF EXISTS 视图名称;
  ```

## 作业

```sql
USE atguigudb;

CREATE TABLE emps
AS
SELECT * FROM atguigudb.employees;

CREATE OR REPLACE VIEW emp_v1
AS
SELECT last_name, salary, email, phone_number
FROM emps
WHERE emps.`phone_number` REGEXP '^011'
AND email REGEXP '[e]';

SELECT * FROM emp_v1;

DESC emp_v1;

INSERT TO emp_v1
VALUES ('方', 12000, 'email', '198');

UPDATE TABLE emp_v1
SET salary = salary + 1000;

DELETE FROM emp_v1
WHERE emp_v1.`last_name` = 'Olsen';

CREATE OR REPLACE VIEW emp_v2
AS 
SELECT department_id, MAX(salary)
FROM employees
GROUP BY department_id
HAVING MAX(salary) > 12000;

SELECT * FROM emp_v2;

SELECT DATABASE();

DROP VIEW emp_v1, emp_v2;
```

### 剩下部分暂不观看, 没什么用, 都是在应用层实现的内容















