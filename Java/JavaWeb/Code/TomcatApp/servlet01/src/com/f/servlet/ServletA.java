package com.f.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.Enumeration;

@WebServlet(
        name="ServletA",
        urlPatterns = "/servletA",
        initParams = {@WebInitParam(name="paramA", value = "valueA"), @WebInitParam(name="paramB", value = "valueB")}
)
public class ServletA extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = getServletName();
        String value = getInitParameter("paramA");
        System.out.println("servletName = " + name);
        System.out.println("paramsA :" + value);

        Enumeration<String> parameterNames = getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String pname = parameterNames.nextElement();
            String pvalue = getInitParameter(pname);

            System.out.println(pname + " : " + pvalue);
        }
    }
}
