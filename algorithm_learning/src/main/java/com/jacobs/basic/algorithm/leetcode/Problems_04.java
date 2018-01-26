package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.algorithm.TreeNode;
import com.jacobs.basic.models.ListNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author lichao
 * @date 2017/11/18
 */
public class Problems_04 {

  public static void main(String[] args) {
//    TreeNode root = new TreeNode(1);
//    root.left = new TreeNode(2);
//    root.right = new TreeNode(2);
//    root.left.left = new TreeNode(3);
//    root.left.right = new TreeNode(4);
//    root.right.left = new TreeNode(4);
//    TreeNode root = new TreeNode(2);
//    root.left = new TreeNode(3);
//    root.left.left = new TreeNode(1);
//
//    recoverTree_048(root);

//    ListNode head = new ListNode(1);
//    head.next = new ListNode(2);
//    head.next.next = new ListNode(3);
//    head.next.next.next = new ListNode(4);
//    head.next.next.next.next = new ListNode(5);
//
//    reverseBetween_062(head, 2, 4);

    //subsetsWithDup_070(new int[]{0});
    //System.out.println(numDecodings_072("1224"));
    merge_073(new int[]{}, 2, new int[]{1}, 1);
  }


  //  Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).
//  For example:
//  Given binary tree{3,9,20,#,#,15,7},
//      3
//      / \
//      9  20
//      /  \
//      15   7
//
//      return its zigzag level order traversal as:
//      [
//      [3],
//      [20,9],
//      [15,7]
//      ]
  public static ArrayList<ArrayList<Integer>> zigzagLevelOrder_044(TreeNode root) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    if (root == null) {
      return resultList;
    }

    //声明双端队列
    Deque<TreeNode> deque = new LinkedList<>();
    boolean isRight = true;
    deque.addFirst(root);
    TreeNode lastLevel = root;
    TreeNode nextLevel = null;
    ArrayList<Integer> tempResults = new ArrayList<>();

    while (!deque.isEmpty()) {
      TreeNode curr;
      if (isRight) {
        curr = deque.pollFirst();
        if (curr.left != null) {
          deque.offerLast(curr.left);
          nextLevel = nextLevel == null ? curr.left : nextLevel;
        }

        if (curr.right != null) {
          deque.offerLast(curr.right);
          nextLevel = nextLevel == null ? curr.right : nextLevel;
        }
      } else {
        curr = deque.pollLast();
        if (curr.right != null) {
          deque.offerFirst(curr.right);
          nextLevel = nextLevel == null ? curr.right : nextLevel;
        }
        if (curr.left != null) {
          deque.offerFirst(curr.left);
          nextLevel = nextLevel == null ? curr.left : nextLevel;
        }
      }

      tempResults.add(curr.val);
      if (curr == lastLevel) {
        isRight = !isRight;
        lastLevel = nextLevel;
        nextLevel = null;
        resultList.add(tempResults);
        //重新分配内存
        tempResults = new ArrayList<>();
      }
    }

    return resultList;
  }


  /**
   * 判断是否为镜像树
   *
   * @param root 根节点
   * @return 返回结果
   */
  public static boolean isSymmetric_045(TreeNode root) {
    if (root == null) {
      return true;
    }

    return check(root.left, root.right);
  }

  public static boolean check(TreeNode left, TreeNode right) {
    if (left == null && right == null) {
      return true;
    } else if (left == null || right == null) {
      return false;
    }

    if (left.val != right.val) {
      return false;
    }

    return check(left.left, right.right) && check(left.right, right.left);
  }


  /**
   * Given two binary trees, write a function to check if they are equal or not.
   *
   * Two binary trees are considered equal if they are structurally identical and the nodes have the
   * same value.
   *
   * @param p 树p
   * @param q 树q
   * @return 返回是否为相同树
   */
  public boolean isSameTree_046(TreeNode p, TreeNode q) {
    if (p == null && q == null) {
      return true;
    } else if (p == null || q == null) {
      return false;
    }

    return (p.val == q.val) && isSameTree_046(p.left, q.left) && isSameTree_046(p.right,
        q.right);
  }


  /**
   * Given a binary tree, determine if it is a valid binary search tree (BST).
   *
   * 方法一：利用中序遍历的特性，记住前一个节点
   *
   * 方法二：每一个节点都对应一个上限，一个下限
   *
   * @param root 树root
   * @return 是否为BST
   */
  public static boolean isValidBST_047(TreeNode root) {
    return helper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public static boolean helper(TreeNode root, int lower, int upper) {
    if (root == null) {
      return true;
    }

    if (root.val <= lower || root.val >= upper) {
      return false;
    }

    return helper(root.left, lower, root.val) && helper(root.right, root.val, upper);
  }

  /**
   * Two elements of a binary search tree (BST) are swapped by mistake.
   *
   * Recover the tree without changing its structure.
   *
   * @param root 二叉搜索树root
   */
  public static void recoverTree_048(TreeNode root) {
    TreeNode[] errors = new TreeNode[2];

    if (root == null) {
      return;
    }

    Stack<TreeNode> stack = new Stack<>();
    TreeNode pre = null;
    TreeNode curr = root;
    while (!stack.isEmpty() || curr != null) {
      if (curr != null) {
        stack.push(curr);
        curr = curr.left;
      } else {
        curr = stack.pop();
        System.out.print(curr.val + " ");
        if (pre != null && pre.val > curr.val) {
          errors[0] = errors[0] == null ? pre : errors[0];
          errors[1] = curr;
        }

        pre = curr;
        curr = curr.right;
      }
    }

    int temp = errors[0].val;
    errors[0].val = errors[1].val;
    errors[1].val = temp;
  }


  //  Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
//
//  For example,
//  Given:
//  s1 ="aabcc",
//  s2 ="dbbca",
//
//  When s3 ="aadbbcbcac", return true.
//  When s3 ="aadbbbaccc", return false.
  public static boolean isInterleave_049(String s1, String s2, String s3) {
    int len1 = s1.length();
    int len2 = s2.length();
    int len3 = s3.length();

    if (len1 + len2 != len3) {
      //长度不一样返回false
      return false;
    }
    char[] chars1 = s1.toCharArray();
    char[] chars2 = s2.toCharArray();
    char[] chars3 = s3.toCharArray();

    //dp[i][j] 代表char1[0...i] char2[0...j] 能否顺利匹配 char3[i+j]
    boolean[][] dp = new boolean[len1 + 1][len2 + 1];
    dp[0][0] = true;

    for (int i = 1; i < len1 + 1; i++) {
      dp[i][0] = dp[i - 1][0] && (chars1[i - 1] == chars3[i - 1]);
    }

    for (int j = 1; j < len2 + 1; j++) {
      dp[0][j] = dp[0][j - 1] && (chars2[j - 1] == chars3[j - 1]);
    }

    for (int i = 1; i < len1 + 1; i++) {
      for (int j = 1; j < len2 + 1; j++) {
        dp[i][j] = (dp[i - 1][j] && chars1[i - 1] == chars3[i + j - 1]) || (dp[i][j - 1]
            && chars2[j - 1] == chars3[i + j - 1]);
      }
    }

    return dp[len1][len2];
  }

//  Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
//
//  For example,
//  Given n = 3, there are a total of 5 unique BST's.
//
//      1         3     3      2      1
//      \       /     /      / \      \
//      3     2     1      1   3      2
//      /     /       \                 \
//      2     1         2                 3

  public ArrayList<TreeNode> generateTrees_050(int n) {
    return generate(1, n);
  }

  public static ArrayList<TreeNode> generate(int start, int end) {
    ArrayList<TreeNode> resultList = new ArrayList<>();

    if (start > end) {
      resultList.add(null);
      return resultList;
    }

    for (int i = start; i <= end; i++) {
      TreeNode head = new TreeNode(i);
      ArrayList<TreeNode> leftSub = generate(start, i - 1);
      ArrayList<TreeNode> rightSub = generate(i + 1, end);

      for (TreeNode left : leftSub) {
        for (TreeNode right : rightSub) {
          head.left = left;
          head.right = right;
          resultList.add(cloneTree(head));
        }
      }
    }

    return resultList;
  }

  public static TreeNode cloneTree(TreeNode root) {
    if (root == null) {
      return null;
    }

    TreeNode temp = new TreeNode(root.val);
    temp.left = cloneTree(root.left);
    temp.right = cloneTree(root.right);

    return temp;
  }


  //  Given a string containing only digits, restore it by returning all possible valid IP address combinations.
//
//  For example:
//  Given"25525511135",
//
//      return["255.255.11.135", "255.255.111.35"]. (Order does not matter)
  public static ArrayList<String> restoreIpAddresses_061(String s) {
    ArrayList<String> results = new ArrayList<>();
    getAllIpAddresses(results, s, 0, "");

    return results;
  }

  public static void getAllIpAddresses(ArrayList<String> results, String left, int n,
      String curr) {
    if (left == null) {
      return;
    }

    int len = left.length();
    if (len < 4 - n || len > 3 * (4 - n)) {
      return;
    }

    if (n == 3) {
      if (len > 1 && left.charAt(0) == '0') {
        return;
      }

      int last = Integer.valueOf(left);
      if (last >= 0 && last <= 255) {
        curr = curr + last;
        results.add(curr);
      }
    }

    for (int i = 1; i < 4 && i < left.length(); i++) {
      String temp = left.substring(0, i);
      if (temp.length() > 1 && temp.charAt(0) == '0') {
        return;
      }
      int t = Integer.valueOf(temp);
      if (t >= 0 && t <= 255) {
        String next = curr + temp + ".";
        getAllIpAddresses(results, left.substring(i), n + 1, next);
      }
    }
  }

  //  Reverse a linked list from position m to n. Do it in-place and in one-pass.
//
//  For example:
//  Given1->2->3->4->5->NULL, m = 2 and n = 4,
//
//  return1->4->3->2->5->NULL.
  public static ListNode reverseBetween_062(ListNode head, int m, int n) {
    if (m <= 0 || n <= 0 || n - m <= 0) {
      return head;
    }

    ListNode copyHead = head;
    ListNode pre = null;
    ListNode aft = null;

    int index = 1;
    while ((index != m - 1 || index != n + 1) && copyHead != null) {
      if (index == m - 1) {
        pre = copyHead;
      }

      if (index == n + 1) {
        aft = copyHead;
      }

      copyHead = copyHead.next;
      index++;
    }

    ListNode curr;
    if (pre == null) {
      curr = head;
    } else {
      curr = pre.next;
    }

    ListNode pre2 = aft;
    ListNode next;

    while (curr != aft) {
      next = curr.next;
      curr.next = pre2;
      pre2 = curr;
      curr = next;
    }

    if (pre != null) {
      pre.next = pre2;
    } else {
      head = pre2;
    }

    return head;
  }


  public static ListNode reverseBetween2_062(ListNode head, int m, int n) {
    if (m <= 0 || n <= 0 || n - m <= 0) {
      return head;
    }

    ListNode dummy = new ListNode(0);
    dummy.next = head;

    ListNode preStart = dummy;
    ListNode start = head;

    for (int i = 1; i < m; i++) {
      preStart = start;
      start = start.next;
    }

    //reverse
    for (int i = 0; i < n - m; i++) {
      ListNode temp = start.next;
      start.next = temp.next;
      temp.next = preStart.next;
      preStart.next = temp;
    }

    return dummy.next;
  }


  //  Given a collection of integers that might contain duplicates, S, return all possible subsets.
//
//      Note:
//
//  Elements in a subset must be in non-descending order.
//  The solution set must not contain duplicate subsets.
//
//
//  For example,
//  If S =[1,2,2], a solution is:
//
//      [
//      [2],
//      [1],
//      [1,2,2],
//      [2,2],
//      [1,2],
//      []
//      ]
  //思路：先排序，然后递归：依次加上以第i个元素开头的子数组,碰到前一个数与后一个数相等的情况则跳过递归
  public static ArrayList<ArrayList<Integer>> subsetsWithDup_070(int[] num) {
    ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
    if (num == null || num.length == 0) {
      return resultList;
    }

    ArrayList<Integer> list = new ArrayList<>();
    Arrays.sort(num);

    findSubList(num, 0, list, resultList);
    return resultList;
  }

  public static void findSubList(int[] num, int start, ArrayList<Integer> list,
      ArrayList<ArrayList<Integer>> resultList) {
    resultList.add(new ArrayList<>(list));

    for (int i = start; i < num.length; i++) {
      if (i > start && num[i] == num[i - 1]) {
        continue;
      }

      list.add(num[i]);
      findSubList(num, i + 1, list, resultList);
      list.remove(list.size() - 1);
    }
  }

  //  A message containing letters fromA-Zis being encoded to numbers using the following mapping:
//
//      'A' -> 1
//      'B' -> 2
//      ...
//      'Z' -> 26
//
//  Given an encoded message containing digits, determine the total number of ways to decode it.
//
//  For example,
//  Given encoded message"12", it could be decoded as"AB"(1 2) or"L"(12).
//
//  The number of ways decoding"12"is 2.
  public static int numDecodings_071(String s) {
    if (s == null || s.equals("")) {
      return 0;
    }

    return getDecodingWays(s, 0, 0);
  }

  public static int getDecodingWays(String str, int start, int num) {
    if (start == str.length() - 1) {
      return 0;
    }

    for (int i = start; i < str.length() - 1; i++) {
      if (start + 1 != str.length() && str.charAt(start) > '0' && str.charAt(start) <= '2') {
        int t = Integer.valueOf(str.substring(start, start + 1));
        if (t <= 26 && t > 0) {
          num = getDecodingWays(str, start + 1, num + 1) + getDecodingWays(str, start + 2, num + 1);
        } else {
          num = getDecodingWays(str, start + 1, num + 1);
        }
      } else {
        num = getDecodingWays(str, start + 1, num + 1);
      }
    }

    return num;
  }

  public static int numDecodings_072(String s) {
    if (s == null || s.length() == 0) {
      return 0;
    }

    int[] dp = new int[s.length() + 1];
    dp[0] = 1;
    if (s.length() > 0 && s.charAt(0) > '0') {
      dp[1] = 1;
    }

    for (int i = 2; i <= s.length(); i++) {
      if (s.charAt(i - 1) > '0') {
        dp[i] += dp[i - 1];
      }
      if (s.charAt(i - 2) > '0') {
        int t = Integer.valueOf(s.substring(i - 2, i));
        if (t <= 26 && t > 0) {
          dp[i] = dp[i] + dp[i - 2];
        }
      }
    }

    return dp[s.length()];
  }

//  The gray code is a binary numeral system where two successive values differ in only one bit.
//
//  Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.
//
//  For example, given n = 2, return[0,1,3,2]. Its gray code sequence is:
//
//      00 - 0
//      01 - 1
//      11 - 3
//      10 - 2

  public ArrayList<Integer> grayCode(int n) {
    ArrayList<Integer> resultList = new ArrayList<>();

    return resultList;
  }

  //Given two sorted integer arrays A and B, merge B into A as one sorted array.
  public static void merge_073(int A[], int m, int B[], int n) {
    if (m == 0) {
      for (int k = 0; k < B.length; k++) {
        A[k] = B[k];
      }
    } else {
      int i = m - 1;
      int j = n - 1;
      int s = m + n - 1;
      while (j >= 0 && i >= 0) {
        if (A[i] > B[j]) {
          A[s--] = A[i--];
        } else {
          A[s--] = B[j--];
        }
      }

      if (i == -1) {
        for (; j >= 0; j--) {
          A[s--] = B[j];
        }
      }
    }
  }
}
