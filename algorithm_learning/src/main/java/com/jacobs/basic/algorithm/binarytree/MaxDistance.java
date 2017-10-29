package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;

/**
 * Created by lichao on 2017/9/19. 二叉树节点间的最大距离问题
 *
 * 一个以h为头的树上，最大距离只可能来自以下三种情况:
 *
 * 1)h的左子树上的最大距离 2)h的右子树上的最大距离 3)h左子树离h.left最远的距离+1(h)+h右子树上离h.right最远的距离
 */
public class MaxDistance {

  public static void main(String[] args) {

  }

  public static int getMaxDistance(TreeNode head) {
    int[] record = new int[1];
    return posOrder(head, record);
  }

  public static int posOrder(TreeNode head, int[] record) {
    if (head == null) {
      record[0] = 0;
      return 0;
    }

    int lMax = posOrder(head.left, record);
    int maxFromLeft = record[0];

    int rMax = posOrder(head.right, record);
    int maxFromRight = record[0];//右子树最大距离

    int curNodeMax = maxFromLeft + maxFromRight + 1; //跨节点距离
    record[0] = Math.max(maxFromLeft, maxFromRight) + 1;//距离当前头节点最远的距离,作为上一层的左／右最大距离

    return Math.max(Math.max(lMax, rMax), curNodeMax);
  }
}
