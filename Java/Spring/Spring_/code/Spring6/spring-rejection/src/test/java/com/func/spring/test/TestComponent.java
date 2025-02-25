package com.func.spring.test;

import com.func.spring.Spring6Configuration;
import com.func.spring.User;
import com.func.spring.Vip;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

public class TestComponent {

    @Test
    public void testConfigClass(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Spring6Configuration.class);
        Object user = annotationConfigApplicationContext.getBean("user");
        System.out.println(user.toString());
    }
    /**
     * 通过注解出创建bean实例
     */
    @Test
    public void testBeanComponent(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println(user);
        Vip vip = (Vip) applicationContext.getBean("vip");
        System.out.println(vip);
    }

}
