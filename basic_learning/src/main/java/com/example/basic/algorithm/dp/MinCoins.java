package com.example.basic.algorithm.dp;

/**
 * Created by lichao on 2017/2/6.
 * 给定数组arr,arr中所有的值都为正数且不重复，每个值代表一种面值的货币，每种面值的货币可以使用任意张，
 * 再给定一个整数aim要找的钱数，求组成aim的最少货币数。
 */
public class MinCoins {
  public static void main(String[] args) {

  }

  /**
   * 非空间压缩的递归
   */
  public static int minCoins1(int[] arr, int aim) {
    if (arr == null || arr.length <= 0 || aim < 0) {
      return -1;
    }

    int max = Integer.MAX_VALUE;
    int[][] dp = new int[arr.length][aim + 1];

    for (int j = 1; j <= aim; j++) {
      dp[0][j] = max;
      if ((j - arr[0] >= 0) && dp[0][j - arr[0]] != max) {
        dp[0][j] = dp[0][j - arr[0]] + 1;
      }
    }

    int left = 0;
    for (int i = 1; i < arr.length; i++) {
      for (int j = 1; j <= aim; j++) {
        left = Integer.MAX_VALUE;
        //加入硬币
        if ((j - arr[i] >= 0) && dp[i][j - arr[i]] != max) {
          left = dp[i][j - arr[i]] + 1;
        }
        //dp[i-1][j] 这一步状态肯定是匹配好的，不需要条件判断
        dp[i][j] = Math.min(left, dp[i - 1][j]);
      }
    }

    return dp[arr.length - 1][aim] != max ? dp[arr.length - 1][aim] : -1;
  }

  /**
   * 空间压缩法
   */
  public static int minCoins2(int[] arr, int aim) {
    if (arr == null || arr.length <= 0 || aim < 0) {
      return -1;
    }

    int[] dp = new int[aim];
    int max = Integer.MAX_VALUE;

    for (int i = 1; i < aim; i++) {
      dp[i] = Integer.MAX_VALUE;
      if ((i - arr[0] >= 0) && dp[i - arr[0]] != max) {
        dp[i] = dp[i - arr[0]] + 1;
      }
    }

    int left = 0;
    for (int i = 1; i < arr.length; i++) {
      for (int j = 1; j <= aim; j++) {
        left = max;
        if ((j - arr[i]) >= 0 && dp[j - arr[i]] != max) {
          left = dp[j - arr[i]] + 1;
        }

        dp[j] = Math.min(left, dp[j]);
      }
    }

    return dp[aim] != max ? dp[aim] : -1;
  }
}
