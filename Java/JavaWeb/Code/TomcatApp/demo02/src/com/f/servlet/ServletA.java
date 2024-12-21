package com.f.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/servletA")
public class ServletA extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie1 = new Cookie("C1", "c1-value");
        cookie1.setPath("/demo02_war_exploded/servletB");
        Cookie cookie2 = new Cookie("C2", "c2-value");
//        cookie2.setPath("/demo2_war_exploded/servletA");
        resp.addCookie(cookie1);
        resp.addCookie(cookie2);

    }
}
