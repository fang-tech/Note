package com.func.spring.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
public class SpringBean {
    public SpringBean() {
        System.out.println(this + "构造完毕");
    }
}
