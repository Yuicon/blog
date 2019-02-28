package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author Yuicon
 */
@Aspect
@Component
public class TestAspect {

    @Before("execution(* test(..))")
    public void beforeTest() {
        System.out.println("AOP test before");
    }

    @After("execution(* test2(..))")
    public void afterTest() {
        System.out.println("AOP test after");
    }

    @Around("execution(* test*(..))")
    public Object aroundTest(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("AOP test around 1");
        Object o = proceedingJoinPoint.proceed();
        System.out.println("AOP test around 2");
        return o;

    }

}
