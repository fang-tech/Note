package com.jdbc.advanced;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.jdbc.advanced.pojo.Employee;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCAdvanced {
    @Test
    public void testORMList() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "root");

        String sql = "SELECT * FROM t_emp";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Employee> EmployeeList = new ArrayList<>();
        Employee employee = null;
        while (resultSet.next()) {
            employee = new Employee();

            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");

            employee.setEmpId(empId);
            employee.setEmpName(empName);
            employee.setEmpSalary(empSalary);
            employee.setEmpAge(empAge);

            EmployeeList.add(employee);
        }

        for (Employee employee1 : EmployeeList) {
            System.out.println(employee1);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testPrimaryKeyBack() throws Exception {
        /**
         * @brief 用于测试主键回显
         */
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "root");

        Employee employee = new Employee(null, "第七个人", 234.5, 99);
        String sql = "INSERT INTO t_emp(emp_name, emp_age, emp_salary) VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, employee.getEmpName());
        preparedStatement.setInt(2, employee.getEmpAge());
        preparedStatement.setDouble(3, employee.getEmpSalary());

        int result = preparedStatement.executeUpdate();

        if (result > 0) {
            System.out.println("successful");
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int empId = generatedKeys.getInt(1);
                employee.setEmpId(empId);
            }
            generatedKeys.close();
        } else {
            System.out.println("failed");
        }

        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testBatch() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?rewriteBatchedStatements=true", "root", "root");

        String sql = "INSERT INTO t_emp(emp_name, emp_salary, emp_age) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        long start = System.currentTimeMillis();
        for (int i = 1; i < 10000+1; i++) {
            preparedStatement.setString(1, "marry" + i);
            preparedStatement.setDouble(2, 20+i);
            preparedStatement.setInt(3, 30+i);

//            preparedStatement.executeUpdate();
            preparedStatement.addBatch();
        }
//        preparedStatement.executeUpdate();
        preparedStatement.executeBatch();
        long end = System.currentTimeMillis();

        System.out.println("wast time :" + (end - start));

        preparedStatement.close();
        connection.close();
    }

}
