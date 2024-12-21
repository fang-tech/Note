package com.f.schedule.test;

import com.f.schedule.pojo.SysUser;

public class TestLombok {
    public static void main(String[] args) {
        SysUser sysUser = new SysUser(1,"fung", "pwd");
        System.out.println("sysUser = " + sysUser);
    }
}
