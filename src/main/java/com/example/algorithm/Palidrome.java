package com.example.algorithm;

import java.util.Stack;

/**
 * Created by lichao on 2017/1/8.
 */
public class Palidrome {
  public static void main(String[] args) {

  }

  /**
   * 利用栈实现,考虑到奇偶数的情况,使用俩个指针不同速率
   */
  public boolean isPalidrome1(Node head) {
    if (head == null) {
      return false;
    }
    if (head.next == null) {
      return true;
    }

    Stack<Node> nodeStack = new Stack<>();
    Node rightSide = head.next;
    Node curr = head;
    while (curr.next != null && curr.next.next != null) {
      curr = curr.next.next;
      rightSide = rightSide.next;
    }

    while (rightSide != null) {
      nodeStack.push(rightSide);
      rightSide = rightSide.next;
    }

    while (!nodeStack.isEmpty()) {
      if (head.value != nodeStack.pop().value) {
        return false;
      }
      head = head.next;
    }

    return true;
  }
}
