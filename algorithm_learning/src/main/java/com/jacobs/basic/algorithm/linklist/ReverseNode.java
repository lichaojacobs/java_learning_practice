package com.jacobs.basic.algorithm.linklist;

import com.jacobs.basic.algorithm.TreeNode;

/**
 * Created by lichao on 2016/12/4.
 */
public class ReverseNode {

  public static void main(String[] args) {
    TreeNode treeNode1 = new TreeNode(1);
    treeNode1.next = new TreeNode(2);
    treeNode1.next.next = new TreeNode(3);
    treeNode1.next.next.next = new TreeNode(4);
    treeNode1.next.next.next.next = new TreeNode(5);
    //TreeNode head = reversePartNodeList(treeNode1, 2, 4);
    TreeNode head = reverserNodeList2(treeNode1);
    while (head != null) {
      System.out.println(head.val);
      head = head.next;
    }
  }

  public static TreeNode reverseNodeList(TreeNode head) {
    if (head == null) {
      return null;
    }

    TreeNode pre = head;
    TreeNode fir = head;
    TreeNode aft = head;

    while (fir.next != null) {
      aft = fir.next;
      fir.next = aft.next;
      aft.next = pre;
      pre = aft;
    }

    return pre;
  }

  //假设result是前面已经排好逆序的头节点
  public static TreeNode reverserNodeList2(TreeNode head) {
    if (head == null) {
      return null;
    }

    TreeNode result = null;

    while (head != null) {
      TreeNode temp = head.next;
      head.next = result;
      result = head;
      head = temp;
    }

    return result;
  }

  public static TreeNode reversePartNodeList(TreeNode head, int from, int to) {
    int len = 0;
    TreeNode phead = head;
    TreeNode fromTreeNode = null;
    TreeNode toTreeNode = null;

    while (phead != null) {
      len++;
      fromTreeNode = len == from - 1 ? phead : fromTreeNode;
      toTreeNode = len == to + 1 ? phead : toTreeNode;
      phead = phead.next;
    }

    if (from > to || from < 1 || to > len) {
      return head;
    }

    phead = fromTreeNode == null ? head : fromTreeNode.next;
    TreeNode fir = phead;
    TreeNode aft;
    while (fir.next != toTreeNode) {
      aft = fir.next;
      fir.next = aft.next;
      aft.next = phead;
      phead = aft;
    }
    if (fromTreeNode != null) {
      fromTreeNode.next = phead;
      return head;
    }

    return phead;
  }

}
