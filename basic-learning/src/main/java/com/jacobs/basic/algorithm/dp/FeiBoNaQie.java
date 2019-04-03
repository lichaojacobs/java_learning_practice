package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/2/5.
 */
public class FeiBoNaQie {
  public static void main(String[] args) {
    //System.out.println(getMethodNumber(10));
  }

  /**
   * 给定整数N，代表台阶数，一次可以跨2个或者一个台阶，返回有多少种走法(暴力递归 复杂度O(2^n))
   */
  public static int getMethodNumber1(int n) {
    if (n == 1) {
      return 1;
    }

    if (n == 2) {
      return 2;
    }

    return getMethodNumber1(n - 1) + getMethodNumber1(n - 2);
  }

  /**
   * 复杂度为O(N)
   */
  public static int getMethodNumber2(int n) {
    if (n < 1) {
      return 0;
    }
    if (n == 1 || n == 2) {
      return 1;
    }
    int result = 1;
    int pre = 1;
    int temp = result;
    for (int i = 3; i < n; i++) {
      temp = result;
      result = result + pre;
      pre = temp;
    }

    return result;
  }


  /**
   * 农场1头母牛，第二年生小母牛，每只小母牛三年之后成熟又可以生小母牛，求N年后牛的数量。牛永远不会死
   */
  public static int getCowNumber(int n) {
    if (n == 1 || n == 2 || n == 3) {
      return n;
    }

    return getCowNumber(n - 1) + getCowNumber(n - 3);
  }


}
