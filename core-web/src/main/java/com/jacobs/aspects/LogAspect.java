package com.jacobs.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 16/8/1.
 */
@Slf4j
@Aspect
@Component
@PropertySource(value = "classpath:configs.properties")
public class LogAspect {

  @Value("${test.name}")
  private String testConfig;


  @Around("@annotation(com.jacobs.aspects.Log)")
  public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("this is the method before" + testConfig);
    proceedingJoinPoint.proceed();
    System.out.println("this is the method after" + testConfig);
  }

  @Around("execution(* com.jacobs.aspects.LogMethod.sayHello())")
  public void sayHello(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("Hello");
    proceedingJoinPoint.proceed();
    System.out.println("World");
  }

  @Before("@annotation(com.jacobs.aspects.LogBefore)")
  public void executeBefore() {
    System.out.println("print before method");
  }

}
