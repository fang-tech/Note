package com.func.mybatis.cache.mapper;

import com.func.mybatis.cache.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.io.InterruptedIOException;

public interface StudentMapper {
    Student selectBySid(@Param("sid") Integer sid);
    Student selectByCid(@Param("cid") Integer cid);
}
