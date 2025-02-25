package com.func.mybatis.pagehelper.test;

import com.func.mybatis.pagehelper.mapper.CarMapper;
import com.func.mybatis.pagehelper.pojo.Car;
import com.func.mybatis.pagehelper.utils.SqlSessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;

import java.util.List;

public class TestPager {
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    @Test
    public void testPager(){
        PageHelper.startPage(2, 2);

        List<Car> cars = mapper.selectAll();
//        System.out.println(cars);

        PageInfo<Car> pageInfo = new PageInfo<>(cars, 5);
        System.out.println(pageInfo);
    }
}
