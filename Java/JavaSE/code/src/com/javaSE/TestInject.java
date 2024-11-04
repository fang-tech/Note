package com.javaSE;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ClosedSelectorException;
import java.time.Period;
import java.util.Properties;

/**
 * 学习反射的测试类
 */
public class TestInject {
    /**
     * 反射之获取Class对象
     */
    @Test
    public void testGetClass() throws ClassNotFoundException {
        // 1. 通过getClass方法获取Class对象
        Person person = new Person();
        Class<? extends Person> aClass1 = person.getClass();
        System.out.println("aClass1 = " + aClass1);

        // 2. 通过每个类的.class静态成员, 这个是jvm为所有类型提供的一个成员
        Class<Person> aClass2 = Person.class;
        System.out.println("aClass2 = " + aClass2);

        // 3. static Class<?> forName获取
        Class<?> aClass3 = Class.forName("com.javaSE.Person");
        System.out.println("aClass3 = " + aClass3);
    }

    /**
     * 最通用的方法
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void testProperties() throws IOException, ClassNotFoundException {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream("Resources/testForInject.properties");
        properties.load(inputStream);

        String className = properties.getProperty("className");
        Class<?> aClass = Class.forName(className);
        System.out.println("aClass = " + aClass);
    }

    /**
     *
     */
}
