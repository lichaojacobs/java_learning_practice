package com.jacobs.basic.algorithm.leetcode;

import java.util.ArrayList;

/**
 * @author lichao
 * @date 2018/2/8
 */
public class Problems_07 {

  public static void main(String[] args) {
    int[] A = new int[]{-2,-1};
    System.out.println(maxSubArray_101(A));
//    System.out.println(canJump_099(A));
//    System.out.println(
//        spiralOrder_100(
//            new int[][]{{1, 11}, {2, 12}, {3, 13}, {4, 14}, {5, 15}, {6, 16}, {7, 17}, {8, 18},
//                {9, 19}, {10, 20}}));
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


  //  Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
//
//  For example,
//  Given the following matrix:
//
//      [
//      [ 1, 2, 3,4 ],
//      [ 5, 6,7,8 ],
//      [ 9, 10 ,11,12 ]
  //    [13, 14, 15, 16]
//      ]
//  You should return[1,2,3,6,9,8,7,4,5].
  public static ArrayList<Integer> spiralOrder_100(int[][] matrix) {
    ArrayList<Integer> resultList = new ArrayList<>();
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return resultList;
    }

    if (matrix.length == 1 && matrix[0].length == 1) {
      resultList.add(matrix[0][0]);
      return resultList;
    }

    int starti = 0;
    int startj = 0;
    int endi = matrix.length - 1;
    int endj = matrix[0].length - 1;
    while (starti <= endi && startj <= endj) {
      //当只有一行一列的情况
      if (starti == endi) {
        for (int j = startj; j <= endj; j++) {
          resultList.add(matrix[starti][j]);
        }
        return resultList;
      } else if (startj == endj) {
        for (int i = starti; i <= endi; i++) {
          resultList.add(matrix[i][startj]);
        }
        return resultList;
      } else {
        int i = starti;
        int j = startj;
        //上边
        while (j < endj) {
          resultList.add(matrix[i][j++]);
        }

        //右边
        while (i < endi) {
          resultList.add(matrix[i++][j]);
        }

        //下边
        while (j > startj) {
          resultList.add(matrix[i][j--]);
        }

        //上边,加一的目的是不需要重复的加，只需要等待下一次循环就好
        while (i > starti) {
          resultList.add(matrix[i--][j]);
        }

        starti++;
        startj++;
        endi--;
        endj--;
      }
    }

    return resultList;
  }


  //  Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
//
//  For example, given the array[−2,1,−3,4,−1,2,1,−5,4],
//  the contiguous subarray[4,−1,2,1]has the largest sum =6.
//
// 思路: sum[i,j]=sum[0,j]-sum[0-i]
  public static int maxSubArray_101(int[] A) {
    if (A == null || A.length == 0) {
      return -1;//非法值
    }

    if (A.length == 1) {
      return A[0];
    }

    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    int sum = A[0];

    for (int i = 0; i < A.length; i++) {
      min = Math.min(min, sum);
      sum += A[i];
      max = Math.max(max, sum - min);
    }

    return max;
  }
}
