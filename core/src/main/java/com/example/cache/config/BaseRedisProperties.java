package com.example.cache.config;

import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 2017/3/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRedisProperties {
  public static final int DEFAULT_TIMEOUT = 2000;

  public static final String DEFAULT_PASSWORD = null;

  public static final int DEFAULT_DB = 0;

  public static final Supplier<BaseRedisPoolProperties> DEFAULT_POOL = BaseRedisPoolProperties::new;

  // 连接地址 ip:port
  private String connection;

  // 超时时间
  private int timeout = DEFAULT_TIMEOUT;

  // 密码
  private String password = DEFAULT_PASSWORD;

  // 数据库
  private int db = DEFAULT_DB;

  // 连接池配置
  private BaseRedisPoolProperties pool = DEFAULT_POOL.get();
}
