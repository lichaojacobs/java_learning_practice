package com.jacobs.basic.algorithm.array;

import java.util.HashSet;
import java.util.Set;

/**
 * 最长的可整合子数组的长度。定义：一个数组在排序之后，每相邻两个数差的绝对值都为1，则该数组称为可整合数组
 *
 * 例如：[5,5,3,2,6,4,3] 最大可整合子数组为【5，3，2，6，4】
 *
 * @author lichao
 * @date 2017/11/18
 */
public class GetLongestSortableSubArr {

  public static void main(String[] args) {
    System.out.println(getLIL(new int[]{5, 5, 3, 2, 6, 4, 3}));
  }

  /**
   * 检验规则：一个数组中如果没有重复元素，并且如果最大值减去最小值，再加上1的结果等于元素个数
   *
   * (max-min+1==元素个数)，那么这个数组就是可整合数组
   *
   * @param arr 数组
   * @return 最大可整合子数组长度
   */
  public static int getLIL(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }

    int maxLen = 0;
    int min = 0;
    int max = 0;
    //确保子数组中间不出现重复的元素
    Set<Integer> set = new HashSet<>();

    for (int i = 0; i < arr.length; i++) {
      min = Integer.MAX_VALUE;
      max = Integer.MIN_VALUE;

      for (int j = i; j < arr.length; j++) {
        if (set.contains(arr[j])) {
          break;
        }

        min = Math.min(arr[j], min);
        max = Math.max(arr[j], max);
        if (max - min == j - i) {
          maxLen = Math.max(maxLen, j - i + 1);
        }
      }

      set.clear();
    }

    return maxLen;
  }
}
