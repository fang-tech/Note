package com.jdbc.advanced.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.jdbc.advanced.JDBCAdvanced;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {
    @Test
    public void testHardDruid() throws Exception {
        // 1. 创建德鲁伊资源对象对象
        DruidDataSource dataSource = new DruidDataSource();

        // 2. 配置参数
        // 必须项
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///atguigu");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        // 非必须项
        dataSource.setInitialSize(10);
        dataSource.setMaxActive(20);

        // 3. 创建连接对象
        DruidPooledConnection connection = dataSource.getConnection();


        // 4. 正常进行CRUD
//        connection.prepareStatement()

        // 5. 回收资源
        connection.close();
    }

    @Test
    public void testResourceDruid() throws Exception {
        // 创建properties集合对象
        Properties properties = new Properties();

        // 将文件内容加载到集合中
        InputStream inputStream = DruidTest.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(inputStream);

        // 将配置信息加载到DruidDataSource
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        // 创建Connection后正常使用
        Connection connection = dataSource.getConnection();

        connection.close();
    }
}
