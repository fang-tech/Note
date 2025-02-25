package com.func.spring;

import lombok.Data;

@Data
public class User {
    private String name;
    private Integer age;
    private User partner;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", partner=" + partner.getName() +
                '}';
    }
}
