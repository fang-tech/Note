package com.func.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class list {
    @GetMapping("/list")
    public String list(){
        return "list";
    }
}
