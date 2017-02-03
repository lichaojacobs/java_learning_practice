package com.example.algorithm.dp;

/**
 * Created by lichao on 2017/2/3.
 * 最长递增子序列
 */
public class LIS {
  public static void main(String[] args) {

  }

  public static int[] getdp1(int[] arr) {
    int[] dp = new int[arr.length];
    for (int i = 0; i < arr.length; i++) {
      dp[i] = 1;
      for (int j = 0; j < i; j++) {
        if (arr[i] > arr[j]) {
          dp[i] = Math.max(dp[j] + 1, dp[i]);
        }
      }
    }

    return dp;
  }

  /**
   * 优化方案：二分法实现
   */
  public static int[] getdp2(int[] arr) {
    int[] ends = new int[arr.length];
    int[] dp = new int[arr.length];

    ends[0] = arr[0];
    dp[0] = 1;
    int right = 0;
    int l = 0;
    int r = 0;
    int m = 0;

    for (int i = 1; i < arr.length; i++) {
      int temp = arr[i];
      l = 0;
      r = right;
      while (l <= r) {
        m = (l + r) / 2;
        if (ends[m] < temp) {
          l = m + 1;
        } else {
          r = m - 1;
        }
      }
      right = Math.max(right, l);
      ends[l] = arr[i];
      dp[i] = 1 + l;
    }

    return dp;
  }

  /**
   * 根据dp数组生成最长递增子序列
   */
  public static int[] generateLIS(int[] arr, int[] dp) {
    int maxLength = 0;
    int index = 0;

    for (int i = 0; i < dp.length; i++) {
      if (dp[i] > maxLength) {
        maxLength = dp[i];
        index = i;
      }
    }

    int[] lis = new int[maxLength];
    for (int j = index; j >= 0; j--) {
      if (arr[j] < arr[index] && dp[j] == dp[index] - 1) {
        lis[--maxLength] = arr[j];
        index = j;
      }
    }

    return lis;
  }
}
