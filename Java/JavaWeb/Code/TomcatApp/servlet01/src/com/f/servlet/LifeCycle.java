package com.f.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LifeCycle extends HttpServlet {
    public LifeCycle(){
        System.out.println("构造方法");
    }
    @Override
    public void init() throws ServletException {
        System.out.println("初始化方法");
    }

    @Override
    public void destroy() {
        System.out.println("销毁方法()");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service()");
    }
}
