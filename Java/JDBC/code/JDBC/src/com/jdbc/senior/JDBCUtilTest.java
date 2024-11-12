package com.jdbc.senior;

import com.jdbc.senior.dao.impl.EmployeeDaoImpl;
import com.jdbc.senior.pojo.Employee;
import com.jdbc.senior.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class JDBCUtilTest {
    @Test
    public void testGetConnection() {
        Connection connection1 = JDBCUtil.getConnection();
        Connection connection2 = JDBCUtil.getConnection();
        Connection connection3 = JDBCUtil.getConnection();

        System.out.println("connection1 = " + connection1);
        System.out.println("connection2 = " + connection2);
        System.out.println("connection3 = " + connection3);
    }

    @Test
    public void testEmployeeDaoImpl(){
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

        List<Employee> employees = employeeDao.selectAll();
        System.out.println(employees);

        Employee employee = employeeDao.selectByEmpId(7);
        Employee employee1 = employeeDao.selectByEmpId(190);
        System.out.println("employee = " + employee);
        System.out.println("employee1 = " + employee1);

        employee.setEmpName("被修改过的人");
        employeeDao.update(employee);
        employeeDao.update(employee1);
        employeeDao.delete(employee.getEmpId());
        System.out.println("employeeDao.selectAll() = " + employeeDao.selectAll());
        Employee employee2 = new Employee(null, "第八个人", 253.22, 20);
        employeeDao.insert(employee2);
        List<Employee> employees1 = employeeDao.selectAll();
        System.out.println("employees1 = " + employees1);
    }
}
