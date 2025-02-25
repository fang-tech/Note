package com.func.spring.service;

import com.func.spring.dao.UserDao;
import com.func.spring.dao.VipDao;
import lombok.Data;

@Data
public class CustomService {
    private UserDao userDao;
    private VipDao vipDao;

    public void save(){
        userDao.insert();
        vipDao.insert();

        System.out.println("用户保存数据完毕");
    }

    public CustomService() {
        System.out.println("加载了CustomService的无参构造方法");
    }

    CustomService(UserDao userDao, VipDao vipDao) {
        System.out.println("加载了CustomService的有参构造方法");
        this.vipDao = vipDao;
        this.userDao = userDao;
    }
}
