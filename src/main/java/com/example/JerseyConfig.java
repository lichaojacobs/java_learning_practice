package com.example;

import com.example.filters.AuthorizationRequestFilter;
import com.example.filters.PoweredByResponseFilter;
import com.example.resource.TestResource;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lichao on 16/8/21.
 */
//@Configuration
public class JerseyConfig extends ResourceConfig {
  public JerseyConfig() {
    register(TestResource.class);
    register(PoweredByResponseFilter.class)
        .register(AuthorizationRequestFilter.class);
  }
}
