package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.algorithm.TreeLinkNode;
import com.jacobs.basic.algorithm.TreeNode;
import com.jacobs.basic.models.ListNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * @author lichao
 * @date 2018/09/09
 */
public class TopInterviewQuestions {

    public static void main(String[] args) {
        //        sortColors(new int[]{2, 1});
        //        TreeNode root = new TreeNode(1);
        //        root.left = new TreeNode(2);
        //        root.right = new TreeNode(3);
        //        root.left.left = new TreeNode(4);
        //        root.right.right = new TreeNode(5);
        //        //        zigzagLevelOrder(root);
        TopInterviewQuestions topInterviewQuestions = new TopInterviewQuestions();
        topInterviewQuestions.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2});
        //        TreeLinkNode root = new TreeLinkNode(1);
        //        root.left = new TreeLinkNode(2);
        //        root.right = new TreeLinkNode(3);
        //        topInterviewQuestions.connect(root);
        System.out.println(2 | 1);
    }


    // 4. Median of Two Sorted Arrays
    // 复杂度 O(m+n)
    //    There are two sorted arrays nums1 and nums2 of size m and n respectively.
    //
    //    Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
    //
    //    You may assume nums1 and nums2 cannot be both empty.
    //
    //    Example 1:
    //
    //    nums1 = [1, 3]
    //    nums2 = [2]
    //
    //    The median is 2.0
    //    Example 2:
    //
    //    nums1 = [1, 2]
    //    nums2 = [3, 4]
    //
    //    The median is (2 + 3)/2 = 2.5
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int preIndex = (len1 + len2) / 2;
        int[] mids =
            (len1 + len2) % 2 == 0 ? new int[]{preIndex - 1, preIndex} : new int[]{preIndex};
        //一次归并
        int start1 = 0;
        int start2 = 0;
        int index = 0;
        int target1 = 0;
        int target2 = 0;
        while (start1 < len1 && start2 < len2) {
            int temp;
            if (nums1[start1] < nums2[start2]) {
                temp = nums1[start1];
                start1++;
            } else {
                temp = nums2[start2];
                start2++;
            }

            if (index == mids[0]) {
                target1 = temp;
                if (mids.length == 1) {
                    return target1;
                }
            }
            if (index == mids[0] + 1) {
                target2 = temp;
                if (mids.length == 2) {
                    return (double) (target1 + target2) / 2;
                }
            }
            index++;
        }

        while (start1 < len1) {
            if (index == mids[0]) {
                target1 = nums1[start1];
                if (mids.length == 1) {
                    return target1;
                }
            }
            if (index == mids[0] + 1) {
                target2 = nums1[start1];
                if (mids.length == 2) {
                    return (double) (target1 + target2) / 2;
                }
            }
            start1++;
            index++;
        }

        while (start2 < len2) {
            if (index == mids[0]) {
                target1 = nums2[start2];
                if (mids.length == 1) {
                    return target1;
                }
            }
            if (index == mids[0] + 1) {
                target2 = nums2[start2];
                if (mids.length == 2) {
                    return (double) (target1 + target2) / 2;
                }
            }
            start2++;
            index++;
        }

        return 0.0;
    }

    //    Explanation 使用binary search
    ////
    ////    The key point of this problem is to ignore half part of A and B each step recursively by comparing the median of remaining A and B:
    ////
    ////        if (aMid < bMid) Keep [aRight + bLeft]
    ////        else Keep [bRight + aLeft]
    ////    As the following: time=O(log(m + n))
    public double findMedianSortedArrays2(int[] A, int[] B) {
        int m = A.length, n = B.length;
        int l = (m + n + 1) / 2;
        int r = (m + n + 2) / 2;
        return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
    }

    public double getkth(int[] A, int aStart, int[] B, int bStart, int k) {
        if (aStart > A.length - 1) {
            return B[bStart + k - 1];
        }
        if (bStart > B.length - 1) {
            return A[aStart + k - 1];
        }
        if (k == 1) {
            return Math.min(A[aStart], B[bStart]);
        }

        int aMid = Integer.MAX_VALUE, bMid = Integer.MAX_VALUE;
        if (aStart + k / 2 - 1 < A.length) {
            aMid = A[aStart + k / 2 - 1];
        }
        if (bStart + k / 2 - 1 < B.length) {
            bMid = B[bStart + k / 2 - 1];
        }

        if (aMid < bMid) {
            return getkth(A, aStart + k / 2, B, bStart, k - k / 2);// Check: aRight + bLeft
        } else {
            return getkth(A, aStart, B, bStart + k / 2, k - k / 2);// Check: bRight + aLeft
        }
    }


    //7. Reverse Integer
    //  Given a 32-bit signed integer, reverse digits of an integer.
    //
    //      Example 1:
    //
    //  Input: 123
    //  Output: 321
    public int reverse(int x) {
        int result = 0;

        while (x != 0) {
            int tail = x % 10;
            int newResult = result * 10 + tail;
            if ((newResult - tail) / 10 != result) {
                return 0;
            }
            result = newResult;
            x = x / 10;
        }

        return result;
    }

    // 41. First Missing Positive
    //    Given an unsorted integer array, find the smallest missing positive integer.
    //
    //    Example 1:
    //
    //    Input: [1,2,0]
    //    Output: 3
    // Your algorithm should run in O(n) time and uses constant extra space.
    //The key here is to use swapping to keep constant space and also make use of the length of the array,
    // which means there can be at most n positive integers.
    // So each time we encounter an valid integer, find its correct position and swap. Otherwise we continue.
    //通过交换，使每个大于0的元素都在自己应该在的位置上: num[i]=i+1
    public int firstMissingPositive(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            if (nums[i] == i + 1 || nums[i] <= 0 || nums[i] > nums.length) {
                i++;
            } else if (nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            } else {
                i++;
            }
        }
        i = 0;
        while (i < nums.length && nums[i] == i + 1) {
            i++;
        }
        return i + 1;
    }

    //75. Sort Colors
    //    Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.
    //
    //    Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
    //
    //        Note: You are not suppose to use the library's sort function for this problem.
    //
    //    Example:
    //
    //    Input: [2,0,2,1,1,0]
    //    Output: [0,0,1,1,2,2]
    // Could you come up with a one-pass algorithm using only constant space?
    // 思路其实就是快排的partition过程
    public static void sortColors(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        int redIndex = 0;
        int blueIndex = nums.length - 1;
        int whiteIndex = 0;

        while (whiteIndex <= blueIndex) {
            while (nums[redIndex] == 0 && redIndex <= blueIndex) {
                redIndex++;
            }
            if (whiteIndex < redIndex) {
                whiteIndex = redIndex;
            }
            while (nums[blueIndex] == 2 && blueIndex >= redIndex) {
                blueIndex--;
            }
            while (nums[whiteIndex] == 1 && whiteIndex <= blueIndex) {
                whiteIndex++;
            }
            if (whiteIndex <= blueIndex) {
                if (nums[whiteIndex] == 0) {
                    swap(nums, whiteIndex, redIndex);
                } else {
                    swap(nums, whiteIndex, blueIndex);
                }
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // 79. Word Search
    //    Given a 2D board and a word, find if the word exists in the grid.
    //
    //    The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.
    //
    //        Example:
    //
    //    board =
    //        [
    //        ['A','B','C','E'],
    //        ['S','F','C','S'],
    //        ['A','D','E','E']
    //        ]
    //
    //    Given word = "ABCCED", return true.
    //    Given word = "SEE", return true.
    //    Given word = "ABCB", return false.
    public boolean exist(char[][] board, String word) {
        if (board == null || (board.length == 0 && board[0].length == 0)) {
            return false;
        }
        if (word == null || "".equals(word)) {
            return true;
        }
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (isExist(i, j, visited, word.substring(1), board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isExist(int current_i, int current_j, boolean[][] visited, String word,
        char[][] board) {
        if (word.length() == 0) {
            return true;
        }
        visited[current_i][current_j] = true;
        boolean top =
            current_i - 1 >= 0 && word.charAt(0) == board[current_i - 1][current_j] && !visited[
                current_i - 1][current_j] && isExist(
                current_i - 1, current_j, visited, word.substring(1), board);
        boolean bottom =
            current_i + 1 < board.length && word.charAt(0) == board[current_i + 1][current_j]
                && !visited[current_i + 1][current_j]
                && isExist(current_i + 1, current_j, visited, word.substring(1), board);
        boolean left =
            current_j - 1 >= 0 && board[current_i][current_j - 1] == word.charAt(0)
                && !visited[current_i][current_j - 1] && isExist(
                current_i, current_j - 1, visited, word.substring(1), board);

        boolean right =
            current_j + 1 < board[current_i].length && board[current_i][current_j + 1] == word
                .charAt(0) && !visited[current_i][current_j
                + 1] && isExist(current_i, current_j + 1, visited, word.substring(1), board);

        if (top || bottom || left || right) {
            return true;
        } else {
            visited[current_i][current_j] = false;
            return false;
        }
    }


    // 19. Remove Nth Node From End of List
    //    Given a linked list, remove the n-th node from the end of list and return its head.
    //
    //    Example:
    //
    //    Given linked list: 1->2->3->4->5, and n = 2.
    //
    //    After removing the second node from the end, the linked list becomes 1->2->3->5.
    //    Note:
    //
    //    Given n will always be valid.
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0) {
            return head;
        }

        ListNode preNode = head;
        ListNode aftNode = head;

        while (aftNode != null && n > 0) {
            aftNode = aftNode.next;
            n--;
        }

        // 此处需要处理头部节点被去掉的corner case
        if (aftNode == null && n > 0) {
            return head;
        }
        if (aftNode == null && n == 0) {
            return head.next;
        }

        while (aftNode.next != null) {
            preNode = preNode.next;
            aftNode = aftNode.next;
        }
        preNode.next = preNode.next.next;
        return head;
    }

    //102. Binary Tree Level Order Traversal
    //    Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
    //
    //    For example:
    //    Given binary tree [3,9,20,null,null,15,7],
    //        3
    //        / \
    //        9  20
    //        /  \
    //        15   7
    //        return its level order traversal as:
    //        [
    //        [3],
    //        [9,20],
    //        [15,7]
    //        ]

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> resultList = new ArrayList<>();
        if (root == null) {
            return resultList;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelNum = queue.size();
            List<Integer> subList = new LinkedList<>();
            for (int i = 0; i < levelNum; i++) {
                if (queue.peek().left != null) {
                    queue.offer(queue.peek().left);
                }
                if (queue.peek().right != null) {
                    queue.offer(queue.peek().right);
                }
                subList.add(queue.poll().val);
            }
            resultList.add(subList);
        }

        return resultList;
    }

    //215. Kth Largest Element in an Array
    //    Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.
    //
    //    Example 1:
    //
    //    Input: [3,2,1,5,6,4] and k = 2
    //    Output: 5
    //    Example 2:
    //
    //    Input: [3,2,3,1,2,4,5,5,6] and k = 4
    //    Output: 4
    //    Note:
    //    You may assume k is always valid, 1 ≤ k ≤ array's length.
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        k = nums.length - k;
        int low = 0;
        int high = nums.length - 1;
        int mid = 0;
        while (low < high) {
            mid = kthLargestPartitionHelper(nums, low, high);
            if (mid < k) {
                low = mid + 1;
            } else if (mid > k) {
                high = mid - 1;
            } else {
                break;
            }
        }

        return nums[mid];
    }

    public int kthLargestPartitionHelper(int[] nums, int low, int high) {
        int pivot = nums[low];
        while (low < high) {
            while (low < high && pivot <= nums[high]) {
                high--;
            }
            nums[low] = nums[high];
            while (low < high && pivot >= nums[low]) {
                low++;
            }
            nums[high] = nums[low];
        }
        nums[low] = pivot;
        return low;
    }

    /**
     * 124. Binary Tree Maximum Path Sum
     *
     * Given a non-empty binary tree, find the maximum path sum.
     *
     * For this problem, a path is defined as any sequence of nodes from some starting node to any
     * node in the tree along the parent-child connections. The path must contain at least one node
     * and does not need to go through the root.
     *
     * 思路：最大和无非就是三种情况：左边，右边，或者是跨节点，或者就是该节点 我的方法没有跑通所有的test cases
     */
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        int[] maxSum = new int[1];
        maxSum[0] = Integer.MIN_VALUE;
        maxPathSumHelper(root, maxSum);
        return maxSum[0];
    }

    public int maxPathSumHelper(TreeNode root, int[] maxSum) {
        if (root == null) {
            maxSum[0] = 0;
            return 0;
        }

        int leftMax = maxPathSumHelper(root.left, maxSum);
        int maxFromLeft = maxSum[0];

        int rightMax = maxPathSumHelper(root.right, maxSum);
        int maxFromRight = maxSum[0];

        // root+right+left, root, root+left, root+right
        int crossMax = Math.max(
            Math.max(Math.max(leftMax + rightMax + root.val, leftMax + root.val),
                rightMax + root.val), root.val);
        int currentMax = Math.max(Math.max(maxFromLeft, maxFromRight), crossMax);

        //这里用一个数组记下局部最大值
        maxSum[0] = currentMax;
        //这里如果不return 跨节点的最大，则在上一层的时候没法确定是否与上一层的当前节点相连
        return crossMax;
    }


    // 网友的解答
    int max = Integer.MIN_VALUE;

    public int maxPathSum2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        backtrack(root);
        return max;
    }

    private int backtrack(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftSum = Math.max(0, backtrack(root.left));//less than 0, then not take left branch
        int rightSum = Math.max(0, backtrack(root.right));//less than 0, then not take right branch
        max = Math.max(max,
            leftSum + rightSum + root.val);//root,left + root, right + root, left + right + root;
        return Math.max(0, Math.max(root.val + leftSum,
            root.val + rightSum));//take left+root or right+root or root or 0
    }

    //Given a binary tree, return the zigzag level order traversal of its nodes' values.
    // (ie, from left to right, then right to left for the next level and alternate between).
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Deque<TreeNode> dq = new ArrayDeque<>();
        if (root != null) {
            dq.add(root);
        }
        int pop_num = dq.size();
        List<List<Integer>> res = new ArrayList<>();
        boolean dir = true;
        while (!dq.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < pop_num; ++i) {
                TreeNode tmp = null;
                if (dir) {
                    tmp = dq.pollFirst();
                    if (tmp.left != null) {
                        dq.addLast(tmp.left);
                    }
                    if (tmp.right != null) {
                        dq.addLast(tmp.right);
                    }
                } else {
                    tmp = dq.pollLast();
                    if (tmp.right != null) {
                        dq.addFirst(tmp.right);
                    }
                    if (tmp.left != null) {
                        dq.addFirst(tmp.left);
                    }
                }
                level.add(tmp.val);
            }
            res.add(level);
            pop_num = dq.size();
            dir = !dir;
        }
        return res;
    }

    //104. Maximum Depth of Binary Tree
    //  Given a binary tree, find its maximum depth.
    //
    //  The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
    //
    //  Note: A leaf is a node with no children.
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    //207. Course Schedule
    //There are a total of n courses you have to take, labeled from 0 to n-1.
    //
    //Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
    //
    //Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
    //
    //Example 1:
    //
    //Input: 2, [[1,0]]
    //Output: true
    //Explanation: There are a total of 2 courses to take.
    //             To take course 1 you should have finished course 0. So it is possible.
    // BFS && DFS solution
    // 用一个数组表示一个图的边的关系，数组的index为from, value为to，表示了一条有向边
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList();
        }
        //构建图
        for (int i = 0; i < prerequisites.length; i++) {
            int from = prerequisites[i][0];
            int to = prerequisites[i][1];
            graph[from].add(to);
        }
        //记录是否走过某条图的边的情况
        boolean[] visited = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (!canFinishDFSHelper(graph, visited, i)) {
                return false;
            }
        }

        return true;
    }

    public boolean canFinishDFSHelper(ArrayList[] graph, boolean[] visited, int course) {
        if (visited[course]) {
            return false;
        }
        //标记为访问过
        visited[course] = true;
        for (int i = 0; i < graph[course].size(); i++) {
            //dfs 查看路径是否有死循环
            if (!canFinishDFSHelper(graph, visited, (int) graph[course].get(i))) {
                return false;
            }
        }
        //将标记改回来，方便下一次循环使用
        visited[course] = false;
        return true;
    }


    //   131. Palindrome Partitioning
    // Given a string s, partition s such that every substring of the partition is a palindrome.
    //
    //    Return all possible palindrome partitioning of s.
    //
    //        Example:
    //
    //    Input: "aab"
    //    Output:
    //        [
    //        ["aa","b"],
    //        ["a","a","b"]
    //        ]
    // 利用动态规划判断是否为回文
    // BFS 思想
    public List<List<String>> palindromePartition(String s) {
        List<List<String>> results = new ArrayList<>();
        if (s == null || s.equals("")) {
            return results;
        }
        char[] chars = s.toCharArray();
        int len = chars.length;
        boolean[][] palindromeMatrix = new boolean[len][len];
        palindromePartitionHelper(chars, palindromeMatrix, results, new ArrayList<>(), 0);
        return results;
    }

    public void palindromePartitionHelper(char[] chars, boolean[][] matrix,
        List<List<String>> result, ArrayList<String> currentResult,
        int start) {
        if (start >= chars.length && currentResult.size() > 0) {
            result.add(new ArrayList<>(currentResult));
        }

        for (int i = start; i < chars.length; i++) {
            if (isPalindrome(matrix, chars, start, i)) {
                matrix[start][i] = true;
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = start; j <= i; j++) {
                    stringBuilder.append(chars[j]);
                }
                currentResult.add(stringBuilder.toString());
                // 截取了start-i作为一个record,下一个从i+1之后开始进入递归
                palindromePartitionHelper(chars, matrix, result, currentResult, i + 1);
                currentResult.remove(currentResult.size() - 1);
            }
        }
    }

    public boolean isPalindrome(boolean[][] matrix, char[] chars, int start, int end) {
        return chars[start] == chars[end] && (end - start < 2
            || matrix[start + 1][end - 1] == true);
    }

    //    148. Sort List
    //    Sort a linked list in O(n log n) time using constant space complexity.
    //
    //    Example 1:
    //
    //    Input: 4->2->1->3
    //    Output: 1->2->3->4
    //    Example 2:
    //
    //    Input: -1->5->3->4->0
    //    Output: -1->0->3->4->5
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode breakNode = null, fast = head, slow = head;
        while (fast != null && fast.next != null) {
            breakNode = slow;
            fast = fast.next.next;
            slow = slow.next;
        }

        breakNode.next = null;
        // step 2. sort each half
        ListNode pre = sortList(head);
        ListNode aft = sortList(slow);

        return mergeListNode(pre, aft);
    }

    public ListNode mergeListNode(ListNode pre, ListNode aft) {
        ListNode newHead = new ListNode(0);
        ListNode nodeIndex = newHead;

        while (pre != null && aft != null) {
            if (pre.val < aft.val) {
                nodeIndex.next = pre;
                pre = pre.next;
            } else {
                nodeIndex.next = aft;
                aft = aft.next;
            }
            nodeIndex = nodeIndex.next;
        }

        if (pre != null) {
            nodeIndex.next = pre;
        }
        if (aft != null) {
            nodeIndex.next = aft;
        }

        return newHead.next;
    }


    //    139. Word Break
    //    Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
    //
    //    Note:
    //
    //    The same word in the dictionary may be reused multiple times in the segmentation.
    //    You may assume the dictionary does not contain duplicate words.
    //    Example 1:
    //
    //    Input: s = "leetcode", wordDict = ["leet", "code"]
    //    Output: true
    //    Explanation: Return true because "leetcode" can be segmented as "leet code".
    //    解法一：DFS，最后一个test case 超时
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.equals("") || wordDict == null || wordDict.size() == 0) {
            return false;
        }

        HashSet<String> wordSet = new HashSet<>(wordDict);
        return workBeakHelper(s, 0, wordSet);
    }

    public boolean workBeakHelper(String rawStr, int start, HashSet<String> wordDict) {
        if (start >= rawStr.length()) {
            return false;
        }
        if (wordDict.contains(rawStr.substring(start))) {
            return true;
        }

        boolean flag = false;
        for (int i = start; i < rawStr.length(); i++) {
            if (wordDict.contains(rawStr.substring(start, i))) {
                flag = workBeakHelper(rawStr, i, wordDict);
                if (flag) {
                    return true;
                }
            }
        }

        return flag;
    }

    /**
     * 采用动态规划思想
     *
     * 定义一个boolean dp[length+1],dp[i]表示长度为i的字符串是否能word break
     */
    public boolean wordBreak2(String s, List<String> wordDict) {
        if (s == null || s.equals("") || wordDict == null || wordDict.size() == 0) {
            return false;
        }

        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                //这里如果dp[j]为true，说明只需要判断j到i之间的字符串是否能word break
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }


    //236. Lowest Common Ancestor of a Binary Tree（最近公共祖先）
    //    All of the nodes' values will be unique.
    //    p and q are different and both values will exist in the binary tree
    //    深度遍历(DFS)或者说树的先序遍历，遍历过程中如果找到匹配的节点就返回，
    //    到最后如果left，right都不为空，说明当前的root即为公共的祖先
    //    如果有一个为空，则只需要返回不为空的node到上层即可
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (root == q || root == p) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        } else {
            return left != null ? left : right;
        }
    }

    //116. Populating Next Right Pointers in Each Node
    //    Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
    //
    //    Initially, all next pointers are set to NULL.
    //
    //        Note:
    //
    //    You may only use constant extra space.
    //    Recursive approach is fine, implicit stack space does not count as extra space for this problem.
    //    You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
    public void connect(TreeLinkNode root) {
        if (root == null) {
            return;
        }
        Deque<TreeLinkNode> queue = new LinkedList<>();
        queue.offerFirst(root);
        //记录同一层最后一个节点
        TreeLinkNode levelLastNode = root;
        //记录同一层前驱节点
        TreeLinkNode levelPreNode = null;
        while (!queue.isEmpty()) {
            TreeLinkNode curr = queue.pollLast();
            if (curr.left != null) {
                queue.offerFirst(curr.left);
            }
            if (curr.right != null) {
                queue.offerFirst(curr.right);
            }
            //如果前驱为空的话说明是一层的开始
            if (levelPreNode != null) {
                levelPreNode.next = curr;
            }
            levelPreNode = curr;
            //栈输出如果是上一层最后一个节点
            if (curr == levelLastNode) {
                levelLastNode = curr.right;
                curr.next = null;
                levelPreNode = null;
            }
        }
    }


    //341. Flatten Nested List Iterator
    //Given a nested list of integers, implement an iterator to flatten it.
    //Each element is either an integer, or a list -- whose elements may also be integers or other lists.
    // This is the interface that allows for creating nested lists.
    // You should not implement it, or speculate about its implementation
    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    public class NestedIterator implements Iterator<Integer> {

        private Stack<NestedInteger> integerStack = new Stack<>();

        public NestedIterator(List<NestedInteger> nestedList) {
            for (int i = nestedList.size() - 1; i >= 0; i--) {
                integerStack.push(nestedList.get(i));
            }
        }

        @Override
        public Integer next() {
            return integerStack.pop().getInteger();
        }

        @Override
        public boolean hasNext() {
            while (!integerStack.isEmpty()) {
                NestedInteger curr = integerStack.peek();
                if (curr.isInteger()) {
                    return true;
                }
                integerStack.pop();
                for (int i = curr.getList().size() - 1; i >= 0; i--) {
                    integerStack.push(curr.getList().get(i));
                }
            }
            return false;
        }
    }

    /**
     * 210. Course Schedule II
     *
     * There are a total of n courses you have to take, labeled from 0 to n-1.
     *
     * Some courses may have prerequisites, for example to take course 0 you have to first take
     * course 1, which is expressed as a pair: [0,1]
     *
     * Given the total number of courses and a list of prerequisite pairs, return the ordering of
     * courses you should take to finish all courses.
     *
     * There may be multiple correct orders, you just need to return one of them. If it is
     * impossible to finish all courses, return an empty array.
     *
     * 有向无环图，用BFS的方法解决，逐渐抹去入度为0的节点以及他们的边，将大问题化小
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        //记录节点的入度
        int[] incLinkCounts = new int[numCourses];
        ArrayList<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList();
        }
        //构建图
        for (int i = 0; i < prerequisites.length; i++) {
            int from = prerequisites[i][1];
            int to = prerequisites[i][0];
            graph[from].add(to);
            incLinkCounts[to]++;
        }

        //BFS
        int[] order = new int[incLinkCounts.length];
        Queue<Integer> toVisit = new ArrayDeque<>();
        for (int i = 0; i < incLinkCounts.length; i++) {
            if (incLinkCounts[i] == 0) {
                toVisit.offer(i);
            }
        }

        int index = 0;
        while (!toVisit.isEmpty()) {
            int from = toVisit.poll();
            order[index++] = from;
            //去除与它相关联的所有依赖边
            for (int to : graph[from]) {
                //入度减1
                incLinkCounts[to]--;
                //如果入度满足为0的情况，则加入下一轮削减的过程
                if (incLinkCounts[to] == 0) {
                    toVisit.offer(to);
                }
            }
        }

        return index == incLinkCounts.length ? order : new int[0];
    }


    /**
     * 227 Basic Calculator II
     *
     * Implement a basic calculator to evaluate a simple expression string.
     *
     * The expression string contains only non-negative integers, +, -, *, / operators and empty
     * spaces . The integer division should truncate toward zero.
     *
     * Example 1:
     *
     * Input: "3+2*2" Output: 7
     */
    public int calculate(String s) {
        if (s == null || s.equals("")) {
            return -1;
        }
        char[] operators = s.toCharArray();
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        //初始化符号
        char sign = '+';
        for (int i = 0; i < s.length(); i++) {
            //连续的数字，可能不止一位
            if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
            }
            //如果是符号
            if (!Character.isDigit(s.charAt(i)) && ' ' != s.charAt(i) || i == s.length() - 1) {
                if (sign == '-') {
                    stack.push(-num);
                }
                if (sign == '+') {
                    stack.push(num);
                }
                if (sign == '*') {
                    stack.push(stack.pop() * num);
                }
                if (sign == '/') {
                    stack.push(stack.pop() / num);
                }
                //重新赋值新的操作符
                sign = s.charAt(i);
                num = 0;
            }
        }

        int re = 0;
        for (int i : stack) {
            re += i;
        }
        return re;
    }

    /**
     * 230. Kth Smallest Element in a BST
     *
     * Given a binary search tree, write a function kthSmallest to find the kth smallest element in
     * it.
     *
     * Note: You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
     *
     * 思路： 1. 二分去查找，相当于数当前节点左边有多少个，如果大于k则往左边，小于往右边，等于返回当前节点
     *
     * 2. 或者采用非递归的中序遍历
     */
    public int kthSmallest(TreeNode root, int k) {
        int currNodeNum = countNodes(root.left);
        if (k <= currNodeNum) {
            return kthSmallest(root.left, k);
        } else if (k > currNodeNum + 1) {
            // 1 is counted as current node
            return kthSmallest(root.right, k - 1 - currNodeNum);
        }

        return root.val;
    }

    /***
     * 计算以n为根节点的的节点数量
     */
    public int countNodes(TreeNode n) {
        if (n == null) {
            return 0;
        }
        return 1 + countNodes(n.left) + countNodes(n.right);
    }

    /**
     * 238. Product(乘积) of Array Except Self
     *
     *
     * Given an array nums of n integers where n > 1,  return an array output such that output[i] is
     * equal to the product of all the elements of nums except nums[i].
     *
     * Example:
     *
     * Input:  [1,2,3,4] Output: [24,12,8,6] Note: Please solve it without division and in O(n).
     *
     * Follow up: Could you solve it with constant space complexity? (The output array does not
     * count as extra space for the purpose of space complexity analysis.)
     *
     * 思路：从头到尾，再从尾到头过一遍，第一遍得出除了num[i]之外左边所有元素的乘积，第二遍再乘上除了num[i]之外所有元素的乘积
     */
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = 1;

        //从左到右
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        //从右边到左
        int right = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= right;
            right *= nums[i];
        }

        return res;
    }

    /**
     * 240. Search a 2D Matrix II
     *
     * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has
     * the following properties:
     *
     * Integers in each row are sorted in ascending from left to right. Integers in each column are
     * sorted in ascending from top to bottom. Example:
     *
     * Consider the following matrix:
     *
     * [ [1,   4,  7, 11, 15],
     *
     * [2,   5,  8, 12, 19],
     *
     * [3,   6,  9, 16, 22],
     *
     * [10, 13, 14, 17, 24],
     *
     * [18, 21, 23, 26, 30] ] Given target = 5, return true.
     *
     * Given target = 20, return false.
     *
     * 思路：复杂度O(M+N) 但不是二分查找的实现
     *
     * 从matrix 右上角开始，如果target小于matrix[i][j] 则说明肯定不会出现在当前列
     *
     * 如果target大于matrix[i][j] 则说明肯定不会出现在当前行，这样逐一排除是线性的复杂度
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        int row = 0;
        int col = matrix[0].length - 1;
        while (row <= matrix.length - 1 && col >= 0) {
            if (target == matrix[row][col]) {
                return true;
            } else if (target > matrix[row][col]) {
                row++;
            } else {
                col--;
            }
        }
        return false;
    }

    /**
     * 279. Perfect Squares
     *
     * Given a positive integer n, find the least number of perfect square numbers (for example, 1,
     * 4, 9, 16, ...) which sum to n.
     *
     * Example 1:
     *
     * Input: n = 12 Output: 3 Explanation: 12 = 4 + 4 + 4
     *
     * 思路：采用动态规划的思路，保存n前面最优的分解方式dp[i]表示i的最优平方分解的方式是多少种
     */
    public int numSquares(int n) {
        if (n < 0) {
            return -1;
        }
        int[] dp = new int[n + 1];
        Arrays.fill(dp, n + 1);
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            //采用取平方的方式，能更快速的定位
            for (int j = (int) Math.sqrt(i); j > 0; j--) {
                dp[i] = Math.min(dp[i], 1 + dp[i - j * j]);
            }
        }

        return dp[n];
    }

    /**
     * 287. Find the Duplicate Number
     *
     * Given an array nums containing n + 1 integers where each integer is between 1 and n
     * (inclusive),
     *
     * prove that at least one duplicate number must exist. Assume that there is only one duplicate
     * number,
     *
     * find the duplicate one.
     *
     * Example 1: Input: [1,3,4,2,2] Output: 2
     *
     * You must use only constant, O(1) extra space. Your runtime complexity should be less than
     * O(n2).
     *
     * 思路：快慢指针，相当于处理链表有环问题，把array看作是一个链表，index对应的value相当于指向下一个节点的指针
     *
     * 类似于: (0,1),(1,3),(2,4),(3,2)(4,2) => (0,1)->(1,3)->(3,2)->(2,4)->(4,2)
     */
    public int findDuplicate(int[] nums) {
        if (nums.length > 1) {
            int slow = nums[0];
            int fast = nums[nums[0]];
            while (slow != fast) {
                slow = nums[slow];
                fast = nums[nums[fast]];
            }

            fast = 0;
            while (fast != slow) {
                fast = nums[fast];
                slow = nums[slow];
            }
            return slow;
        }
        return -1;
    }

    /**
     * 最长递增子序列长度
     *
     * 300. Longest Increasing Subsequence
     *
     * Given an unsorted array of integers, find the length of longest increasing subsequence.
     *
     * Example:
     *
     * Input: [10,9,2,5,3,7,101,18]
     *
     * Output: 4 Explanation: The longest increasing subsequence is [2,3,7,101], therefore the
     * length is 4. Note:
     *
     * There may be more than one LIS combination, it is only necessary for you to return the
     * length. Your algorithm should run in O(n2) complexity.
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        int maxLength = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                    maxLength = Math.max(dp[i], maxLength);
                }
            }
        }
        return maxLength;
    }

    /**
     * 300. Longest Increasing Subsequence
     *
     * O(nlogn)的解法
     *
     * tails is an array storing the smallest tail of all increasing subsequences with length i+1 in
     * tails[i]. For example, say we have nums = [4,5,6,3], then all the available increasing
     * subsequences are:
     *
     * len = 1   :      [4], [5], [6], [3]   => tails[0] = 3
     *
     * len = 2   :      [4, 5], [5, 6] => tails[1] = 5
     *
     * len = 3   :      [4, 5, 6]            => tails[2] = 6
     */
    public int lengthOfLIS2(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int i = 0, j = size;
            while (i != j) {
                int m = (i + j) / 2;
                if (tails[m] < x) {
                    i = m + 1;
                } else {
                    j = m;
                }
            }
            tails[i] = x;
            if (i == size) {
                ++size;
            }
        }
        return size;
    }

    /**
     * 322. Coin Change
     *
     * You are given coins of different denominations and a total amount of money amount. Write a
     * function to compute the fewest number of coins that you need to make up that amount. If that
     * amount of money cannot be made up by any combination of the coins, return -1.
     *
     * Example 1:
     *
     * Input: coins = [1, 2, 5], amount = 11
     *
     * Output: 3 Explanation: 11 = 5 + 5 + 1
     */
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return 0;
        }

        //dp[i][j] 表示以0..i种货币换总数j的货币需要的最少货币数量
        int[][] dp = new int[coins.length][amount + 1];
        int max = Integer.MAX_VALUE;
        for (int j = 1; j < amount + 1; j++) {
            dp[0][j] = max;
            if ((j - coins[0] >= 0) && dp[0][j - coins[0]] != max) {
                dp[0][j] = dp[0][j - coins[0]] + 1;
            }
        }

        int left = 0;
        //状态方程为:dp[i][j]=Math.min(dp[i-1][j],(dp[i][j-arr[i]]+1))
        // dp[i-1][j]是上层循环的时候就计算好的结果，所以只需要计算同一层就可以了
        //这里其实已经体现张数了，因为同层的话，如果全用某一张货币的话，循环aim确实能体现张数
        for (int i = 1; i < coins.length; i++) {
            for (int j = 1; j < amount + 1; j++) {
                left = max;
                if (j - coins[i] > 0 && dp[i][j - coins[i]] != max) {
                    left = dp[i][j - coins[i]] + 1;
                }
                dp[i][j] = Math.min(left, dp[i - 1][j]);
            }
        }

        return dp[coins.length - 1][amount] != max ? dp[coins.length - 1][amount] : -1;
    }

    /**
     * 324. Wiggle Sort II
     *
     * Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
     *
     * Example 1:
     *
     * Input: nums = [1, 5, 1, 1, 6, 4]
     *
     * Output: One possible answer is [1, 4, 1, 5, 1, 6].
     *
     * 思路：https://leetcode.com/problems/wiggle-sort-ii/discuss/77682/Step-by-step-explanation-of-index-mapping-in-Java
     *
     * 思路其实就是找kth smallest element,当size为奇数时，k是size一半，当size为奇数时,k是size/2+1，等array一分为二，再依次左右递归
     */
    public void wiggleSort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int median = findKthLargest(nums, (nums.length + 1) / 2);
        int n = nums.length;

        int left = 0, i = 0, right = n - 1;
        while (i <= right) {
            if (nums[newIndex(i, n)] > median) {
                swap(nums, newIndex(left++, n), newIndex(i++, n));
            } else if (nums[newIndex(i, n)] < median) {
                swap(nums, newIndex(right--, n), newIndex(i, n));
            } else {
                i++;
            }
        }
    }

    private int newIndex(int index, int n) {
        return (1 + 2 * index) % (n | 1);
    }

    /**
     * 93. Restore IP Addresses
     *
     * Given a string containing only digits, restore it by returning all possible valid IP address
     * combinations.
     *
     * Example:
     *
     * Input: "25525511135" Output: ["255.255.11.135", "255.255.111.35"]
     */
    public List<String> restoreIpAddresses(String s) {
        List<String> resultList = new ArrayList<>();
        if (s == null && s.equals("")) {
            return resultList;
        }

        restoreIp(s, resultList, 0, "", 0);
        return resultList;
    }

    private void restoreIp(String ip, List<String> resultList, int start, String restored,
        int count) {
        if (count > 4) {
            return;
        }
        if (count == 4 && start == ip.length()) {
            resultList.add(restored);
            return;
        }

        for (int i = 1; i < 4; i++) {
            if (start + i > ip.length()) {
                break;
            }
            String s = ip.substring(start, start + i);
            if ((s.startsWith("0") && s.length() > 1) || (i == 3 && Integer.parseInt(s) >= 256)) {
                continue;
            }
            restoreIp(ip, resultList, start + i, restored + s + (count == 3 ? "" : "."), count + 1);
        }
    }

    /**
     * 328. Odd Even Linked List
     *
     * Given a singly linked list, group all odd nodes together followed by the even nodes. Please
     * note here we are talking about the node number and not the value in the nodes.
     *
     * You should try to do it in place. The program should run in O(1) space complexity and
     * O(nodes) time complexity.
     *
     * Example 1:
     *
     * Input: 1->2->3->4->5->NULL
     *
     * Output: 1->3->5->2->4->NULL
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode odd = head;
        ListNode evenHead = head.next;
        ListNode even = evenHead;

        while (odd.next != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;
        return head;
    }

    /**
     * 334. Increasing Triplet Subsequence
     *
     * Given an unsorted array return whether an increasing subsequence of length 3 exists or not in
     * the array.
     *
     * Formally the function should:
     *
     * Return true if there exists i, j, k
     *
     * such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
     *
     * Note: Your algorithm should run in O(n) time complexity and O(1) space complexity.
     *
     * Example 1:
     *
     * Input: [1,2,3,4,5] Output: true 思路：类似耀宗那个求最小的两个值的题目，只需要维护更新两个全局变量即可
     */
    public boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        //相当于维护三个全局变量，代表三个值，只是最后一个变量可以省去
        int small = Integer.MAX_VALUE, big = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= small) {
                small = nums[i];
            } else if (nums[i] <= big) {
                big = nums[i];
            } else {
                // return if you find a number bigger than both
                return true;
            }
        }

        return false;
    }

    /**
     * 347. Top K Frequent Elements
     *
     * Given a non-empty array of integers, return the k most frequent elements.
     *
     * Example 1:
     *
     * Input: nums = [1,1,1,2,2,3], k = 2 Output: [1,2]
     *
     * Note:
     *
     * You may assume k is always valid, 1 ≤ k ≤ number of unique elements. Your algorithm's time
     * complexity must be better than O(n log n), where n is the array's size.
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        //定义一个桶，bucket[i]表示出现频次为i次的key有多少
        List<Integer>[] bucket = new List[nums.length + 1];
        for (int n : map.keySet()) {
            int freq = map.get(n);
            if (bucket[freq] == null) {
                bucket[freq] = new LinkedList<>();
            }
            bucket[freq].add(n);
        }

        List<Integer> res = new ArrayList<>();
        for (int i = bucket.length - 1; i >= 0 && res.size() < k; i--) {
            if (bucket[i] != null) {
                res.addAll(bucket[i]);
            }
        }

        return res;
    }

    /**
     * [128] 最长连续序列 * 给定一个未排序的整数数组，找出最长连续序列的长度。
     *
     * 要求算法的时间复杂度为 O(n)。
     *
     * 示例:
     *
     * 输入: [100, 4, 200, 1, 3, 2] 输出: 4
     *
     * 解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Map<Integer, Integer> elementsMap = new HashMap<>();
        elementsMap.put(nums[0], 1);
        int maxLength = 1;
        for (int i = 1; i < nums.length; i++) {
            if (!elementsMap.containsKey(nums[i])) {
                int left = elementsMap.getOrDefault(nums[i] - 1, 0);
                int right = elementsMap.getOrDefault(nums[i] + 1, 0);

                //如果左右都有了的话，相当于把左右两边连了起来
                int curr = 1 + left + right;
                if (curr > maxLength) {
                    maxLength = curr;
                }

                //更新区间
                elementsMap.put(nums[i], curr);
                elementsMap.put(nums[i] - left, curr);
                elementsMap.put(nums[i] + right, curr);
            }
        }

        return maxLength;
    }

    public List<List<Integer>> findSubsequences(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        if (nums == null || nums.length == 0) {
            return new ArrayList<>(res);
        }

        subsequencesHelper(res, new ArrayList<>(), nums, 0);
        return new ArrayList<>(res);
    }

    public void subsequencesHelper(Set<List<Integer>> resultList, List<Integer> tempList,
        int[] nums, int start) {
        if ((tempList.size() >= 2)) {
            resultList.add(new ArrayList<>(tempList));
        }
        if (start == nums.length) {
            return;
        }

        for (int i = start; i < nums.length; i++) {
            if (tempList.isEmpty() || tempList.get(tempList.size() - 1) <= nums[i]) {
                tempList.add(nums[i]);
                subsequencesHelper(resultList, tempList, nums, i + 1);
                tempList.remove(tempList.size() - 1);
            }
        }
    }
}
