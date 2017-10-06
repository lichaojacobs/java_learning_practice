package com.example.basic.algorithm.binarytree;

import com.example.basic.algorithm.Node;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by lichao on 2017/10/6.
 */
//二叉树中和为某一定值的路径
public class FindPath {

  public static void main(String[] args) {
    Node testHead = new Node(6);
    testHead.left = new Node(1);
    testHead.right = new Node(12);
    testHead.left.left = new Node(1);
    testHead.left.right = new Node(3);
    testHead.right.left = new Node(10);
    testHead.right.right = new Node(13);

    findPath(testHead, 8, new Stack<>(), 0);
  }


  public static void findPath(Node root, int expectedSum, Stack<Node> currentPath, int currSum) {
    if (root == null) {
      return;
    }

    currSum += root.value;
    currentPath.push(root);

    //如果是叶子节点
    if ((root.left == null && root.right == null) && currSum == expectedSum) {
      //打印路径
      Iterator<Node> stackIterator = currentPath.iterator();
      System.out.println("find path: ");
      while (stackIterator.hasNext()) {
        System.out.print(stackIterator.next().value + " ");
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
