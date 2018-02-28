package com.jacobs.basic.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author lichao
 * @date 2018/2/8
 */
public class Problems_07 {

  public static void main(String[] args) {
    int[] A = new int[]{-2, -1};
    System.out.println(maxSubArray_101(A));
//    System.out.println(canJump_099(A));
//    System.out.println(
//        spiralOrder_100(
//            new int[][]{{1, 11}, {2, 12}, {3, 13}, {4, 14}, {5, 15}, {6, 16}, {7, 17}, {8, 18},
//                {9, 19}, {10, 20}}));
    //System.out.println(solveNQueens_102(8));

    System.out.println(permute_105(new int[]{1, 2, 3, 4}));
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


  //  Given an integer n, return all distinct solutions to the n-queens puzzle.
//
//  Each solution contains a distinct board configuration of the n-queens' placement, where'Q'and'.'both indicate a queen and an empty space respectively.
  public static ArrayList<String[]> solveNQueens_102(int n) {
    ArrayList<String[]> resultList = new ArrayList<>();
    if (n <= 0) {
      return resultList;
    }

    //cols[i]表示第i个皇后所在的列
    int[] cols = new int[n];
    //循环n列
    for (int i = 0; i < n; i++) {
      cols[0] = i;//先放置第一个确定的皇后,之后从第二行开始
      queensHelper(resultList, cols, 1);
    }

    return resultList;
  }

  public static void queensHelper(ArrayList<String[]> resultList, int[] cols, int row) {
    if (row == cols.length) {
      String[] resultStrs = new String[cols.length];
      for (int i = 0; i < resultStrs.length; i++) {
        resultStrs[i] = "";
        for (int j = 0; j < resultStrs.length; j++) {
          if (j == cols[i]) {
            resultStrs[i] += "Q";
          } else {
            resultStrs[i] += ".";
          }
        }
      }
      resultList.add(resultStrs);
      return;
    }

    //递归放置其他皇后
    for (int i = 0; i < cols.length; i++) {
      if (isValid(cols, row, i)) {
        cols[row] = i;
        queensHelper(resultList, cols, row + 1);
      }
    }
  }

  public static boolean isValid(int[] cols, int row, int col) {
    if (cols == null) {
      return false;
    }

    //与之前的对比
    for (int i = 0; i < row; i++) {
      if (cols[i] == col || Math.abs(cols[i] - col) == Math.abs(row - i)) {
        return false;
      }
    }

    return true;
  }

  //  Follow up for N-Queens problem.
//
//  Now, instead outputting board configurations, return the total number of distinct solutions.
  static int sum = 0;

  public static int totalNQueens_103(int n) {
    if (n <= 0) {
      return 0;
    }

    //cols[i]表示第i个皇后所在的列
    int[] cols = new int[n];
    //循环n列
    for (int i = 0; i < n; i++) {
      cols[0] = i;//先放置第一个确定的皇后,确定了行与列
      queensHelper2(cols, 1);
    }

    return sum;
  }

  public static void queensHelper2(int[] cols, int row) {
    if (row == cols.length) {
      sum = sum + 1;
      return;
    }

    //递归放置其他皇后
    for (int i = 0; i < cols.length; i++) {
      if (isValid(cols, row, i)) {
        cols[row] = i;
        queensHelper2(cols, row + 1);
      }
    }
  }

  //Implement pow(x, n).
  public static double pow_104(double x, int n) {
    if (n == 0) {
      return 1;
    }

    if (n < 0) {
      return 1 / x * pow_104(1 / x, -(n + 1));
    }
    if (n % 2 == 0) {
      return pow_104(x * x, n / 2);
    } else {
      return pow_104(x * x, n / 2) * x;
    }
  }


  //  Given a collection of numbers, return all possible permutations.
//
//      For example,
//  [1,2,3]have the following permutations:
//      [1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2], and[3,2,1].
  public static ArrayList<ArrayList<Integer>> permute_105(int[] num) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    if (num == null || num.length == 0) {
      return resultList;
    }

    permute_helper(resultList, new ArrayList<>(), num);

    return resultList;
  }

  public static void permute_helper(ArrayList<ArrayList<Integer>> resultList,
      ArrayList<Integer> result, int[] nums) {
    if (result.size() == nums.length) {
      resultList.add(new ArrayList<>(result));
    }

    for (int i = 0; i < nums.length; i++) {
      if (!result.contains(nums[i])) {
        result.add(nums[i]);
        permute_helper(resultList, result, nums);
        result.remove(result.size() - 1);
      }
    }
  }

  //  Given a collection of numbers that might contain duplicates, return all possible unique permutations.
//
//  For example,
//  [1,1,2]have the following unique permutations:
//      [1,1,2],[1,2,1], and[2,1,1].
  public static ArrayList<ArrayList<Integer>> permuteUnique_106(int[] num) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    if (num == null || num.length == 0) {
      return resultList;
    }

    Arrays.sort(num);
    boolean[] used = new boolean[num.length];
    permute_helper2(resultList, new ArrayList<>(), num, used);

    return resultList;
  }

  public static void permute_helper2(ArrayList<ArrayList<Integer>> resultList,
      ArrayList<Integer> result, int[] nums, boolean[] used) {
    if (result.size() == nums.length) {
      resultList.add(new ArrayList<>(result));
    }

    for (int i = 0; i < nums.length; i++) {
      if (used[i]) {
        continue;
      }
      if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
        continue;
      }

      result.add(nums[i]);
      used[i] = true;
      permute_helper2(resultList, result, nums, used);
      used[i] = false;
      result.remove(result.size() - 1);
    }
  }
}
