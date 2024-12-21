package com.f.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name="servlet1",
        urlPatterns="/servlet1"
)
public class Servlet1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("服务1");

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("servlet2");

        // 获取请求参数
        String name = req.getParameter("username");
        System.out.println("Ausername = " + name);

        // 设置请求域
        req.setAttribute("reqKey", "regMessage");
        // 转发请求
        requestDispatcher.forward(req, resp);
    }
}
