package com.func.bank.test;

import javassist.*;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestJavassist {
    @Test
    public void testJavassist() throws CannotCompileException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // 获取类池
        ClassPool pool = ClassPool.getDefault();

        // 创建类
        CtClass ctClass = pool.makeClass("com.func.bank.test.Test");
        // 为类创建方法
        // 返回类型, 方法名, 形式参数列表, 所属类
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, ctClass);
        // 为方法添加权限修饰符
        ctMethod.setModifiers(Modifier.PUBLIC);
        // 为方法添加方法体
        ctMethod.setBody("{System.out.println(\"Hello world\");}");
        // 为类添加方法
        ctClass.addMethod(ctMethod);
        //调用方法j
        Class<?> aClass = ctClass.toClass();
        Object o = aClass.newInstance();
        Method method = o.getClass().getDeclaredMethod("execute");
        method.invoke(o);
    }
}

