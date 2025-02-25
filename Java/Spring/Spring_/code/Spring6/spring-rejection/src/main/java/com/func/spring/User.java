package com.func.spring;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("user")
public class User {
    @Value("UserName")
    private String name;

    @Resource(name = "vip")
    private Vip vip;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", vip=" + vip.getName() +
                '}';
    }
}
