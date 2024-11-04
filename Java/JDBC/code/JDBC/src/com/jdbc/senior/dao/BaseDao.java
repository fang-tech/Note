package com.jdbc.senior.dao;

import com.jdbc.senior.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public ResultSet executeQuery(Integer EmpId)
}
