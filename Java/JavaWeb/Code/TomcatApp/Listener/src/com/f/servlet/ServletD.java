package com.f.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 修改和删除数据
 */
@WebServlet("/servletD")
public class ServletD extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
//        String bId = session.getId();
//        System.out.println("bId = " + bId);

        session.setAttribute("s-K", "s-V");
        session.removeAttribute("s-K");
        session.invalidate();
    }
}
