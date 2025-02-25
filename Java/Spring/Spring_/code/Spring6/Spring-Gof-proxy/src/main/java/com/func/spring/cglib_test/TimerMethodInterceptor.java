package com.func.spring.cglib_test;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TimerMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retValue = methodProxy.invokeSuper(o, objects);
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");
        System.out.println("method = " + method);
        System.out.println("methodProxy = " + methodProxy);
        return retValue;
    }
}
