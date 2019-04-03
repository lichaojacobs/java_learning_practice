package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/2/5. 矩阵的最小路径和
 */
public class MinPathSum {

  public static void main(String[] args) {

  }

  /**
   * 时间和空间复杂度O(M*N) 每个位置都计算一次从（0，0）位置到达自己的最小路径和
   */
  public static int minPathSum1(int[][] matrix) {
    if (matrix == null || matrix.length == 0) {
      return 0;
    }

    int row = matrix.length;
    int col = matrix[0].length;
    int[][] dp = new int[row][col];

    for (int i = 0; i < row; i++) {
      dp[i][0] = matrix[i][0];
    }

    for (int j = 1; j < col; j++) {
      dp[0][j] = matrix[0][j];
    }

    for (int i = 1; i < row; i++) {
      for (int j = 1; j < col; j++) {
        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + matrix[i][j];
      }
    }

    return dp[row - 1][col - 1];
  }

  //矩阵的最小路径和
  public static int getMinPathSum(int[][] m) {
    if (m == null || m[0].length == 0 || m.length == 0) {
      return 0;
    }

    int col = m[0].length;
    int row = m.length;
    int[][] sum = new int[row][col];
    sum[0][0] = m[0][0];

    //初始化
    for (int i = 1; i <= row; i++) {
      sum[i][0] = sum[i - 1][0] + m[i][0];
    }

    for (int j = 1; j <= col; j++) {
      sum[0][j] = sum[0][j - 1] + m[0][j];
    }

    for (int i = 1; i < row; i++) {
      for (int j = 1; j < col; j++) {
        sum[i][j] = Math.min(sum[i - 1][j], sum[i][j - 1]) + m[i][j];
      }
    }

    return sum[row - 1][col - 1];
  }


  /**
   * 空间压缩的动态规划，dp矩阵空间大小为Min({M,N})
   */
  public static int minPathSum2(int[][] matrix) {
    if (matrix == null || matrix.length == 0) {
      return 0;
    }

    int more = Math.max(matrix.length, matrix[0].length);
    int less = Math.min(matrix.length, matrix[0].length);
    boolean rowMore = matrix.length > matrix[0].length ? true : false;

    int[] arr = new int[less];

    arr[0] = matrix[0][0];
    for (int i = 1; i < less; i++) {
      arr[i] = arr[i - 1] + (rowMore ? matrix[0][i] : matrix[i][0]);
    }

    for (int j = 1; j < more; j++) {
      arr[0] = arr[0] + (rowMore ? matrix[0][j] : matrix[j][0]);

      for (int i = 1; i < less; i++) {
        arr[i] = Math.min(arr[i - 1], arr[i]) + (rowMore ? matrix[i][j] : matrix[j][i]);
      }
    }

    return arr[less - 1];
  }
}
