package com.jdbc.base;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCOperation {
    @Test
    public void testQuery() throws Exception {
        // 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 建立连接
        String url = "jdbc:mysql:///atguigu";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 1. 单行单列

        String sql = "SELECT COUNT(*) FROM t_emp";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 执行查询
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            System.out.println("count = " + count);
        }

        // 释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testInsert() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "root");

        String sql = "INSERT INTO t_emp(emp_name, emp_salary, emp_age) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,"第六个人");
        preparedStatement.setDouble(2,2222.2);
        preparedStatement.setInt(3,29);

        int result = preparedStatement.executeUpdate();

        System.out.println("result = " + result);
        if (result > 0) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }

        preparedStatement.close();
        connection.close();
    }
}
