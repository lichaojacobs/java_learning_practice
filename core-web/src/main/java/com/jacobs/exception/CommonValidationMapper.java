package com.jacobs.exception;

import com.alibaba.fastjson.JSON;
import com.jacobs.exception.CommonRestException.CommonExceptionModel;
import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.validation.internal.ValidationHelper;

/**
 * Created by lichao on 2017/4/19.
 */
@Provider
public class CommonValidationMapper implements ExceptionMapper<ValidationException> {

  @Context
  private HttpServletRequest request;

  @Override
  public Response toResponse(ValidationException exception) {
    if (exception instanceof ConstraintViolationException) {
      ConstraintViolationException cve = (ConstraintViolationException) exception;
      ConstraintViolation next = (ConstraintViolation) cve.getConstraintViolations().iterator()
          .next();
      CommonExceptionModel model = (CommonExceptionModel) this.request.getAttribute("exModel");
      String propertyPath = next.getPropertyPath().toString();
      model.setDetailMsg(MessageFormat.format(model.getDetailMsg(),
          new Object[]{propertyPath.substring(propertyPath.lastIndexOf(".") + 1)}));
      return Response.status(ValidationHelper.getResponseStatus(cve))
          .type(MediaType.APPLICATION_JSON_TYPE).entity(
              JSON.toJSONString(model)).build();
    } else {
      return Response.serverError().type("application/json").entity(exception.getMessage()).build();
    }
  }
}
