package com.jacobs.basic.algorithm.array;

import com.jacobs.basic.algorithm.TreeNode;

/**
 * 将单向链表按某值划分成左边小，中间相等，右边大的形式 Created by lichao on 2017/1/8.
 */
public class ListPartition {
//  public static void main(String[] args) {
//
//  }

  public static TreeNode listPartition1(TreeNode head, int pivot) {
    if (head == null) {
      return head;
    }
    TreeNode curr = head;
    int i = 0;
    while (curr != null) {
      i++;
      curr = curr.next;
    }
    TreeNode[] treeNodeArray = new TreeNode[i];
    curr = head;
    for (i = 0; i != treeNodeArray.length; i++) {
      treeNodeArray[i] = curr;
      curr = curr.next;
    }
    arrPartition(treeNodeArray, pivot);
    for (i = 1; i != treeNodeArray.length; i++) {
      treeNodeArray[i - 1].next = treeNodeArray[i];
    }
    treeNodeArray[i - 1].next = null;
    return treeNodeArray[0];
  }

  public static void arrPartition(TreeNode[] treeNodeArray, int pivot) {
    int small = -1;
    int big = treeNodeArray.length;
    int index = 0;
    while (index != big) {
      if (treeNodeArray[index].val < pivot) {
        swap(treeNodeArray, ++small, index++);
      } else if (treeNodeArray[index].val == pivot) {
        index++;
      } else {
        swap(treeNodeArray, --big, index);
      }
    }
  }

  public static void swap(TreeNode[] treeNodeArr, int a, int b) {
    TreeNode temp = treeNodeArr[a];
    treeNodeArr[a] = treeNodeArr[b];
    treeNodeArr[b] = temp;
  }
}
