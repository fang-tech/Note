package com.func.mybatis.cache.pojo;

import com.mysql.cj.ServerPreparedQuery;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Clazz implements Serializable{
    private Integer cid;
    private String cname;
    private List<Student> stus;
}
