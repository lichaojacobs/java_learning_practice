package com.jacobs.basic.algorithm.array;

/**
 * Created by lichao on 2017/2/17. 求一堆有正数负数拥有最大和的子数组的和
 */
public class MaxChildListSum {

  public static void main(String[] args) {
    int[] arr = new int[]{1, -1};
    System.out.println(maxChildListSum2(arr));
  }

  /**
   * 动态规划：循环整个数组，对i来讲，默认前面i-1的子数组已经找好，则以i结尾的子数组的最大，要么是sum[i-1]+arr[i]，要么是arr[i]
   * 即i要么成组，要么单独作为子数组。这样一来，以i结尾的连续子数组的最大和就可以求出。
   */
  public static int maxChildListSum(int[] nums) {
    int[] sum = new int[nums.length];
    sum[0] = nums[0];
    int maxResult = nums[0];
    for (int i = 1; i < nums.length; i++) {
      if ((sum[i - 1] + nums[i]) > nums[i]) {
        sum[i] = sum[i - 1] + nums[i];
        if (maxResult < sum[i]) {
          maxResult = sum[i];
        }
      } else {
        sum[i] = nums[i];
        maxResult = nums[i];
      }
    }

    return maxResult;
  }

  /**
   * arr[i],arr[i+1]...arr[j] 的和等于sum[j]-sum[i-1]，显然，只需要sum[i-1]达到最小 和才会最大
   */
  public static int maxChildListSum2(int[] nums) {
    int[] sum = new int[nums.length];
    sum[0] = nums[0];
    int min = sum[0] > 0 ? 0 : sum[0];
    int max = nums[0];
    for (int i = 1; i < nums.length; i++) {
      sum[i] = sum[i - 1] + nums[i];
      max = Math.max(sum[i] - min, max);
      min = Math.min(sum[i], min);
    }

    return max;
  }

  /**
   * @param arr 数组
   * @return 最大子数组和
   */
  public static int maxChildListSum3(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }

    int max = Integer.MIN_VALUE;
    int cur = 0;

    for (int i = 0; i != arr.length; i++) {
      cur += arr[i];
      max = Math.max(max, cur);
      cur = cur < 0 ? 0 : cur;
    }

    return max;
  }
}
