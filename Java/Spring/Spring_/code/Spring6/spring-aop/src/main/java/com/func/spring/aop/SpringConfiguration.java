package com.func.spring.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.func.spring.aop")
@EnableAspectJAutoProxy(exposeProxy = true)
public class SpringConfiguration {
}
