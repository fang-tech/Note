package com.func.spring.jdk_proxy;

public class OrderServiceImpl implements OrderService {
    @Override
    public void Insert() {

        try {
            Thread.sleep(12);
            System.out.println("完成插入");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Delete() {
        try {
            Thread.sleep(18);
            System.out.println("完成删除");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void Update() {
        try {
            Thread.sleep(24);
            System.out.println("完成更新");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
