package com.f.servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/sessionA")
public class SessionA extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");

        // 获取Session对线
        HttpSession httpSession = req.getSession();
        // 获取SessionId
        String JSESSIONID = httpSession.getId();
        System.out.println("JSESSIONID = " + JSESSIONID);

        // 判断是不是新创建的session
        boolean isNew = httpSession.isNew();
        System.out.println("isNew = " + isNew);
        // 在服务端向sessionID中存入数据
        httpSession.setAttribute("username", name);
    }
}
