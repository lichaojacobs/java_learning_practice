package com.jacobs.basic.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author lichao
 * @date 2017/11/10
 */
public class Practice {

    public static void main(String[] args) {
        //    TreeNode root = new TreeNode(1);
        //////    root.left = new TreeNode(-2);
        //////    root.right = new TreeNode(3);
        //////    System.out.println(maxPathSum(root));
        //        double a = new BigDecimal(3.23557)
        //                .setScale(3, BigDecimal.ROUND_UP).doubleValue() * 10000;
        //        List<List<Long>> lists = new ArrayList<>();
        //        double logRatio = (new BigDecimal((double) (209129 - 210482) / 209129)
        //                .setScale(4, BigDecimal.ROUND_DOWN).doubleValue() * 10000);
        //
        //        double diffRatio = new BigDecimal((double) (330037 - 316478) / 330037)
        //                .setScale(4, BigDecimal.ROUND_DOWN).doubleValue() * 10000;
        //
        //        lists.add(Lists.newArrayList(1L, 2L));
        //        lists.add(Lists.newArrayList(3L, 4L));
        //        System.out.println(a);
        //        //System.out.println(String.format("list: %s", lists));
        //        System.out.println((int) Math.abs(diffRatio));
        //System.out.println(calculateGrowth());
        //        System.out.println(MoreThanHalfNum_Solution(new int[]{1, 2, 3, 2, 2, 2, 5, 4, 2}));
        getTwoMinNumber(new int[]{2, 2, 3, 2, 1, 2, 5, 3, 2}).stream().forEach(System.out::println);
    }

    public static void mergeSort(int[] arr, int left, int right) {
        if (arr == null || arr.length == 0 || left >= right) {
            return;
        }

        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, right, mid);
    }

    public static void merge(int[] arr, int left, int right, int mid) {
        int[] tempArr = new int[right + 1];
        int tempLeft = left;
        int tempMid = mid + 1;

        int index = 0;
        while (tempLeft <= mid && tempMid <= right) {
            if (tempArr[tempLeft] >= tempArr[tempMid]) {
                tempArr[index++] = arr[tempMid++];
            } else {
                tempArr[index++] = arr[tempLeft++];
            }
        }

        if (tempLeft <= mid) {
            tempArr[index++] = arr[tempLeft++];
        }

        if (tempMid <= right) {
            tempArr[index++] = arr[tempMid++];
        }

        //复制到原数组
        for (int i = left; i <= right; i++) {
            arr[i] = tempArr[i];
        }
    }


    /**
     * 获取一棵树中，两个节点的最大距离
     *
     * @param root root 根节点
     * @return distance 最大距离
     */
    public static int getMaxDistance(TreeNode root, int[] res) {
        if (root == null) {
            res[0] = 0;
            return 0;
        }

        int leftMax = getMaxDistance(root.left, res);
        int maxFromLeft = res[0];

        int rightMax = getMaxDistance(root.right, res);
        int maxFromRight = res[0];

        int currNodeMax = maxFromLeft + maxFromRight + 1;
        res[0] = Math.max(leftMax, rightMax) + 1;

        return Math.max(Math.max(leftMax, rightMax), currNodeMax);
    }

    /**
     * 在其他数都出现k次的数组中找到只出现一次的数
     *
     * @param arr 整形数组
     * @param k k 进制
     * @return 返回只出现一次的数
     */
    public static int onceNum(int[] arr, int k) {
        int[] result = new int[32];
        for (int i = 0; i < arr.length; i++) {
            int[] kResult = getKvalue(arr[i], k);
            for (int j = 0; j < result.length; j++) {
                result[j] = (result[j] + kResult[j]) % k;
            }
        }

        return changeToTenValue(result, k);
    }

    public static int[] getKvalue(int num, int k) {
        int[] res = new int[32];

        int index = 0;
        while (num != 0) {
            res[index] = num % k;
            num = num / k;
            index++;
        }

        return res;
    }

    public static int changeToTenValue(int[] result, int k) {
        int num = 0;
        for (int i = result.length - 1; i >= 0; i--) {
            num = num * k + result[i];
        }

        return num;
    }


    /**
     * 判断一棵树是否是搜索二叉树
     *
     * @param root 根节点
     * @return 返回是否是搜索二叉树
     */
    public static boolean isValidBST(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode currentNode = root;
        TreeNode preNode = null;

        while (!stack.isEmpty() || currentNode != null) {
            if (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            } else {
                currentNode = stack.pop();
                if (preNode != null && (preNode.val >= currentNode.val)) {
                    return false;
                }
                preNode = currentNode;
                currentNode = currentNode.right;
            }
        }

        return true;
    }

    //Given preorder and inorder traversal of a tree, construct the binary tree.
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }

        return constructNode(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode constructNode(int[] preorder, int[] inorder, int preStart, int preEnd,
        int inStart, int inEnd) {
        if (preStart < preEnd) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[preStart]);
        int index = inStart;
        for (; index <= inEnd; index++) {
            if (inorder[index] == preorder[preStart]) {
                break;
            }
        }

        root.left = constructNode(preorder, inorder, preStart + 1, preStart + index - inStart,
            inStart, index - 1);
        root.right = constructNode(preorder, inorder, preStart + index - inStart + 1, preEnd,
            index + 1, inEnd);

        return root;
    }

    // Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
    // Given the sorted array: [-10,-3,0,5,9],
    //
    //  One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
    //
    //      0
    //      / \
    //      -3   9
    //      /   /
    //      -10  5
    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        return constructBST(nums, 0, nums.length - 1);
    }

    public static TreeNode constructBST(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        TreeNode head = new TreeNode(mid);
        head.left = constructBST(nums, start, mid - 1);
        head.right = constructBST(nums, mid + 1, end);
        return head;
    }

    //  Given a binary tree and a sum, determine if the tree has a root-to-leaf
    //  path such that adding up all the values
    //  along the path equals the given sum.
    public static boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        if ((root.left == null && root.right == null)) {
            if (root.val == sum) {
                return true;
            } else {
                return false;
            }
        }

        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    //  Given a binary tree, find the maximum path sum.
    //
    //  For this problem, a path is defined as any sequence of nodes from some starting node
    //
    //  to any node in the tree along the parent-child connections. The path must contain at least one node
    //
    //  and does not need to go through the root.
    //无非来自三个地方，左边，右边，跨节点，或者就是当前节点
    public static int maxPathSum(TreeNode root) {
        return help(root, new int[1]);
    }

    public static int help(TreeNode root, int[] res) {
        if (root == null) {
            res[0] = 0;
            return Integer.MIN_VALUE;
        }

        int leftMax = help(root.left, res);
        int maxFromLeft = res[0];

        int rightMax = help(root.right, res);
        int maxFromRight = res[0];

        int current = maxFromLeft + maxFromRight + root.val;
        res[0] = Math.max(maxFromLeft, maxFromRight) + root.val;

        //返回最大的情况
        return Math.max(Math.max(Math.max(leftMax, rightMax), current), root.val);
    }

    //杨辉三角
    public static void printYFTriangle() {
        int lines = 8;
        int[] a = new int[lines + 1];
        int previous = 1;
        for (int i = 1; i <= lines; i++) {
            for (int j = 1; j <= i; j++) {
                int current = a[j];
                a[j] = previous + current;
                previous = current;
                System.out.print(a[j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 搜狐大数据开发笔试题（替别人答）
     */
    public static int calculateGrowth() {
        Scanner intput = new Scanner(System.in);
        int rows = intput.nextInt();
        if (rows <= 0) {
            return -1;//非法值
        }

        int dailyGrowth = 0;
        int missionGrowth = 0;
        int[] maxDailyGrowth = new int[256];
        for (int i = 0; i < maxDailyGrowth.length; i++) {
            maxDailyGrowth[i] = Integer.MIN_VALUE;
        }

        while (rows > 0) {
            String line = intput.next();
            List<Integer> lineArr = Arrays.stream(line.split(",")).map(s -> Integer.valueOf(s))
                .collect(Collectors.toList());

            //每日增长
            if (lineArr.get(0) == 1) {
                int start = lineArr.get(1);
                int end = lineArr.get(2);
                int value = lineArr.get(3);
                for (int i = start; i <= end; i++) {
                    if (maxDailyGrowth[i] > 0) {
                        if (value > maxDailyGrowth[i]) {
                            dailyGrowth = dailyGrowth + value - maxDailyGrowth[i];
                        }
                    } else {
                        dailyGrowth = dailyGrowth + value;
                    }
                    maxDailyGrowth[i] = Math.max(value, maxDailyGrowth[i]);
                }
            } else if (lineArr.get(0) == 2) {
                //任务增长
                missionGrowth += lineArr.get(2);
            }
            rows--;
        }

        return dailyGrowth + missionGrowth;
    }


    public static void coins() {
        Scanner input = new Scanner(System.in);
        int loop = input.nextInt();
        while (loop > 0) {
            String[] defineStrArr = input.next().split(" ");
            int numberOfCoins = Integer.valueOf(defineStrArr[0]);
            int target = Integer.valueOf(defineStrArr[1]);

            List<Integer> coninsArr = Arrays.stream(input.next().split(" ")).map(Integer::valueOf)
                .collect(Collectors.toList());
            if (numberOfCoins == coninsArr.size()) {
                System.out.println(changeCoins(coninsArr, target));
            }
            loop--;
        }

    }

    public static int changeCoins(List<Integer> arr, int aim) {
        if (arr == null || arr.size() == 0 || aim < 0) {
            return 0;
        }

        //dp[i][j] 使用0..i种货币，换j面值的钱的方法数
        int[][] dp = new int[arr.size()][aim + 1];

        for (int i = 0; i < arr.size(); i++) {
            //组成金钱0的种数为1
            dp[i][0] = 1;
        }

        for (int j = 0; arr.get(0) * j < aim; j++) {
            dp[0][arr.get(0) * j] = 1;
        }

        int num;
        for (int i = 1; i < arr.size(); i++) {
            for (int j = 1; j <= aim; j++) {
                num = 0;
                for (int k = 0; j - arr.get(j) * k >= 0; k++) {
                    num += dp[i - 1][j - arr.get(j) * k];
                }
                dp[i][j] = num;
            }
        }

        return dp[arr.size() - 1][aim];
    }

    //    数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
    //    例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
    //    由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
    public static int MoreThanHalfNum_Solution(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int times = 1;
        int number = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] == number) {
                times++;
            } else if (times > 0) {
                times--;
            } else {
                number = array[number];
                times = 1;
            }
        }

        times = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == number) {
                times++;
            }
        }

        return times > array.length / 2 ? number : 0;
    }

    //输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        int length = input.length;
        if (k > length || k == 0) {
            return result;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        for (int i = 0; i < length; i++) {
            if (maxHeap.size() != k) {
                maxHeap.offer(input[i]);
            } else if (maxHeap.peek() > input[i]) {
                Integer temp = maxHeap.poll();
                temp = null;
                maxHeap.offer(input[i]);
            }
        }
        for (Integer integer : maxHeap) {
            result.add(integer);
        }
        return result;
    }

    /**
     * 侯耀宗快手面试题
     */
    public static ArrayList<Integer> getTwoMinNumber(int[] nums) {
        ArrayList<Integer> resultList = new ArrayList<>();
        if (nums == null || nums.length <= 1) {
            return resultList;
        }

        int firstMin = Integer.MAX_VALUE;
        int secondMin = Integer.MAX_VALUE;
        int thridMin = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < firstMin) {
                thridMin = secondMin;
                secondMin = firstMin;
                firstMin = nums[i];
            } else if (nums[i] < secondMin && nums[i] != firstMin) {
                thridMin = secondMin;
                secondMin = nums[i];
            } else if (nums[i] < thridMin && nums[i] != secondMin && nums[i] != firstMin) {
                thridMin = nums[i];
            }
        }

        resultList.add(firstMin);
        resultList.add(secondMin - firstMin > 1 ? secondMin : thridMin);
        return resultList;
    }

    //values数组分别保存左右子树最大搜索二叉树的节点数，最小值，和最大值
    public static TreeNode getSearchTreeWithMaxNodes(TreeNode treeNode, int[] values) {
        if (treeNode == null) {
            values[0] = 0;
            values[1] = Integer.MIN_VALUE;
            values[2] = Integer.MAX_VALUE;
            return treeNode;
        }

        int value = treeNode.val;
        TreeNode left = treeNode.left;
        TreeNode right = treeNode.right;
        TreeNode lBst = getSearchTreeWithMaxNodes(treeNode.left, values);

        int lSize = values[0];
        int lMin = values[1];
        int lMax = values[2];

        TreeNode rBst = getSearchTreeWithMaxNodes(treeNode.left, values);
        int rSize = values[0];
        int rMin = values[1];
        int rMax = values[2];

        //首先看是否满足第一种情况:以当前节点为头，当然也得确定左右子树的头节点与获取到搜索树的头节点相同
        if (left == lBst && right == rBst && value >= lMax && value <= rMin) {
            values[0] = lSize + rSize + 1;
            return treeNode;
        } else {
            values[0] = Math.max(lSize, rSize);
            return lSize > rSize ? lBst : rBst;
        }
    }

    /**
     * 找到搜索二叉树的两个位置错误的节点，中序遍历即可
     */
    public static TreeNode[] getTwoErrorNodes(TreeNode root) {
        TreeNode[] errs = new TreeNode[2];
        if (root == null) {
            return errs;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode preNode = null;
        TreeNode head = root;
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                //pop
                TreeNode curr = stack.pop();
                if (preNode != null) {
                    if (preNode.val > curr.val) {
                        if (errs[0] == null) {
                            errs[0] = curr;
                        } else {
                            errs[1] = curr;
                            //找到两个节点，不需要继续遍历了
                            break;
                        }
                        preNode = curr;
                        head = curr.right;
                    }
                }
            }
        }

        return errs;
    }


    /**
     * 判断一个后续遍历的数组是否是搜索二叉树
     */
    public boolean isPostArrSearchTree(int[] postArr) {
        if (postArr == null || postArr.length == 0) {
            return false;
        }

        //递归的搞，比根节点大的在右边，比根节点小的在左边
        return isPost(postArr, 0, postArr.length - 1);
    }

    public boolean isPost(int[] arr, int start, int end) {
        if (start == end) {
            return true;
        }

        int less = -1;
        int more = end;
        for (int i = start; i < end; i++) {
            if (arr[i] < arr[end]) {
                less = i;
            } else {
                more = more == end ? i : more;
            }
        }

        //如果没有左子树或右子树
        if (less == -1 || more == end) {
            return isPost(arr, start, end - 1);
        }
        //如果不满足单调性
        if (less != more - 1) {
            return false;
        }
        return isPost(arr, start, less) && isPost(arr, more, end - 1);
    }

    /**
     * 根据后续遍历构建搜索二叉树
     */
    public TreeNode constructBST(int[] postArr) {
        if (postArr == null || postArr.length == 0) {
            return null;
        }

        return constructBSTHelper(postArr, 0, postArr.length - 1);
    }

    public TreeNode constructBSTHelper(int[] postArr, int start, int end) {
        if (start >= end) {
            return null;
        }

        TreeNode root = new TreeNode(postArr[end]);
        int less = -1;
        int more = end;
        for (int i = start; i < end; i++) {
            if (postArr[i] < postArr[end]) {
                less = i;
            } else {
                more = more == end ? i : more;
            }
        }
        root.left = constructBST(postArr, start, less);
        root.right = constructBST(postArr, more, end - 1);

        return root;
    }

    /**
     * 从二叉树的中续遍历中找到某个节点的后续节点
     */
    public TreeNode getNextNodeFromInTraverse(TreeNode node) {
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            return getLeftMost1(node);
        } else {
            TreeNode parent = node.parent;
            //如果不是头节点，且不是父节点的左节点
            while (parent != null && parent.left != node) {
                node = parent;
                parent = node.parent;
            }

            return parent;
        }
    }

    public TreeNode getLeftMost1(TreeNode node) {
        if (node == null) {
            return node;
        }

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    /**
     * 统计和生成所有不同的二叉树
     *
     * 给定整数n，n<1代表空树结构，否则代表中序遍历的结果为{1,2,3,...,N}。请返回可能的二叉树结构有多少
     *
     * 思路：以1为头节点的，不可能有左子树，故而以1为头节点有多少种可能的结构，完全取决于1的右子树有多少种可能结构，1的右子树有N-1个节点，所以有num(N-1)种可能
     */
    public int numTrees(int n) {
        if (n < 2) {
            return 1;
        }
        //使用动态规划，减少重复计算
        int[] num = new int[n + 1];
        num[0] = 1;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < i + 1; j++) {
                //以j作为二叉树头节点
                num[j] = num[j - 1] * num[i - j];
            }
        }

        return num[n];
    }

    /**
     * 进阶：统计和生成所有不同的二叉树，并返回所有二叉树的头节点
     */
    public List<TreeNode> generateTrees(int n) {
        return generate(1, n);
    }

    public List<TreeNode> generate(int start, int end) {
        List<TreeNode> resultNodes = new LinkedList<>();
        if (start > end) {
            resultNodes.add(null);
        }
        TreeNode head = null;
        for (int i = start; i < end + 1; i++) {
            head = new TreeNode(i);
            //得出所有可能的左子树list
            List<TreeNode> leftNodes = generate(start, i - 1);
            List<TreeNode> rightNodes = generate(i + 1, end);
            for (TreeNode l : leftNodes) {
                for (TreeNode r : rightNodes) {
                    head.left = l;
                    head.right = r;
                    resultNodes.add(cloneTree(head));
                }
            }
        }
        return resultNodes;
    }

    public TreeNode cloneTree(TreeNode head) {
        if (head == null) {
            return null;
        }

        TreeNode newHead = new TreeNode(head.val);
        newHead.left = cloneTree(head.left);
        newHead.right = cloneTree(head.right);
        return newHead;
    }

    /**
     * 统计完全二叉树的节点数：给定一颗完全二叉树的头节点head，返回这颗树的节点个数；要求时间负责度低于O(N)的解法
     *
     * 看树的最左节点能到哪一层
     *
     * 层数记为h, 整个递归过程记为bs(node,l,h)，node表示当前节点，l表示node所在的层数，h表示整颗树的层数是始终不变的
     *
     * bs(node, l ,h )的返回值表示以node为头的完全二叉树的节点数是多少
     *
     * 1，右子树的最左节点能到达最后一层的情况: 说明左子树是满二叉树, 整体结果就是 2^(h-l)+bs(node.right,l+1,h)
     *
     * 2.右子树的最左节点没有到最后一层：说明整颗右子树都是满二叉树，且层数为h-l-1，整体结果就是 2^(h-l-1)+bs(node.left,l+1,h)
     */
    public int getTreeNodesNumOfBST(TreeNode head) {
        if (head == null) {
            return 0;
        }

        return bs(head, 1, getMostLeftLevel(head, 1));
    }

    public int bs(TreeNode curr, int l, int h) {
        if (l == h) {
            //叶子节点，直接返回1到上层累加即可
            return 1;
        }

        //看看右子树是否到达了最后一层
        if (getMostLeftLevel(curr.right, l + 1) == h) {
            return (1 << (h - l)) + bs(curr.right, l + 1, h);
        } else {
            return (1 << (h - l - 1)) + bs(curr.left, l + 1, h);
        }
    }

    public int getMostLeftLevel(TreeNode head, int level) {
        while (head != null) {
            level++;
            head = head.left;
        }

        return level - 1;
    }

    /**
     * 左神动态规划：判断目标字符串是否由另外两个字符串交叉组成（字符串内部相对顺序不变）
     */
    public boolean isCrossStr(String str1, String str2, String aim) {
        if (str1 == null || str2 == null || aim == null) {
            return false;
        }

        char[] str1Chars = str1.toCharArray();
        char[] str2Chars = str2.toCharArray();
        char[] aimChars = aim.toCharArray();
        if (aimChars.length != str1Chars.length + str2Chars.length) {
            return false;
        }

        boolean[][] dp = new boolean[str1Chars.length + 1][str2Chars.length + 1];
        dp[0][0] = true;
        for (int i = 1; i <= str1Chars.length; i++) {
            if (str1Chars[i - 1] != aimChars[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }

        for (int j = 1; j <= str2Chars.length; j++) {
            if (str2Chars[j - 1] != aimChars[j - 1]) {
                break;
            }
            dp[0][j] = true;
        }

        for (int i = 1; i <= str1Chars.length; i++) {
            for (int j = 1; j <= str2Chars.length; j++) {
                if ((str1.charAt(i - 1) == aim.charAt(i + j - 1) && dp[i - 1][j]) ||
                    (str2.charAt(j - 1) == aim.charAt(i + j - 1) && dp[i][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }
        return dp[str1Chars.length][str2Chars.length];
    }


    /**
     * 跳跃游戏
     */
    public int getTheLeastJump(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }
        //表示跳到位置i所需要的最少次数
        int jump = 0;
        int maxAttach = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (maxAttach < arr[i] + i) {
                jump++;
                maxAttach = arr[i] + i;
            }
        }

        return jump;
    }

    /**
     * n 皇后问题
     */
    public int NQueen(int n) {
        if (n < 1) {
            return n;
        }
        int[] records = new int[n];
        return NQueenHelper(n, 0, records);
    }

    public int NQueenHelper(int n, int row, int[] records) {
        if (row == n) {
            return 1;
        }
        int res = 0;
        for (int col = 0; col < n; col++) {
            if (isNQueenValid(records, row, col)) {
                records[row] = col;
                res += NQueenHelper(n, row + 1, records);
            }
        }

        return res;
    }

    public boolean isNQueenValid(int[] records, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (col == records[i] || Math.abs(records[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }
}
