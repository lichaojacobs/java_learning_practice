package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/10/3. 龙与地下城游戏的问题
 */
//给定一个二维数组map，含义是一张地图
//左程云 Page 233
public class MinHP {

  public static void main(String[] args) {

  }

  //从后向前推的动态规划
  public static int minHP1(int[][] map) {
    if (map == null || map.length == 0 || map[0] == null || map[0].length == 0) {
      //最低血量要求为1
      return 1;
    }

    int row = map.length;
    int col = map[0].length;
    int[][] dp = new int[row--][col--];
    dp[row][col] = map[row][col] > 0 ? 1 : 1 - map[row][col];

    for (int j = col - 1; j >= 0; j--) {
      dp[row][j] = Math.max((dp[row][j + 1] - map[row][j]), 1);
    }

    for (int i = row - 1; i >= 0; i--) {
      dp[i][col] = Math.max((dp[i + 1][col] - map[i][col]), 1);
      for (int j = col - 1; j >= 0; j--) {
        //两条路径
        int right = Math.max((dp[i][j + 1] - map[i][j]), 1);
        int down = Math.max((dp[i + 1][j] - map[i][j]), 1);
        dp[i][j] = Math.min(right, down);
      }
    }

    return dp[0][0];
  }

  //空间压缩
  public static int minHP2(int[][] map) {
    if (map == null || map.length == 0 || map[0] == null || map[0].length == 0) {
      //最低血量要求为1
      return 1;
    }

    int more = Math.max(map.length, map[0].length);
    int less = Math.min(map.length, map[0].length);
    int[] dp = new int[less];
    boolean rowMore = more == map.length ? true : false;
    int tmp = map[map.length - 1][map[0].length - 1];
    dp[less - 1] = tmp > 0 ? 1 : 1 - tmp;

    int row = 0;
    int col = 0;
    for (int j = less - 2; j >= 0; j--) {
      row = rowMore ? more - 1 : j;
      col = rowMore ? j : more - 1;
      dp[j] = Math.max((dp[j + 1] - map[row][col]), 1);
    }

    int choosen1 = 0;
    int choosen2 = 0;
    for (int i = more - 2; i >= 0; i--) {
      row = rowMore ? i : less - 1;
      col = rowMore ? less - 1 : i;
      dp[less - 1] = Math.max(dp[less - 1] - map[row][col], 1);
      for (int j = less - 2; j >= 0; j--) {
        row = rowMore ? i : j;
        col = rowMore ? j : i;
        choosen1 = Math.max(dp[j] - map[row][col], 1);
        choosen2 = Math.max(dp[j + 1] - map[row][col], 1);
        dp[j] = Math.max(choosen1, choosen2);
      }
    }

    return dp[0];
  }
}
