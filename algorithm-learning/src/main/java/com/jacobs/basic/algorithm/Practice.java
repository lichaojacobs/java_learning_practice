package com.jacobs.basic.algorithm;

import com.google.common.collect.Lists;
import com.jacobs.basic.algorithm.array.MaxChildListSum;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;
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
        root.right = constructNode(preorder, inorder, preStart + index - inStart + 1, preEnd, index + 1,
            inEnd);

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
            List<Integer> lineArr = Arrays.stream(line.split(","))
                .map(s -> Integer.valueOf(s))
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

            List<Integer> coninsArr = Arrays.stream(input.next().split(" "))
                .map(Integer::valueOf)
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
}
