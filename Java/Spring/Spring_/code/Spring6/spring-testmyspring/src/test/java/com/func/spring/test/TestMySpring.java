package com.func.spring.test;

import com.func.spring.UserDao;
import org.junit.Test;
import org.myspringframework.core.ApplicationContext;
import org.myspringframework.core.ClassPathXmlApplicationContext;

public class TestMySpring {
    @Test
    public void testMySpring(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Object user1 = applicationContext.getBean("user1");
        System.out.println(user1.toString());

        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        userDao.insert();
    }
}
