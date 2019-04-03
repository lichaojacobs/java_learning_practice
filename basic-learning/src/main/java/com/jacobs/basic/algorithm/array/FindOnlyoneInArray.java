package com.jacobs.basic.algorithm.array;

/**
 * 数组中，其他数都是出现俩次，只有n个数出现一次，找出这n个数¬
 * Created by lichao on 2017/3/10.
 */
public class FindOnlyoneInArray {
  public static void main(String[] args) {
    int[] arrays = new int[]{1, 2, 3, 2, 1, 3, 4};
    //FindOnlyoneInArray(arrays);
    System.out.println(findFirstBitIsOne(4));
  }

  /**
   * 只有一个数出现一次
   */
  public static void FindOnlyoneInArray(int[] arrays) {
    if (arrays == null || arrays.length < 2) {
      return;
    }

    int result = 0;

    for (int i = 0; i < arrays.length; i++) {
      result ^= arrays[i];
    }

    System.out.println("出现一次的那个数为: " + result);
  }


  /**
   * 有俩个数出现一次,关键在于分组
   */
  public static void FindOnlyOneInArray2(int[] arrays) {
    if (arrays == null || arrays.length < 2) {
      return;
    }

    int result = 0;
    for (int i = 0; i < arrays.length; i++) {
      result ^= arrays[i];
    }
    int index = findFirstBitIsOne(result);

    //划分数组
    int num1 = 0;
    int num2 = 0;
    for (int i = 0; i < arrays.length; i++) {
      if (isOne(arrays[i], index)) {
        num1 ^= arrays[i];
      } else {
        num2 ^= arrays[i];
      }
    }

    System.out.println("只出现一次的俩个数为: " + num1 + ", " + num2);
  }

  public static boolean isOne(int number, int index) {
    number = number >> index;
    return (number & 1) == 1;
  }

  /**
   * 找到右边第一个是1的位置
   */
  public static int findFirstBitIsOne(int result) {
    int index = 0;
    while ((result & 1) == 0 && index < 4 * 8) {
      result = result >> 1;
      index++;
    }

    return index;
  }
}
