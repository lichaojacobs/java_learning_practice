package com.jacobs.jersey;

import com.jacobs.exception.CommonRestException.CommonExceptionModel;
import com.jacobs.exception.ExFactor;

/**
 * Created by lichao on 2017/4/19.
 */
public enum CommonExceptionFactor implements ExFactor {
  PARAM_ERROR(400, 4000001, "Parameter error!", ""),
  PARAM_MISSING(400, 4000002, "Parameter missing!", ""),
  URL_NOT_FOUND(404, 4040000, "Url not found!", ""),
  HTTP_METHOD_NOT_ALLOWED(405, 4050001, "Http method not allow!", ""),
  INTERNAL_ERROR(500, 5000001, "Service internal error!", ""),
  IS_NOT_INTERNAL_IP(403, 4030006, "is not internal ip!", ""),
  ACCESS_TOKEN_AUTH_FAILD(403, 4030004, "access_token is wrong!", ""),
  ACCESS_TOKEN_IS_EXPIRE(403, 4030003, "access_token is expire!", ""),
  USER_IS_BLOCKED(403, 4030005, "user is blocked!", ""),
  NO_ROLES(403, 4030007, "no roles!", ""),
  APP_NO_ROLES(403, 4030011, "app no roles", "");

  int httpCode;
  int errCode;
  String errorMsg;
  String detailMsg;

  CommonExceptionFactor(int httpCode, int errCode, String errorMsg, String detailMsg) {
    this.httpCode = httpCode;
    this.errCode = errCode;
    this.errorMsg = errorMsg;
    this.detailMsg = detailMsg;
  }

  @Override
  public CommonExceptionModel getExModel() {
    CommonExceptionModel model = new CommonExceptionModel();
    model.setHttpCode(this.httpCode);
    model.setErrCode(this.errCode);
    model.setErrorMsg(this.errorMsg);
    model.setDetailMsg(this.detailMsg);
    return model;
  }
}
