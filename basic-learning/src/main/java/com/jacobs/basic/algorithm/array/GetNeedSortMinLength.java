package com.jacobs.basic.algorithm.array;

/**
 * 需要排序的最短子数组长度
 *
 * @author lichao
 * @date 2017/11/17
 */
public class GetNeedSortMinLength {

  public static void main(String[] args) {

  }

  /**
   * 给定一个无序数组arr，求出需要排序的最短子数组长度
   *
   * @param arr 数组arr
   * @return 返回需要排序的长度
   */
  public static int getMinLength(int[] arr) {
    if (arr == null || arr.length < 2) {
      return 0;
    }

    int min = arr[arr.length - 1];
    int noMinIndex = -1;
    for (int i = arr.length - 2; i != -1; i--) {
      if (arr[i] > min) {
        noMinIndex = i;
      } else {
        min = Math.min(min, arr[i]);
      }
    }

    if (noMinIndex == -1) {
      return 0;
    }

    int max = arr[0];
    int noMaxIndex = -1;
    for (int i = 1; i != arr.length; i++) {
      if (arr[i] < max) {
        noMaxIndex = i;
      } else {
        max = Math.max(max, arr[i]);
      }
    }

    return noMaxIndex - noMinIndex + 1;
  }
}
