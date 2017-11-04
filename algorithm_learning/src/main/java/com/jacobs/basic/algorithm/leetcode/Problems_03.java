package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.algorithm.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author lichao
 * @date 2017/11/1
 */
public class Problems_03 {

  public static void main(String[] args) {
//    TreeNode root = new TreeNode(0);
//    root.left = new TreeNode(1);
//    root.right = new TreeNode(2);
//    root.left.left = new TreeNode(3);
//    root.left.right = new TreeNode(4);
//    root.right.left = new TreeNode(5);
//    root.right.right = new TreeNode(6);
//    connect_029(root);
    numDistinct_030("b", "b");
  }

  //  Given a binary tree
//  struct TreeLinkNode {
//    TreeLinkNode *left;
//    TreeLinkNode *right;
//    TreeLinkNode *next;
//  }
//
//  Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set toNULL.
//      Initially, all next pointers are set toNULL.
//  Note:
//  You may only use constant extra space.
//  You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
//  For example,
//  Given the following perfect binary tree,
//       1
//      /  \
//      2    3
//      / \  / \
//      4  5  6  7
//
//  After calling your function, the tree should look like:
//      1 -> NULL
//     /  \
//    2 -> 3 -> NULL
//  / \  / \
// 4->5->6->7 -> NULL
  public static void connect_029(TreeNode root) {
    if (root == null) {
      return;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    //只需要last 和 nlast即可
    TreeNode currentLevelNode = root;
    TreeNode nextLevelNode = root;
    TreeNode lastNode = null;
    while (!queue.isEmpty()) {
      TreeNode temp = queue.poll();
      if (lastNode != null) {
        lastNode.next = temp;
      }
      if (temp == currentLevelNode) {
        temp.next = null;
        lastNode = null;
      } else {
        lastNode = temp;
      }

      if (temp.left != null) {
        nextLevelNode = temp.left;
        queue.add(temp.left);
      }
      if (temp.right != null) {
        nextLevelNode = temp.right;
        queue.add(temp.right);
      }

      //该换行了
      if (temp == currentLevelNode) {
        currentLevelNode = nextLevelNode;
      }
    }
  }


  //  Given a string S and a string T, count the number of distinct subsequences of T in S.
//  A subsequence of a string is a new string which is formed from the original string by
//  deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters.
//  (ie,"ACE"is a subsequence of"ABCDE"while"AEC"is not).
//  Here is an example:
//  S ="rabbbit", T ="rabbit"
//  Return3.
  /*
   *  思路：dp题。
   *  状态定义：dp[i][j]代表s[0~i-1]中T[0~j-1]不同子串的个数。
   *  递推关系式：S[i-1]!= T[j-1]：  DP[i][j] = DP[i][j-1] （不选择S中的s[i-1]字符）
   *              S[i-1]==T[j-1]： DP[i][j] = DP[i-1][j-1]（选择S中的s[i-1]字符） + DP[i][j-1]
   *  初始状态：第0列：DP[i][0] = 0，第0行：DP[0][j] = 1
   */
  public static int numDistinct_030(String S, String T) {
    if (S == null || T == null) {
      return 0;
    }

    char[] sChars = S.toCharArray();
    char[] tChars = T.toCharArray();
    int row = sChars.length + 1;
    int col = tChars.length + 1;
    int[][] dp = new int[row][col];

    for (int i = 1; i < col; i++) {
      dp[0][i] = 0;
    }

    for (int j = 0; j < row; j++) {
      dp[j][0] = 1;
    }

    for (int i = 1; i < row; i++) {
      for (int j = 1; j < col; j++) {
        if (sChars[i - 1] == tChars[j - 1]) {
          dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
        } else {
          dp[i][j] = dp[i - 1][j];
        }
      }
    }

    return dp[row - 1][col - 1];
  }

  //空间压缩法
  public static int numDistinct_031(String S, String T) {
    return 0;
  }
}
