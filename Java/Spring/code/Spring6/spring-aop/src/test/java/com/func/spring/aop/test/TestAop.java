package com.func.spring.aop.test;

import com.func.spring.aop.OrderService;
import com.func.spring.aop.SpringConfiguration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {
    @Test
    public void testXml(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("aop.xml");
        OrderService orderService = classPathXmlApplicationContext.getBean("order", OrderService.class);
        orderService.insert();
    }
    @Test
    public void testAnnotation(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        OrderService orderService = annotationConfigApplicationContext.getBean("orderService", OrderService.class);
        Object myAspect = annotationConfigApplicationContext.getBean("myAspect");
        orderService.insert();
        System.out.println(myAspect);
    }

    @Test
    public void testAop(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop.xml");
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        orderService.delete();
//        orderService.insert();
    }
}
