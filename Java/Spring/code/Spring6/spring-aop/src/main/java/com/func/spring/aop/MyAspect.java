package com.func.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class MyAspect {

    @Pointcut("execution(* com.func.spring.aop.OrderService.*(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void beforeAdvice(){
        System.out.println("前置通知");
    }

    @Around("pointcut()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知开始");
        // 执行目标方法
        proceedingJoinPoint.proceed();
        System.out.println("环绕通知开始");
    }
    @AfterReturning("pointcut()")
    public void afterAdvice(){
        System.out.println("后置通知");
    }
    @AfterThrowing("pointcut()")
    public void throwingAdvice(){
        System.out.println("异常通知");
    }
    @After("pointcut()")
    public void finalAdvice(){
        System.out.println("最终通知");
    }
}
