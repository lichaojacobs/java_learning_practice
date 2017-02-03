package com.example.algorithm.dp;

/**
 * Created by lichao on 2017/2/3.
 * 最长公共子序列问题
 */
public class LCSE {
  public static void main(String[] args) {

  }

  public int[][] getdp(char[] str1, char[] str2) {
    int[][] dp = new int[str1.length][str2.length];
    dp[0][0] = str1[0] == str2[0] ? 1 : 0;
    for (int i = 1; i < str1.length; i++) {
      dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
    }

    for (int j = 1; j < str2.length; j++) {
      dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
    }

    for (int i = 1; i < str1.length; i++) {
      for (int j = 1; j < str2.length; j++) {
        if (str1[i] == str2[j]) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    return dp;
  }

  public static char[] getLCS(char[] str1, char[] str2, int[][] dp) {
    int len1 = str1.length - 1;
    int len2 = str2.length - 1;
    int index = dp[len1][len2];
    char[] lcs = new char[index];
    index--;
    while (len1 >= 0 && len2 >= 0) {
      if (str1[len1] == str2[len2]) {
        lcs[index--] = str1[len1];
        len1--;
        len2--;
      } else if (dp[len1 - 1][len2] == dp[len1][len2]) {
        len1--;
      } else if (dp[len1][len2 - 1] == dp[len1][len2]) {
        len2--;
      }
    }

    return lcs;
  }
}
