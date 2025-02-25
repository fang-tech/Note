package com.func.spring.jdk_proxy;

public class OrderServiceProxy implements OrderService{

    private OrderServiceImpl service;

    public OrderServiceProxy(OrderServiceImpl service) {
        this.service = service;
    }

    @Override
    public void Insert() {
        long begin = System.currentTimeMillis();
        service.Insert();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");
    }

    @Override
    public void Delete() {
        long begin = System.currentTimeMillis();
        service.Delete();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");

    }

    @Override
    public void Update() {
        long begin = System.currentTimeMillis();
        service.Update();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");
    }
}
