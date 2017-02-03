package com.example.algorithm;

import java.util.LinkedList;

/**
 * Created by lichao on 2016/10/31. 最大值减去最小值小于或者等于num的子数组数量。
 */
public class MaxArray {
  public static void main(String[] args) {

  }

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
}
