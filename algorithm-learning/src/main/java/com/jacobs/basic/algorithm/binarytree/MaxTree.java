package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by lichao on 2016/10/14.
 */
public class MaxTree {
  public static void main(String[] args) {
  }

  public TreeNode getMaxTree(int[] arr) {
    TreeNode[] nArr = new TreeNode[arr.length];
    for (int i = 0; i != arr.length; i++) {
      nArr[i] = new TreeNode(arr[i]);
    }
    Stack<TreeNode> stack = new Stack<>();
    HashMap<TreeNode, TreeNode> lBigMap = new HashMap<>();
    HashMap<TreeNode, TreeNode> rBigMap = new HashMap<>();
    //右边
    for (int i = 0; i != nArr.length; i++) {
      TreeNode curTreeNode = nArr[i];
      while (!stack.isEmpty() && stack.peek().val < curTreeNode.val) {
        popStackSetMap(stack, lBigMap);
      }
      stack.push(curTreeNode);
    }
    //最大数
    while (!stack.isEmpty()) {
      popStackSetMap(stack, lBigMap);
    }
    //右边
    for (int i = nArr.length - 1; i != -1; i--) {
      TreeNode curTreeNode = nArr[i];
      while (!stack.isEmpty() && stack.peek().val < curTreeNode.val) {
        popStackSetMap(stack, lBigMap);
      }
      stack.push(curTreeNode);
    }
    while (!stack.isEmpty()) {
      popStackSetMap(stack, rBigMap);
    }
    //开始构造
    TreeNode head = null;
    for (int i = 0; i < nArr.length; i++) {
      TreeNode curTreeNode = nArr[i];
      TreeNode left = lBigMap.get(curTreeNode);
      TreeNode right = rBigMap.get(curTreeNode);
      if (left == null && right == null) {
        head = curTreeNode;
      } else if (left == null) {
        if (right.left == null) {
          right.left = curTreeNode;
        } else {
          right.right = curTreeNode;
        }
      } else if (right == null) {
        if (left.left == null) {
          left.left = curTreeNode;
        } else {
          left.right = curTreeNode;
        }
      } else {
        TreeNode parent = left.val < right.val ? left : right;
        if (parent.left == null) {
          parent.left = curTreeNode;
        } else {
          parent.right = curTreeNode;
        }
      }
    }
    return head;
  }

  public void popStackSetMap(Stack<TreeNode> stack, HashMap<TreeNode, TreeNode> map) {
    TreeNode popTreeNode = stack.pop();
    if (stack.isEmpty()) {
      map.put(popTreeNode, null);
    } else {
      map.put(popTreeNode, stack.peek());
    }
  }
}

