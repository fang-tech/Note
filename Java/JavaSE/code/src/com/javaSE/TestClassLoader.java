package com.javaSE;

import org.junit.Test;

/**
 * 说明类的加载机制
 */
public class TestClassLoader {
    @Test
    public void testBootstrapLoader(){
        //测试启动类加载器
        ClassLoader classLoader = String.class.getClassLoader();
        System.out.println("classLoader = " + classLoader);
        // 启动类的加载器是用c语言写的, 无法直接获取

        ClassLoader classLoader1 = TestClassLoader.class.getClassLoader().getParent().getParent();
        System.out.println("classLoader1 = " + classLoader1);
    }

    @Test
    public void testExtensionLoader(){
        //测试拓展类加载器
        ClassLoader classLoader = TestClassLoader.class.getClassLoader().getParent();
        System.out.println("classLoader = " + classLoader);
    }

    @Test
    public void testApplicationLoader(){
        //测试应用类加载器
        ClassLoader classLoader = TestClassLoader.class.getClassLoader();
        System.out.println("classLoader = " + classLoader);
    }
}
