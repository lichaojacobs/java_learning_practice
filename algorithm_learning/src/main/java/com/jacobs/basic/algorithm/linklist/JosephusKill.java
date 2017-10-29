package com.jacobs.basic.algorithm.linklist;

import com.jacobs.basic.algorithm.TreeNode;

/**
 * Created by lichao on 2017/1/7.
 * 约瑟夫环问题
 */
public class JosephusKill {
  public static void main(String[] args) {

  }

  public static TreeNode josephusKill1(TreeNode head, int m) {
    if (head == null || head.next == head || m < 1) {
      return head;
    }

    TreeNode last = head;
    while (last.next != head) {
      last = last.next;
    }
    int count = 1;
    while (head != last) {
      if (++count != m) {
        last = last.next;
      } else {
        last.next = head.next;
        count = 1;
      }
      head = last.next;
    }
    return head;
  }

  /**
   * B=(A-1)%i+1
   * old=(new+s-1)%i+1
   * s=(m-1)%i+1
   * old=(new+m-1)%i+1
   */
  public static TreeNode josephusKill2(TreeNode head, int m) {
    if (head == null || head.next == head || m < 1) {
      return head;
    }

    TreeNode cur = head.next;
    int temp = 1;
    while (cur != head) {
      temp++;
      cur = cur.next;
    }
    temp = getLive(temp, m);
    while (--temp != 0) {
      head = head.next;
    }
    return head.next;
  }

  public static int getLive(int i, int m) {
    if (i == 1) {
      return 1;
    }

    return (getLive(i - 1, m) + m - i) % i + 1;
  }
}
