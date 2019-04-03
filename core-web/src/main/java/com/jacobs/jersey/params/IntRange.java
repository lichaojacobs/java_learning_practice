package com.jacobs.jersey.params;

/**
 * Created by lichao on 2017/4/19.
 */
public class IntRange implements ParamRange<Integer> {

  int min = -2147483648;
  int max = 2147483647;

  public IntRange() {
  }

  public IntRange(String range) {
    String[] arr = range.split("~");
    this.min = Integer.parseInt(arr[0]);
    this.max = Integer.parseInt(arr[1]);
    if (this.max < this.min) {
      this.max = 2147483647;
    }

  }

  public IntRange(int min, int max) {
    this.min = min;
    this.max = max;
    if (this.max < this.min) {
      this.max = 2147483647;
    }

  }

  public boolean isInRange(Integer value) {
    int v = value.intValue();
    return v >= this.min && v <= this.max;
  }

  public String getBaseSample() {
    int d = this.getRandomValue();
    return d + "";
  }

  public int getRandomValue() {
    int d;
    for (d = rdm.nextInt(this.max + 1); !this.isInRange(Integer.valueOf(d));
        d = rdm.nextInt(this.max + 1)) {
      ;
    }

    return d;
  }

  public String getDesc() {
    return this.min + "~" + this.max;
  }

  public boolean isCompatible(Class<?> type) {
    return type == Integer.TYPE || type == Integer.class;
  }
}
