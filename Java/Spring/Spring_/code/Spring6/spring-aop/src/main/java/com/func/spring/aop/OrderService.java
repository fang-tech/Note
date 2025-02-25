package com.func.spring.aop;

import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    public void insert(){
        System.out.println("正在插入数据");
//        OrderService proxy = (OrderService) AopContext.currentProxy();
//        proxy.delete();
//        System.out.println("proxy = " + proxy);
//        System.out.println("this = " + this);
    }

    public void delete(){
        System.out.println("正在添加数据");
        if (false) {
            throw new RuntimeException("模拟异常的发生");
        }
    }
}
