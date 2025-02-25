package com.func.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class TimerAspect {
    // 切面类
    public void time(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        // 执行目标方法
        proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - begin) + "s");
    }
}
