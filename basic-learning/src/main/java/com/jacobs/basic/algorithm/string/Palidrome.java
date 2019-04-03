package com.jacobs.basic.algorithm.string;

import com.jacobs.basic.algorithm.TreeNode;
import java.util.Stack;

/**
 * Created by lichao on 2017/1/8.
 */
public class Palidrome {

  public static void main(String[] args) {

  }


  /**
   * 求最长的回文子串
   */
  public static String getLongestPalindromeStr(String s) {
    if (s == null || s == "") {
      return null;
    }

    char[] ch = s.toCharArray();
    String str = "";
    String re = "";

    for (int i = 0; i < ch.length; i++) {
      re = getEvery(ch, i, i);//当以一个字符串为中心，也就是回文串为奇数的时候
      if (re.length() > str.length()) {
        str = re;
      }
      re = getEvery(ch, i, i + 1);
      if (re.length() > str.length()) {
        str = re;
      }
    }

    return str;
  }

  public static String getEvery(char[] ch, int i, int j) {
    int length = ch.length;
    while (i >= 0 && j <= length - 1 && ch[i] == ch[j]) {
      i--;
      j++;
    }
    return String.valueOf(ch)
        .substring(i + 1, j);// 不可以用ch.toString()，这个方法返回的不是字符数组对应的字符串，而是一个内存地址，
    // 且此处无论是前面边界退出还是两端值不等退出都应该将ij的值回退，所以sub这两端。
  }


  /**
   * 利用栈实现,考虑到奇偶数的情况,使用俩个指针不同速率
   */
  public boolean isPalidrome1(TreeNode head) {
    if (head == null) {
      return false;
    }
    if (head.next == null) {
      return true;
    }

    Stack<TreeNode> treeNodeStack = new Stack<>();
    TreeNode rightSide = head.next;
    TreeNode curr = head;
    while (curr.next != null && curr.next.next != null) {
      curr = curr.next.next;
      rightSide = rightSide.next;
    }

    while (rightSide != null) {
      treeNodeStack.push(rightSide);
      rightSide = rightSide.next;
    }

    while (!treeNodeStack.isEmpty()) {
      if (head.val != treeNodeStack.pop().val) {
        return false;
      }
      head = head.next;
    }

    return true;
  }
}
