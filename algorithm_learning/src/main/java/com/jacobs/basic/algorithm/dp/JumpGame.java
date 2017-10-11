package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/10/8.
 */
//给定一个数组arr,arr[j]=k代表可以从位置i向右跳1~k个距离
//比如arr[2]=3,代表从位置2跳到位置3，4，5。如果从位置0出发，返回最少跳几次
//能跳到arr最后的位置上
public class JumpGame {

  public static void main(String[] args) {
    System.out.println(jumpStep(new int[]{3, 2, 3, 1, 1, 4}));
  }

  public static int jumpStep(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }

    //当前步骤可以覆盖到的位置
    int curr = 0;
    //下一步可以覆盖到的最远的位置
    int next = 0;
    //需要跳跃的次数
    int jump = 0;

    for (int i = 0; i < arr.length; i++) {
      if (curr < i) {
        jump++;
        curr = next;
      }

      //快速退出
      if (curr >= arr.length - 1) {
        break;
      }

      next = Math.max(next, i + arr[i]);
    }

    return jump;
  }
}
