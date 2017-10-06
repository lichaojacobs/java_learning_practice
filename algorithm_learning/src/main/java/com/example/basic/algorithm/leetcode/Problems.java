package com.example.basic.algorithm.leetcode;

import com.example.basic.ListNode;
import java.util.Stack;

/**
 * Created by lichao on 2017/10/6.
 */
public class Problems {

  public static void main(String[] args) {
//    String[] tokens = new String[]{"0", "3", "/"};
//    System.out.println(evalRPN_01(tokens));

    ListNode root = new ListNode(1);
    root.next = new ListNode(2);
    System.out.println(hasCycle_02(root));
  }


  //  Evaluate the value of an arithmetic expression in Reverse Polish Notation.
//
//  Valid operators are +, -, *, /. Each operand may be an integer or another expression.
//
//  Some examples:
//
//      ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
//      ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
  public static int evalRPN_01(String[] tokens) {
    if (tokens == null || tokens.length == 0) {
      return -1;//非法值
    }

    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < tokens.length; i++) {
      if (!tokens[i].equals("+")
          && !tokens[i].equals("-")
          && !tokens[i].equals("*")
          && !tokens[i].equals("/")) {
        stack.push(Integer.valueOf(tokens[i]));
      } else {
        int n = stack.pop();
        int m = stack.pop();

        if (tokens[i].equals("+")) {
          stack.push(m + n);
        }

        if (tokens[i].equals("-")) {
          stack.push(m - n);
        }

        if (tokens[i].equals("*")) {
          stack.push(m * n);
        }

        if (tokens[i].equals("/")) {
          stack.push(m / n);
        }

      }
    }

    return stack.pop();
  }

  //  Given a linked list, determine if it has a cycle in it.
//  Follow up:
//  Can you solve it without using extra space?
  public static boolean hasCycle_02(ListNode head) {
    if (head == null) {
      return false;
    }

    ListNode pre = head;
    ListNode aft = head;

    while (aft != null && aft.next != null) {
      pre = pre.next;
      aft = aft.next.next;

      if (pre == aft) {
        return true;
      }
    }

    return false;
  }
}
