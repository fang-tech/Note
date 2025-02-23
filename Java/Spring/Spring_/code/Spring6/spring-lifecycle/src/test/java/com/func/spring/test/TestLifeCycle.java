package com.func.spring.test;

import com.func.spring.User;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.StubNotFoundException;

public class TestLifeCycle {
    // 测试在半途中将对象交给Spring管理
    @Test
    public void testRegisterBean(){
        User user = new User();
        System.out.println(user);

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerSingleton("userBean",user);
        User user1 = factory.getBean("userBean", User.class);
        System.out.println(user1);
    }
    // 测试Bean的生命周期 : 五步
    @Test
    public void testLifeCycleFive(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        User user = applicationContext.getBean("user", User.class);
        System.out.println("第四步: 使用bean" + user);

        ClassPathXmlApplicationContext context = (ClassPathXmlApplicationContext) applicationContext;
        context.close();
    }
}
