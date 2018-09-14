package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.models.ListNode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author lichao
 * @date 2018/1/1
 */
public class Problems_06 {

  public static void main(String[] args) {
    //sortColors_082(new int[]{2, 0, 0, 1, 2, 0, 1});
    //System.out.println(addBinary_089("1", "111"));
//    Problems_06 problems_06 = new Problems_06();
//    System.out.println(problems_06.getPermutation_094(3, 5));
  }

  //  Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent,
//
//  with the colors in the order red, white and blue.
//
//  Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
  public static void sortColors_082(int[] A) {
    if (A == null || A.length <= 1) {
      return;
    }

    int left = 0, index = 0;
    int right = A.length - 1;

    while (index <= right) {
      if (A[index] == 0) {
        swap(A, index++, left++);
      } else if (A[index] == 1) {
        index++;
      } else if (A[index] == 2) {
        //这时候index不能自加了，因为换过去的可能是0,2需要下次循环中判断
        swap(A, right--, index);
      }
    }
  }

  public static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  //  Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
//
//
//  Integers in each row are sorted from left to right.
//  The first integer of each row is greater than the last integer of the previous row.
//
//  For example,
//
//  Consider the following matrix:
//
//      [
//      [1,   3,  5,  7],
//      [10, 11, 16, 20],
//      [23, 30, 34, 50]
//      ]
  public static boolean searchMatrix_083(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return false;
    }

    int m = 0, n = matrix[0].length - 1;
    while (m >= 0 && m < matrix.length && n >= 0 && n < matrix[0].length) {
      if (target > matrix[m][n]) {
        m++;
      } else if (target < matrix[m][n]) {
        n--;
      } else {
        return true;
      }
    }

    return false;
  }

  //  Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in place.
//
//  click to show follow up.
//
//  Follow up:
//  Did you use extra space?
//  A straight forward solution using O(m n) space is probably a bad idea.
//  A simple improvement uses O(m + n) space, but still not the best solution.
//  Could you devise a constant space solution?
  public static void setZeroes_084(int[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return;
    }

    int m = matrix.length - 1;
    int n = matrix[0].length - 1;

  }


  //  Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)
//
//  You have the following 3 operations permitted on a word:
//
//  a) Insert a character
//  b) Delete a character
//  c) Replace a character
  public int minDistance_083(String word1, String word2) {
    if (word1 == null && word2 == null) {
      return 0;
    }
    if (word1 == null) {
      return word2.length();
    }
    if (word2 == null) {
      return word1.length();
    }

    //dp[i][j]代表由word1的前i个子串变为word2的前j个子串的花费
    int[][] dp = new int[word1.length() + 1][word2.length() + 2];
    dp[0][0] = 0;
    for (int i = 1; i <= word1.length(); i++) {
      dp[i][0] = i;
    }

    for (int j = 1; j <= word2.length(); j++) {
      dp[0][j] = j;
    }

    for (int i = 1; i <= word1.length(); i++) {
      for (int j = 1; j <= word2.length(); j++) {
        //删除插入修改操作中取花费最小的那个
        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
        }
      }
    }

    return dp[word1.length()][word2.length()];
  }


  //  You are climbing a stair case. It takes n steps to reach to the top.
//
//  Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
  public static int climbStairs_084(int n) {
    if (n <= 0) {
      return 0;
    }
    if (n == 1) {
      return 1;
    }

    int[] sum = new int[n + 1];
    sum[1] = 1;

    for (int i = 2; i <= n; i++) {
      sum[i] = sum[i - 1] + sum[i - 2];
    }

    return sum[n];
  }

  //  Implementint sqrt(int x).
//
//  Compute and return the square root of x.
  public static int sqrt_085(int x) {
    if (x == 0) {
      return x;
    }

    int left = 1, right = x;
    while (true) {
      int mid = (left + right) / 2;
      if (mid * mid > x) {
        right = mid - 1;
      } else {
        if (mid + 1 > x / (mid + 1)) {
          return mid;
        }
        left = mid + 1;
      }
    }
  }

  //  words:["This", "is", "an", "example", "of", "text", "justification."]
//  L:16.
//
//  Return the formatted lines as:
//
//      [
//      "This    is    an",
//      "example  of text",
//      "justification.  "
//      ]
  public ArrayList<String> fullJustify_086(String[] words, int L) {
    ArrayList<String> resultStrList = new ArrayList<>();
    if (words == null || words.length == 0 || L < 0) {
      return resultStrList;
    }

    int index = 0;
    while (index < words.length) {
      int len = words[index].length();
      String resultStr = words[index];

      int start = index + 1;
      //找到能加到一行的最多的单词数
      for (; start < words.length && len + words[start].length() + 1 <= L; start++) {
        len += words[start].length() + 1;
      }

      if (start < words.length) {
        //计算有多少空格以及插槽
        int space = L - len;
        int gap = start - index - 1;
        //如果没有插槽，那么就是添加空格就完了
        if (gap == 0) {
          while (resultStr.length() < L) {
            resultStr += " ";
          }
        } else {
          for (int p = index + 1; p < start; p++) {
            resultStr += " ";
            //除了原本的空格外，每个单词后面还可以添加的空格数量为space/gap
            for (int q = 0; q < space / gap; q++) {
              resultStr += " ";
            }
            //不足以每个插槽都添加空格，那就在靠近前面的插槽添加
            if (p - index <= space % gap) {
              resultStr += " ";
            }
            resultStr += words[p];
          }
        }
      } else {
        //处理最后一行
        for (int p = index + 1; p < words.length; p++) {
          resultStr += " " + words[p];
        }
        //其余的无脑的填空即可
        while (resultStr.length() < L) {
          resultStr += " ";
        }
      }
      resultStrList.add(resultStr);
      index = start;
    }

    return resultStrList;
  }

  //Given a number represented as an array of digits, plus one to the number.
  public static int[] plusOne_087(int[] digits) {
    String s = "";
    for (int i : digits) {
      s += i;
    }
    s = new BigDecimal(s).add(BigDecimal.valueOf(1)).toString();
    int[] res = new int[s.length()];
    for (int i = 0; i < s.length(); i++) {
      res[i] = s.charAt(i) - '0';
    }

    return res;
  }

  public boolean isNumber_088(String s) {
    return s.matches(
        "(\\s)*([+-])?(([0-9]*\\.)?([0-9]+)|([0-9]+)(\\.[0-9]*)?)([eE][\\+-]?[0-9]+)?(\\s)*");
  }

  //  Given two binary strings, return their sum (also a binary string).
//
//  For example,
//      a ="11"
//  b ="1"
//  Return"100".
  public static String addBinary_089(String a, String b) {
    int aLength = a.length();
    int bLength = b.length();
    int shouldUpgrade = 0;
    StringBuffer resultBuilder = new StringBuffer("");

    int i = aLength - 1, j = bLength - 1;
    while (i >= 0 || j >= 0 || shouldUpgrade != 0) {
      int aValue = i >= 0 ? a.charAt(i) - '0' : 0;
      int bValue = j >= 0 ? b.charAt(j) - '0' : 0;
      int current = shouldUpgrade + aValue + bValue;
      resultBuilder.append(current % 2);
      shouldUpgrade = current / 2;
      i--;
      j--;
    }
    return resultBuilder.reverse().toString();
  }

  //  Merge two sorted linked lists and return it as a new list.
//  The new list should be made by splicing together the nodes of the first two lists.
  public static ListNode mergeTwoLists_090(ListNode l1, ListNode l2) {
    ListNode resultListNode = new ListNode(0);
    ListNode p = resultListNode;
    while (l1 != null && l2 != null) {
      if (l1.val < l2.val) {
        p.next = l1;
        l1 = l1.next;
      } else {
        p.next = l2;
        l2 = l2.next;
      }
      p = p.next;
    }

    if (l1 != null) {
      p.next = l1;

    }

    if (l2 != null) {
      p.next = l2;
    }

    return resultListNode.next;
  }


  //  Given a m x n grid filled with non-negative numbers,
//  find a path from top left to bottom right which minimizes the sum of all numbers along its path.
//
//  Note: You can only move either down or right at any point in time.
  public int minPathSum_091(int[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) {
      return -1;
    }

    int row = grid.length;
    int col = grid[0].length;
    int[][] dp = new int[row][col];

    //初始化
    dp[0][0] = grid[0][0];

    for (int i = 1; i < row; i++) {
      dp[i][0] = dp[i - 1][0] + grid[i][0];
    }

    for (int j = 1; j < col; j++) {
      dp[0][j] = dp[0][j - 1] + grid[0][j];
    }

    for (int i = 1; i < row; i++) {
      for (int j = 1; j < col; j++) {
        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
      }
    }

    return dp[row - 1][col - 1];
  }


  //
//  A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
//
//  The robot can only move either down or right at any point in time.
//
// The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
//
//  How many possible unique paths are there?
  public int uniquePaths(int m, int n) {
    if (m < 0 || n < 0) {
      return -1;
    }

    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++) {
      dp[i][0] = 1;
    }

    for (int j = 0; j < n; j++) {
      dp[0][j] = 1;
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
      }
    }

    return dp[m - 1][n - 1];
  }

  //  Follow up for "Unique Paths":
//
//  Now consider if some obstacles are added to the grids. How many unique paths would there be?
//
//  An obstacle and empty space is marked as1and0respectively in the grid.
//
//      For example,
//
//  There is one obstacle in the middle of a 3x3 grid as illustrated below.
//
//  [
//      [0,0,0],
//      [0,1,0],
//      [0,0,0]
//      ]
//  The total number of unique paths is2.
//
//      Note: m and n will be at most 100.
  public static int uniquePathsWithObstacles_093(int[][] obstacleGrid) {
    int m = obstacleGrid.length;
    int n = obstacleGrid[0].length;
    if (m < 0 || n < 0) {
      return -1;
    }

    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++) {
      if (obstacleGrid[i][0] == 1) {
        break;
      }
      dp[i][0] = 1;
    }

    for (int j = 0; j < n; j++) {
      if (obstacleGrid[0][j] == 1) {
        break;
      }
      dp[0][j] = 1;
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (obstacleGrid[i][j] == 1) {
          dp[i][j] = 0;
        } else {
          dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
      }
    }

    return dp[m - 1][n - 1];
  }

  //  The set[1,2,3,…,n]contains a total of n! unique permutations.
//
//  By listing and labeling all of the permutations in order,
//  We get the following sequence (ie, for n = 3):
//
//      "123"
//      "132"
//      "213"
//      "231"
//      "312"
//      "321"
//
//  Given n and k, return the k th permutation sequence.
  public String getPermutation_094(int n, int k) {
    char[] arr = new char[n];
    for (int i = 0; i < n; i++) {
      arr[i] = (char) ('1' + i);
    }
    permutation(arr, 0, k);
    return result;
  }

  int count;
  String result;

  public void permutation(char[] arr, int index, int k) {
    if (index == arr.length) {
      count++;
      System.out.println(String.valueOf(arr));
      if (count == k) {
        result = String.valueOf(arr);
      }
    }

    for (int i = index; i < arr.length; i++) {
      swap1(arr, index, i);
      permutation(arr, index + 1, k);
      swap2(arr, index, i);
    }
  }

  private void swap1(char[] arr, int index, int k) {
    char tmp = arr[k];
    //用插入法不改变后续元素的大小顺序
    for (int i = k; i > index; i--) {
      arr[i] = arr[i - 1];
    }
    // arr[k] = arr[index];
    arr[index] = tmp;
  }

  private void swap2(char[] arr, int index, int k) {
    char tmp = arr[index];
    //用插入法不改变后续元素的大小顺序
    for (int i = index; i < k; i++) {
      arr[i] = arr[i + 1];
    }
    arr[k] = tmp;
  }


  //  Given an integer n, generate a square matrix filled with elements from 1 to n 2 in spiral order.
//
//      For example,
//  Given n =3,
//
//  You should return the following matrix:
//  [
//      [ 1, 2, 3 ],
//      [ 8, 9, 4 ],
//      [ 7, 6, 5 ]
//  ]
  public static int[][] generateMatrix_095(int n) {
    int[][] arr = new int[n][n];
    int start = 0;
    int end = n - 1;
    int i, j;
    int index = 1;

    while (end - start >= 0) {
      i = start;
      j = start;
      //向右
      while (j <= end) {
        arr[i][j++] = index++;
      }
      j--;
      index--;

      //向下
      while (i <= end) {
        arr[i++][j] = index++;
      }
      i--;
      index--;

      //向左
      while (j >= start) {
        arr[i][j--] = index++;
      }
      j++;
      index--;

      //向上
      while (i > start) {
        arr[i--][j] = index++;
      }

      start++;
      end--;
    }

    return arr;
  }

  //  Given a string s consists of upper/lower-case alphabets and empty space characters' ', return the length of last word in the string.
//
//  If the last word does not exist, return 0.
//
//  Note: A word is defined as a character sequence consists of non-space characters only.
//
//      For example,
//  Given s ="Hello World",
//  return5.
  public static int lengthOfLastWord_096(String s) {
    if (s == null || s.equals("")) {
      return 0;
    }

    int len = s.length();
    int end = len - 1;
    while (s.charAt(end) == ' ' && end > 0) {
      end--;
    }

    int start = end;
    while (start >= 0 && s.charAt(start) != ' ') {
      start--;
    }

    return end - start;
  }

  public static ArrayList<Interval> insert_097(ArrayList<Interval> intervals,
      Interval newInterval) {
    ArrayList<Interval> resultIntervals = new ArrayList<>();
    if (intervals == null || intervals.size() == 0 || newInterval == null) {
      return intervals;
    }

    int insertPos = 0;//记录将要插入的位置
    for (Interval interval : intervals) {
      //先考虑两种在区间外的情况
      if (interval.start > newInterval.end) {
        resultIntervals.add(interval);
      } else if (interval.end < newInterval.start) {
        resultIntervals.add(interval);
        insertPos++;
      } else {
        //剩下的两种情况只需要取公共区间就行
        newInterval.start = Math.min(interval.start, newInterval.start);
        newInterval.end = Math.max(interval.end, newInterval.end);
      }
    }

    resultIntervals.add(insertPos, newInterval);
    return resultIntervals;
  }

  public static class Interval {

    int start;
    int end;

    Interval() {
      start = 0;
      end = 0;
    }

    public Interval(int s, int e) {
      start = s;
      end = e;
    }
  }


  //  Given a collection of intervals, merge all overlapping intervals.
//
//  For example,
//  Given[1,3],[2,6],[8,10],[15,18],
//      return[1,6],[8,10],[15,18].
  public ArrayList<Interval> merge_098(ArrayList<Interval> intervals) {
    if (intervals == null || intervals.size() == 0) {
      return intervals;
    }
    //排序
    Collections.sort(intervals, new Comparator<Interval>() {
      @Override
      public int compare(Interval o1, Interval o2) {
        return o1.start - o2.start;
      }
    });

    ArrayList<Interval> resultList = new ArrayList<>();
    int index = 0;
    while (index < intervals.size()) {
      int start = index + 1;
      Interval interval = intervals.get(index);
      while (start < intervals.size()) {
        Interval tempInterval = intervals.get(start);
        if (tempInterval.start > interval.end) {
          break;
        } else {
          interval.start = Math.min(interval.start, tempInterval.start);
          interval.end = Math.max(interval.end, tempInterval.end);
        }
        start++;
      }
      resultList.add(interval);
      index = start;
    }

    return resultList;
  }
}