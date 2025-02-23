package com.func.spring;

import com.func.spring.bean.SpringBean;
import org.junit.After;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
    @Test
    public void testThreadScope(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-bean.xml");
        SpringBean scope = applicationContext.getBean("scope", SpringBean.class);
        System.out.println(scope);

        SpringBean scope1 = applicationContext.getBean("scope", SpringBean.class);
        System.out.println(scope1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SpringBean scope2 = applicationContext.getBean("scope", SpringBean.class);
                System.out.println(scope2);
                SpringBean scope3 = applicationContext.getBean("scope", SpringBean.class);
                System.out.println(scope3);
            }
        }).start();

    }
    @Test
    public void testScope(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-bean.xml");
        SpringBean scope = applicationContext.getBean("scope", SpringBean.class);
        System.out.println(scope);

        SpringBean scope1 = applicationContext.getBean("scope", SpringBean.class);
        System.out.println(scope1);

        SpringBean scope2 = applicationContext.getBean("scope", SpringBean.class);
        System.out.println(scope2);
    }
}
