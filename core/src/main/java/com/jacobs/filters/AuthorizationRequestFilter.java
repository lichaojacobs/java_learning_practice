package com.jacobs.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by lichao on 2016/12/16.
 */
public class AuthorizationRequestFilter implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    final SecurityContext securityContext =
        requestContext.getSecurityContext();
//    if (securityContext == null ||
//        !securityContext.isUserInRole("privileged")) {
//
//      requestContext.abortWith(Response
//          .status(Response.Status.UNAUTHORIZED)
//          .entity("User cannot access the resource.")
//          .build());
//    }
  }
}
