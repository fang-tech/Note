package com.f.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

/**
 * @brief   实现应用域的监听
 */
@WebListener
public class ApplicationListener implements ServletContextListener, ServletContextAttributeListener {
    /**
     * 监听ServletContext对象的创建和销毁
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        System.out.println("application"+application.hashCode()+" initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        System.out.println("application"+application.hashCode()+" destroyed");
    }

    /**
     * 监听ServletContext中的属性的添加移除和修改
     */
    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        ServletContext application = scae.getServletContext();
        System.out.println("application = " + application.hashCode() + " added");
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        ServletContext application = scae.getServletContext();
        System.out.println("application = " + application.hashCode() + " replaced");
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        ServletContext application = scae.getServletContext();
        System.out.println("application = " + application.hashCode() + " removed");
    }
}
