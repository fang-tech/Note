package com.jdbc.senior;

import com.jdbc.senior.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;

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
}
