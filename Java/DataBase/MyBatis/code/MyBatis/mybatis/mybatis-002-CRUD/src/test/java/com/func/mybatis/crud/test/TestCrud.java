package com.func.mybatis.crud.test;

import com.func.mybatis.crud.Car;
import com.func.mybatis.crud.CarMapper;
import com.func.mybatis.util.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCrud {
//    CarMapper carMapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    @Test
    public void testArgsSelect(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 10);
        map.put("car_num", "5262");
        List<Car> selectCarById = sqlSession.selectList("car.selectCarById", map);
        selectCarById.forEach(car -> {
            System.out.println(car);
        });
    }
//    @Test
//    public void testAutoGenerateKey(){
//        Car car = new Car();
//        car.setCarNum("5262");
//        car.setBrand("BYD汉");
//        car.setGuidePrice(30.3);
//        car.setProduceTime("2020-10-11");
//        car.setCarType("新能源");
//        carMapper.insertGenerateKey(car);
//        System.out.println(car);
//    }
//    @Test
//    public void testAliases(){
//        List<Car> cars = carMapper.selectAll();
//        cars.forEach(car -> {
//            System.out.println(car);
//        });
//    }
    @Test
    public void testNameSpace(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        List<Object> cars = sqlSession.selectList("car2.selectCarAll");
        cars.forEach(car -> System.out.println(car));
    }
    @Test public void testSelectList(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        List<Object> cars = sqlSession.selectList("selectCarAll");
        cars.forEach(car -> System.out.println(car));
    }
    @Test
    public void testQueryOne(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Object car = sqlSession.selectOne("selectCarById", 1);
        System.out.println("car = " + car);
    }
    @Test
    public void testUpdate(){
        Car car = new Car();
        car.setId(7);
        car.setBrand("奔驰");
        car.setCarType("电车");
        car.setProduceTime("2022-01-23");
        car.setGuidePrice(12.3);
        car.setCarNum("126");

        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.update("updateById", car);
        System.out.println("count = " + count);

    }
    @Test
    public void testDelete(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.delete("deleteByCarNum", "124");
        System.out.println("count = " + count);

    }
    @Test
    public void testObjectInsert(){
        Car car = new Car();
        car.setBrand("奔驰E");
        car.setCarNum("124");
        car.setCarType("燃油车");
        car.setGuidePrice(123.2);
        car.setProduceTime("2023-12-05");

        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.insert("insertCar", car);
        System.out.println("count = " + count);

    }
    @Test
    public void testMapInsert(){
        HashMap map = new HashMap<>();
        map.put("carNum", "111");
        map.put("brand", "奔驰E300L");
        map.put("guidePrice", 70.3);
        map.put("produceTime", "2020-10-12");
        map.put("carType", "燃油车");

        SqlSession sqlSession = null;

        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            sqlSession = sqlSessionFactory.openSession();
            int count = sqlSession.insert("insertCar", map);
            System.out.println("count = " + count);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
