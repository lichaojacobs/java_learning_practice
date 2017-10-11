package com.jacobs.basic.algorithm.sortmethods;

import static java.lang.Math.pow;

import java.util.Arrays;

/**
 * Created by lichao on 2017/9/19.
 */
public class RadixSort {


  /**
   * 稳定排序与非稳定排序的区别:非稳定排序在排序的过程中元素之间的相对位置会发生变化
   */
  public static void main(String[] args) {
    int[] arr = new int[]{3, 4, 12, 34, 33, 22, 25, 44, 12};
    sort(arr);
    Arrays.stream(arr).forEach(System.out::println);
  }


  //有n个数，取值范围是0~n^2
  public static void sort(int[] a) {
    int n = a.length;
    int[] c = new int[n];
    int[] remainder = new int[a.length];
    int[] b = new int[a.length];

    //考虑的是两位数的排序,所以这里先取个位，再取十位
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < a.length; j++) {
        int temp = getRadix(a[j], i, n);
        remainder[j] = temp;
        c[temp]++;
      }

      for (int k = 1; k < n; k++) {
        c[k] += c[k - 1];
      }

      for (int j = a.length - 1; j >= 0; j--) {
        b[--c[remainder[j]]] = a[j];
      }

      for (int j = 0; j < n; j++) {
        c[j] = 0;
      }

      for (int j = 0; j < a.length; j++) {
        a[j] = b[j];
      }
    }
  }

  public static int getRadix(int a, int i, int radix) {
    return ((int) (a / pow(radix, i))) % radix;
  }
}
