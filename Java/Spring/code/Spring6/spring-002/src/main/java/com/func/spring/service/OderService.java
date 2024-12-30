package com.func.spring.service;

import com.func.spring.dao.OderDao;

public class OderService {
    private OderDao oderDao;

    public void setOderDao(OderDao oderDao) {
        this.oderDao = oderDao;
    }

    public void order(){
        oderDao.insert();
        System.out.println("用户完成了点单");
    }
}
