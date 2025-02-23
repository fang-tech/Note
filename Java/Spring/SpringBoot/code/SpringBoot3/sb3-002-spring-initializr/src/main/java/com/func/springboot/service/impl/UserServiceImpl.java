package com.func.springboot.service.impl;

import com.func.springboot.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Override
    public void save(){
        System.out.println("保存用户信息");
    }
}
