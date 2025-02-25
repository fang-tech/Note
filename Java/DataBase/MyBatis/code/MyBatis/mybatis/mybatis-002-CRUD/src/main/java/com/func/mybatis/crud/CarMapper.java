package com.func.mybatis.crud;

import java.util.List;

public interface CarMapper {
    List<Car> selectAll();

    void insertGenerateKey(Car car);
}
