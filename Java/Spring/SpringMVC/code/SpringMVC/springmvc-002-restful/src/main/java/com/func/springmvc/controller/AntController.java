package com.func.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AntController {
    @RequestMapping("/xz/detail/**")
    public String toDetail(){
        System.out.println("模糊匹配页面正在解析");
        return "/ant";
    }
}
