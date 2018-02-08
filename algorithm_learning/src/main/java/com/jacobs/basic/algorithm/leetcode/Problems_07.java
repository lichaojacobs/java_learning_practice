package com.jacobs.basic.algorithm.leetcode;

/**
 * @author lichao
 * @date 2018/2/8
 */
public class Problems_07 {

  public static void main(String[] args) {
    int[] A = new int[]{2,3,1,1,4};
    System.out.println(canJump_099(A));
  }

  //  Given an array of non-negative integers, you are initially positioned at the first index of the array.
//
//  Each element in the array represents your maximum jump length at that position.
//
//      Determine if you are able to reach the last index.
//
//  For example:
//  A =[2,3,1,1,4], returntrue.
//
//      A =[3,2,1,0,4], returnfalse.
  //动态规划，第一个设为true，其他看情况
  public static boolean canJump_099(int[] A) {
    if (A == null || A.length == 0) {
      return true;
    }

    int length = A.length;
    boolean[] dp = new boolean[length];
    dp[0] = true;
    for (int i = 1; i < length; i++) {
      dp[i] = false;
    }

    for (int i = 0; i < length && dp[i]; i++) {
      for (int j = 0; j <= A[i] && (i + j < length); j++) {
        dp[i + j] = true;
      }
    }

    return dp[length - 1];
  }
}
