package com.javaSE;

import jdk.jshell.MethodSnippet;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.*;
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
     * 获取Class对象中的所有构造方法
     */
    @Test
    public void testGetConstructors(){
        Class<?> aClass = Person.class;

        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
    }
    /**
     * 获取Class对象中的某个构造方法
     */
    @Test
    public void testGetConstructor() throws Exception {
        Class<?> aClass = Person.class;

        Constructor<?> constructor = aClass.getConstructor(String.class, Integer.class);
        System.out.println(constructor);

        Person p = (Person) constructor.newInstance("fang",20);
        System.out.println("p = " + p);
    }

    /**
     * 暴力获取私有构造
     */
    @Test
    public void testGetPrivateConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 获取私有构造
        Class<?> aClass = Person.class;

        Constructor<?>[] dc = aClass.getDeclaredConstructors();
        for (Constructor<?> constructor : dc) {
            System.out.println(constructor);
        }

        // 解除私有构造的权限, 使之能使用私有构造
        Constructor<?> constructor = aClass.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        Person p = (Person) constructor.newInstance("fung");
        System.out.println(p);
    }

    /**
     * 通过Class使用public成员方法
     */
    @Test
    public void testGetMethods() throws Exception{
        Class<Person> aClass = Person.class;

        // 获取所有的public方法
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }

        // 获取指定的public方法
        Method setName = aClass.getMethod("setName", String.class);
        System.out.println(setName);
        Method method = aClass.getMethod("getName");
        System.out.println("method = " + method);
        Person p = aClass.newInstance();
        Object o1 = setName.invoke(p, "fung");
        Object o = method.invoke(p);
        System.out.println("o1 = " + o1);
        System.out.println("o = " + o);
    }

    /**
     * 通过Class使用private方法
     */
    @Test
    public void testGetPrivateMethods() throws Exception{
        // 获取所有方法
        Class<Person> aClass = Person.class;

        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }

        // 获取指定的private方法并使用
        Person p = aClass.newInstance();
        Method privateMethod = aClass.getDeclaredMethod("eat");
        privateMethod.setAccessible(true);
        privateMethod.invoke(p);
    }
    /**
     * 通过Class获取成员变量
     */
    @Test
    public void testGetField() throws Exception{
        Class<Person> aClass = Person.class;
        Person p = aClass.newInstance();

        Field[] pubFields = aClass.getFields();
        Field[] allFields = aClass.getDeclaredFields();
        for (Field pubField : pubFields) {
            System.out.println("pubField = " + pubField);
        }
        for (Field allField : allFields) {
            System.out.println("allField = " + allField);
        }

        System.out.println("<==================>");

        Field name = aClass.getDeclaredField("name");
        name.setAccessible(true);
        System.out.println("name = " + name);
        name.set(p,"fang");
        Object o = name.get(p);
        System.out.println(o);
        Field publicField = aClass.getField("publicField");
        publicField.set(p,"test");
        Object o1 = publicField.get(p);
        System.out.println(o1);
    }
}
