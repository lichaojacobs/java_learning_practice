package com.example.basic.algorithm.sortmethods;

/**
 * 二分排序决定必须是有序序列
 * Created by lichao on 2017/1/21.
 */
public class BinarySearch {
  public static void main(String[] args) {
    int[] list = {1, 5, 6, 8, 10, 25, 16};
    System.out.println(binarySearch2(list, 0, list.length - 1, 25));
  }

  /**
   * 非递归
   */
  public static int binarySearch1(int[] list, int key) {
    if (list == null || list.length < 1) {
      return -1;
    }
    int low = 0;
    int high = list.length - 1;
    while (low <= high) {
      int mid = (low + high) / 2;
      if (list[mid] == key) {
        return mid;
      }
      if (list[mid] > key) {
        high = mid - 1;
      } else {
        low = mid + 1;
      }
    }

    return list[low] == key ? low : -1;
  }

  public static int binarySearch2(int[] list, int low, int high, int key) {
    if (low > high) {
      return list[low] == key ? low : -1;
    }
    int mid = (low + high) / 2;
    if (list[mid] == key) {
      return mid;
    }
    if (list[mid] > key) {
      return binarySearch2(list, low, mid - 1, key);
    } else {
      return binarySearch2(list, mid + 1, high, key);
    }
  }
}
