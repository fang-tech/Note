package com.jdbc.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCQuick {
    public static void main(String[] args) throws Exception {
        // 1. 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. 创建连接
        String url = "jdbc:mysql://localhost:3306/atguigu";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url,username,password);

        // 3. 创建Statement对象
        Statement statement = connection.createStatement();

        // 4. 编写SQl语句, 并执行, 以及接收返回
        String sql = "SELECT * FROM t_emp";
        ResultSet resultSet = statement.executeQuery(sql);

        // 5. 处理结果, 遍历结果
        while(resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        // 6. 释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
