package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/2/3. 最长公共字串
 */
public class LCST {

  public static void main(String[] args) {
    String str1 = "abcdredfar";
    String str2 = "cdresadfaere";
    System.out.println(getLCS2(str1, str2));
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


  //优化的方案：空间复杂度由O(M)(N) 降到O(1)
  public static String getLCS2(String str1, String str2) {
    if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
      return null;
    }

    char[] chs1 = str1.toCharArray();
    char[] chs2 = str2.toCharArray();
    int row = 0;//斜线的开始位置的行
    int col = chs2.length - 1;//斜线开始位置的列
    int max = 0;//记录最大长度;
    int end = 0;//最大长度更新时，记录字串的结尾位置

    while (row < chs1.length) {
      int j = row;
      int i = col;
      int len = 0;

      //从(i,j)开始向右下方遍历
      while (i < chs1.length && j < chs2.length) {
        if (chs1[i] != chs2[j]) {
          len = 0;
        } else {
          len++;
        }

        //记录最大值，以及结束字符的位置
        if (len > max) {
          end = i;
          max = len;
        }
        i++;
        j++;
      }
      //斜线开始位置的列先向左移动
      if (col > 0) {
        col--;
      } else {// 列移动到最左之后，行向下移动
        row++;
      }
    }

    return str1.substring(end - max + 1, end + 1);
  }
}
