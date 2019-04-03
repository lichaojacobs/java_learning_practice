package com.jacobs.basic.algorithm.sortmethods;

import java.util.Arrays;

/**
 * 荷兰国旗问题
 * Created by lichao on 2017/3/7.
 */
public class SortBalls {
  public static void main(String[] args) {
    int[] arrays = new int[]{0, 1, 2, 2, 1, 0, 0, 2, 1,1,2,0,0,2,1};
    sortBalls2(arrays);
    Arrays.stream(arrays)
        .forEach(System.out::print);
  }

  /**
   * 有RGB三色球若干，颜色值为0，1，2。现在要将其数组排列成0000111122222....
   * 类似于一次快排的划分过程
   */
  public static void sortBalls(int[] arrays) {
    if (arrays == null || arrays.length == 0) {
      return;
    }

    int left = 0;
    int right = arrays.length - 1;
    int middle = 0;

    while (left != right) {
      if (arrays[middle] == 0) {
        swap(left, middle, arrays);
        left++;
        middle++;
      }
      if (arrays[middle] == 1) {
        middle++;
      }
      if (arrays[middle] == 2) {
        swap(middle, right, arrays);
        right--;
      }
    }
  }

  /**
   * 有RGB三色球若干，颜色值为0，1，2。现在要将其数组排列成012,012,012...
   */
  public static void sortBalls2(int[] arrays) {
    if (arrays == null || arrays.length == 0) {
      return;
    }

    int r = 0;
    int g = 1;
    int b = 2;
    int index = 0;

    while (index < arrays.length) {
      int mod = index % 3;
      if (mod == 0) {
        //此位置应该是红球
        if (arrays[index] == 0) {
          if (r == index) {
            r = r + 3;
          }
          index++;
        } else if (arrays[index] == 1) {
          swap(g, index, arrays);
          g = g + 3;
        } else if (arrays[index] == 2) {
          swap(b, index, arrays);
          b = b + 3;
        }
      } else if (mod == 1) {
        //此位置应该为绿球
        if (arrays[index] == 0) {
          swap(r, index, arrays);
          r++;
        } else if (arrays[index] == 1) {
          if (g == index) {
            g = g + 3;
          }
          index++;
        } else if (arrays[index] == 2) {
          swap(b, index, arrays);
          b = b + 3;
        }
      } else if (mod == 2) {
        //此位置应该为蓝球
        if (arrays[index] == 0) {
          swap(r, index, arrays);
          r++;
        } else if (arrays[index] == 1) {
          swap(g, index, arrays);
          g = g + 3;
        } else if (arrays[index] == 2) {
          if (b == index) {
            b = b + 3;
          }
          index++;
        }
      }
    }
  }

  public static void swap(int index1, int index2, int[] arrays) {
    int temp = arrays[index1];
    arrays[index1] = arrays[index2];
    arrays[index2] = temp;
  }
}
