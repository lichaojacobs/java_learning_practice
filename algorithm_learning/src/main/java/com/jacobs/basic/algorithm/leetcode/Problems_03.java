package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.algorithm.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author lichao
 * @date 2017/11/1
 */
public class Problems_03 {

  public static void main(String[] args) {
    TreeNode root = new TreeNode(5);
    root.left = new TreeNode(4);
    root.right = new TreeNode(8);
    root.left.left = new TreeNode(11);
    root.left.left.left = new TreeNode(7);
    root.left.left.right = new TreeNode(2);
    root.right.left = new TreeNode(13);
    root.right.right = new TreeNode(4);
    root.right.right.left = new TreeNode(5);
    root.right.right.right = new TreeNode(1);

    System.out.println(pathSum_033(root, 22));
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


  //  Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
//  For example:
//  Given the below binary tree andsum = 22,
//       5
//      / \
//      4   8
//      /   / \
//      11  13  4
//      /  \      \
//      7    2      1
//      return true, as there exist a root-to-leaf path5->4->11->2which sum is 22.
  public static boolean hasPathSum_032(TreeNode root, int sum) {
    if (root == null) {
      return false;
    }

    if (sum == root.val && root.left == null && root.right == null) {
      return true;
    }

    return hasPathSum_032(root.left, sum - root.val) ||
        hasPathSum_032(root.right, sum - root.val);
  }

  //  return
//      [
//      [5,4,11,2],
//      [5,8,4,5]
//      ]
  public static ArrayList<ArrayList<Integer>> pathSum_033(TreeNode root, int sum) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    getPathSumResult(resultList, root, sum, new ArrayList<>());
    return resultList;
  }

  //先序遍历
  public static void getPathSumResult(ArrayList<ArrayList<Integer>> resultList, TreeNode root,
      int sum, List<TreeNode> onePathResult) {
    if (root == null) {
      return;
    }

    if (sum == root.val && root.left == null && root.right == null) {
      onePathResult.add(root);
      ArrayList<Integer> result = new ArrayList<>();
      for (TreeNode node : onePathResult) {
        result.add(node.val);
      }
      //还原
      resultList.add(result);
      onePathResult.remove(root);
      return;
    }

    onePathResult.add(root);
    getPathSumResult(resultList, root.left, sum - root.val, onePathResult);
    getPathSumResult(resultList, root.right, sum - root.val, onePathResult);
    //还原
    onePathResult.remove(root);
  }

  //  Given a binary tree, determine if it is height-balanced.
//      For this problem, a height-balanced binary tree is defined as a binary tree
//  in which the depth of the two subtrees of every node never differ by more than 1.
  //分别计算左右子树的高度
  public static boolean isBalanced_034(TreeNode root) {
    if (root == null) {
      return true;
    }

    int left = getHeight(root.left);
    int right = getHeight(root.right);
    if (Math.abs(left - right) > 1) {
      return false;
    }

    return isBalanced_034(root.left) && isBalanced_034(root.right);
  }

  public static int getHeight(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int left = getHeight(root.left) + 1;
    int right = getHeight(root.right) + 1;
    return left > right ? left : right;
  }
}
