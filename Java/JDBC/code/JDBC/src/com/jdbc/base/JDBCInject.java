package com.jdbc.base;

import java.sql.*;
import java.util.Scanner;

public class JDBCInject {
    public static void main(String[] args) throws Exception {

        // 2. 创建连接
        String url = "jdbc:mysql://localhost:3306/atguigu";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url,username,password);

        // 3. 创建Statement对象
        String sql = "SELECT * FROM t_emp WHERE emp_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 4. 编写SQl语句, 并执行, 以及接收返回
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        preparedStatement.setString(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();

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
        preparedStatement.close();
        connection.close();
    }
}
