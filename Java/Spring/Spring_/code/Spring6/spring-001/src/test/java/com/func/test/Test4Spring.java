package com.func.test;

import com.func.spring.bean.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test4Spring {

    @Test
    public void testLog4j2(){
        // 测试Spring中的Log4j2
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        User user = applicationContext.getBean("user", User.class);

        System.out.println("user = " + user);

        // 自己使用Log4j2打印信息
        Logger logger  = LoggerFactory.getLogger(Test4Spring.class);
        logger.info("info信息");
        logger.debug("debug信息");
        logger.error("error信息");
    }
    @Test
    public void testBean(){
        // 在spring容器中, 加载类
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        User user = applicationContext.getBean("user", User.class);

        System.out.println("user = " + user);
    }

    @Test
    public void testBeanFactory(){
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring.xml");

        User user = beanFactory.getBean("user", User.class);

        System.out.println("user = " + user);
    }
}
