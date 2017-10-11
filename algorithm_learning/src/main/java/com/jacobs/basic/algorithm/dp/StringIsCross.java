package com.jacobs.basic.algorithm.dp;

/**
 * 字符串的交错组成
 */
//给定三个字符串str1，str2，和aim，如果aim包含且仅包含来自str1和str2的所有字符，而且在aim中属于str1的字符
//之间保持原来在str1中的顺序，str2也一样，那么称aim是str1和str2的交错组成，实现一个函数判断aim是否是str1和str2
//交错组成
public class StringIsCross {

  public static void main(String[] args) {
    //test
    System.out.println(isCross1("AB", "12", "A1B2"));
  }

  //非空间压缩法
  public static boolean isCross1(String str1, String str2, String aim) {
    if (str1 == null || str2 == null || aim == null) {
      return false;
    }

    char[] chars1 = str1.toCharArray();
    char[] chars2 = str2.toCharArray();
    char[] charsAim = aim.toCharArray();

    //长度不等也就没有必要进行下一步了
    if (charsAim.length != chars1.length + chars2.length) {
      return false;
    }

    //dp[i][j] 的值代表aim[0..i+j-1]能否被str1[0..i-1]和str2[0...j-1]交错组成
    boolean[][] dp = new boolean[chars1.length + 1][chars2.length + 1];

    //为了避免歧义,默认置为true,而不用比较
    dp[0][0] = true;
    //索引从1开始，则i最后需要等于字符长度
    for (int i = 1; i <= chars1.length; i++) {
      if (chars1[i - 1] != charsAim[i - 1]) {
        break;
      }
      dp[i][0] = true;
    }

    for (int j = 1; j <= chars2.length; j++) {
      if (chars2[j - 1] != charsAim[j - 1]) {
        break;
      }
      dp[0][j] = true;
    }

    for (int i = 1; i <= chars1.length; i++) {
      for (int j = 1; j <= chars2.length; j++) {
        if ((chars1[i - 1] == charsAim[i + j - 1] && dp[i - 1][j]) || (
            chars2[j - 1] == charsAim[i + j - 1] && dp[i][j - 1])) {
          dp[i][j] = true;
        }
      }
    }

    return dp[chars1.length][chars2.length];
  }

  //空间压缩法,长度较小的那个作为列对应的字符串，然后生成和较短字符串长度一样的一维数组dp，滚动更新即可
  public static boolean isCross2(String str1, String str2, String aim) {
    if (str1 == null || str2 == null || aim == null) {
      return false;
    }

    char[] chars1 = str1.toCharArray();
    char[] chars2 = str2.toCharArray();
    char[] charsAim = aim.toCharArray();

    if (charsAim.length != chars1.length + chars2.length) {
      return false;
    }

    char[] longs = chars1.length >= chars2.length ? chars1 : chars2;
    char[] shorts = chars1.length >= chars2.length ? chars2 : chars1;
    boolean[] dp = new boolean[shorts.length + 1];

    for (int i = 1; i <= shorts.length; i++) {
      if (shorts[i - 1] != charsAim[i - 1]) {
        break;
      }
      dp[i] = true;
    }

    for (int i = 1; i <= longs.length; i++) {
      //从上一层的更新过来
      dp[0] = dp[0] && longs[i - 1] == charsAim[i - 1];
      for (int j = 1; j <= shorts.length; j++) {
        if ((longs[i - 1] == charsAim[i + j - 1] && dp[j]) ||
            (shorts[j - 1] == charsAim[i + j - 1] && dp[j - 1])) {
          dp[j] = true;
        } else {
          dp[j] = false;
        }
      }
    }

    return dp[shorts.length];
  }
}
