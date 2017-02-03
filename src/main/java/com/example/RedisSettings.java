package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Created by lichao on 16/7/31.
 */
@Component
@Data
@ConfigurationProperties(prefix = "redis")
public class RedisSettings {
  private String host;
  private int port;
  private int timeout;
}
