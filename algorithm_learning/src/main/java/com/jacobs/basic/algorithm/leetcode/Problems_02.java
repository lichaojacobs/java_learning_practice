package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.algorithm.TreeNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by lichao on 2017/10/22.
 */
public class Problems_02 {

  public static void main(String[] args) {
//    HashSet<String> dic = Sets.newHashSet("hot", "dot", "dog", "lot", "log");
//    System.out.println(ladderLength_021("hit", "cog", dic))
    System.out.println(maxProfit_026(new int[]{2, 1, 2, 0, 1}));
  }


  //  Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
//  For example,
//  Given[100, 4, 200, 1, 3, 2],
//  The longest consecutive(连续的) elements sequence is[1, 2, 3, 4]. Return its length:4.
//  Your algorithm should run in O(n) complexity.
  //最简单的办法就是先排好序，然后再遍历一遍，但肯定达不到O(n)级别
  public static int longestConsecutive_018(int[] num) {
    //用哈希表辅助查找，找过的删掉即可
    Set<Integer> numSet = new HashSet<>();
    for (int i : num) {
      numSet.add(i);
    }

    int max = 1;
    for (int cur : num) {
      if (numSet.remove(cur)) {
        int sum = 1;
        int val_less = cur - 1;
        int val_more = cur + 1;

        while (numSet.remove(val_less)) {
          sum++;
          val_less--;
        }

        while (numSet.remove(val_more)) {
          sum++;
          val_more++;
        }
        max = Math.max(max, sum);
      }
    }

    return max;
  }


  //  Given two words (start and end), and a dictionary, find all shortest transformation sequence(s) from start to end, such that:
//  Only one letter can be changed at a time
//  Each intermediate word must exist in the dictionary
//  For example,
//  Given:
//  start ="hit"
//  end ="cog"
//  dict =["hot","dot","dog","lot","log"]
//  Return
//  [
//      ["hit","hot","dot","dog","cog"],
//      ["hit","hot","lot","log","cog"]
//      ]
//  ]
  public ArrayList<ArrayList<String>> findLadders_019(String start, String end,
      HashSet<String> dict) {
    return null;
  }

  //  Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:
//  Only one letter can be changed at a time
//  Each intermediate word must exist in the dictionary
//  For example,
//  Given:
//  start ="hit"
//  end ="cog"
//  dict =["hot","dot","dog","lot","log"]
//  As one shortest transformation is"hit" -> "hot" -> "dot" -> "dog" -> "cog",
//      return its length5.
  //主要思想：广度优先搜索。先构造一个字符串队列，并将start加入队列。1.对队列头字符串做单个字符替换
//每次替换后，2.判断是否和end匹配，如果匹配，返回答案；3.没有匹配，则在字典里面查询是否有“邻居字符串”,
//如果有，则将该字符串加入队列，同时将该字符串从字典里删除。重复1的过程，知道和end匹配。如果最后队列
//为空还未匹配到，则返回0.
  public static int ladderLength_020(String start, String end, HashSet<String> dict) {
    Queue<String> queue = new LinkedList<>();
    HashSet<String> visited = new HashSet<>();
    Queue<Integer> numQueue = new LinkedList<>();

    queue.add(start);
    visited.add(start);
    numQueue.add(1);

    while (!queue.isEmpty()) {
      String temp = queue.remove();
      int nums = numQueue.remove();

      for (int i = 0; i < start.length(); i++) {
        char[] arr = temp.toCharArray();
        for (char c = 'a'; c <= 'z'; ++c) {
          arr[i] = c;
          String next = new String(arr);
          if (next.equals(end)) {
            return nums + 1;
          }
          if (dict.contains(next) && (!visited.contains(next))) {
            queue.add(next);
            visited.add(next);
            numQueue.add(nums + 1);
          }
        }

      }
    }

    return 0;
  }

  public static int ladderLength_021(String start, String end, HashSet<String> dict) {
    return getLength(start, end, 1, dict);
  }

  public static int getLength(String start, String end, int num, HashSet<String> dict) {
    if (start.equals(end)) {
      return num + 1;
    }

    for (int i = 0; i < start.length(); i++) {
      char[] arr = start.toCharArray();
      for (char c = 'a'; c <= 'z'; ++c) {
        arr[i] = c;
        String next = new String(arr);

        if (next.equals(end)) {
          return num;
        }

        if (dict.contains(next)) {
          dict.remove(next);
          return getLength(next, end, num + 1, dict);
        }
      }
    }

    return 0;
  }

  //  Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
//  For example,
//  "A man, a plan, a canal: Panama"is a palindrome.
//  "race a car"is not a palindrome.
  public static boolean isPalindrome_022(String s) {
    if (s == null || s.equals("")) {
      return false;
    }

    char[] chars = s.toCharArray();
    int start = 0;
    int end = chars.length - 1;
    while (start <= end) {

      //奇数，立即返回即可
      if (start == end) {
        return true;
      }

      if (!isAlphanumeric(chars[start])) {
        start++;
        continue;
      }

      if (!isAlphanumeric(chars[end])) {
        end--;
        continue;
      }

      if ((chars[start] != chars[end]) && (Math.abs(chars[start] - chars[end]) != 32)) {
        return false;
      }
      start++;
      end--;
    }

    return true;
  }

  private static boolean isAlphanumeric(char value) {
    if (value >= 'a' && value <= 'z') {
      return true;
    } else if (value >= 'A' && value <= 'Z') {
      return true;
    } else if (value >= 48 && value <= 57) {
      return true;
    }

    return false;
  }


  //  Given a binary tree, find the maximum path sum.
//  The path may start and end at any node in the tree.
//  For example:
//  Given the below binary tree,
//      1
//      / \
//      2   3
//
//  Return6.
  //最大值的来源无非就是三种：左子树，右子树，左子树右子树加上父节点的值
  int maxValue;

  public int maxPathSum_023(TreeNode root) {
    maxValue = Integer.MIN_VALUE;
    getMaxSum(root);

    return maxValue;
  }

  public int getMaxSum(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int left = Math.max(0, getMaxSum(root.left));
    int right = Math.max(0, getMaxSum(root.right));

    //全局的最大值
    maxValue = Math.max(maxValue, left + right + root.value);

    //对于每一层来说下一层对应的最长路径
    return Math.max(left, right) + root.value;
  }


  //  Say you have an array for which the i th element is the price of a given stock on day i.
//  If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock),
//  design an algorithm to find the maximum profit.
  public int maxProfit_024(int[] prices) {
    if (prices == null || prices.length < 2) {
      return 0;
    }

    int minimum = prices[0];
    int maxPro = 0;

    for (int i = 1; i < prices.length; i++) {
      if (prices[i] < minimum) {
        minimum = prices[i];
      } else {
        maxPro = Math.max(maxPro, prices[i] - minimum);
      }
    }

    return maxPro;
  }


  //  Say you have an array for which the i th element is the price of a given stock on day i.
//  Design an algorithm to find the maximum profit.
//  You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times).
//  However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
  //只能先买再出
  public static int maxProfit_025(int[] prices) {
    if (prices == null || prices.length < 2) {
      return 0;
    }

    int maxPro = 0;

    for (int i = 1; i < prices.length; i++) {
      if ((prices[i] - prices[i - 1]) > 0) {
        maxPro = maxPro + prices[i] - prices[i - 1];
      }
    }

    return maxPro;
  }

  //  Say you have an array for which the i th element is the price of a given stock on day i.
//  Design an algorithm to find the maximum profit. You may complete at most two transactions.
//      Note:
//  You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
  // 以空间换时间：以某一个位置 i 为分割，分别计算i 之前和之后的收益。
  // 由于分割后，i之后的值得重新计算，所以想到从后往前计算，这样就不需要每次分割都重新计算了，所以只需要3次遍历即可
  public static int maxProfit_026(int[] prices) {
    if (prices == null || prices.length < 2) {
      return 0;
    }

    int[] preProfits = new int[prices.length];
    int[] aftProfits = new int[prices.length];

    int minmum = prices[0];
    for (int i = 1; i < prices.length; i++) {
      if (prices[i] < minmum) {
        minmum = prices[i];
        preProfits[i] = preProfits[i - 1];
      } else {
        preProfits[i] = Math.max(preProfits[i - 1], prices[i] - minmum);
      }
    }

    int max = prices[prices.length - 1];
    for (int j = prices.length - 2; j >= 0; j--) {
      if (prices[j] > max) {
        max = prices[j];
        aftProfits[j] = aftProfits[j + 1];
      } else {
        aftProfits[j] = Math.max(aftProfits[j + 1], max - prices[j]);
      }
    }

    int maxProfit = 0;
    for (int i = 0; i < prices.length; i++) {
      maxProfit = Math.max(maxProfit, preProfits[i] + aftProfits[i]);
    }

    return maxProfit;
  }
}
