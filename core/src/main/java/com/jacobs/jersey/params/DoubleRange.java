package com.jacobs.jersey.params;

/**
 * Created by lichao on 2017/4/19.
 */

import java.text.DecimalFormat;

public class DoubleRange implements ParamRange<Double> {

  double min = 4.9E-324D;
  double max = 1.7976931348623157E308D;

  public DoubleRange() {
  }

  public DoubleRange(String range) {
    String[] arr = range.split("~");
    this.min = Double.parseDouble(arr[0]);
    this.max = Double.parseDouble(arr[1]);
    if (this.max < this.min) {
      this.max = 1.7976931348623157E308D;
    }

  }

  public DoubleRange(double min, double max) {
    this.min = min;
    this.max = max;
    if (this.max < this.min) {
      this.max = 1.7976931348623157E308D;
    }

  }

  public boolean isInRange(Double value) {
    double v = value.doubleValue();
    return v >= this.min && v <= this.max;
  }

  public boolean isValidate(String value) {
    try {
      double e = Double.parseDouble(value);
      return this.isInRange(Double.valueOf(e));
    } catch (Exception var4) {
      return false;
    }
  }

  public String getBaseSample() {
    double d = this.getRandomValue();
    DecimalFormat nf = new DecimalFormat("######.000");
    return nf.format(d);
  }

  public double getRandomValue() {
    int i = rdm.nextInt(100);

    double d;
    for (d = this.min + (double) i; !this.isInRange(Double.valueOf(d)); d = this.min + (double) i) {
      i = rdm.nextInt(100);
    }

    return d;
  }

  public String getDesc() {
    return this.min + "~" + this.max;
  }

  public boolean isCompatible(Class<?> type) {
    return type == Double.TYPE || type == Double.class;
  }
}

