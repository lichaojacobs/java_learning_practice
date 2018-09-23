package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.models.ListNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author lichao
 * @date 2018/08/17
 */
public class ProblemsMedium_10 {

  public static void main(String[] args) {
    System.out.println(-(-Integer.MIN_VALUE));
  }

  // 43. Multiply Strings
  //    Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.
  //
  //    Example 1:
  //
  //    Input: num1 = "2", num2 = "3"
  //    Output: "6"
  //    Example 2:
  //
  //    Input: num1 = "123", num2 = "456"
  //    Output: "56088"
  //    Note:
  //
  //    The length of both num1 and num2 is < 110.
  //    Both num1 and num2 contain only digits 0-9.
  //    Both num1 and num2 do not contain any leading zero, except the number 0 itself.
  //    You must not use any built-in BigInteger library or convert the inputs to integer directly.
  // 根据乘法的移位原则，最后结果的位数是 num1.count+num2.count
  public String multiply(String num1, String num2) {
    int m = num1.length();
    int n = num2.length();
    int[] pos = new int[m + n];
    for (int i = m - 1; i >= 0; i--) {
      for (int j = n - 1; j >= 0; j--) {
        int multi = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
        int p1 = i + j, p2 = i + j + 1;
        int sum = multi + pos[p2];

        pos[p1] += sum / 10;
        pos[p2] = (sum) % 10;
      }
    }

    StringBuilder sb = new StringBuilder();
    for (int p : pos) {
      if (!(sb.length() == 0 && p == 0)) {
        sb.append(p);
      }
    }
    return sb.length() == 0 ? "0" : sb.toString();
  }

  //    Given a collection of distinct integers, return all possible permutations.
  //
  //        Example:
  //
  //    Input: [1,2,3]
  //    Output:
  //        [
  //        [1,2,3],
  //        [1,3,2],
  //        [2,1,3],
  //        [2,3,1],
  //        [3,1,2],
  //        [3,2,1]
  //        ]
  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> resultList = new ArrayList<>();
    if (nums == null || nums.length == 0) {
      return resultList;
    }
    permuteHelper(0, nums, resultList);
    return resultList;
  }

  private void permuteHelper(int start, int[] nums, List<List<Integer>> resultList) {
    if (start >= nums.length - 1) {
      List<Integer> tempResult = new ArrayList<>();
      for (int i : nums) {
        tempResult.add(i);
      }
      resultList.add(tempResult);
      return;
    }

    for (int i = start; i < nums.length; i++) {
      swap(nums, i, start);
      permuteHelper(start + 1, nums, resultList);
      swap(nums, i, start);
    }
  }

  private void swap(int[] arr, int n1, int n2) {
    int temp = arr[n1];
    arr[n1] = arr[n2];
    arr[n2] = temp;
  }


  //    Given an array of non-negative integers, you are initially positioned at the first index of the array.
  //
  //    Each element in the array represents your maximum jump length at that position.
  //
  //        Determine if you are able to reach the last index.
  //
  //    Example 1:
  //
  //    Input: [2,3,1,1,4]
  //    Output: true
  //    Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
  public boolean canJump(int[] nums) {
    if (nums == null || nums.length == 0) {
      return true;
    }
    int maxArrive = 0;
    for (int i = 0; i < nums.length; i++) {
      if (maxArrive < i) {
        return false;
      }
      maxArrive = maxArrive > (i + nums[i]) ? maxArrive : (i + nums[i]);
      if (maxArrive >= nums.length - 1) {
        return true;
      }
    }

    return true;
  }

  //    A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
  //
  //    The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
  //
  //    How many possible unique paths are there?
  //    Note: m and n will be at most 100.
  //
  //    Example 1:
  //
  //    Input: m = 3, n = 2
  //    Output: 3
  //    Explanation:
  //    From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
  //        1. Right -> Right -> Down
  //        2. Right -> Down -> Right
  //        3. Down -> Right -> Right
  public int uniquePaths(int m, int n) {
    if (m <= 0 || n <= 0) {
      return 0;
    }

    int[][] dp = new int[m][n];
    for (int i = 0; i < n; i++) {
      dp[0][i] = 1;
    }
    for (int j = 0; j < m; j++) {
      dp[j][0] = 1;
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
      }
    }

    return dp[m - 1][n - 1];
  }

  //    A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
  //
  //    The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
  //
  //    Now consider if some obstacles are added to the grids. How many unique paths would there be?
  //An obstacle and empty space is marked as 1 and 0 respectively in the grid.
  public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
      return 0;
    }
    int m = obstacleGrid.length;
    int n = obstacleGrid[0].length;
    int[][] dp = new int[m][n];
    dp[0][0] = obstacleGrid[0][0] == 0 ? 1 : 0;
    for (int i = 1; i < n; i++) {
      if (dp[0][i - 1] == 0) {
        dp[0][i] = 0;
      } else {
        dp[0][i] = obstacleGrid[0][i] == 0 ? 1 : 0;
      }
    }
    for (int j = 1; j < m; j++) {
      dp[j][0] = 1;
      if (dp[j - 1][0] == 0) {
        dp[j][0] = 0;
      } else {
        dp[j][0] = obstacleGrid[j][0] == 0 ? 1 : 0;
      }
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

  //    15. 3Sum
  //    Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
  //
  //        Note:
  //
  //    The solution set must not contain duplicate triplets.
  // 其实就是翻译成towSum的解法
  public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> list = new ArrayList<List<Integer>>();
    for (int i = 0; i < nums.length - 2; i++) {
      if (i > 0 && (nums[i] == nums[i - 1])) {
        continue; // avoid duplicates
      }
      for (int j = i + 1, k = nums.length - 1; j < k; ) {
        if (nums[i] + nums[j] + nums[k] == 0) {
          list.add(Arrays.asList(nums[i], nums[j], nums[k]));
          j++;
          k--;
          while ((j < k) && (nums[j] == nums[j - 1])) {
            j++;// avoid duplicates
          }
          while ((j < k) && (nums[k] == nums[k + 1])) {
            k--;// avoid duplicates
          }
        } else if (nums[i] + nums[j] + nums[k] > 0) {
          k--;
        } else {
          j++;
        }
      }
    }
    return list;
  }

  int len = 0;

  //    18. 4Sum
  //    all ksum problem can be divided into two problems:
  //
  //        2sum Problem
  //    Reduce K sum problem to K – 1 sum Problem
  public List<List<Integer>> fourSum(int[] nums, int target) {
    len = nums.length;
    Arrays.sort(nums);
    return kSum(nums, target, 4, 0);
  }

  private ArrayList<List<Integer>> kSum(int[] nums, int target, int k, int index) {
    ArrayList<List<Integer>> res = new ArrayList<>();
    if (index >= len) {
      return res;
    }
    if (k == 2) {
      int i = index, j = len - 1;
      while (i < j) {
        //find a pair
        if (target - nums[i] == nums[j]) {
          List<Integer> temp = new ArrayList<>();
          temp.add(nums[i]);
          temp.add(target - nums[i]);
          res.add(temp);
          //skip duplication
          while (i < j && nums[i] == nums[i + 1]) {
            i++;
          }
          while (i < j && nums[j - 1] == nums[j]) {
            j--;
          }
          i++;
          j--;
          //move left bound
        } else if (target - nums[i] > nums[j]) {
          i++;
          //move right bound
        } else {
          j--;
        }
      }
    } else {
      for (int i = index; i < len - k + 1; i++) {
        //use current number to reduce ksum into k-1sum
        ArrayList<List<Integer>> temp = kSum(nums, target - nums[i], k - 1, i + 1);
        if (temp != null) {
          //add previous results
          for (List<Integer> t : temp) {
            t.add(0, nums[i]);
          }
          res.addAll(temp);
        }
        while (i < len - 1 && nums[i] == nums[i + 1]) {
          //skip duplicated numbers
          i++;
        }
      }
    }
    return res;
  }


  // 64. Minimum Path Sum
  //  Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.
  //
  //    Note: You can only move either down or right at any point in time.
  //
  //        Example:
  //
  //    Input:
  //        [
  //        [1,3,1],
  //        [1,5,1],
  //        [4,2,1]
  //        ]
  //    Output: 7
  //    Explanation: Because the path 1→3→1→1→1 minimizes the sum.
  public int minPathSum(int[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) {
      return 0;
    }

    int m = grid.length;
    int n = grid[0].length;
    int[][] dp = new int[m][n];

    dp[0][0] = grid[0][0];
    for (int i = 1; i < m; i++) {
      dp[i][0] = dp[i - 1][0] + grid[i][0];
    }

    for (int j = 1; j < n; j++) {
      dp[0][j] = dp[0][j - 1] + grid[0][j];
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] =
            dp[i - 1][j] > dp[i][j - 1] ? dp[i][j - 1] + grid[i][j] : dp[i - 1][j] + grid[i][j];
      }
    }

    return dp[m - 1][n - 1];
  }

  //    Given an absolute path for a file (Unix-style), simplify it.
  //
  //    For example,
  //        path = "/home/", => "/home"
  //    path = "/a/./b/../../c/", => "/c"
  //
  //    Corner Cases:
  //
  //    Did you consider the case where path = "/../"?
  //        In this case, you should return "/".
  //    Another corner case is the path might contain multiple slashes '/' together, such as "/home//foo/".
  //    In this case, you should ignore redundant slashes and return "/home/foo".
  public String simplifyPath(String path) {
    if (path == null || "".equals(path)) {
      return path;
    }

    Deque<String> stack = new LinkedList<>();
    Set<String> skip = new HashSet<>(Arrays.asList("..", ".", ""));
    for (String dir : path.split("/")) {
      if (dir.equals("..") && !stack.isEmpty()) {
        stack.pop();
      } else if (!skip.contains(dir)) {
        stack.push(dir);
      }
    }
    String res = "";
    for (String dir : stack) {
      res = "/" + dir + res;
    }
    return res.isEmpty() ? "/" : res;
  }

  //73. Set Matrix Zeroes
  //    Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
  //
  //        Example 1:
  //
  //    Input:
  //        [
  //        [1,1,1],
  //        [1,0,1],
  //        [1,1,1]
  //        ]
  //    Output:
  //        [
  //        [1,0,1],
  //        [0,0,0],
  //        [1,0,1]
  //        ]
  // fr = first row
  // fc = first col

  // Use first row and first column as markers.
  // if matrix[i][j] = 0, mark respected row and col marker = 0; indicating
  //    that later this respective row and col must be marked 0;
  // And because you are altering first row and collumn,
  //    you need to  have two variables to track their own status.
  // So, for ex, if any one of the first row is 0, fr = 0,
  //    and at the end set all first row to 0;
  public void setZeroes(int[][] matrix) {
    if (matrix == null || (matrix.length == 0 && matrix[0].length == 0)) {
      return;
    }

    boolean fr = false;
    boolean fc = false;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] == 0) {
          if (i == 0) {
            fr = true;
          }
          if (j == 0) {
            fc = true;
          }
          matrix[0][j] = 0;
          matrix[i][0] = 0;
        }
      }
    }
    for (int i = 1; i < matrix.length; i++) {
      for (int j = 1; j < matrix[0].length; j++) {
        if (matrix[i][0] == 0 || matrix[0][j] == 0) {
          matrix[i][j] = 0;
        }
      }
    }
    if (fr) {
      for (int j = 0; j < matrix[0].length; j++) {
        matrix[0][j] = 0;
      }
    }
    if (fc) {
      for (int i = 0; i < matrix.length; i++) {
        matrix[i][0] = 0;
      }
    }
  }


  //50. Pow(x, n)
  //    Implement pow(x, n), which calculates x raised to the power n (xn).
  //
  //    Example 1:
  //
  //    Input: 2.00000, 10
  //    Output: 1024.00000
  //    Example 2:
  //
  //    Input: 2.10000, 3
  //    Output: 9.26100
  //    Example 3:
  //
  //    Input: 2.00000, -2
  //    Output: 0.25000
  //    Explanation: 2-2 = 1/22 = 1/4 = 0.25
  //    Note:
  //
  //        -100.0 < x < 100.0
  //    n is a 32-bit signed integer, within the range [−231, 231 − 1]
  //超时, 这种线性级别的复杂度通不过，得使用log(n)级别的复杂度
  public double myPow(double x, int n) {
    if (x == 0.0) {
      return 0.0;
    }
    if (n == 0) {
      return 1;
    }

    int temp = 1;
    double val = x;
    while (temp < Math.abs(n)) {
      val = val * x;
      temp++;
    }

    return n > 0 ? val : 1 / val;
  }

  //优化后的
  public static double pow(double x, int n) {
    if (n == 0) {
      return 1;
    }
    if (n < 0) {
      n = -n;
      x = 1 / x;
    }
    //二分法，始终凑对并行相乘
    return (n % 2 == 0) ? pow(x * x, n / 2) : x * pow(x * x, n / 2);
  }


  //86. Partition List
  //  Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
//
//  You should preserve the original relative order of the nodes in each of the two partitions.
//
//  Example:
//
//  Input: head = 1->4->3->2->5->2, x = 3
//  Output: 1->2->2->4->3->5
  public ListNode partition(ListNode head, int x) {
    ListNode dummy1 = new ListNode(0);
    ListNode dummy2 = new ListNode(0);  //dummy heads of the 1st and 2nd queues
    ListNode curr1 = dummy1, curr2 = dummy2;      //current tails of the two queues;
    while (head != null) {
      if (head.val < x) {
        curr1.next = head;
        curr1 = head;
      } else {
        curr2.next = head;
        curr2 = head;
      }
      head = head.next;
    }
    curr2.next = null;          //important! avoid cycle in linked list. otherwise u will get TLE.
    curr1.next = dummy2.next;
    return dummy1.next;
  }
}
