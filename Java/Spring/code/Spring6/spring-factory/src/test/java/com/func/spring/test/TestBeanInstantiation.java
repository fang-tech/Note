package com.func.spring.test;

import com.func.spring.Gun;
import com.func.spring.Person;
import com.func.spring.Star;
import com.func.spring.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBeanInstantiation {
    // FactoryBean的实际运用
    @Test
    public void testDateFactory(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Student stu = applicationContext.getBean("stu", Student.class);
        System.out.println(stu.toString());
    }
    // 测试beanFactory接口实例化
    @Test
    public void testFactoryBeanImpl(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
    }
    // 测试factory-bean实例化
    @Test
    public void testFactoryBean(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Gun gun = applicationContext.getBean("gun", Gun.class);
        System.out.println(gun);
    }
    // 测试简单工厂模式
    @Test
    public void testSimpleFactory(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Star starBean = applicationContext.getBean("StarBean", Star.class);
        System.out.println("starBean = " + starBean);
    }

}
