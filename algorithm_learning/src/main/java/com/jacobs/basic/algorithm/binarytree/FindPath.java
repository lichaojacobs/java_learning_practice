package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by lichao on 2017/10/6.
 */
//二叉树中和为某一定值的路径
public class FindPath {

  public static void main(String[] args) {
    TreeNode testHead = new TreeNode(6);
    testHead.left = new TreeNode(1);
    testHead.right = new TreeNode(12);
    testHead.left.left = new TreeNode(1);
    testHead.left.right = new TreeNode(3);
    testHead.right.left = new TreeNode(10);
    testHead.right.right = new TreeNode(13);

    findPath(testHead, 8, new Stack<>(), 0);
  }

  /**
   * 先序遍历
   *
   * @param root 跟节点
   * @param expectedSum 期望的累加和
   * @param currentPath 目前的路径
   * @param currSum 目前路径的累加和
   */
  public static void findPath(TreeNode root, int expectedSum, Stack<TreeNode> currentPath,
      int currSum) {
    if (root == null) {
      return;
    }

    currSum += root.val;
    currentPath.push(root);

    //如果是叶子节点
    if ((root.left == null && root.right == null) && currSum == expectedSum) {
      //打印路径
      Iterator<TreeNode> stackIterator = currentPath.iterator();
      System.out.println("find path: ");
      while (stackIterator.hasNext()) {
        System.out.print(stackIterator.next().val + " ");
      }
    }

    if (root.left != null) {
      findPath(root.left, expectedSum, currentPath, currSum);
    }

    if (root.right != null) {
      findPath(root.right, expectedSum, currentPath, currSum);
    }

    //返回父级的时候弹出自己的节点，防止计算路径的时候带入
    currentPath.pop();
  }
}
