package com.func.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TableController {
    @RequestMapping("/table/{username}/{passwd}")
    public String toTable(@PathVariable("username") String username,
                          @PathVariable("passwd") String passwd){
        System.out.println(username);
        System.out.println(passwd);
        return "RESTful";
    }
}
