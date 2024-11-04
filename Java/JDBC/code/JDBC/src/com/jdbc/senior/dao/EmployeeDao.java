package com.jdbc.senior.dao;

import com.jdbc.senior.pojo.Employee;

import java.util.List;

/**
 * DAO : Data Access Operation : 数据访问操作
 * 对应Employee的CRUD操作的接口
 */
public interface EmployeeDao {
    /**
     * 查询所有的表中的数据
     * @return 表中的所有数据
     */
    List<Employee> selectAll();

    /**
     * 用主键Id查询到我们所需要的行的信息
     * @param EmpId
     * @return
     */
    Employee selectByEmpId(Integer EmpId);

    /**
     * 新增一行数据
     * @param employee
     * @return 受影响的行数
     */
    int insert(Employee employee);

    /**
     * 修改Employee对象
     * @param employee
     * @return 受影响的行数
     */
    int update(Employee employee);

    /**
     * 根据主键删除数据
     * @param EmpId
     * @return 受影响的行数
     */
    int delete(Integer EmpId);
}
