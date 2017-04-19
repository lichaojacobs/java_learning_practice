package com.example.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

/**
 * Created by lichao on 2016/12/16.
 * filter before matching filters, can influence which method to match
 */
public class PreMatchingFilter implements ContainerRequestFilter {
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    if (requestContext.getMethod()
        .equals("GET")) {
      requestContext.setMethod("POST");
    }
  }
}
