package com.f.schedule.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@AllArgsConstructor // 全参构造
@NoArgsConstructor // 无参构造
@Data // getter setter equals hashcode toString
public class SysUser implements Serializable {
    private Integer uid;
    private String userName;
    private String userPwd;

}
