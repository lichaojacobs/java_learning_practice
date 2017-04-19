package com.example.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import lombok.Data;

/**
 * Created by lichao on 2017/3/23.
 */
@Data
@ConfigurationProperties(prefix = "redis")
public class MultiBusinessRedisProperties {
  Map<String, BaseRedisProperties> redis;
}
