package com.func.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class ProductController {
    @RequestMapping({"/detail", "/detail1"})
    public String toDetail(){
        return "/user/detail";
    }
}
