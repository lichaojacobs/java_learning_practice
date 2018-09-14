package com.jacobs.basic.algorithm.array;

/**
 * 给定排序数组arr和整数k，不重复打印arr中所有相加和为k的不降序二元组和三元组
 *
 * 例如，arr=[-8,-4,-3,0,1,2,4,5,8,9]，k=10，打印结果为：
 *
 * 1，9
 *
 * 2，8
 *
 * @author lichao
 * @date 2017/11/18
 */
public class PrintUniquePairWithFixedSum {

  public static void main(String[] args) {
    printUniquePair2(new int[]{-8, -4, -3, 0, 1, 2, 4, 5, 8, 9}, 10);
  }


  /**
   * 打印二元组问题: 由于已经排好序，所以只需要维护左右指针即可
   *
   * @param arr 排好序的数组
   * @param k 要求和为k
   */
  public static void printUniquePair(int[] arr, int k) {
    if (arr == null || arr.length <= 1) {
      return;
    }

    int left = 0;
    int right = arr.length - 1;

    while (left < right) {
      int sum = arr[left] + arr[right];
      if (sum == k) {
        System.out.println(arr[left] + ", " + arr[right]);
        left++;
        right--;
      } else if (sum < k) {
        left++;
      } else {
        right--;
      }
    }
  }


  /**
   * 打印三元组问题: 将第一个元素压住不动，其余两个元素的求值转变为二元组问题
   *
   * @param arr 排好序的数组
   * @param k 要求和为k
   */
  public static void printUniquePair2(int[] arr, int k) {
    if (arr == null || arr.length <= 3) {
      return;
    }

    int mid;
    int right;
    int sum;

    for (int i = 0; i < arr.length - 2; i++) {
      sum = k - arr[i];
      mid = i + 1;
      right = arr.length - 1;

      while (mid < right) {
        int tempSum = arr[mid] + arr[right];
        if (tempSum == sum) {
          System.out.println(arr[i] + ", " + arr[mid] + ", " + arr[right]);
          mid++;
          right--;
        } else if (tempSum < sum) {
          mid++;
        } else {
          right--;
        }
      }
    }
  }
}
