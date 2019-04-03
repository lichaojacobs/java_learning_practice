package com.jacobs.exception;

import com.alibaba.fastjson.JSON;
import com.jacobs.exception.CommonRestException.CommonExceptionModel;
import com.jacobs.jersey.CommonExceptionFactor;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.server.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lichao on 2017/4/19.
 */
@Provider
public class CommonExceptionMapper implements ExceptionMapper<Throwable> {

  private static final Logger log = LoggerFactory.getLogger(CommonExceptionMapper.class);
  @Context
  private HttpServletRequest request;

  public CommonExceptionMapper() {
  }

  public Response toResponse(Throwable t) {
    Response rp = null;
    try {
      if (t instanceof WebApplicationException) {
        if (t instanceof CommonRestException) {
          rp = this.buildApolloRestExceptionResponse((CommonRestException) t);
        } else if (t instanceof ParamException) {
          rp = this.buildParamExceptionResponse((ParamException) t);
        } else if (t instanceof NotAllowedException) {
          rp = this.buildNotAllowedException((NotAllowedException) t);
        } else if (t instanceof NotFoundException) {
          rp = this.buildNotFoundException((NotFoundException) t);
        }

        if (rp == null) {
          rp = this.buildDefaultWebApplicationExceptionResponse((WebApplicationException) t);
        }
      } else {
        rp = this.buildResponseFromExceptionFactor(CommonExceptionFactor.INTERNAL_ERROR, "",
            null);
      }
    } catch (Throwable var7) {
      rp = this.buildResponseFromExceptionFactor(CommonExceptionFactor.INTERNAL_ERROR, "",
          null);
      log.error("Severe error occured while building error response : {}", var7.getMessage(), t);
    } finally {
      if (t instanceof CommonRestException) {
        if (((CommonRestException) t).getExFactor() == CommonExceptionFactor.INTERNAL_ERROR) {
          log.error("Internal throwable caught. {}", t);
        } else {
          log.warn("RhllorRestException throwable caught. {}",
              ((CommonRestException) t).getExFactor());
        }
      } else if (!(t instanceof ParamException) && !(t instanceof NotAllowedException)
          && !(t instanceof NotFoundException)) {
        log.error("Throwable caught.", t);
      }

      return rp;
    }
  }

  private Response buildNotFoundException(NotFoundException t) {
    Response r = this.buildResponseFromExceptionFactor(CommonExceptionFactor.URL_NOT_FOUND,
        this.request.getPathInfo(), null);
    return r;
  }

  private Response buildNotAllowedException(NotAllowedException ex) {
    Response r = this
        .buildResponseFromExceptionFactor(CommonExceptionFactor.HTTP_METHOD_NOT_ALLOWED,
            this.request.getPathInfo(), null);
    return r;
  }

  private Response buildApolloRestExceptionResponse(CommonRestException rre) {
    Response r = this
        .buildResponseFromExceptionFactor(rre.getExFactor(), this.request.getPathInfo(),
            rre.getMessage());
    return r;
  }

  private Response buildDefaultWebApplicationExceptionResponse(WebApplicationException wae) {
    return this.buildResponseFromExceptionFactor(CommonExceptionFactor.INTERNAL_ERROR,
        this.request.getPathInfo(), null);
  }

  private Response buildParamExceptionResponse(ParamException pex) {
    String paramName = pex.getParameterName();
    String paramType = pex.getParameterType().getCanonicalName();
    String err_msg =
        "parameter: {" + paramName + "} should be compatible with Java type: {" + paramType + "}";
    return this.buildResponseFromExceptionFactor(CommonExceptionFactor.PARAM_ERROR,
        this.request.getPathInfo(), err_msg);
  }

  private Response buildResponseFromExceptionFactor(ExFactor factor, String uri, String errMsg) {
    CommonExceptionModel exModel = factor.getExModel();
    if (errMsg != null) {
      exModel.setErrorMsg(errMsg);
    }

    ResponseBuilder responseBuilder = Response.status(exModel.getHttpCode());
    responseBuilder.type("application/json");
    if (StringUtils.isBlank(uri)) {
      uri = this.request.getPathInfo();
    }

    exModel.setRequestUri(uri);
    String entity = JSON.toJSONString(exModel);
    responseBuilder.entity(entity);
    Response response = responseBuilder.build();
    return response;
  }
}

