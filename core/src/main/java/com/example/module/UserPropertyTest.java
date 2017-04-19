package com.example.module;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by lichao on 2017/4/7.
 */
@Component
@PropertySource(value = "classpath:configs.properties")
public class UserPropertyTest {
  @Value("${test.name}")
  private String name;
}
