package com.func.mybatis.select;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CarMapper {

    int insertBatchForeach(@Param("cars") List<Car> cars);
    int deleteWithForeach(@Param("ids") Integer[] ids);
    List<Car> selectWithChoose(@Param("guidePrice") String guidePrice, @Param("carType") String carType, @Param("brand") String brand);
    List<Car> selectDynamic(@Param("carNum") String carNum, @Param("carType") String carType, @Param("brand") String brand);
    List<Car> selectAll();

    List<Car> selectAllByResultMap();
}
