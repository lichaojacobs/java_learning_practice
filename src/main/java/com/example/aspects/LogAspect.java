package com.example.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 16/8/1.
 */
@Slf4j
@Aspect
@Component
@PropertySource(value = "classpath:configs.property")
public class LogAspect {

  @Value("${test.name}")
  private String testConfig;


  @Around("@annotation(com.example.aspects.Log)")
  public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("this is the method before" + testConfig);
    proceedingJoinPoint.proceed();
    System.out.println("this is the method after" + testConfig);
  }

  @Around("execution(* com.example.aspects.LogMethod.sayHello())")
  public void sayHello(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("Hello");
    proceedingJoinPoint.proceed();
    System.out.println("World");
  }

}
