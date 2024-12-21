package com.f.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;

@WebServlet("/sessionB")
public class SessionB extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session
        HttpSession session = req.getSession();
        String JSESSIONID = session.getId();
        System.out.println("JSESSIONID = " + JSESSIONID);

        // 是不是新的ID
        boolean isNew = session.isNew();
        System.out.println(isNew);
        // 获取其中的数据
        String name = (String) session.getAttribute("username");
        System.out.println("name = " + name);
    }
}
