package com.example.filters;

import com.example.constants.BaseConstant;
import com.example.exception.CommonRestException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by lichao on 2017/4/12.
 */
@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CommonRestException.class)
  @ResponseBody
  Object handleControllerException(HttpServletRequest req, CommonRestException ex) {
    CommonRestException.CommonExceptionModel exceptionModel = ex.getExFactor().getExModel();
    exceptionModel.setErrorMsg(ex.getMessage());
    exceptionModel.setRequestUri(req.getRequestURI());
    exceptionModel.setHttpCode(BaseConstant.BASE_HTTP_ERROR_CODE);

    log.warn("错误信息为：{},请求URI为：{},exmodel为: {}", ex.getMessage(), req.getRequestURI(),
        exceptionModel);

    return exceptionModel;
  }
}
