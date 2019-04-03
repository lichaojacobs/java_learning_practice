package com.jacobs.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * Created by lichao on 2016/12/16.
 */
public class PoweredByResponseFilter implements ContainerResponseFilter {
  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
//    responseContext.getHeaders()
//        .add("X-Powered-By", "Jersey :-)");
//    responseContext.setEntity(responseContext.getEntity() + "this is body");
  }
}
