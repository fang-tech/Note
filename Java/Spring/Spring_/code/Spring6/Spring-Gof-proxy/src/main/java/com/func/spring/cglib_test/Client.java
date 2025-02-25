package com.func.spring.cglib_test;

import net.sf.cglib.proxy.Enhancer;

public class Client {
    public static void main(String[] args) {
        // 创建增强器
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(OrderServiceImpl.class);
        // 设置回调接口
        enhancer.setCallback(new TimerMethodInterceptor());
        OrderServiceImpl proxy = (OrderServiceImpl) enhancer.create();
        proxy.Delete();
        proxy.Insert();
        proxy.Update();
    }
}
