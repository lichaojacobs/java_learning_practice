package com.jacobs.exception;

import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lichao on 2017/4/12.
 */
public class CommonResult implements Serializable {

  private Object result = "";

  public Object getResult() {
    return this.result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public CommonResult(Object result) {
    this.result = result;
  }

  public CommonResult() {
  }

  public static CommonResult booleanCommonResult(boolean result) {
    HashMap map = Maps.newHashMap();
    map.put("is_success", Boolean.valueOf(result));
    return new CommonResult(map);
  }

  public static CommonResult oneCommonResult(String key, Object result) {
    HashMap map = Maps.newHashMap();
    map.put(key, result);
    return new CommonResult(map);
  }

  public static CommonResult mapCommonResult(Map<String, Object> map) {
    return new CommonResult(map);
  }
}

