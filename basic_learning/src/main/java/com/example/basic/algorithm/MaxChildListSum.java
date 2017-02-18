package com.example.basic.algorithm;

/**
 * Created by lichao on 2017/2/17.
 * 求一堆有正数负数拥有最大和的子数组的和
 */
public class MaxChildListSum {
  public static void main(String[] args) {
    int[] arr = new int[]{1, -2, 3, 10, -4, 7, 2, -5};
    System.out.println(maxChildListSum2(arr));
  }

  /**
   * 动态规划：循环整个数组，对i来讲，默认前面i-1的子数组已经找好，则以i结尾的子数组的最大，要么是sum[i-1]+arr[i]，要么是arr[i]
   * 即i要么成组，要么单独作为子数组。这样一来，以i结尾的连续子数组的最大和就可以求出。
   */
  public static int maxChildListSum(int[] arr) {
    int[] sum = new int[arr.length];
    sum[0] = arr[0];
    int maxResult = arr[0];
    for (int i = 1; i < arr.length; i++) {
      if ((sum[i - 1] + arr[i]) > arr[i]) {
        sum[i] = sum[i - 1] + arr[i];
        if (maxResult < sum[i]) {
          maxResult = sum[i];
        }
      } else {
        sum[i] = arr[i];
        maxResult = arr[i];
      }
    }

    return maxResult;
  }

  /**
   * arr[i],arr[i+1]...arr[j] 的和等于sum[j]-sum[i-1]，显然，只需要sum[i-1]达到最小
   * 和才会最大
   */
  public static int maxChildListSum2(int[] arr) {
    int[] sum = new int[arr.length];
    sum[0] = arr[0];
    int min = arr[0];
    int max = arr[0];
    for (int i = 1; i < arr.length; i++) {
      sum[i] = sum[i - 1] + arr[i];
      max = Math.max(sum[i] - min, max);
      min = Math.min(sum[i], min);
    }

    return max;
  }
}
