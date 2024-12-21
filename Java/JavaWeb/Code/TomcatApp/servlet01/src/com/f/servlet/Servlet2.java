package com.f.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name="servlet2",
        urlPatterns = "/servlet2"
)
public class Servlet2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");
        System.out.println("Busername = " + name);

        String reqMessage = (String) req.getAttribute("reqKey");
        System.out.println("reqMessage = " + reqMessage);

        resp.getWriter().write("sB response");
    }
}
