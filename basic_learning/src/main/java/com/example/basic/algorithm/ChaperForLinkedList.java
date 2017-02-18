package com.example.basic.algorithm;

/**
 * Created by lichao on 2016/11/2.
 */
public class ChaperForLinkedList {
  //  public static void main(String[] args) {
  //
  //  }

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


  /**
   * Given a singly linked list L: L0→L1→…→Ln-1→Ln,
   * reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
   * You must do this in-place without altering the nodes' values.
   * For example,
   * Given{1,2,3,4}, reorder it to{1,4,2,3}.
   * 解题思路：快慢指针寻找到中间节点（使得前半段大于等于后半段，然后后半段逆序，再合并）
   */
  public void reorderList(Node head) {
    if (head == null || head.next == null) {
      return;
    }
    Node slow = head;
    Node fast = head;
    while (fast.next != null && fast.next.next != null) {
      fast = fast.next.next;
      slow = slow.next;
    }

    slow.next = reverse(slow.next);
    merge(head, slow.next);
  }

  public Node reverse(Node head) {
    if (head == null || head.next == null) {
      return head;
    }
    Node pre = null;
    Node next = null;
    while (head != null) {
      next = head.next;
      head.next = pre;
      pre = head;
      head = next;
    }

    return pre;
  }

  public void merge(Node head, Node aft) {
    Node p = head;
    Node q = aft.next;
    while (p != null && q != null) {
      aft.next = q.next;
      q.next = p.next;
      p.next = q;
      p = p.next.next;
      q = aft.next;
    }
  }
}
