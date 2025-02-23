package com.func.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MyController {
    @RequestMapping("/hello")
    public String hello(){
        return "<h1>Hello World</h1>";
    }
}
