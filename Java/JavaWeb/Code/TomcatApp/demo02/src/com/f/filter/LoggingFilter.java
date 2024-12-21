package com.f.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingFilter implements Filter {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 父转子
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // 拼接日志文本
        String requestURI = req.getRequestURI();
        String time = dateFormat.format(new Date());
        String beforeLogging = requestURI + " at " + time;
        System.out.println("beforeLogging = " + beforeLogging);

        long t1 = System.currentTimeMillis();
        filterChain.doFilter(req, resp);
        long t2 = System.currentTimeMillis();
        String afterLogging = requestURI + " 耗时 " + (t2-t1);
        System.out.println("afterLogging = " + afterLogging);
    }
}
