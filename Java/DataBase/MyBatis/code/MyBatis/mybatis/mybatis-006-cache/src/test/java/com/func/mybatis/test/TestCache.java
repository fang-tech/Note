package com.func.mybatis.test;

import com.func.mybatis.cache.mapper.ClazzMapper;
import com.func.mybatis.cache.pojo.Clazz;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.lang.annotation.Retention;
import java.util.List;

public class TestCache {
    @Test
    public void testSecondCache() throws Exception{
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        List<Clazz> clazzes = mapper.selectAll();
        System.out.println(clazzes);
        sqlSession.close();
        ClazzMapper mapper1 = sqlSessionFactory.openSession().getMapper(ClazzMapper.class);
        List<Clazz> clazzes1 = mapper1.selectAll();
        System.out.println(clazzes1);
    }
    @Test
    public void testFirstCache() throws Exception{
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        System.out.println(mapper.selectStusByCid(1001));
        // 完全一样的语句
        System.out.println(mapper.selectStusByCid(1001)); // 只这次执行并没有执行SQL语句
        // 改变查询内容
        System.out.println(mapper.selectStusByCid(1002));
        //  更换SqlSession
        ClazzMapper mapper1 = sqlSessionFactory.openSession().getMapper(ClazzMapper.class);
        System.out.println(mapper1.selectStusByCid(1001));
        // 对数据库进行增删改查
        System.out.println(mapper.insertClazz("高三六班", 1003));
        sqlSession.commit();
        // 再次进行查询
        System.out.println(mapper.selectStusByCid(1001));
    }
}
