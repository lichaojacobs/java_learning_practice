package com.jacobs.basic.algorithm.sortmethods;

import java.util.Arrays;

/**
 * Created by lichao on 2017/1/21.
 */
public class MergeSort {
  public static void main(String[] args) {
    int[] list = {1, 5, 6, 8, 10, 25, 16};
    mergeSort(list, 0, list.length - 1);
    Arrays.stream(list)
        .forEach(System.out::println);
  }

  //  详细步骤：
  //  申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列
  //  设定两个指针，最初位置分别为两个已经排序序列的起始位置
  //  比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置
  //      重复步骤3直到某一序列的指针达到序列尾
  //  将另一序列剩下的所有元素直接复制到合并序列尾，一次归并结束
  public static void mergeSort(int[] list, int left, int right) {
    if (left >= right) {
      //退栈
      return;
    }
    int center = (left + right) >> 1;
    mergeSort(list, left, center);
    mergeSort(list, center + 1, right);
    merge(list, left, center, right);
  }

  public static void merge(int[] list, int left, int center, int right) {
    int[] tempArr = new int[right + 1];
    int index = left;
    int tmpLeft = left;
    int tmpMid = center + 1;

    while (tmpLeft <= center && tmpMid <= right) {
      tempArr[index++] = list[tmpLeft] >= list[tmpMid] ? list[tmpMid++] : list[tmpLeft++];
    }
    while (tmpMid <= right) {
      tempArr[index++] = list[tmpMid++];
    }
    while (tmpLeft <= center) {
      tempArr[index++] = list[tmpLeft++];
    }

    //复制到原数组
    for (int i = left; i <= right; i++) {
      list[i] = tempArr[i];
    }
  }
}
