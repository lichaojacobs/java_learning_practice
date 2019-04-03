package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;

/**
 * Created by lichao on 2017/9/16. (二叉树中找到一个节点的后继节点)
 */
public class GetNextNode {

  public static void main(String[] args) {

  }

  //做不下去的解法
//  public static TreeNode getNextNode(TreeNode node) {
//    if (node == null) {
//      return null;
//    }
//    if (node.left == null && node.right == null) {
//      return node.parent;
//    }
//
//    TreeNode left = getNextNode(node.left);
//    TreeNode right = getNextNode(node.right);
//
//    if (left != null && left != node) {
//      return left;
//    }
//    if (right != null && right.parent == node) {
//      return right;
//    }
//
//    if (left == null) {
//      next = getNextNode(node.parent);
//    }
//
//  }

  /**
   * * 可以非递归实现(思维定势，其实根据图总结所有情况思路差不多想出来了，但是为什么会坚持用递归去做。。。)
   */
  public static TreeNode getNextNode2(TreeNode treeNode) {
    if (treeNode == null) {
      return treeNode;
    }
    if (treeNode.right != null) {
      return getLeftMost(treeNode);
    } else {
      TreeNode parent = treeNode.parent;
      while (parent != null && parent.left != treeNode) {
        treeNode = parent;
        parent = treeNode.parent;
      }
      return parent;
    }
  }

  public static TreeNode getLeftMost(TreeNode treeNode) {
    if (treeNode == null) {
      return treeNode;
    }
    while (treeNode.left != null) {
      treeNode = treeNode.left;
    }

    return treeNode;
  }

}
