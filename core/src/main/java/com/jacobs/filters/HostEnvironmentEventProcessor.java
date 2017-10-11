package com.jacobs.filters;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.EnumerableCompositePropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * Created by lichao on 2017/10/10.
 */
@Slf4j
public class HostEnvironmentEventProcessor implements
    ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  private static final String PROPERTY_SOURCE_NAME = "applicationConfigurationProperties";
  private static final String ADMIN_SERVER_PROPERTY_NAME = "spring.boot.admin.url";
  private static final String CLIENT_SERVICE_URL_PROPERTY_NAME = "spring.boot.admin.client.service-url";
  private static final String SERVER_PORT_PROPERTY_NAME = "server.port";
  private static final String CUSTOM_PROPERTY_MAP_NAME = "custom_props";


  @Override
  public void onApplicationEvent(
      ApplicationEnvironmentPreparedEvent event) {
    ConfigurableEnvironment environment = event.getEnvironment();

    if (getPropertyValue(environment, ADMIN_SERVER_PROPERTY_NAME) == null) {
      log.info("no need to override admin client service url");
      return;
    }

    Properties props = new Properties();
    try {
      props.put(CLIENT_SERVICE_URL_PROPERTY_NAME,
          String.format("http://%s:%d", InetAddress.getLocalHost().getCanonicalHostName(),
              getPropertyValue(environment, SERVER_PORT_PROPERTY_NAME)));
    } catch (Exception ex) {
      log.error("getLocalHost error: ", ex);
    }
    environment.getPropertySources()
        .addFirst(new PropertiesPropertySource(CUSTOM_PROPERTY_MAP_NAME, props));
  }

  public static <T> T getPropertyValue(ConfigurableEnvironment environment, String propertyName) {
    PropertySource source = environment.getPropertySources()
        .get(PROPERTY_SOURCE_NAME);
    List<EnumerableCompositePropertySource> propertySources = (ArrayList<EnumerableCompositePropertySource>) source
        .getSource();
    if (propertySources != null && !propertySources.isEmpty()) {
      for (EnumerableCompositePropertySource enumerableCompositePropertySource : propertySources) {
        for (PropertySource propertySource : enumerableCompositePropertySource
            .getSource()) {
          if (propertySource instanceof MapPropertySource) {
            MapPropertySource target = (MapPropertySource) propertySource;
            Object value = target.getProperty(propertyName);
            log.info("getPropertyValue propertyName:{},value:{}", propertyName, value);
            if (value != null) {
              return (T) value;
            } else {
              return null;
            }
          }
        }
      }
    }

    return null;
  }
}
