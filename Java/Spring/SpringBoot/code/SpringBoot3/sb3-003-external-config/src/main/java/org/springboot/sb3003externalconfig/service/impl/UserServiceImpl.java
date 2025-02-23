package org.springboot.sb3003externalconfig.service.impl;


import org.springboot.sb3003externalconfig.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public void generate() {
        System.out.println("生成订单");
    }

    @Override
    public void detail() {
        System.out.println("订单细节");
    }
}
