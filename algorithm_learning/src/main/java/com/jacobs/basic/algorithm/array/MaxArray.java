package com.jacobs.basic.algorithm.array;

import java.util.LinkedList;

/**
 * Created by lichao on 2016/10/31.
 */
public class MaxArray {

  public static void main(String[] args) {
    int[][] matrix = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
    rotate(matrix);
  }

  /**
   * 最大值减去最小值小于或者等于参数num的子数组数量。
   *
   * @param arr 数组arr
   * @param num 指定和 num
   */
  public static int getNum(int[] arr, int num) {
    if (arr == null || arr.length == 0) {
      return 0;
    }
    LinkedList<Integer> qmin = new LinkedList<>();
    LinkedList<Integer> qmax = new LinkedList<>();
    int i = 0;
    int j = 0;
    int res = 0;
    while (i < arr.length) {
      while (j < arr.length) {
        while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]) {
          qmin.pollLast();
        }
        qmin.addLast(j);
        while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j]) {
          qmax.pollLast();
        }
        qmax.addLast(j);
        if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
          break;
        }
        j++;
      }
      res += j - i;
      i++;
    }

    return res;
  }

  /**
   * 转圈打印矩阵
   *
   * @param matrix 矩阵
   */
  public static void spiralOrderPrint(int[][] matrix) {
    int rowStart = 0;
    int rowEnd = matrix.length - 1;
    int colStart = 0;
    int colEnd = matrix[0].length - 1;

    //开始打印
    while ((rowStart <= rowEnd) && (colStart <= colEnd)) {
      //处理只有一行一列的情况
      if (rowStart == rowEnd) {
        for (int k = colStart; k <= colEnd; k++) {
          System.out.print(matrix[rowStart][k] + ", ");
        }
      } else if (colStart == colEnd) {
        for (int k = rowStart; k <= rowEnd; k++) {
          System.out.print(matrix[k][colStart]);
        }
      } else {

        int i = rowStart;
        int j = colStart;

        //上
        for (; j <= colEnd; j++) {
          System.out.print(matrix[i][j] + ", ");
        }
        j--;
        i++;

        //右
        for (; i <= rowEnd; i++) {
          System.out.print(matrix[i][j] + ", ");
        }
        i--;
        j--;

        //下
        for (; j >= colStart; j--) {
          System.out.print(matrix[i][j] + ",");
        }
        j++;
        i--;

        //左
        for (; i > rowStart; i--) {
          System.out.print(matrix[i][j] + ",");
        }
      }

      rowStart++;
      colStart++;
      colEnd--;
      rowEnd--;
    }
  }

  /**
   * N * N 矩阵顺时针旋转90度，要求额外空间为O（1）
   *
   * @param matrix N*N 矩阵
   */
  public static void rotate(int[][] matrix) {
    int rowStart = 0;
    int rowEnd = matrix.length - 1;
    int colStart = 0;
    int colEnd = matrix[0].length - 1;

    while (rowStart < rowEnd) {
      int times = colEnd - colStart;
      int j = 0;

      while (j < times) {
        int temp1 = matrix[j + rowStart][colEnd];
        matrix[j + rowStart][colEnd] = matrix[rowStart][j + colStart];
        int temp2 = matrix[rowEnd][colEnd - j];
        matrix[rowEnd][colEnd - j] = temp1;
        int temp3 = matrix[rowEnd - j][colStart];
        matrix[rowEnd - j][colStart] = temp2;
        matrix[rowStart][j + colStart] = temp3;
        j++;
      }

      rowStart++;
      colStart++;
      colEnd--;
      rowEnd--;
    }

    printMatrix(matrix);
  }


  /**
   * 之字形打印矩阵 想法：起始坐标: (tR,tC) 从左到右，如果到了最后一列，就从最后一列往下， 另一个坐标(dR,dC) 从上到下，如果到了最后一行，就从最后一行往右，直至最后一个坐标
   * 期间，他们的连线就是需要打印的路径，用一个标志标志从上或者从下开始打印
   *
   * @param matrix 矩阵
   */
  public static void printZigZag(int[][] matrix) {
    int tR = 0;
    int tC = 0;
    int dR = 0;
    int dC = 0;
    int endRow = matrix.length - 1;
    int endCol = matrix[0].length - 1;
    boolean fromUp = false;

    while (tR != endRow + 1) {
      if (fromUp) {
        while (tR != dR + 1) {
          System.out.print(matrix[tR++][tC--] + " ");
        }
      } else {
        while (dR != tR - 1) {
          System.out.print(matrix[dR--][dC++] + " ");
        }
      }
      tR = tC == endCol ? tR + 1 : tR;
      tC = tC == endCol ? tC : tC + 1;
      dR = dR == endRow ? dR : dR + 1;
      dC = dR == endRow ? dC + 1 : dC;
    }
  }

  public static void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        System.out.print(matrix[i][j] + ", ");
      }
      System.out.println();
    }
  }
}
