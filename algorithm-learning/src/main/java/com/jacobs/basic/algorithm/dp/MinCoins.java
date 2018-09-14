package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/2/6. 给定数组arr,arr中所有的值都为正数且不重复，每个值代表一种面值的货币，每种面值的货币可以使用任意张，
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

  /**
   * 第二遍(非空间压缩法)：每张货币可以使用多次
   */
  public static int minCoin3(int[] arr, int aim) {
    if (arr == null || arr.length == 0 || aim < 0) {
      return -1;
    }

    //用来表示兑换不了的情况
    int MAX = Integer.MAX_VALUE;
    //dp[i][j]表示可用货币0...i的情况下，组成j所需要的货币的最小张数
    int[][] dp = new int[arr.length][aim + 1];
    for (int j = 1; j <= aim; j++) {
      dp[0][j] = MAX;
      if ((j - arr[0] > 0) && (dp[0][j - arr[0]] != MAX)) {
        dp[0][j] = dp[0][j - arr[0]] + 1;
      }
    }

    //状态方程为:dp[i][j]=Math.min(dp[i-1][j],(dp[i][j-arr[i]]+1))
    // dp[i-1][j]是上层循环的时候就计算好的结果，所以只需要计算同一层就可以了
    int left = MAX;
    //这里其实已经体现张数了，因为同层的话，如果全用某一张货币的话，循环aim确实能体现张数
    for (int i = 1; i < arr.length; i++) {
      for (int j = 1; j <= aim; j++) {
        if ((j - arr[i] > 0) && (dp[i][j - arr[i]] != MAX)) {
          left = dp[i][j - arr[i]] + 1;
        }
        dp[i][j] = Math.min(left, dp[i - 1][j]);
      }
    }

    return dp[arr.length - 1][aim] != MAX ? dp[arr.length - 1][aim] : -1;
  }

  //第二遍（非空间压缩）：数组中每个值仅代表一张货币的面值，不能重复使用多次
  public static int minCoin4(int[] arr, int aim) {
    if (arr == null || arr.length == 0 || aim < 0) {
      return -1;
    }

    int MAX = Integer.MAX_VALUE;
    int[][] dp = new int[arr.length][aim + 1];

    for (int j = 1; j <= aim; j++) {
      dp[0][j] = MAX;
    }

    //一行对于某张货币来说，应该只有一次的兑换机会
    if (arr[0] <= aim) {
      dp[0][arr[0]] = 1;
    }

    int leftup = 0;//左上角某个位置的值
    for (int i = 1; i < arr.length; i++) {
      for (int j = 1; j <= aim; j++) {
        leftup = MAX;
        if ((j - arr[i] >= 0) && (dp[i - 1][j - arr[i]] != MAX)) {
          leftup = dp[i - 1][j - arr[i]] + 1;
        }
        dp[i][j] = Math.min(leftup, dp[i - 1][j]);
      }
    }

    return dp[arr.length - 1][aim] != MAX ? dp[arr.length - 1][aim] : -1;
  }
}
