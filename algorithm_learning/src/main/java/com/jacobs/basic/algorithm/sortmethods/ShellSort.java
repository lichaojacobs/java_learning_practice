package com.jacobs.basic.algorithm.sortmethods;

/**
 * Created by lichao on 2017/9/16. 希尔排序
 */
public class ShellSort {

  public static void main(String[] args) {

  }

  public static void shellSort(int[] a) {
    int N = a.length;

    int h = 1;
    while (h < N / 3) {
      h = 3 * h + 1;//1,4,13,40,121
    }

    while (h >= 1) {
      for (int i = h; i < N; i++) {
        for (int j = i; j > h && less(a[j], a[j - h]); j -= h) {
          exch(a, a[j], a[j - h]);
        }
      }

      h = h / 3;
    }
  }

  public static boolean less(int a, int b) {
    return a > b ? false : true;
  }

  public static void exch(int[] a, int b, int c) {
    int temp = a[b];
    a[b] = a[c];
    a[c] = temp;
  }
}
