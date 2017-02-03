package com.example.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 16/8/1.
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
  @Around("@annotation(com.example.aspects.Log)")
  public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("this is the method before");
    proceedingJoinPoint.proceed();
    System.out.println("this is the method after");
  }

  @Around("execution(* com.example.aspects.LogMethod.sayHello())")
  public void sayHello(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
    System.out.println("Hello");
    proceedingJoinPoint.proceed();
    System.out.println("World");
  }

}
