package com.jacobs.basic.algorithm.dp;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;

/**
 * Created by lichao on 2017/10/17.
 */
public class PalindromePartitioning {

  public static void main(String[] args) {
    System.out.println(JSON.toJSONString(partition("aab")));
  }

  public static int minCut(String s) {
    if (s == null || s.length() == 0 || s.length() == 1) {
      return 0;
    }

    char[] chars = s.toCharArray();
    int len = chars.length;
    //dp[i] 含义是子串str[i..len-1]至少需要切割几次，才能把str[i..len-1]全部切成回文子串，dp[0]即是结果
    int[] dp = new int[len + 1];

    //对左边的情况进行枚举
    //字符串str[i..j]是回文有三种情况
    //str[i..j]由一个字符组成
    //str[i..j]由两个字符组成
    //str[i+1..j-1]是回文串，即p[i+1][j-1]为true，且，str[i]==str[j]
    boolean[][] p = new boolean[len][len];

    dp[len] = -1;
    for (int i = len - 1; i >= 0; i--) {
      dp[i] = Integer.MAX_VALUE;
      //向右边枚举
      for (int j = i; j < len; j++) {
        if (chars[i] == chars[j] && (j - i < 2 || p[i + 1][j - 1] == true)) {
          dp[i] = Math.min(dp[j + 1] + 1, dp[i]);
        }
      }
    }

    return dp[0];
  }

  public static ArrayList<ArrayList<String>> partition(String s) {
    ArrayList<ArrayList<String>> results = new ArrayList<>();
    if (s == null || s.length() == 0) {
      return results;
    }

    char[] chars = s.toCharArray();
    int len = chars.length;
    boolean[][] p = new boolean[len][len];
    getResults(chars, p, results, new ArrayList<>(), 0);
    return results;
  }

  public static void getResults(char[] chars, boolean[][] p,
      ArrayList<ArrayList<String>> results, ArrayList<String> current, int start) {
    if (start >= chars.length && current.size() > 0) {
      results.add((ArrayList<String>) current.clone());
      return;
    }

    for (int i = start; i < chars.length; i++) {
      if (isPalindrome(chars, p, start, i)) {
        p[start][i] = true;
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = start; j <= i; j++) {
          stringBuilder.append(chars[j]);
        }
        current.add(stringBuilder.toString());
        getResults(chars, p, results, current, i + 1);
        current.remove(current.size() - 1);
      }
    }
  }

  public static boolean isPalindrome(char[] chars, boolean[][] p, int start, int end) {
    return chars[start] == chars[end] && (end - start < 2 || p[start + 1][end - 1] == true);
  }
}
