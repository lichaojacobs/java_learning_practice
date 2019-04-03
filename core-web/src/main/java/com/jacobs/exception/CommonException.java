package com.jacobs.exception;

/**
 * Created by lichao on 2017/4/11.
 */
public enum CommonException implements ExFactor {
  PARAMETER_ERROR(1, "params error");

  private int errorCode;
  private String detail;

  CommonException(int errorCode, String detail) {
    this.errorCode = errorCode;
    this.detail = detail;
  }

  @Override
  public CommonRestException.CommonExceptionModel getExModel() {
    return CommonRestException.CommonExceptionModel.builder()
        .detailMsg(detail)
        .errCode(errorCode)
        .requestUri("")
        .errorMsg("")
        .build();
  }
}
