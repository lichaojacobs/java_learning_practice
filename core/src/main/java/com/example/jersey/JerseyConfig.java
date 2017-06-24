package com.example.jersey;

import com.example.exception.CommonExceptionMapper;
import com.example.exception.CommonValidationMapper;
import com.example.filters.AuthorizationRequestFilter;
import com.example.filters.PoweredByResponseFilter;
import com.example.resource.TestResource;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lichao on 16/8/21.
 */
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    register(TestResource.class)//resource样例
        .register(PoweredByResponseFilter.class)//请求拦截样例
        //.register(AuthorizationRequestFilter.class)//权限验证
        .register(CommonValidationMapper.class)
        .register(CommonExceptionMapper.class);//参数错误处理
  }
}
