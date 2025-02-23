package com.func.springmvc.controller;

import com.func.springmvc.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Iterator;

@Controller
public class UserController {
    @RequestMapping("/")
    public String toIndex(){
        return "index";
    }

    public static void main(String[] args) {
        int[] a1 = new int[12];

    }
    @RequestMapping(value = "/register" , method = {RequestMethod.GET, RequestMethod.POST})
    public String register(User user,
                           @RequestHeader("Referer") String Referer,
                           @CookieValue("id") String cookie_id
    ){
        System.out.println("user = " + user);
        System.out.println("Referer = " + Referer);
        System.out.println("cookie = " + cookie_id);
        return "success";
    }



}
