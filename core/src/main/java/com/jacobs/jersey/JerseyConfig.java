package com.jacobs.jersey;

import com.jacobs.exception.CommonExceptionMapper;
import com.jacobs.exception.CommonValidationMapper;
import com.jacobs.filters.PoweredByResponseFilter;
import com.jacobs.resource.TestResource;

import org.glassfish.jersey.server.ResourceConfig;

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
