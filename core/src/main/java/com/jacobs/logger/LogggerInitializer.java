package com.jacobs.logger;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Created by lichao on 2017/3/16.
 */
@Configuration
public class LogggerInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

  private int order = Ordered.HIGHEST_PRECEDENCE + 100;

  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    try {
      MyLogLayOut.PROJECT_NAME = configurableApplicationContext.getEnvironment()
          .getProperty("spring.application.name");
    } catch (Exception ex) {

    }
  }

  @Override
  public int getOrder() {
    return order;
  }
}
