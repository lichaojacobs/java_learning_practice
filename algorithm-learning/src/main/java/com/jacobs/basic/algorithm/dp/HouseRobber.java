package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/2/19.
 */
public class HouseRobber {
  public static void main(String[] args) {

  }

  /**
   * 暴力递归思路
   */
  public static int robMaxValue(int index, int[] values) {
    if (index < 0) {
      return 0;
    }

    return Math.max(values[index] + robMaxValue(index - 2, values), robMaxValue(index - 1, values));
  }

  /**
   * 动态规划
   */
  public static int robMaxValue(int[] values) {
    if (values == null || values.length <= 0) {
      return 0;
    }

    int max0 = 0;
    int max1 = 0;

    for (int i = 0; i < values.length; i++) {
      int temp = max0;
      max0 = Math.max(max0, max1);
      max1 = temp + values[i];
    }

    return Math.max(max0, max1);
  }
}
