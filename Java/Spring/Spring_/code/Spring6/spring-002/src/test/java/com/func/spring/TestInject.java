package com.func.spring;

import com.func.spring.bean.*;
import com.func.spring.jdbc.MyDataSource;
import com.func.spring.service.CustomService;
import com.func.spring.service.OderService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInject {
    // 测试装配外部文件
    @Test
    public void testOutFile(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-properties.xml");
        MyDataSource dataSource = applicationContext.getBean("prop", MyDataSource.class);
        System.out.println(dataSource.toString());
    }


    // 测试自动注入
    @Test
    public void testAuto(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("auto-inject.xml");
        CustomService customService = applicationContext.getBean("cs", CustomService.class);
        customService.save();
    }
    // 测试P, C命名空间注入
    @Test
    public void testInject(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        Object dog = applicationContext.getBean("dog");
        System.out.println(dog.toString());

        Dog dog1 = applicationContext.getBean("dog1", Dog.class);
        System.out.println(dog1.toString());
    }
    // 测试特殊字符
    @Test
    public void testMath(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        MathBean math = applicationContext.getBean("math", MathBean.class);
        System.out.println(math.toString());
    }
    // 测试注入null和空字符串
    @Test
    public void testCat(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        Cat cat = applicationContext.getBean("cat", Cat.class);
        System.out.println(cat.getName().toUpperCase());
    }
    // 测试List, Set注入, Map集合, Properties集合
    @Test
    public void testCap(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-array.xml");
        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person.toString());
    }
    /**
     * 测试注入数组
     */
    @Test
    public void testArray(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-array.xml");
        YuQian qian = applicationContext.getBean("qian", YuQian.class);
        System.out.println(qian.toString());
    }
    /**
     * 测试级联属性
     */
    @Test
    public void testCascade(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("cascade.xml");
        Student student = applicationContext.getBean("student", Student.class);
        System.out.println(student.toString());
    }
    /**
     * 测试简单数据的应用 : 数据源的注入
     */
    @Test
    public void testDataSource(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        MyDataSource dataSource = applicationContext.getBean("dataSource", MyDataSource.class);
        System.out.println(dataSource.toString());
    }
    /**
     * 测试所有的简单类型
     */
    @Test
    public void testSimpleType(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        SimpleValue spl = applicationContext.getBean("spv", SimpleValue.class);
        System.out.println(spl.toString());
    }
    /**
     * 测试简单类型的set注入
     */
    @Test
    public void testSimpleTypeInject(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        UserBean user = applicationContext.getBean("user", UserBean.class);
        System.out.println(user.toString());
    }

    /**
     * 测试set注入中的外部注入和内部注入
     */
    @Test
    public void testSetDi(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("set-di.xml");
        OderService oderServ = applicationContext.getBean("oderServ1", OderService.class);
        oderServ.order();

        ApplicationContext applicationContext1 = new ClassPathXmlApplicationContext("set-di.xml");
        OderService oderServ2 = applicationContext1.getBean("oderServ2", OderService.class);
        oderServ2.order();
    }
    /**
     * 测试构造注入
     */
    @Test
    public void testConstructor(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

        CustomService customService = applicationContext.getBean("customService", CustomService.class);
        customService.save();

        ApplicationContext applicationContext1 = new ClassPathXmlApplicationContext("beans.xml");
        CustomService customService1 = applicationContext1.getBean("csbean2", CustomService.class);
        customService1.save();

        ApplicationContext applicationContext2 = new ClassPathXmlApplicationContext("beans.xml");
        CustomService customService2 = applicationContext2.getBean("csbean3", CustomService.class);
        customService2.save();
    }
}
