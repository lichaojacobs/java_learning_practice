package com.jacobs.jersey.params;

import java.text.DecimalFormat;

/**
 * Created by lichao on 2017/4/19.
 */
public class FloatRange implements ParamRange<Float> {

  float min = 1.4E-45F;
  float max = 3.4028235E38F;

  public FloatRange() {
  }

  public FloatRange(String range) {
    String[] arr = range.split("~");
    this.min = Float.parseFloat(arr[0]);
    this.max = Float.parseFloat(arr[1]);
    if (this.max < this.min) {
      this.max = 3.4028235E38F;
    }

  }

  public FloatRange(float min, float max) {
    this.min = min;
    this.max = max;
    if (this.max < this.min) {
      this.max = 3.4028235E38F;
    }

  }

  public boolean isInRange(Float value) {
    float v = value.floatValue();
    return v >= this.min && v <= this.max;
  }

  public String getEnumRangeDesc() {
    return "";
  }

  public String getRegExpDesc() {
    return "";
  }

  public String getBaseSample() {
    float d = this.getRandomValue();
    DecimalFormat nf = new DecimalFormat("######.000");
    return nf.format((double) d);
  }

  public float getRandomValue() {
    int i = rdm.nextInt(100);

    float d;
    for (d = this.min + (float) i; !this.isInRange(Float.valueOf(d)); d = this.min + (float) i) {
      i = rdm.nextInt(100);
    }

    return d;
  }

  public String getDesc() {
    return this.min + "~" + this.max;
  }

  public boolean isCompatible(Class<?> type) {
    return type == Float.TYPE || type == Float.class;
  }
}
