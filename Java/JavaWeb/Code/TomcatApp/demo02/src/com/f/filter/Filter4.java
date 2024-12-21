package com.f.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;

@WebFilter(
        filterName = "loggingFilter",
        initParams = {@WebInitParam(name = "dateTimePattern", value = "yyyy-MM-dd HH:mm:ss")},
//        urlPatterns = {
//                "/servletG",
//                "*.html"
//        },
        servletNames = {"ServletG"}
)
public class Filter4 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter 4 before doFilter invoked");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Filter 4 after doFilter invoked");
    }
}
