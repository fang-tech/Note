package com.func.mybatis.pagehelper.mapper;

import com.func.mybatis.pagehelper.pojo.Car;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CarMapper {
    List<Car> selectAll();
}
