package com.example.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 2017/1/6.
 */
@Configuration
public class EndPointAutoConfig {
  private List<MemStatus> status = new ArrayList<>();

  public EndPointAutoConfig() {
    System.out.println("EndPointAutoConfig 初始化");
  }

  @Bean
  public MyEndPoint myEndPoint() {
    return new MyEndPoint(status);
  }

  @Bean
  public MemCollector memCollector() {
    return new MemCollector(status);
  }
}
