package com.jacobs.basic.algorithm.leetcode;

import com.google.common.collect.Lists;
import com.jacobs.basic.algorithm.TreeNode;
import com.jacobs.basic.models.ListNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author lichao
 * @date 2017/11/1
 */
public class Problems_03 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);

        //System.out.println(pathSum_033(root, 22));
        System.out.println(maxDepth(root));

//    LinkedList<Integer> rawList = new LinkedList<>();
//    rawList.add(1);
//    rawList.add(2);
//    rawList.add(3);
//
//    printAll_036(rawList, new ArrayList<>(), 2);

        //System.out.println(getMaxLengthSubListForFixedSum_038(new int[]{1, 2, 1, 1, 1}, 3));
//    System.out.println(sortedArrayToBST_041(new int[]{0}));

        printAll_036(Lists.newArrayList(1, 2, 3, 4, 5), Lists.newArrayList(), 3);
        printAll_036_02("", 3, Lists.newArrayList("1", "2", "3", "4"));
    }

    //  Given a binary tree
//  struct TreeLinkNode {
//    TreeLinkNode *left;
//    TreeLinkNode *right;
//    TreeLinkNode *next;
//  }
//
//  Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set toNULL.
//      Initially, all next pointers are set toNULL.
//  Note:
//  You may only use constant extra space.
//  You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
//  For example,
//  Given the following perfect binary tree,
//       1
//      /  \
//      2    3
//      / \  / \
//      4  5  6  7
//
//  After calling your function, the tree should look like:
//      1 -> NULL
//     /  \
//    2 -> 3 -> NULL
//  / \  / \
// 4->5->6->7 -> NULL
    public static void connect_029(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        //只需要last 和 nlast即可
        TreeNode currentLevelNode = root;
        TreeNode nextLevelNode = root;
        TreeNode lastNode = null;
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            if (lastNode != null) {
                lastNode.next = temp;
            }
            if (temp == currentLevelNode) {
                temp.next = null;
                lastNode = null;
            } else {
                lastNode = temp;
            }

            if (temp.left != null) {
                nextLevelNode = temp.left;
                queue.add(temp.left);
            }
            if (temp.right != null) {
                nextLevelNode = temp.right;
                queue.add(temp.right);
            }

            //该换行了
            if (temp == currentLevelNode) {
                currentLevelNode = nextLevelNode;
            }
        }
    }


    //  Given a string S and a string T, count the number of distinct subsequences of T in S.
//  A subsequence of a string is a new string which is formed from the original string by
//  deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters.
//  (ie,"ACE"is a subsequence of"ABCDE"while"AEC"is not).
//  Here is an example:
//  S ="rabbbit", T ="rabbit"
//  Return3.
  /*
   *  思路：dp题。
   *  状态定义：dp[i][j]代表s[0~i-1]中T[0~j-1]不同子串的个数。
   *  递推关系式：S[i-1]!= T[j-1]：  DP[i][j] = DP[i][j-1] （不选择S中的s[i-1]字符）
   *              S[i-1]==T[j-1]： DP[i][j] = DP[i-1][j-1]（选择S中的s[i-1]字符） + DP[i][j-1]
   *  初始状态：第0列：DP[i][0] = 0，第0行：DP[0][j] = 1
   */
    public static int numDistinct_030(String S, String T) {
        if (S == null || T == null) {
            return 0;
        }

        char[] sChars = S.toCharArray();
        char[] tChars = T.toCharArray();
        int row = sChars.length + 1;
        int col = tChars.length + 1;
        int[][] dp = new int[row][col];

        for (int i = 1; i < col; i++) {
            dp[0][i] = 0;
        }

        for (int j = 0; j < row; j++) {
            dp[j][0] = 1;
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (sChars[i - 1] == tChars[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[row - 1][col - 1];
    }

    //空间压缩法
    public static int numDistinct_031(String S, String T) {
        return 0;
    }


    //  Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
//  For example:
//  Given the below binary tree andsum = 22,
//       5
//      / \
//      4   8
//      /   / \
//      11  13  4
//      /  \      \
//      7    2      1
//      return true, as there exist a root-to-leaf path5->4->11->2which sum is 22.
    public static boolean hasPathSum_032(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        if (sum == root.val && root.left == null && root.right == null) {
            return true;
        }

        return hasPathSum_032(root.left, sum - root.val) ||
                hasPathSum_032(root.right, sum - root.val);
    }

    //  return
//      [
//      [5,4,11,2],
//      [5,8,4,5]
//      ]
    public static ArrayList<ArrayList<Integer>> pathSum_033(TreeNode root, int sum) {
        ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
        getPathSumResult(resultList, root, sum, new ArrayList<>());
        return resultList;
    }

    //先序遍历
    public static void getPathSumResult(ArrayList<ArrayList<Integer>> resultList, TreeNode root,
                                        int sum, List<TreeNode> onePathResult) {
        if (root == null) {
            return;
        }

        if (sum == root.val && root.left == null && root.right == null) {
            onePathResult.add(root);
            ArrayList<Integer> result = new ArrayList<>();
            for (TreeNode node : onePathResult) {
                result.add(node.val);
            }
            //还原
            resultList.add(result);
            onePathResult.remove(root);
            return;
        }

        onePathResult.add(root);
        getPathSumResult(resultList, root.left, sum - root.val, onePathResult);
        getPathSumResult(resultList, root.right, sum - root.val, onePathResult);
        //还原
        onePathResult.remove(root);
    }

    //  Given a binary tree, determine if it is height-balanced.
//      For this problem, a height-balanced binary tree is defined as a binary tree
//  in which the depth of the two subtrees of every node never differ by more than 1.
    //分别计算左右子树的高度
    public static boolean isBalanced_034(TreeNode root) {
        if (root == null) {
            return true;
        }

        int left = getHeight(root.left);
        int right = getHeight(root.right);
        if (Math.abs(left - right) > 1) {
            return false;
        }

        return isBalanced_034(root.left) && isBalanced_034(root.right);
    }

    public static int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = getHeight(root.left) + 1;
        int right = getHeight(root.right) + 1;
        return left > right ? left : right;
    }

    //从1到n输出数字全排序
    //解决小问题，将1，2-n看作是两个数，以此类推
    public static void printAll_035(int[] arr, int n, int i) {
        if (i == n - 1) {
            for (int temp : arr) {
                System.out.print(temp + " ");
            }
            System.out.println();
            return;
        }

        for (int j = i; j < n; j++) {
            swap(arr, i, j);
            printAll_035(arr, n, i + 1);
            //记得还原
            swap(arr, j, i);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //m个数据集合中选出n个数据，对n个数据进行全排列，打印全排列结果
    public static void printAll_036(List<Integer> rawList, List<Integer> result,
                                    int n) {
        for (int i = 0; i < rawList.size(); i++) {
            //保证同一层的集合不变
            LinkedList<Integer> tempList = new LinkedList<>(rawList);
            result.add(rawList.get(i));
            tempList.remove(rawList.get(i));
            if (n == 1) {
                for (int temp : result) {
                    System.out.print(temp + " ");
                }
                System.out.println();
            } else {
                printAll_036(tempList, result, n - 1);
            }
            result.remove(rawList.get(i));
        }
    }

    public static void printAll_036_02(String s, int total, List lst) {
        for (int i = 0; i < lst.size(); i++) {
            List templst = new LinkedList(lst);
            String n = (String) templst.remove(i);// 取出来的数字
            String str = s + n;
            if (total == 1) {
                System.out.println(str);//以最极端 n个里面只取一个，直接把取出来的结果输出即可

            } else {
                int temp = total - 1;//在同一层中total总量不能减,不能再原有变量的基础上
                printAll_036_02(str, temp, templst);// 这里的temp以及templst都是全新的变量和集合
            }
        }

    }

    //未排序正数数组中累加和为定值的最长子数组长度
    public static int getMaxLengthSubListForFixedSum_037(int[] arr, int sum) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        //滑动窗口问题
        int tempSum = 0;
        int pre = 0;
        int aft = 0;
        int maxLength = 0;

        while (aft < arr.length) {
            tempSum += arr[aft];
            while (tempSum >= sum) {
                if (tempSum == sum) {
                    maxLength = Math.max(maxLength, aft - pre + 1);
                }
                tempSum -= arr[pre];
                pre++;
            }
            aft++;
        }

        return maxLength;
    }

    //未排序数组中（元素可正可负可0）累加和为给定值的最长子数组系列问题
    //s(j..i)=s(i)-s(j-1) 定义一个map 只记录每一个累加和最早出现的位置
    public static int getMaxLengthSubListForFixedSum_038(int[] arr, int sum) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        Map<Integer, Integer> sumIndxMap = new HashMap<>();
        int maxLength = 0;
        int tempSum = 0;

        for (int i = 0; i < arr.length; i++) {
            tempSum += arr[i];
            if (sumIndxMap.containsKey(tempSum - sum)) {
                maxLength = Math.max(i - sumIndxMap.get(tempSum - sum), maxLength);
            }
            if (!sumIndxMap.containsKey(tempSum)) {
                sumIndxMap.put(tempSum, i);
            }
        }

        return maxLength;
    }

    /**
     * 子矩阵的最大累加和问题（深度利用了上一题的思想） 如何求必须含有两行元素的子矩阵的最大累加和？可以把两行的元素累加，得到累加数组，对累加数组求最大子数组累加和。
     *
     * @param m 矩阵
     * @return 最大子矩阵的和
     */
    public static int getMaxSubMatrixSum_039(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        int cur = 0;
        int[] s = null;//累加数组

        for (int i = 0; i != m.length; i++) {
            s = new int[m[0].length];
            for (int j = i; j != m.length; j++) {
                cur = 0;
                for (int k = 0; k != s.length; k++) {
                    //与上题一致
                    s[k] += m[j][k];
                    cur += s[k];
                    max = Math.max(max, cur);
                    cur = cur < 0 ? 0 : cur;
                }
            }
        }

        return max;
    }

    //Given a singly linked list where elements are sorted in ascending order,
    // convert it to a height balanced BST.
    // 目的其实就是要找到链表的中间节点，这个可以通过快慢指针搞定
    public static TreeNode sortedListToBST_040(ListNode head) {
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            return new TreeNode(head.val);
        }

        //快慢指针
        ListNode mid = head;
        ListNode end = head;
        ListNode preMid = null;

        while (end != null && end.next != null) {
            preMid = mid;
            mid = mid.next;
            end = end.next.next;
        }

        TreeNode root = new TreeNode(mid.val);
        preMid.next = null;
        root.left = sortedListToBST_040(head);
        root.right = sortedListToBST_040(mid.next);

        return root;
    }

    //Given an array where elements are sorted in ascending order,
    // convert it to a height balanced BST.
    public static TreeNode sortedArrayToBST_041(int[] num) {
        if (num == null || num.length == 0) {
            return null;
        }
        return constructTreeNode(num, 0, num.length - 1);
    }

    public static TreeNode constructTreeNode(int[] num, int low, int high) {
        if (low > high) {
            return null;
        }
        if (low == high) {
            return new TreeNode(num[low]);
        }

        int mid = (low + high + 1) / 2;
        TreeNode root = new TreeNode(num[mid]);
        root.left = constructTreeNode(num, low, mid - 1);
        root.right = constructTreeNode(num, mid + 1, high);
        return root;
    }

    //  Given a binary tree, return the bottom-up level order traversal of its nodes' values.
    // (ie, from left to right, level by level from leaf to root).
//  For example:
//  Given binary tree{3,9,20,#,#,15,7},
//      3
//      / \
//      9  20
//      /  \
//      15   7
//
//      return its bottom-up level order traversal as:
//      [
//      [15,7]
//      [9,20],
//      [3],
//      ]
    public static ArrayList<ArrayList<Integer>> levelOrderBottom_042(TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            ArrayList<Integer> tempRes = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode temp = queue.poll();
                tempRes.add(temp.val);
                if (temp.left != null) {
                    queue.add(temp.left);
                }
                if (temp.right != null) {
                    queue.add(temp.right);
                }
            }
            res.add(0, tempRes);
        }

        return res;
    }

    //  Given inorder and postorder traversal of a tree, construct the binary tree.
    //You may assume that duplicates do not exist in the tree.
    public static TreeNode buildTree_043(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null || inorder.length != postorder.length) {
            return null;
        }

        return constructTree(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);
    }

    public static TreeNode constructTree(int[] inorder, int[] postorder, int inStart, int inEnd,
                                         int postStart, int postEnd) {

        if (inStart > inEnd) {
            return null;
        }

        int midRoot = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == postorder[postEnd]) {
                midRoot = i;
            }
        }

        int leftLen = midRoot - inStart;
        TreeNode root = new TreeNode(inorder[midRoot]);
        root.left = constructTree(inorder, postorder, inStart, midRoot - 1, postStart,
                postStart + leftLen - 1);
        root.right = constructTree(inorder, postorder, midRoot + 1, inEnd, postStart + leftLen,
                postEnd - 1);

        return root;
    }

    public static TreeNode buildTree_044(int[] preorder, int[] inorder) {
        if (inorder == null || preorder == null || inorder.length != preorder.length) {
            return null;
        }

        return construceTree_02(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public static TreeNode construceTree_02(int[] preorder, int[] inorder, int preStart, int preEnd,
                                            int inStart, int inEnd) {

        if (inStart > inEnd) {
            return null;
        }

        int midIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == preorder[preStart]) {
                midIndex = i;
            }
        }

        int rightLen = midIndex - inStart;
        TreeNode root = new TreeNode(inorder[midIndex]);
        root.left = construceTree_02(preorder, inorder, preStart + 1, preStart + rightLen, inStart,
                midIndex - 1);
        root.right = construceTree_02(preorder, inorder, preStart + rightLen + 1, preEnd, midIndex + 1,
                inEnd);

        return root;
    }


    //  Given a binary tree, find its maximum depth.
//  The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int lDepth = maxDepth(root.left);
        int rDepth = maxDepth(root.right);

        return 1 + (lDepth > rDepth ? lDepth : rDepth);
    }
}
