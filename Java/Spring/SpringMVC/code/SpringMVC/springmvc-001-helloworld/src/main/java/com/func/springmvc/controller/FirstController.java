package com.func.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstController {
    @RequestMapping("/hello")
    public String hello(){
        System.out.println("正在处理hello页面的请求");
        // 返回逻辑试图名称 (决定要跳转到哪个页面)
        return "first";
    }
    @RequestMapping("/other")
    public String other(){
        System.out.println("正在处理other页面的请求");
        return "other";
    }
}
