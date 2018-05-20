package com.jacobs.jersey;

import java.util.HashMap;
import javax.ws.rs.ApplicationPath;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by lichao on 2017/4/19.
 */
//@Configuration
//@AutoConfigureBefore(JerseyAutoConfiguration.class)
//@ConditionalOnBean(
//    annotation = {EnableJerseyConfiguration.class}
//)
//@ComponentScan
public class JerseyAnnotationConfiguration implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Bean
  @ConditionalOnMissingBean
  public JerseyConfig jerseyConfig() {
    String[] beanNames = this.applicationContext
        .getBeanNamesForAnnotation(EnableJerseyConfiguration.class);
    if (beanNames != null) {
      Object entrance = this.applicationContext.getBean(beanNames[0]);

      EnableJerseyConfiguration enableJerseyConfiguration = entrance
          .getClass().getAnnotation(EnableJerseyConfiguration.class);
      if (enableJerseyConfiguration != null) {
        HashMap valuesMap = new HashMap();
        //注入的时候设置applicationPath
        valuesMap.put("value", enableJerseyConfiguration.applicationPath());
        RuntimeAnnotations.putAnnotation(JerseyConfig.class, ApplicationPath.class, valuesMap);

        //为了演示简便，去掉扫描包的逻辑，直接代码写死
        // JerseyConfig jerseyConfig = new JerseyConfig(enableJerseyConfiguration.scanPackage(),
        // enableJerseyConfiguration.componentClasses());

        return new JerseyConfig();
      }
    }

    return null;
  }
}
