package com.jacobs.jersey.params;

/**
 * Created by lichao on 2017/4/19.
 */
public class LongRange implements ParamRange<Long> {

  long min = -9223372036854775808L;
  long max = 9223372036854775807L;

  public LongRange() {
  }

  public LongRange(String range) {
    String[] arr = range.split("~");
    this.min = Long.parseLong(arr[0]);
    this.max = Long.parseLong(arr[1]);
    if (this.max < this.min) {
      this.max = 9223372036854775807L;
    }

  }

  public LongRange(long min, long max) {
    this.min = min;
    this.max = max;
    if (this.max < this.min) {
      this.max = 2147483647L;
    }

  }

  public boolean isInRange(Long value) {
    long v = value.longValue();
    return v >= this.min && v <= this.max;
  }

  public String getBaseSample() {
    long d = this.getRandomValue();
    return String.valueOf(d);
  }

  public long getRandomValue() {
    int i = rdm.nextInt(100);

    long d;
    for (d = this.min + (long) i; !this.isInRange(Long.valueOf(d)); d = this.min + (long) i) {
      i = rdm.nextInt(100);
    }

    return d;
  }

  public String getDesc() {
    return this.min + "~" + this.max;
  }

  public boolean isCompatible(Class<?> type) {
    return type == Long.TYPE || type == Long.class;
  }
}
