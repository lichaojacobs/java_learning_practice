package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/10/4.
 */
//N皇后问题。N*N的棋盘上要摆N个皇后，要求任何两个皇后不同行，不同列，也不在同一条斜线上。给定一个整数n,返回n皇后的摆法有
//多少种
public class NQueens {

  public static void main(String[] args) {

  }

  public static int num1(int n) {
    if (n < 1) {
      return 0;
    }

    //表示第i行皇后所在的列数
    int[] record = new int[n];
    return process(1, record, n);
  }

  public static int process(int i, int[] record, int n) {
    if (i == n) {
      return 1;
    }

    int res = 0;
    for (int j = 0; j < n; j++) {
      if (isvaild(record, i, j)) {
        record[i] = j;
        res += process(i + 1, record, n);
      }
    }

    return res;
  }

  public static boolean isvaild(int[] record, int i, int j) {
    for (int k = 0; k < i; k++) {
      if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
        return false;
      }
    }

    return true;
  }
}
