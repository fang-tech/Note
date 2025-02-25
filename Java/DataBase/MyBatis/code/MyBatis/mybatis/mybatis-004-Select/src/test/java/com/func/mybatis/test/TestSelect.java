package com.func.mybatis.test;

import com.func.mybatis.select.Car;
import com.func.mybatis.select.CarMapper;
import com.func.mybatis.utils.SqlSessionUtil;
import com.mysql.cj.ServerPreparedQuery;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestSelect {
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);

    @Test
    public void testSelectAll(){
        List<Car> cars = mapper.selectAll();
        cars.forEach(car -> {
            System.out.println(car);
        });
    }
    /**
     * 使用foreach批量增加
     */
    @Test
    public void testInsertBatchForeach(){
        Car car1 = new Car("1022", "兰博基尼", 100.0,"1998-10-11", "燃油车" ,  null);
        Car car2 = new Car("1022", "兰博基尼", 100.0,"1998-10-11", "燃油车" ,  null);
        Car car3 = new Car("1022", "兰博基尼", 100.0,"1998-10-11", "燃油车" ,  null);
        List<Car> cars = Arrays.asList(car1, car2, car3);
        System.out.println(mapper.insertBatchForeach(cars));
        SqlSessionUtil.openSession().commit();
    }
    /**
     * 用于测试foreach批量删除
     */
    @Test
    public void testDeleteWithForeach(){
        int count = mapper.deleteWithForeach(new Integer[]{1, 2, 3, 4});
        System.out.println(count);
        SqlSessionUtil.openSession().commit();
    }
    @Test
    public void testSelectWithChoose(){
        List<Car> cars = mapper.selectWithChoose("", null, null);
        cars.forEach(car -> {
            System.out.println(car);
        });
    }
    /**
     *  测试动态SQL
     */
    @Test
    public void testSelectDynamic(){
        List<Car> cars = mapper.selectDynamic("", "氢能源", "丰");
        System.out.println(cars);
    }
    @Test
    public void testSelectAllByResultMap(){
        List<Car> cars = mapper.selectAllByResultMap();
        cars.forEach(car -> {
            System.out.println(car);
        });
    }

//    @Test
//    public void testMapSelect(){
//        Map<Long, Map<String, Object>> map = mapper.selectAll();
//        System.out.println(map);
//    }
}
