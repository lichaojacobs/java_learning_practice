package com.jacobs.basic.algorithm.array;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lichao on 2017/9/6.
 */
public class MaxLengthArray {

  public static void main(String[] args) {
    int[] arr = {1, 2, 1, 1, 1};
    System.out.println(getMaxLength2(arr, 3));
  }


  /**
   * 未排序正数数组中累加和为给定值的最长子数组长度 解法：还是俩个指针标记为left right，先right不断右移，直到sum[left...right]的累加和等于k,这时看看是不是更新maxLength
   * 然后此时说明以left开头的子数组,right后面就会一直大于k（正数的条件下）所以此时我们要将left往右移，即考虑以left+1的位置开始的子数组
   */
  public static int getMaxLength(int[] arr, int k) {
    if (arr == null || arr.length == 0 || k <= 0) {
      return 0;
    }

    int maxLength = 0;
    int left = 0;
    int right = 0;
    int sum = 0;

    while (right < arr.length) {
      if (sum < k) {
        sum += arr[right];
        right += 1;
      } else {
        if (sum == k) {
          maxLength = Math.max(maxLength, right - left + 1);
        }
        sum -= arr[left];
        left = left + 1;
      }
    }

    return maxLength;
  }

  //记忆搜索法 适合于一般情况（数组有正有负）
  public static int getMaxLength2(int[] arr, int k) {
    if (arr == null || arr.length == 0 || k <= 0) {
      return 0;
    }

    int sum = 0;
    int res = 0;
    //记录从第0个位置到当前位置的所有可能的累加和
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);

    for (int i = 0; i < arr.length; i++) {
      sum += arr[i];
      if (map.containsKey(sum - k)) {
        res = Math.max(i - map.get(sum - k), res);
      }
      if (!map.containsKey(sum)) {
        map.put(sum, i);
      }
    }

    return res;
  }
}
