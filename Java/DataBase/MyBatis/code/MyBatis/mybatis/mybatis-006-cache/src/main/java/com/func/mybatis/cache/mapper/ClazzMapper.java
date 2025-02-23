package com.func.mybatis.cache.mapper;

import com.func.mybatis.cache.pojo.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzMapper {
    Clazz selectByCid(@Param("cid") Integer cid);
    Clazz selectStusByCid(@Param("cid") Integer cid);
    int insertClazz(@Param("cname") String cname, @Param("cid") Integer cid);
    List<Clazz> selectAll();
}
