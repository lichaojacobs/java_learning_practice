package com.jacobs.basic.algorithm.array;

/**
 * Created by lichao on 2017/2/26.
 * 给定容器如[1,0,3,...,4,1] 获取容器的存水量
 */
public class GetWater {
  public static void main(String[] args) {

  }

  /**
   * 创建俩个数组，分别表示当为i,j时，从0到i和从j到length-2的最大值
   */
  public static int getWater1(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }

    int n = arr.length - 2;
    int[] leftMaxs = new int[n];
    leftMaxs[0] = arr[0];
    for (int i = 0; i < n; i++) {
      leftMaxs[i] = Math.max(leftMaxs[i - 1], arr[i]);
    }

    int[] rightMaxs = new int[n];
    rightMaxs[n - 1] = arr[n + 1];
    for (int i = n - 2; i >= 0; i--) {
      rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i + 2]);
    }

    int value = 0;

    for (int i = 1; i <= n; i++) {
      value += Math.max(0, Math.min(leftMaxs[i - 1], rightMaxs[i - 1]) - arr[i]);
    }

    return value;
  }

  /**
   * 不用额外的空间，头尾指针去遍历，维护四个变量:LPoint, RPoint, LMax, RMax,
   */
  public static int getWater2(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }

    int LPoint = 0;
    int RPoint = arr.length - 1;
    int LMax = arr[0];
    int RMax = arr[arr.length - 1];

    int value = 0;
    while (LPoint != RPoint) {
      if (LMax > RMax) {
        value += Math.max(0, arr[RPoint] - arr[RPoint - 1]);
        RMax = Math.max(RMax, arr[RPoint--]);
      } else if (LMax == RMax) {
        value += Math.max(0, arr[RPoint] - arr[RPoint - 1]);
        value += Math.max(0, arr[LPoint] - arr[LPoint + 1]);

        RMax = Math.max(RMax, arr[RPoint--]);
        LPoint = Math.max(LMax, arr[LPoint++]);
      } else {
        value += Math.max(0, arr[LPoint] - arr[LPoint + 1]);
        LPoint = Math.max(LMax, arr[LPoint++]);
      }
    }

    return value;
  }
}
