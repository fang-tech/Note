package com.f.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.*;

import java.io.IOException;

public class Filter2 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter2 before chain.doFilter code invoked");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Filter2 before chain.doFilter code invoked");
    }
}
