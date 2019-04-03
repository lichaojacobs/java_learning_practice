package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lichao on 2017/9/9.
 */
public class PrintZigZag {

  public static void main(String[] args) {

  }

  /**
   * 层打印,同一层的节点必须打印在一行上，并要求输出行号
   */
  public static void printByLevel(TreeNode head) {
    if (head == null) {
      return;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(head);

    TreeNode levelLast = head;
    TreeNode nLast = null;
    int level = 0;

    System.out.println("Level " + (level++) + ": ");
    while (!queue.isEmpty()) {
      TreeNode temp = queue.poll();
      System.out.print(temp.val + " ");
      if (temp.left != null) {
        nLast = temp.left;
        queue.offer(temp.left);
      }
      if (temp.right != null) {
        nLast = temp.right;
        queue.offer(temp.right);
      }

      if (temp == levelLast && !queue.isEmpty()) {
        levelLast = nLast;
        System.out.println("\n Level " + (level++) + ": ");
      }

    }
  }

  /**
   * zigzag层打印（双端队列或者使用两个栈）
   */
  public static void printZigZag(TreeNode head) {
    if (head == null) {
      return;
    }

    Deque<TreeNode> dq = new LinkedList<>();
    int level = 1;
    boolean isRight = true;
    TreeNode last = head;
    TreeNode nLast = null;

    dq.offerFirst(head);
    printLevelAndOrientation(level, isRight);

    while (!dq.isEmpty()) {

      if (isRight) {
        head = dq.pollFirst();
        if (head.left != null) {
          nLast = nLast == null ? head.left : nLast;
          dq.offerLast(head.left);
        }
        if (head.right != null) {
          nLast = nLast == null ? head.right : nLast;
          dq.offerLast(head.right);
        }
      } else {
        head = dq.pollLast();
        if (head.right != null) {
          nLast = nLast == null ? head.right : nLast;
          dq.offerFirst(head.right);
        }
        if (head.left != null) {
          nLast = nLast == null ? head.left : nLast;
          dq.offerFirst(head.left);
        }
      }

      System.out.print(head.val + " ");
      if (head == last && !dq.isEmpty()) {
        isRight = !isRight;
        last = nLast;
        System.out.println();
        printLevelAndOrientation(level++, isRight);
      }
      System.out.println();
    }
  }

  public static void printLevelAndOrientation(int level, boolean lr) {
    System.out.println("Level " + level + " from ");
    System.out.println(lr ? "left to right: " : "right to left: ");
  }

}
