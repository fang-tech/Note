package com.jdbc.senior.dao;

import com.jdbc.senior.util.JDBCUtil;
import com.zaxxer.hikari.metrics.IMetricsTracker;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
    /**
     * 增删改的方法的共性
     * @param sql
     * @param params
     * @return
     */
    public int executeUpdate(String sql, Object... params) throws Exception {
        Connection connection = JDBCUtil.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        if (params != null && params.length > 0) {
            for (int i =0; i <params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
        }

        int result = preparedStatement.executeUpdate();

        preparedStatement.close();
        JDBCUtil.release();
        return result;
    }

    /**
     * 通用的查询方法
     * 可能给出的返回
     *  单行单列 : double, Integer...
     *  单行多列 : Employee
     *  多行多列 : List<Employee>
     * 封装过程
     *  1. 返回的结果结果类型不确定, 但是调用者知道, 所以通过泛型传入类型
     *  2. 统一使用List返回, 这样就能包含所有情况
     *  3. 结果的返回, 通过反射, 要求调用者告知要封装的类的类对象
     */
    public <T> List<T> executeQuery(Class<T> clazz,String sql, Object... params) throws Exception {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (params != null && params.length > 0) {
            for (int i = 1; i <= params.length; i++) {
                preparedStatement.setObject(i, params[i-1]);
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        List<T> list = new ArrayList<>();

        while(resultSet.next()) {
            T t = clazz.newInstance();

            for (int i = 1; i <= columnCount; i++) {
                Object value = resultSet.getObject(i);
                String name = metaData.getColumnLabel(i);
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                field.set(t, value);
            }
            list.add(t);
        }

        resultSet.close();
        preparedStatement.close();
        JDBCUtil.release();
        return list;
    }

    /**
     * 通用查询, 但是是只针对于单个查询的情况
     */
    public <T> T executeQueryBean(Class<T> clazz, String sql, Object... params) throws Exception {
        List<T> ts = executeQuery(clazz, sql, params);
        if (ts == null || ts.size() == 0) {
            return null;
        }
        return ts.get(0);
    }
}
