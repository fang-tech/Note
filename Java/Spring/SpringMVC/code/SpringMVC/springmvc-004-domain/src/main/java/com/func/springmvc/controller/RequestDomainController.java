package com.func.springmvc.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.Map;

@SessionAttributes({"x", "y"})
@Controller
public class RequestDomainController {
    @RequestMapping("/")
    public String toIndex(){
        return "index";
    }
    @RequestMapping("/testDomainForModel")
    public String toView(Model model){
        model.addAttribute("testRequestScope", "这是通过Model加载到Request的请求域里的内容");
        return "view";
    }
    @RequestMapping("/testDomainForModelMap")
    public String toView(Map<String, Object> map){
        map.put("testRequestScope", "这是通过map加载到Request请求域里的内容");
        return "view";
    }
    @RequestMapping("/testDomainForMap")
    public String toView(ModelMap modelMap){
        modelMap.addAttribute("testRequestScope", "这是通过ModelMap加载到Request的请求域里的内容");
        return "view";
    }
    @RequestMapping("/testDomainForModelAndView")
    public ModelAndView toView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testRequestScope", "这是通过ModelAndView加载到Request请求域里的内容");
        modelAndView.setViewName("view");
        return modelAndView;
    }

    @RequestMapping("/testSessionScope")
    public String testSession(HttpSession session){
        session.setAttribute("testSessionScope", "value of x");
        return "view";
    }

    @RequestMapping("/testSessionWithMap")
    public String testSessionWithMap(ModelMap map){
        map.addAttribute("x", "value of x");
        map.addAttribute("y", "value of y");

        return "view";
    }

    @RequestMapping("/testApplication")
    public String testApplication(HttpServletRequest request){
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("applicationkey","application-value");

        return "view";
    }
}
