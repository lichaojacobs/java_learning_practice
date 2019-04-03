package com.jacobs.aspects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by lichao on 16/8/1.
 */
@Component
@PropertySource(value = "classpath:configs.properties")
public class LogService {

  @Value("${test.name}")
  private String testConfig;

  @Log
  public void logTest() {
    System.out.println("this is the method" + testConfig);
  }
}
