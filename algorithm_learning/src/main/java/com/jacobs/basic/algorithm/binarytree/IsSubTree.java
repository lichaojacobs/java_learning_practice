package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;

/**
 * Created by lichao on 2017/9/11. 判断t1树完全包含t2树的拓扑结构
 */
public class IsSubTree {

  public static void main(String[] args) {

  }

  public static boolean isSubTree(TreeNode t1, TreeNode t2) {
    String t1Str = serialByPre(t1);
    String t2Str = serialByPre(t2);

    return getIndexOf(t1Str, t2Str) != -1;
  }

  public static String serialByPre(TreeNode head) {
    if (head == null) {
      return "#!";
    }

    String res = head.val + "!";
    res += serialByPre(head.left);
    res += serialByPre(head.right);

    return res;
  }

  /**
   * KMP 算法 关键在于计算next数组
   */
  public static int getIndexOf(String s, String m) {
    if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
      return -1;
    }

    char[] ss = s.toCharArray();
    char[] ms = m.toCharArray();
    int si = 0;
    int mi = 0;
    int[] next = getNextArray(ms);
    while (si < ss.length && mi < ms.length) {
      if (ss[si] == ms[mi]) {
        si++;
        mi++;
      } else if (next[mi] == -1) {
        si++;
      } else {
        mi = next[mi];
      }
    }

    return mi == ms.length ? si - mi : -1;
  }

  /**
   * next数组其实就是除了匹配到的当前字符，前面的字符串的最大长度的前缀后缀
   */
  public static int[] getNextArray(char[] ms) {
    if (ms.length == 1) {
      return new int[]{-1};
    }

    int[] next = new int[ms.length];
    next[0] = -1;

    int pre = 0;
    int aft = 2;
    while (aft < ms.length) {
      if (ms[aft - 1] == ms[pre]) {
        pre++;
        aft++;
        next[aft] = pre;
      } else if (pre > 0) {
        //没有更长的相同前缀后缀，咱们可以寻找长度短点的相同前缀后缀，依次递减，直到找到相等的情况，或者pre降到0都没有发现
        pre = next[pre];
      } else {
        next[aft++] = 0;
      }
    }

    return next;
  }
}
