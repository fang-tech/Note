package com.func.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.plaf.synth.SynthUI;

@Controller
public class ResultMappingTest {
    // 只有两个参数都填入的时候才符合格式
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET}, params = {"username", "passwd"})
    public String testParam(){
        System.out.println("两个参数都填入了");
        return "login";
    }

    // username 需要为空, passwd不能为空
    @RequestMapping(value =  "/login", method = {RequestMethod.POST, RequestMethod.GET}, params = {"!username", "passwd"})
    public String testParam2(){
        System.out.println("只有密码填入的时候");
        return "login";
    }

    // username 需要=admin, passwd为root的时候
    @RequestMapping(value =  "/login", method = {RequestMethod.POST, RequestMethod.GET}, params = {"username=admin", "passwd=root"})
    public String testParam3(){
        System.out.println("admin用户登录");
        return "login";
    }
    // username 需要!=admin, passwd为root的时候
    @RequestMapping(value =  "/login", method = {RequestMethod.POST, RequestMethod.GET}, params = {"username!=admin", "passwd"})
    public String testParam4(){
        System.out.println("非admin用户登录");
        return "login";
    }
}
