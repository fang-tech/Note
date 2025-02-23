package org.springboot.sb3003externalconfig;

import org.junit.jupiter.api.Test;
import org.springboot.sb3003externalconfig.bean.*;
import org.springboot.sb3003externalconfig.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.time.Period;

@SpringBootTest
class Sb3003ExternalConfigApplicationTests {

    @Autowired
    // 注入的方式获取对象
    private UserService userService;
    @Test
    void contextLoads() {
        System.out.println("userService.getClass() = " + userService.getClass());
        userService.detail();
        userService.generate();
    }
}
