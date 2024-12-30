package com.func.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Aspect
@Component
@Order(2)
public class MyAspect2 {

    @Pointcut("execution(* com.func.spring.aop.OrderService.*(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void beforeAdvice(){
        System.out.println("aspect2前置通知");
    }

    @Around("pointcut()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("aspect2环绕通知开始");
        // 执行目标方法
        proceedingJoinPoint.proceed();
        System.out.println("aspect2环绕通知开始");
    }
    @AfterReturning("pointcut()")
    public void afterAdvice(){
        System.out.println("aspect2后置通知");
    }
    @AfterThrowing("pointcut()")
    public void throwingAdvice(){
        System.out.println("aspect2异常通知");
    }
    @After("pointcut()")
    public void finalAdvice(){
        System.out.println("aspect2最终通知");
    }
}
