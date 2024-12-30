package com.func.spring.test;

import com.func.spring.Husband;
import com.func.spring.Wife;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCycleReference {
    // 测试构造方法赋值属性的时候的循环依赖
    @Test
    public void test2(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring2.xml");
        Husband husband = applicationContext.getBean("h", Husband.class);
        System.out.println(husband);
    }
    // 测试setter赋值属性的方式的循环依赖
    @Test
    public void testCycleReference(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring1.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        System.out.println(husband);
    }
}
