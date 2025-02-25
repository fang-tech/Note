package com.func.spring.jdk_proxy;

public class Client {
    public static void main(String[] args) {
        // 目标对象
        OrderService target = new OrderServiceImpl();
        // 代理对象
        OrderService proxy = (OrderService) ProxyUtil.newProxyInstance(target);
        proxy.Delete();
        proxy.Insert();
        proxy.Update();
    }
}
