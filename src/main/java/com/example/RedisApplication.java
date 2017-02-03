package com.example;

import com.example.conditional.HelloWorld;
import com.example.module.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

//SpringBootApplication自动包括了ennableAutoConfiguration ComponentScan Configuration这些,
@SpringBootApplication
@EnableAspectJAutoProxy
//单独开启线程
@EnableAsync
public class RedisApplication {
  public static void main(String[] args) {
    ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(
        RedisApplication.class, args);
//    configurableApplicationContext.getBean(HelloWorld.class)
    //        .print();
  }
}
