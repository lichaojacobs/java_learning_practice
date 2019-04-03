package com.jacobs.basic.algorithm.dp;

/**
 * @author lichao
 * @date 2017/11/7
 */
//国王和金矿:
//有一个国家发现了5座金矿，每座金矿的黄金储量不同，需要参与挖掘的工人数也不同。
// 参与挖矿工人的总数是10人。每座金矿要么全挖，要么不挖，不能派出一半人挖取一半金矿。
// 要求用程序求解出，要想得到尽可能多的黄金，应该选择挖取哪几座金矿？
//Math.max(dp[i - 1][j], dp[i - 1][j - person[i]] + gold[i]);
public class MaxGold {

  public static void main(String[] args) {
    //金矿数组
    int[] gold = new int[]{400, 500, 200, 300, 350};
    int[] person = new int[]{5, 5, 3, 4, 3};
    int personCount = 10;

    System.out.println(getMaxGold(gold, person, personCount));
  }

  public static int getMaxGold(int[] gold, int[] person, int personCount) {
    if (gold == null || person == null || gold.length == 0 || person.length == 0) {
      return 0;
    }

    int[][] dp = new int[gold.length][personCount + 1];
    //填充边界
    for (int i = 0; i <= personCount; i++) {
      if (person[0] > i) {
        dp[0][i] = 0;
      } else {
        dp[0][i] = gold[0];
      }
    }

    for (int i = 1; i < gold.length; i++) {
      for (int j = 1; j <= personCount; j++) {
        if (j < person[i]) {
          dp[i][j] = dp[i - 1][j];
        } else {
          //递推式
          dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - person[i]] + gold[i]);
        }
      }
    }

    return dp[gold.length - 1][personCount];
  }
}
