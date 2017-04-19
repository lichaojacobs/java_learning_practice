package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Created by lichao on 16/7/31.
 */
@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisSettings {
  private String host;
  private int port;
  private int timeout;
}
