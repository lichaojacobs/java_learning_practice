package com.jacobs.basic.algorithm.array;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by lichao on 16/10/11.
 */
public class MaxWindow {
  public static void main(String[] args) {
    int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
    Arrays.stream(getMaxArray(arr, 3))
        .forEach(number -> {
          System.out.println(number);
        });
  }

  /**
   * 自己实现的,略复杂
   */
  public static int[] getMaxArray(int[] arr, int w) {
    if (arr == null || arr.length == 0 || w < 1) {
      return null;
    }
    int[] resultArr = new int[arr.length - w + 1];
    int index = 0;
    LinkedList<Integer> qmax = new LinkedList<>();
    for (int i = 0; i < arr.length; i++) {
      if (qmax.isEmpty()) {
        qmax.add(i);
      } else {
        if (qmax.peekFirst() == i - w) {
          qmax.pollFirst();
        }
        while (!qmax.isEmpty()) {
          int last = qmax.peekLast();
          if (arr[last] > arr[i]) {
            qmax.addLast(i);
            break;
          } else {
            qmax.pollLast();
          }
        }
        if (qmax.isEmpty()) {
          qmax.add(i);
        }
      }
      if (i >= w - 1) {
        resultArr[index++] = arr[qmax.peekFirst()];
      }
    }
    return resultArr;

  }

  /**
   * 书上实现
   */
  public static int[] getMaxArrayNew(int[] arr, int w) {
    if (arr == null || w < 1 || arr.length < w) {
      return null;
    }
    LinkedList<Integer> qmax = new LinkedList<>();
    int[] res = new int[arr.length - w + 1];
    int index = 0;
    for (int i = 0; i < arr.length; i++) {
      while (!qmax.isEmpty() && arr[qmax.peekLast()] < arr[i]) {
        qmax.pollLast();
      }
      qmax.add(i);
      if (qmax.peekFirst() == i - w) {
        qmax.pollFirst();
      }
      if (i >= w - 1) {
        res[index + 1] = arr[qmax.peekFirst()];
      }
    }
    return res;
  }
}
