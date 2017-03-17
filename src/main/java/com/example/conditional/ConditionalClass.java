package com.example.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lichao on 2017/1/6.
 */
@Configuration
@ConditionalOnMyProperties(name = "message")
public class ConditionalClass {
  @Bean
  public HelloWorld helloWorld() {
    return new HelloWorld();
  }
}
