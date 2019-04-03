package com.jacobs.module;

import com.alibaba.fastjson.JSON;
import com.jacobs.exception.CommonException;
import com.jacobs.exception.CommonRestException;
import com.jacobs.jersey.ParamDesc;
import java.util.Map;
import javax.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 2017/4/20.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {

  @FormParam("key")
  @ParamDesc(isRequired = true,
      desc = "test_case对应的key")
  String key;

  @FormParam("value")
  @ParamDesc(isRequired = true,
      desc = "对应的值")
  String value;

  @FormParam("description")
  @ParamDesc(isRequired = false,
      desc = "当前testCase的描述")
  String description;

  @FormParam("anonymous")
  @ParamDesc(isRequired = false,
      desc = "是否需要登陆")
  boolean anonymous;

  @ParamDesc(isRequired = false,
      desc = "需要满足的条件")
  Map<String, String> conditions;

  @FormParam("conditions")
  public void generateConditions(String param) {
    try {
      Map<String, String> conditions = JSON.parseObject(param.toString(), Map.class);
      this.conditions = conditions;
    } catch (Exception ex) {
      //直接抛给上层,由jersey或serviceExceptionHandler捕获
      throw new CommonRestException(CommonException.PARAMETER_ERROR);
    }
  }
}

