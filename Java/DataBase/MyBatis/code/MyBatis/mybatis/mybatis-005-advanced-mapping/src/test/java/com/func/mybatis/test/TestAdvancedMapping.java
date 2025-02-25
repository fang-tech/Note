package com.func.mybatis.test;

import com.func.mybatis.mapper.ClazzMapper;
import com.func.mybatis.mapper.StudentMapper;
import com.func.mybatis.pojo.Clazz;
import com.func.mybatis.pojo.Student;
import com.func.mybatis.utils.SqlSessionUtil;
import org.junit.Test;

import java.sql.SQLClientInfoException;
import java.sql.Struct;
import java.util.Arrays;
import java.util.List;

public class TestAdvancedMapping {
    ClazzMapper clazzMapper = SqlSessionUtil.openSession().getMapper(ClazzMapper.class);
    StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
    @Test
    public void testCollection(){
        Clazz clazz = clazzMapper.selectStusByCid(1001);
        System.out.println(clazz);
    }
    @Test
    public void testMapping() {
        Student student = mapper.selectBySid(2);
        System.out.println(student.getSid());
        System.out.println(student.getClazz());
    }
}
