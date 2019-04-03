package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/9/29.
 */
public class HannoTa {

  public static void main(String[] args) {
    hannoi(5);
  }

  //原始题目
  public static void hannoi(int n) {
    if (n > 0) {
      func(n, "left", "mid", "right");
    }
  }

  public static void func(int n, String from, String mid, String to) {
    if (n == 1) {
      System.out.println("move from " + from + " to " + to);
    } else {
      func(n - 1, from, to, mid);
      func(1, from, mid, to);
      func(n - 1, mid, from, to);
    }
  }
}
