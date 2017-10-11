package com.jacobs.basic.algorithm.recursion;

/**
 * Created by lichao on 2017/9/25.
 */
public class RecursionSamples {

  public static void main(String[] args) {
    //feibonaqie
    System.out.println(getCowNum(5));
  }


  //斐波那契额数组
  public static int feibonaqie(int n) {
    if (n < 0) {
      return 0;
    }
    if (n == 1 || n == 2) {
      return 1;
    }

    return feibonaqie(n - 1) + feibonaqie(n - 2);
  }

  //斐波那契非递归
  public static int feibonaqie2(int n) {
    if (n < 0) {
      return 0;
    }

    if (n == 1 || n == 2) {
      return 1;
    }

    int f1 = 1;
    int f2 = 1;

    int fi = 0;
    for (int i = 3; i <= n; i++) {
      fi = f1 + f2;
      f1 = f2;
      f2 = fi;
    }

    return fi;
  }

  //给定整数N,代表台阶数，一次可以跨2个或者1个台阶，返回有多少种走法
  public static int getStepsNum(int n) {
    if (n < 0) {
      return 0;
    }
    if (n == 1 || n == 2) {
      return n;
    }

    return getStepsNum(n - 1) + getStepsNum(n - 2);
  }

  //农场奶牛每年生一头小母牛，永远不会死，第一年有一只成熟的母牛，从第二年开始，母牛开始生小母牛，每只小母牛3年后又可以生小母牛
  //给定整数N，求出N年后牛的数量
  public static int getCowNum(int n) {
    if (n < 0) {
      return 0;
    }
    if (n < 3) {
      return n;
    }

    return getCowNum(n - 3) + getCowNum(n - 1);
  }
}
