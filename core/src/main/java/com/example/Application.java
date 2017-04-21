package com.example;

import com.example.jersey.EnableJerseyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

//SpringBootApplication自动包括了ennableAutoConfiguration ComponentScan Configuration这些,
@SpringBootApplication
@EnableAspectJAutoProxy
//单独开启线程
@EnableAsync
@EnableJerseyConfiguration(scanPackage = "", applicationPath = "v1")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
