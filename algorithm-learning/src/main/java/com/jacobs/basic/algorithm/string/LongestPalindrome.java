package com.jacobs.basic.algorithm.string;

/**
 * Created by lichao on 2017/3/17.
 */
public class LongestPalindrome {
  public static void main(String[] args) {
    System.out.println(longestPalindrome1("abcdce"));
  }

  /**
   * 返回值为0表示不存在回文字串，假设中心为i,向两边扩展。
   */
  public static int longestPalindrome1(String str) {
    if (str == null || str.length() <= 0) {
      return 0;
    }

    int length = str.length();
    int max = 0;
    int loopLength = 0;
    for (int i = 0; i < str.length(); i++) {
      for (int j = 0; (i - j >= 0) && (i + j < length); j++) {
        if (str.charAt(i - j) != str.charAt(i + j)) {
          break;
        }
        loopLength = j * 2 + 1;
      }

      if (loopLength > max) {
        max = loopLength;
      }

      for (int j = 0; (i - j >= 0) && (i + j + 1 < length); j++) {
        if (str.charAt(i - j) != str.charAt(i + j + 1)) {
          break;
        }
        loopLength = j * 2 + 2;
      }

      if (loopLength > max) {
        max = length;
      }
    }

    return max;
  }

  /**
   * 优化复杂度为 O(n) Manacher算法...待实现
   */
  public static int longestPalindrome2(String str) {
    if (str == null || str.length() <= 0) {
      return 0;
    }

    return 0;
  }
}
