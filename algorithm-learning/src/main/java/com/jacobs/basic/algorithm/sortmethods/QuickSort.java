package com.jacobs.basic.algorithm.sortmethods;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lichao on 2017/1/21.
 */
public class QuickSort {

  public static void main(String[] args) {
    int[] list = {1, 9, 6, 5, 8, 7};
    sort(list, 0, list.length - 1);
    Arrays.stream(list)
        .forEach(System.out::println);
    ArrayList<Integer> list1 = new ArrayList<>();
  }

  public static void sort(int[] list, int low, int high) {
    if (low >= high) {
      return;
    }
    int index = partition(list, low, high);
    sort(list, low, index - 1);
    sort(list, index + 1, high);
  }

  public static int partition(int[] list, int low, int high) {
    int index = list[low];
    while (low < high) {
      while (low < high && index <= list[high]) {
        high--;
      }
      list[low] = list[high];
      while (low < high && index >= list[low]) {
        low++;
      }
      list[high] = list[low];
    }
    list[low] = index;
    return low;
  }

  public static int partition2(int[] list, int low, int high) {
    int pivot = list[low];
    int index = low;
    low = low - 1;
    high = high + 1;
    while (index != high) {
      if (list[index] < pivot) {
        swap(list, ++low, index++);
      } else if (list[index] == pivot) {
        index++;
      } else {
        swap(list, --high, index);
      }
    }
    return index;
  }

  public static int partition3(int[] a, int p, int r) {
    int x = a[r];
    int i = p;
    int j = p;
    for (; j < r; ++j) {
      if (a[j] < x) {
        if (i != j) {
          swap(a, a[i], a[j]);
        }
        i++;
      }
    }
    swap(a, a[i], a[j]);
    return i;
  }

  public static int partition4(int[] arr, int low, int high) {
    int temp = arr[low];

    while (low < high) {
      while ((low < high) && temp <= arr[high]) {
        high--;
      }

      arr[low] = arr[high];

      while ((low < high) && temp >= arr[low]) {
        low++;
      }

      arr[high] = arr[low];
    }

    arr[low] = temp;
    return low;
  }

  public static void swap(int[] list, int a, int b) {
    int temp = list[a];
    list[a] = list[b];
    list[b] = temp;
  }

  /**
   * 快速排序优化对于基准位置的选取一般有三种方法：固定切分，随机切分和三取样切分。固定切分的效率并不是太好， 随机切分是常用的一种切分，效率比较高，最坏情况下时间复杂度有可能为O(N2).对于三数取中选择基准点是最理想的一种。
   */
  public static int partitionOptimize(int[] array, int lo, int hi) {
    //三数取中
    int mid = lo + (hi - lo) / 2;
    if (array[mid] > array[hi]) {
      swap(array[mid], array[hi]);
    }
    if (array[lo] > array[hi]) {
      swap(array[lo], array[hi]);
    }
    if (array[mid] > array[lo]) {
      swap(array[mid], array[lo]);
    }
    int key = array[lo];

    while (lo < hi) {
      while (array[hi] >= key && hi > lo) {
        hi--;
      }
      array[lo] = array[hi];
      while (array[lo] <= key && hi > lo) {
        lo++;
      }
      array[hi] = array[lo];
    }
    array[hi] = key;
    return hi;
  }

  public static void swap(int a, int b) {
    int temp = a;
    a = b;
    b = temp;
  }

}
