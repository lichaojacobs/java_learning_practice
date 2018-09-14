package com.jacobs.basic.algorithm.array;

import java.util.Stack;

/**
 * Created by lichao on 2016/10/17.
 */
public class MaxMatrix {
  public static void main(String[] args) {
    int[][] matrix = new int[3][4];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        if ((i == 0 && j == 1) || (i == 2 && j == 3)) {
          matrix[i][j] = 0;
        } else {
          matrix[i][j] = 1;
        }
      }
    }
    System.out.println(stupidImplement(matrix));
  }

  public static int stupidImplement(int[][] matrix) {
    int maxLength = 0;
    int column = matrix[0].length;
    int row = matrix.length;
    for (int i = 0; i < row; i++) {
      int value = 0;
      for (int j = 0; j < column; j++) {
        if (matrix[i][j] == 1) {
          value = value + 1;
        } else {
          break;
        }
      }
      //替换
      if (value > maxLength) {
        maxLength = value;
      }
    }

    return maxLength;
  }

  public int maxRecSize(int[][] map) {
    if (map == null || map.length == 0 || map[0].length == 0) {
      return 0;
    }
    //元素弹出时即更新最大值
    int maxArea = 0;
    int[] height = new int[map[0].length];
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        height[j] = map[i][j] == 0 ? 0 : height[j] + 1;
      }
      //从左到右进行入栈出栈操作
      maxArea = Math.max(maxRecFromBottom(height), maxArea);
    }

    return maxArea;

  }

  public int maxRecFromBottom(int[] height) {
    if (height == null || height.length == 0) {
      return 0;
    }
    int maxArea = 0;
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < height.length; i++) {
      while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
        int j = stack.pop();
        int k = stack.isEmpty() ? -1 : stack.peek();
        int currentArea = (i - k - 1) * height[j];
        maxArea = Math.max(currentArea, maxArea);
      }
    }
    while (!stack.isEmpty()) {
      int j = stack.pop();
      int k = stack.isEmpty() ? -1 : stack.peek();
      int currentArea = (height.length - k - 1) * height[j];
      maxArea = Math.max(currentArea, maxArea);
    }

    return maxArea;
  }
}
