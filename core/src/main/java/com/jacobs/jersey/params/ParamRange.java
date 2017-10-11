package com.jacobs.jersey.params;

import java.util.Random;

/**
 * Created by lichao on 2017/4/19.
 */
public interface ParamRange<T> {

  Random rdm = new Random();

  boolean isInRange(T var1);

  String getDesc();

  String getBaseSample();

  boolean isCompatible(Class<?> var1);
}
