package com.func.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product")
@Controller
public class UserController {
    @RequestMapping("/detail")
    public String toDetail(){
        return "/product/detail";
    }
}
