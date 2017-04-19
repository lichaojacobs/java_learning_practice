package com.example.endpoint;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by lichao on 2017/1/6.
 */
@Component
public class MyHealth implements HealthIndicator {
  @Override
  public Health health() {
    return new Health.Builder().withDetail("tair", "timeout")
        .withDetail("eeee", "no")
        .status("500")
        .down()
        .build();
  }
}
