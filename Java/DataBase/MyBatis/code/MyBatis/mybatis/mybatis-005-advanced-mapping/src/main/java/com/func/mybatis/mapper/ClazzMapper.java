package com.func.mybatis.mapper;

import com.func.mybatis.pojo.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.Iterator;
import java.util.List;

public interface ClazzMapper {
    Clazz selectByCid(@Param("cid") Integer cid);
    Clazz selectStusByCid(@Param("cid") Integer cid);
}
