package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.Node;

/**
 * Created by lichao on 2017/9/16. (二叉树中找到一个节点的后继节点)
 */
public class GetNextNode {

  public static void main(String[] args) {

  }

  //做不下去的解法
//  public static Node getNextNode(Node node) {
//    if (node == null) {
//      return null;
//    }
//    if (node.left == null && node.right == null) {
//      return node.parent;
//    }
//
//    Node left = getNextNode(node.left);
//    Node right = getNextNode(node.right);
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
  public static Node getNextNode2(Node node) {
    if (node == null) {
      return node;
    }
    if (node.right != null) {
      return getLeftMost(node);
    } else {
      Node parent = node.parent;
      while (parent != null && parent.left != node) {
        node = parent;
        parent = node.parent;
      }
      return parent;
    }
  }

  public static Node getLeftMost(Node node) {
    if (node == null) {
      return node;
    }
    while (node.left != null) {
      node = node.left;
    }

    return node;
  }

}
