package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;

/**
 * Created by lichao on 2017/2/16.
 * 给定一颗二叉树头节点head，按照如下俩种标准分别实现二叉树边界节点的逆时针打印
 * 标准一：
 * 头节点为便节点
 * 叶节点为边界点
 * 每层最左或最右亦为边界点
 * 标准二：
 * 头节点为边界点
 * 叶节点为便节点
 * 树左边界延伸下去的路径为边界节点
 * 树右边延伸下去的路径为边界节点。
 */
public class PrintEdge {
  public static void main(String[] args) {

  }

  /**
   * 第一种标准
   */
  public static void printEdge1(TreeNode head) {
    if (head == null) {
      return;
    }
    int height = getHeight(head);
    TreeNode[][] edgeMap = new TreeNode[height][2];
    setEdgeMap(head, 0, edgeMap);
    //打印左边界
    for (int i = 0; i != edgeMap.length; i++) {
      System.out.println(edgeMap[i][0].val + " ");
    }

    //打印叶子节点，且不是最左或者最右
    printLeafNotInMap(head, 0, edgeMap);

    //打印右边界
    for (int i = edgeMap.length - 1; i != -1; i--) {
      if (edgeMap[i][0] != edgeMap[i][1]) {
        System.out.println(edgeMap[i][1].val + " ");
      }
    }
  }

  /**
   * 标准二
   */
  public static void printEdge2(TreeNode head) {
    if (head == null) {
      return;
    }
    System.out.println(head.val + " ");

    if (head.left != null && head.right != null) {//开始条件
      printLeftEdge(head, true);
      printRightEdge(head, true);
    } else {
      printEdge2(head.left == null ? head.right : head.left);
    }
  }

  public static void printLeftEdge(TreeNode h, boolean print) {
    if (h == null) {
      return;
    }
    if (print || (h.left == null && h.left == null)) {
      System.out.println(h.val + " ");
    }

    printLeftEdge(h.left, print);//紧左打，后面退栈
    printLeftEdge(h.right, print && h.left == null ? true : false);
  }

  public static void printRightEdge(TreeNode h, boolean print) {
    if (h == null) {
      return;
    }
    printRightEdge(h.left, print && h.right == null ? true : false);
    printRightEdge(h.right, print);
    if (print || (h.left == null && h.right == null)) {
      System.out.println(h.val + " ");
    }
  }

  public static int getHeight(TreeNode head) {
    if (head == null) {
      return 1;
    }

    return Math.max(getHeight(head.left), getHeight(head.right)) + 1;
  }


  public static void printLeafNotInMap(TreeNode head, int level, TreeNode[][] edgeMap) {
    if (head == null) {
      return;
    }

    if (head.left == null && head.right == null && head != edgeMap[level][0] &&
        head != edgeMap[level][1]) {
      System.out.println(head.val + " ");
    }

    printLeafNotInMap(head.left, level + 1, edgeMap);
    printLeafNotInMap(head.right, level + 1, edgeMap);
  }

  /**
   * 设置每一层最左和最右节点。
   */
  public static void setEdgeMap(TreeNode h, int level, TreeNode[][] edgeMap) {
    if (h == null) {
      return;
    }

    edgeMap[level][0] = edgeMap[level][0] == null ? h : edgeMap[1][0];
    edgeMap[level][1] = h;
    setEdgeMap(h.left, level + 1, edgeMap);
    setEdgeMap(h.right, level + 1, edgeMap);
  }
}
