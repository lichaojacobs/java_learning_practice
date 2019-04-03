package com.jacobs.jersey.params;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * Created by lichao on 2017/4/19.
 */
public class RangeFactory {

  private static String EMAIL_REGX = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
  public static final String MOBILE_REGX = "^(1(([35][0-9])|(47)|[8][0-9]))\\d{8}$";

  public RangeFactory() {
  }

  public static ParamRange getRangeInstance(String range) {
    if (StringUtils.isBlank(range)) {
      return null;
    } else {
      String[] arr = range.split(":", 2);
      if (arr.length != 2) {
        throw new IllegalArgumentException("invalid range define:" + range);
      } else {
        Object result = null;
        if (arr[0].equals("int")) {
          result = new IntRange(arr[1].trim());
        } else if (arr[0].equals("long")) {
          result = new LongRange(arr[1].trim());
        } else if (arr[0].equals("float")) {
          result = new FloatRange(arr[1].trim());
        } else if (arr[0].equals("double")) {
          result = new DoubleRange(arr[1].trim());
        }

        return (ParamRange) result;
      }
    }
  }

  public static void main(String[] args) {
    String test = "spec:join_int[100~10000]";
    ParamRange paramRange = getRangeInstance(test);
    System.out.println(paramRange);
    System.out.println(paramRange.isInRange("1000,1,2345"));
    System.out.println(paramRange.isInRange("1000,2001,2345"));
  }
}
