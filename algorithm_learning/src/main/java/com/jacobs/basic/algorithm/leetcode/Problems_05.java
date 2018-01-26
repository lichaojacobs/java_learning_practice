package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.models.ListNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author lichao
 * @date 2017/12/3
 */
public class Problems_05 {

  public static void main(String[] args) {
    //System.out.println(largestRectangleArea(new int[]{2, 1, 5, 6, 6, 4}));
    //1->1->1->2->3
//    ListNode head = new ListNode(1);
//    head.next = new ListNode(1);
//    System.out.println(deleteDuplicates_075(head));
    System.out.println(combine_080(4, 2));
  }

  /**
   * 题目：数组中出现次数超过一半的数字
   *
   * 在遍历数组的过程中，保存两个值，一个是数组中数字，一个是出现次数。当遍历到下一个数字时，
   *
   * 如果这个数字跟之前保存的数字相同，则次数加1，如果不同，则次数减1。
   *
   * 如果次数为0，则保存下一个数字并把次数设置为1，由于我们要找的数字出现的次数比其他所有数字出现的次数之和还要多，
   *
   * 那么要找的数字肯定是最后一次把次数设为1时对应的数字。
   *
   * @param nums 数组
   * @return 返回出现超过一半的数字
   */
  public int moreHalf(int[] nums) {
    if (nums == null || nums.length == 0) {
      return -1;
    }

    int count = 1;
    int result = nums[0];
    for (int i = 1; i < nums.length; i++) {
      if (count == 0) {
        result = nums[i];
        count = 1;
      }

      if (result == nums[i]) {
        count++;
      } else {
        count--;
      }
    }

    count = 0;
    for (int i = 0; i < nums.length - 1; i++) {
      if (result == nums[i]) {
        count++;
      }
    }

    if (count > nums.length / 2) {
      return result;
    } else {
      return 0;
    }
  }

  /**
   * Given n non-negative integers representing the histogram's bar height where the width of each
   * bar is 1,
   *
   * find the area of largest rectangle in the histogram.
   */
  public static int largestRectangleArea(int[] height) {
    if (height == null || height.length == 0) {
      return 0;
    }

    int n = height.length;
    int maxSize = 0;
    Stack<Integer> stack = new Stack<>();
    //push first element
    stack.push(0);
    for (int i = 0; i < n; i++) {
      if (height[i] >= height[stack.peek()]) {
        stack.push(i);
      } else {
        while (!stack.isEmpty() && height[i] < height[stack.peek()]) {
          int index = stack.pop();
          int tempSize = (i - 1 - (stack.isEmpty() ? (-1) : stack.peek())) * height[index];
          maxSize = Math.max(maxSize, tempSize);
        }
        stack.push(i);
      }
    }

    while (!stack.isEmpty()) {
      int value = height[stack.peek()];
      stack.pop();
      maxSize = Math.max(maxSize, (n - 1 - (stack.isEmpty() ? (-1) : stack.peek())) * value);
    }

    return maxSize;
  }

  /**
   * Given a 2D binary matrix filled with 0's and 1's,
   *
   * find the largest rectangle containing all ones and return its area.
   *
   * 解法:类似于上一题的思路，对于每一行都看作是以那一行为底，temp[col]为从那一行到最上边连续为1的个数
   */
  public static int maximalRectangle(char[][] matrix) {
    if (matrix == null || matrix.length == 0) {
      return 0;
    }

    int m = matrix.length;
    int n = matrix[0].length;
    int maxArea = 0;

    for (int i = 0; i < m; i++) {
      int[] temp = new int[n];
      for (int j = 0; j < n; j++) {
        int r = i;
        while (r >= 0 && matrix[r][j] == '1') {
          temp[j]++;
          r--;
        }
      }

      maxArea = Math.max(maxArea, largestRectangleArea(temp));
    }

    return maxArea;
  }

  //  Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
//  For example,
//  Given1->2->3->3->4->4->5, return1->2->5.
//  Given1->1->1->2->3, return2->3.
  public static ListNode deleteDuplicates_075(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }

    ListNode preNode = new ListNode(0);
    preNode.next = head;
    ListNode aftNode = preNode.next.next;

    while (aftNode != null) {
      if (aftNode.val == preNode.next.val) {
        aftNode = aftNode.next;
        continue;
      }

      if (preNode.next.next == aftNode) {
        preNode = preNode.next;
        aftNode = aftNode.next;
      } else if (preNode.next == head) {
        preNode.next = aftNode;
        head = preNode.next;
      } else {
        preNode.next = aftNode;
      }
    }

    //处理后续
    if (preNode.next.next != null) {
      if (preNode.next == head) {
        preNode.next = aftNode;
        head = preNode.next;
      } else {
        preNode.next = aftNode;
      }
    }
    return head;
  }


  //  Follow up for "Remove Duplicates":
//  What if duplicates are allowed at most twice?
//
//  For example,
//  Given sorted array A =[1,1,1,2,2,3],
//
//  Your function should return length =5, and A is now[1,1,2,2,3].
  public int removeDuplicates_076(int[] A) {
    if (A == null || A.length <= 0) {
      return A == null ? 0 : A.length;
    }

    int count = 2;
    for (int i = 2; i < A.length; i++) {
      if (A[i] != A[count - 2]) {
        A[count] = A[i];
        count += 1;
      }
    }

    return count;
  }


  //  Given a 2D board and a word, find if the word exists in the grid.
//
//  The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.
//
//      For example,
//  Given board =
//
//  [
//      ["ABCE"],
//      ["SFCS"],
//      ["ADEE"]
//      ]
//  word ="ABCCED", -> returnstrue,
//  word ="SEE", -> returnstrue,
//  word ="ABCB", -> returnsfalse.
  //DFS，维护一个数组用来记录已经访问过的路径
  public static boolean exist_077(char[][] board, String word) {
    if (board == null || board.length == 0 || board[0].length == 0 || word == null) {
      return false;
    }

    int row = board.length;
    int col = board[0].length;
    char[] toMatchChars = word.toCharArray();
    boolean[][] isVisited = new boolean[row][col];

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        if (toMatchChars[0] != board[i][j]) {
          continue;
        }
        if (dfs_077(board, toMatchChars, isVisited, i, j, row, col, 0)) {
          return true;
        }
      }
    }

    return false;
  }

  private static boolean dfs_077(char[][] board, char[] toMatchChars, boolean[][] isVisited, int i,
      int j,
      int m, int n,
      int count) {
    if (count == toMatchChars.length) {
      return true;
    }

    if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] != toMatchChars[count]) {
      return false;
    }

    if (isVisited[i][j]) {
      return false;
    }

    isVisited[i][j] = true;
    boolean result = dfs_077(board, toMatchChars, isVisited, i - 1, j, m, n, count + 1) ||
        dfs_077(board, toMatchChars, isVisited, i + 1, j, m, n, count + 1) ||
        dfs_077(board, toMatchChars, isVisited, i, j - 1, m, n, count + 1) ||
        dfs_077(board, toMatchChars, isVisited, i, j + 1, m, n, count + 1);
    isVisited[i][j] = false;
    return result;
  }


  //  Given a set of distinct integers, S, return all possible subsets.
//
//      Note:
//
//  Elements in a subset must be in non-descending order.
//  The solution set must not contain duplicate subsets.
//
//  For example,
//  If S =[1,2,3], a solution is:
  public static ArrayList<ArrayList<Integer>> subsets_078(int[] S) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    getAllSubsets_078(S, 0, resultList);

    return resultList;
  }

  public static void getAllSubsets_078(int[] S, int start,
      ArrayList<ArrayList<Integer>> resultList) {
    if (start == S.length) {
      ArrayList<Integer> result = new ArrayList<>();
      for (int i = 0; i < S.length; i++) {
        result.add(S[i]);
      }
      resultList.add(result);

      return;
    }

    for (int i = start; i < S.length; i++) {
      swap(S, start, i);
      getAllSubsets_078(S, start + 1, resultList);
      swap(S, i, start);
    }
  }

  public static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  //all subsets in non-descending order.
  public static ArrayList<ArrayList<Integer>> subsets_079(int[] S) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    Arrays.sort(S);
    for (int j = 0; j <= S.length; j++) {
      getAllSubsets_079(S, j, 0, resultList, new ArrayList<>());
    }

    return resultList;
  }

  public static void getAllSubsets_079(int[] S, int k, int start,
      ArrayList<ArrayList<Integer>> resultList, List<Integer> list) {
    if (k < 0) {
      return;
    } else if (k == 0) {
      resultList.add(new ArrayList<>(list));
    } else {
      for (int i = start; i < S.length; i++) {
        list.add(S[i]);
        getAllSubsets_079(S, k - 1, i + 1, resultList, list);
        list.remove(list.size() - 1);
      }
    }
  }


  //  Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
//
//      For example,
//  If n = 4 and k = 2, a solution is:
//
//      [
//      [2,4],
//      [3,4],
//      [2,3],
//      [1,2],
//      [1,3],
//      [1,4],
//      ]
  public static ArrayList<ArrayList<Integer>> combine_080(int n, int k) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    getAllCombineList(1, n, k, resultList, new ArrayList<>());

    return resultList;
  }

  public static void getAllCombineList(int start, int n, int k,
      ArrayList<ArrayList<Integer>> resultList,
      ArrayList<Integer> list) {
    if (k == 0) {
      resultList.add(new ArrayList<>(list));
      return;
    }

    for (int i = start; i <= n; i++) {
      list.add(i);
      getAllCombineList(i + 1, n, k - 1, resultList, list);
      list.remove(list.size() - 1);
    }
  }


  //  Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
//
//  For example,
//      S ="ADOBECODEBANC"
//  T ="ABC"
//
//  Minimum window is"BANC".
//
//  Note:
//  If there is no such window in S that covers all characters in T, return the emtpy string"".
//
//  If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S
  public static String minWindow(String S, String T) {
    if (S == null || S.equals("") || T == null || T.equals("")) {
      return "";
    }

    int[] map = new int[128];
    //int map，记录T中每个元素出现的次数
    for (int i = 0; i < T.length(); i++) {
      map[T.charAt(i)]++;
    }

    int begin = 0, end = 0, d = Integer.MAX_VALUE, counter = T.length(), head = 0;
    while (end < S.length()) {
      if (map[S.charAt(end++)]-- > 0) {
        counter--;
      }
      //当counter==0，说明窗口包含了T中所有的元素
      while (counter == 0) {
        if (end - begin < d) {
          d = end - (head = begin);
        }

        if (map[S.charAt(begin++)]++ == 0) {
          counter++;
        }
      }
    }

    return d == Integer.MAX_VALUE ? "" : S.substring(head, head + d);
  }
}
