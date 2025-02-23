package com.func.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

public class User implements BeanClassLoaderAware, BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {
    private String name;
    public User(){
        System.out.println("第一步: 构造bean");
    }

    public void setName(String name) {
        System.out.println("第二步: 为bean赋值");
        this.name = name;
    }
    public void initBean(){
        System.out.println("第三步: 初始化bean");
    }

    public void destroyBean(){
        System.out.println("第五步: 销毁bean");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("Bean的类加载器是" + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("生产bean的工厂是" + beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("bean的名字是" + name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行了 afterPropertiesSet() 方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行了销毁bean前的会执行的方法");
    }
}
