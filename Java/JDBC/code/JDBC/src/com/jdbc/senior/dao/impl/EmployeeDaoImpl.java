package com.jdbc.senior.dao.impl;

import com.jdbc.senior.dao.BaseDao;
import com.jdbc.senior.dao.EmployeeDao;
import com.jdbc.senior.pojo.Employee;

import java.util.List;

/**
 * 对EmployeeDao的具体实现
 */
public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {
    @Override
    public List<Employee> selectAll() {
        try {
            String sql = "SELECT emp_id empId, emp_name empName, emp_salary empSalary, emp_age empAge FROM t_emp;";
            return executeQuery(Employee.class, sql, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee selectByEmpId(Integer EmpId) {
        try {
            String sql = "SELECT emp_id empId, emp_name empName, emp_salary empSalary, emp_age empAge FROM t_emp WHERE emp_id = ?;";
            return executeQueryBean(Employee.class, sql, EmpId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Employee employee) {
        try {
            if (employee == null) return 0;
            String sql = "INSERT INTO t_emp(emp_id, emp_age, emp_name, emp_salary) VALUES (?, ?, ?, ?);";
            int result = executeUpdate(sql, null, employee.getEmpAge(), employee.getEmpName(), employee.getEmpSalary());
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Employee employee) {
        try {
            if (employee == null) return 0;
            String sql = "UPDATE t_emp SET emp_name = ?, emp_age = ?, emp_salary = ? WHERE emp_id = ?";
            int result = executeUpdate(sql, employee.getEmpName(), employee.getEmpAge(), employee.getEmpSalary(), employee.getEmpId());
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Integer EmpId) {
        try {
            String sql = "DELETE FROM t_emp WHERE emp_id = ?";
            int result = executeUpdate(sql, EmpId);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
