package com.jacobs.basic.algorithm.dp;

/**
 * 最小编辑代价,给定俩个字符串str1和str2，再给定三个整数ic、dc、和rc，分别代表插入、删除和替换一个字符的代价，返回将str1编辑成str2的最小代价
 */
public class MinEditCost {

  public static void main(String[] args) {

  }

  public static int minCost1(String str1, String str2, int ic, int dc, int rc) {
    if (str1 == null || str2 == null) {
      return 0;
    }

    char[] chs1 = str1.toCharArray();
    char[] chs2 = str2.toCharArray();
    int row = chs1.length + 1;
    int col = chs2.length + 1;
    //dp[i][j]表示把str1[0,i]编辑成str2[0,j]的最小代价
    int[][] dp = new int[row][col];

    for (int i = 1; i < row; i++) {
      dp[i][0] = dc * i;
    }

    for (int j = 1; j < col; j++) {
      dp[0][j] = ic * j;
    }

    //来自四种情况
    for (int i = 1; i < row; i++) {
      for (int j = 1; j < col; j++) {
        if (chs1[i - 1] == chs2[j - 1]) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          dp[i][j] = dp[i - 1][j - 1] + rc;
        }

        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
        dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
      }
    }

    return dp[row - 1][col - 1];
  }
}
