package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/2/3. 最长递增子序列
 */
public class LongestIncrementSubStr {

  public static void main(String[] args) {

  }


  /**
   * 获取最长递增子序列长度O(N^2)
   */
  public static int getMaxIncrString(String str) {
    //边界条件

    char[] charArr = str.toCharArray();
    //dp[i]表示为必须以i结尾的前提下，最长递增子序列的长度
    int[] dp = new int[charArr.length];
    dp[0] = 1;
    int maxLength = 0;

    for (int i = 1; i < charArr.length; i++) {
      for (int j = 0; j < i; j++) {
        if (charArr[j] < charArr[i]) {
          dp[i] = dp[j] + 1;
        }
        maxLength = Math.max(dp[i], maxLength);
      }
    }

    return maxLength;
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

  //优化方案：二分法实现复杂度O(N*log(N))
  //ends[j] 代表遍历到此时为止长度为j+1的最长递增子序列的最小值
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

  //自己实现一遍二分优化过程
  public static int[] getdp3(int[] arr) {
    int[] dp = new int[arr.length];
    //数组的索引位置加1表示递增子序列的长度，值表示结尾的最小的值
    int[] helper = new int[arr.length];

    int l = 0;
    int r = 0;
    int m = 0;
    int right = 0;

    dp[0] = 1;
    helper[0] = arr[0];
    for (int i = 1; i < arr.length; i++) {
      int temp = arr[i];
      l = 0;
      r = right;
      //找到第一个比当前值大的数，更新helper值
      while (l <= r) {
        m = (l + r) / 2;
        if (helper[m] < temp) {
          l = m + 1;
        } else {
          r = m - 1;
        }
      }
      right = Math.max(right, l + 1);
      dp[l] = l + 1;
      helper[l] = arr[i];
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
