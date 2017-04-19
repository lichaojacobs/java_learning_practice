package com.example.cache.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 2017/3/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRedisPoolProperties {
  public static final int DEFAULT_MAX_TOTAL = 256;
  public static final int DEFAULT_MAX_IDLE = 32;
  public static final int DEFAULT_MIN_IDLE = 8;
  public static final int DEFAULT_MAX_WAIT_MILLIS = 1000;
  public static final boolean DEFAULT_TEST_ON_BORROW = true;

  // 最大连接数
  private int maxTotal = DEFAULT_MAX_TOTAL;

  // 最大空闲数
  private int maxIdle = DEFAULT_MAX_IDLE;

  // 最小空闲数
  private int minIdle = DEFAULT_MIN_IDLE;

  // 最大等待时间
  private int maxWaitMillis = DEFAULT_MAX_WAIT_MILLIS;

  // 是否测试连接
  private boolean testOnBorrow = DEFAULT_TEST_ON_BORROW;

}
