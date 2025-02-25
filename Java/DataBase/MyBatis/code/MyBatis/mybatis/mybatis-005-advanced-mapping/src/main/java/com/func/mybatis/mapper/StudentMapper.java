package com.func.mybatis.mapper;

import com.func.mybatis.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Iterator;

public interface StudentMapper {
    Student selectBySid(@Param("sid") Integer sid);
    Student selectByCid(@Param("cid") Integer cid);
}
