package com.example.algorithm;

/**
 * Created by lichao on 2016/11/2.
 */
public class ChaperForLinkedList {
  public static void main(String[] args) {

  }

  /**
   * 打印俩个有序链表的公共部分
   */
  public static void printCommonPart(Node head1, Node head2) {
    System.out.println("Common part: ");
    while (head1 != null && head2 != null) {
      if (head1.value < head2.value) {
        head1 = head1.next;
      } else if (head1.value > head2.value) {
        head2 = head2.next;
      } else {
        System.out.println(head1.value + " ");
        head1 = head1.next;
        head2 = head2.next;
      }
    }
    System.out.println();
  }

  /**
   * 链表和双链表中删除倒数第K个节点
   */
  public static Node deleteLastKNode(Node head, int lastKth) {
    if (head == null || head.next == null) {
      return head;
    }
    Node pre = head;
    Node aft = head;
    int index = 1;
    while (pre.next != null) {
      pre = pre.next;
      if (index < lastKth) {
        index++;
      } else {
        aft = aft.next;
      }
    }
    if (pre.next == null && aft.next != null) {
      aft = aft.next.next;
    }

    return head;
  }

  /**
   * 删除中间代码
   */
  public static Node removeMiddleNode(Node head) {
    if (head == null || head.next == null) {
      return head;
    }
    if (head.next.next == null) {
      return head.next;
    }
    Node pre = head;
    Node aft = head.next.next;
    while (aft.next != null && aft.next.next != null) {
      pre = pre.next;
      aft = aft.next.next;
    }
    pre.next = pre.next.next;
    return head;
  }

  /**
   * 删除a/b处的节点。
   */
  public static Node removeByRatio(Node head, int a, int b) {
    if (a < 1 || a > b) {
      return head;
    }
    int n = 0;
    Node cur = head;
    while (cur != null) {
      n++;
      cur = cur.next;
    }
    n = (int) Math.ceil(((double) (a * n)) / (double) b);
    if (n == 1) {
      head = head.next;
    }
    if (n > 1) {
      cur = head;
      while (--n > 0) {
        cur = cur.next;
      }
      cur.next = cur.next.next;
    }

    return head;
  }
}
