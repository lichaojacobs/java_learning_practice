package com.example.basic.algorithm.dp;

/**
 * Created by lichao on 2017/2/3.
 * 最长公共字串
 */
public class LCST {
  public static void main(String[] args) {
    String str1 = "abcdresdfar";
    String str2 = "cdresasdfaere";
    System.out.println(getLCS(str1, str2));
  }

  /**
   * 未曾优化的方案1
   */
  public static int[][] getdp1(char[] str1, char[] str2) {
    int[][] dp = new int[str1.length][str2.length];
    dp[0][0] = str1[0] == str2[0] ? 1 : 0;
    for (int i = 1; i < str1.length; i++) {
      if (str1[i] == str2[0]) {
        dp[i][0] = 1;
      }
    }
    for (int j = 1; j < str2.length; j++) {
      if (str1[0] == str2[j]) {
        dp[0][j] = 1;
      }
    }

    for (int i = 1; i < str1.length; i++) {
      for (int j = 1; j < str2.length; j++) {
        if (str1[i] == str2[j]) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        }
      }
    }

    return dp;
  }

  public static String getLCS(String str1, String str2) {
    if (str1 == null || str2 == null) {
      return "";
    }

    char[] chars1 = str1.toCharArray();
    char[] chars2 = str2.toCharArray();
    int[][] dp = getdp1(chars1, chars2);
    int end = 0;
    int max = 0;

    for (int i = 0; i < chars1.length; i++) {
      for (int j = 0; j < chars2.length; j++) {
        if (dp[i][j] > max) {
          end = i;
          max = dp[i][j];
        }
      }
    }

    return str1.substring(end - max + 1, end + 1);
  }

}
