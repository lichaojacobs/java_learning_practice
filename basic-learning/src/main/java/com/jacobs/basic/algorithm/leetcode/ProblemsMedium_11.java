package com.jacobs.basic.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import com.jacobs.basic.algorithm.TreeNode;
import com.jacobs.basic.models.ListNode;

/**
 * @author lichao
 * Created on 2019-06-22
 */
public class ProblemsMedium_11 {
    public static void main(String[] args) {
//        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 50}};
//        System.out.println(searchMatrix(matrix, 3));
//        int[] nums = new int[]{1, 2, 2};
//        System.out.println(subsetsWithDup(nums));
//        TreeNode root = new TreeNode(1);
//        root.left = new TreeNode(2);
//        root.right = new TreeNode(5);
//        root.left.left = new TreeNode(3);
//        root.left.right = new TreeNode(4);
//        root.right.right = new TreeNode(6);

//        flatten(root);

        ListNode listNode = new ListNode(3);
        listNode.next = new ListNode(1);
        listNode.next.next = new ListNode(2);
        listNode.next.next.next = new ListNode(5);
        listNode.next.next.next.next = new ListNode(4);

//        reorderList(listNode);
//        listNode = insertionSortList(listNode);
//        while (listNode != null) {
//            System.out.print(listNode.val + " ");
//            listNode = listNode.next;
//        }

//        System.out.println(maxProduct(new int[]{0, 2}));
//        System.out.println(findMin(new int[]{1, 2, 3}));
//        findRepeatedDnaSequences("AAAAAAAAAAAA");

//        TreeNode root = new TreeNode(1);
//        root.left = new TreeNode(2);
//        root.right = new TreeNode(3);
//        root.left.right = new TreeNode(4);
//        rightSideView(root);

//        System.out.println(minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3, 6}));
        System.out.println(rob(new int[]{0, 0}));
    }

    private static final byte[] HEX_BYTES = new byte[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    private static final byte POSITIVE_MARKER = (byte) '=';
    private static final byte NEGATIVE_MARKER = (byte) '*';

    static byte[] toKey(Object value, byte prefix) {
        final byte[] result;
        int bytes;

        if (value instanceof Integer) {
            bytes = Integer.SIZE;
        } else if (value instanceof Long) {
            bytes = Long.SIZE;
        } else if (value instanceof Short) {
            bytes = Short.SIZE;
        } else if (value instanceof Byte) {
            bytes = Byte.SIZE;
        } else {
            throw new IllegalArgumentException(String.format("Type %s not allowed as key.",
                    value.getClass().getName()));
        }

        bytes = bytes / Byte.SIZE;

        byte[] key = new byte[bytes + 2];
        long longValue = ((Number) value).longValue();
        key[0] = prefix;
        key[1] = longValue >= 0 ? POSITIVE_MARKER : NEGATIVE_MARKER;

        for (int i = 0; i < key.length - 2; i++) {
            int masked = (int) ((longValue >>> (4 * i)) & 0xF);
            key[key.length - i - 1] = HEX_BYTES[masked];
        }

        result = key;

        return result;
    }

    /**
     * leetcode 74
     * 搜索二维矩阵
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        // 从左上和右下对角线开始
        int lefti = 0, leftj = 0;
        int righti = rows - 1, rightj = cols - 1;
        while (lefti <= righti && leftj <= rightj) {
            int midi = (lefti + righti) / 2;
            int midj = (leftj + rightj) / 2;
            if (matrix[midi][midj] == target) {
                return true;
            }
            if (matrix[midi][midj] > target) {
                if (matrix[midi][0] <= target) {
                    righti = midi;
                    lefti = midi;
                    rightj = midj - 1;
                } else {
                    righti = midi - 1;
                    rightj = cols - 1;
                }
            } else {
                if (matrix[midi][cols - 1] >= target) {
                    righti = midi;
                    lefti = midi;
                    leftj = midj + 1;
                } else {
                    lefti = midi + 1;
                    leftj = 0;
                }
            }
        }

        return false;
    }

    /**
     * leetcode 59
     * 给定一个正整数 n，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵。
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int starti = 0;
        int startj = 0;
        int endi = n - 1;
        int endj = n - 1;

        int val = 1;
        while (val <= n * n && starti <= endi && startj <= endj) {
            int cursori = starti;
            int cursorj = startj;
            // 从左到右
            while (cursorj <= endj) {
                matrix[cursori][cursorj++] = val++;
            }
            // 需要去除重复
            cursori++;
            cursorj--;
            // 上到下
            while (cursori <= endi) {
                matrix[cursori++][cursorj] = val++;
            }
            cursorj--;
            cursori--;

            // 从右到左
            while (cursorj >= startj) {
                matrix[cursori][cursorj--] = val++;
            }
            cursori--;
            cursorj++;

            // 从下到上
            while (cursori > starti) {
                matrix[cursori--][cursorj] = val++;
            }

            starti++;
            startj++;
            endi--;
            endj--;
        }

        return matrix;
    }

    /**
     * leetcode 80 删除排序数组中的重复项 II
     *
     * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。
     *
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }
        // 由后往前挪的过程中length也随着减小
        int length = nums.length;
        int count = 1;
        // 从index位置开始处理重复的记录
        int index = 1;
        while (index < length) {
            if (nums[index] != nums[index - 1]) {
                index++;
                count = 1;
                continue;
            }
            // 如果相等且没超过2的话，继续往后处理
            if (count < 2) {
                count++;
                index++;
                continue;
            }
            // 如果超过了2，把后面不为nums[index]的数往前覆盖，同时将count置为1
            // 处理原始数组的index，需要将后面的数往前挪
            int afterIndex = index + 1;
            while (afterIndex < nums.length && nums[afterIndex] == nums[index]) {
                afterIndex++;
            }
            // 找到第一个不等于nums[index]，将后面的覆盖前面的
            int copyIndex = index;
            while (afterIndex < length) {
                nums[copyIndex++] = nums[afterIndex++];
            }
            // 覆盖完后，再更改数组长度
            length = copyIndex;
            count = 1;
        }

        return length;
    }

    /**
     * leetcode 80 推荐解法
     * @param nums
     * @return
     */
    public int removeDuplicates2(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i - 2])
                nums[i++] = n;
        return i;
    }

    /**
     * leetcode 89
     *
     * 格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个位数的差异。
     * 给定一个代表编码总位数的非负整数 n，打印其格雷编码序列。格雷编码序列必须以 0 开头。
     *
     * 输入: 2
     * 输出: [0,1,3,2]
     * 解释:
     * 00 - 0
     * 01 - 1
     * 11 - 3
     * 10 - 2
     * @param n
     * @return
     */
    public List<Integer> grayCode(int n) {
        if (n == 0) {
            return Arrays.asList(0);
        }
        List<Integer> prev = grayCode(n - 1);
        List<Integer> next = new ArrayList<Integer>(prev);
        int pow = 1 << (n - 1);
        for (int i = prev.size() - 1; i >= 0; i--) {
            // 对前面得到的每一项在高位加上1，生成新的一项
            next.add(prev.get(i) | pow);
        }
        return next;
    }

    /**
     *leetcode 90 子集
     *
     * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     *
     * 说明：解集不能包含重复的子集。
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return resultList;
        }
        // 首先得排序
        Arrays.sort(nums);
        helper(resultList, new ArrayList<>(), 0, nums);
        return resultList;
    }

    public static void helper(List<List<Integer>> resultList, List<Integer> tmp, int start, int[]
            nums) {
        if (start <= nums.length) {
            resultList.add(new ArrayList<>(tmp));
        }

        for (int i = start; i < nums.length; i++) {
            // i > start是关键，说明已经添加一轮到resultList里面了并出栈了，这一轮是重复的
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            tmp.add(nums[i]);
            //注意这里是i+1，否则i为1，start回归0的时候，再进入helper start+1还是为1，会造成重复计算
            helper(resultList, tmp, i + 1, nums);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     *[109] 有序链表转换二叉搜索树
     *
     * 给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。
     *
     * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        return toBST(head, null);
    }

    public TreeNode toBST(ListNode head, ListNode tail) {
        ListNode slow = head;
        ListNode fast = head;
        if (head == tail) {
            return null;
        }

        // 有序链表即想到二分
        while (fast != tail && fast.next != tail) {
            fast = fast.next.next;
            slow = slow.next;
        }
        TreeNode root = new TreeNode(slow.val);
        root.left = toBST(head, slow);
        root.right = toBST(slow.next, tail);
        return root;
    }

    /**
     * 114. 二叉树展开为链表
     * 给定一个二叉树，原地将它展开为链表。
     *
     * 例如，给定二叉树
     *
     *     1
     *    / \
     *   2   5
     *  / \   \
     * 3   4   6
     * 将其展开为：
     *
     * 1
     *  \
     *   2
     *    \
     *     3
     *      \
     *       4
     *        \
     *         5
     *          \
     *           6
     *
     * @param root
     */
    public static void flatten(TreeNode root) {
        flattenHelper(root);
    }

    //递归式返回的是当前根结点，左链表或者右链表最后的尾节点（因为头节点就是root.left或root.right，只需记录尾节点即可）
    public static TreeNode flattenHelper(TreeNode root) {
        //自身为null或者无左右节点
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }

        //采用后续遍历的方式，从下往上，从左往右去变更树节点的关系
        TreeNode leftEndNode = flattenHelper(root.left);
        TreeNode rightEndNode = flattenHelper(root.right);
        if (leftEndNode != null) {
            // 此时root左右两个节点变更关系
            leftEndNode.right = root.right;
            root.right = root.left;
            root.left = null;
        }
        return rightEndNode == null ? leftEndNode : rightEndNode;
    }

    //另一种方式
//    public void flatten(TreeNode root) {
//        if (root == null) {
//            return;
//        }
//        flattenHelper(root);
//    }
//
//    public void flattenHelper(TreeNode root) {
//        // 自身为null或者无左右节点
//        if (root == null) {
//            return;
//        }
//
//        TreeNode left = root.left;
//        TreeNode right = root.right;
//        root.left = null;
//        // 采用后续遍历的方式，从下往上，从左往右去变更树节点的关系
//        flattenHelper(root.left);
//        flattenHelper(root.right);
//
//        // 此时root左右两个节点变更关系
//        root.right = left;
//        TreeNode curr = root;
//        while (curr.right != null) {
//            curr = curr.right;
//        }
//        // 再接上右边的节点
//        curr.right = right;
//    }


    /**
     * @lc app=leetcode.cn id=137 lang=java
     * [137] 只出现一次的数字 II
     *
     * https://leetcode-cn.com/problems/single-number-ii/description/
     *
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现了三次。找出那个只出现了一次的元素。
     *
     *
     * 示例 1:
     *
     * 输入: [2,2,3,2]
     * 输出: 3
     *
     *
     * 示例 2:
     *
     * 输入: [0,1,0,1,0,1,99]
     * 输出: 99
     *
     * 思路：将数变成二进制之后，就是数各个位置上1的个数了；可延伸到每个元素出现k次，找只出现一次的元素
     */
    public int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        // 将数变成二进制之后，就是数各个位置上1的个数了
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                if (((nums[j] >> i) & 1) == 1) {
                    sum++;
                    // 如果该位置上1的个数能被3整除，说明该位置上目标数没做贡献
                    sum %= 3;
                }
            }
            // 完成一个位置的计算后将中间结果加上即可
            if (sum != 0) {
                ans |= sum << i;
            }
        }

        return ans;
    }

    /*
     * @lc app=leetcode.cn id=143 lang=java
     *
     * [143] 重排链表
     *
     * https://leetcode-cn.com/problems/reorder-list/description/
     *
     * 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
     * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
     *
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     *
     * 示例 1:
     *
     * 给定链表 1->2->3->4, 重新排列为 1->4->2->3.
     *
     * 示例 2:
     *
     * 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
     *
     */

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        ListNode pre = head;
        ListNode aft = head;
        while (aft.next != null && aft.next.next != null) {
            pre = pre.next;
            aft = aft.next.next;
        }

        // found mid node
        ListNode headReverse = pre.next;
        pre.next = null;

        // reverse nodes after mid node
        pre = null;
        while (headReverse != null) {
            aft = headReverse.next;
            headReverse.next = pre;
            pre = headReverse;
            headReverse = aft;
        }
        headReverse = pre;

        // finally merge two list
        ListNode cursor = head;
        ListNode cursorReverse = headReverse;
        int index = 0;
        while (cursor != null && cursorReverse != null) {
            if (index % 2 == 0) {
                ListNode tmpAft = cursor.next;
                cursor.next = cursorReverse;
                cursor = tmpAft;
            } else {
                ListNode tmpAft = cursorReverse.next;
                cursorReverse.next = cursor;
                cursorReverse = tmpAft;
            }
            index++;
        }
    }

    /*
     * @lc app=leetcode.cn id=144 lang=java
     *
     * [144] 二叉树的前序遍历
     *
     * https://leetcode-cn.com/problems/binary-tree-preorder-traversal/description/
     *
     * 给定一个二叉树，返回它的 前序 遍历。
     *
     * 示例:
     *
     * 输入: [1,null,2,3]
     * ⁠  1
     * ⁠   \
     * ⁠    2
     * ⁠   /
     * ⁠  3
     *
     * 输出: [1,2,3]
     *
     *
     * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
     *
     */

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> resultList = new ArrayList<>();
        if (root == null) {
            return resultList;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            resultList.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return resultList;
    }

    /**
     * [147] 对链表进行插入排序
     * https://leetcode-cn.com/problems/insertion-sort-list/description/
     * @param head
     * @return
     */
    public static ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode orderedNode = head;
        ListNode cursorNode = head.next;
        // make sure not in cycle
        orderedNode.next = null;

        while (cursorNode != null) {
            ListNode aftNode = cursorNode.next;
            // make sure not in cycle
            cursorNode.next = null;
            // check 两端的情况
            if (cursorNode.val <= head.val) {
                cursorNode.next = head;
                // should update head ref
                head = cursorNode;
            } else if (cursorNode.val >= orderedNode.val) {
                orderedNode.next = cursorNode;
                orderedNode = cursorNode;
            } else {
                ListNode preNode = head;
                ListNode currNode = head;
                while (currNode != orderedNode.next) {
                    if (currNode.val < cursorNode.val) {
                        preNode = currNode;
                        currNode = currNode.next;
                        continue;
                    }

                    preNode.next = cursorNode;
                    cursorNode.next = currNode;
                    break;
                }
            }

            cursorNode = aftNode;
        }

        return head;
    }

    /**
     * [152] 乘积最大子序列
     *
     * https://leetcode-cn.com/problems/maximum-product-subarray/description/
     * 示例 1:
     * 输入: [2,3,-2,4]
     * 输出: 6
     * 解释: 子数组 [2,3] 有最大乘积 6。
     *
     * 动态规划，由于可能有负数需维护最大值和最小值；对于每一个元素，可以选择加入，或不加入自成一组
     * maxDP[i + 1] = max(maxDP[i] * A[i + 1], A[i + 1],minDP[i] * A[i + 1])
     * minDP[i + 1] = min(minDP[i] * A[i + 1], A[i + 1],maxDP[i] * A[i + 1])
     * dp[i + 1] = max(dp[i], maxDP[i + 1])
     *
     * @param nums
     * @return
     */
    public static int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0];
        }

        int result = nums[0];
        int maxVal = nums[0];
        int minVal = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = maxVal;
            maxVal = Math.max(Math.max(temp * nums[i], nums[i]), minVal * nums[i]);
            minVal = Math.min(Math.min(minVal * nums[i], nums[i]), temp * nums[i]);
            result = Math.max(maxVal, result);
        }

        return result;
    }

    /**
     * [153] 寻找旋转排序数组中的最小值
     * https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/description/
     *
     * 输入: [3,4,5,1,2]
     * 输出: 1
     *
     * @param nums
     * @return
     */
    public static int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left < right) {
            if (left + 1 == right) {
                if (nums[left] > nums[right]) {
                    return nums[right];
                } else {
                    return nums[left];
                }
            }
            mid = (left + right) / 2;
            if (nums[left] < nums[mid] && nums[right] < nums[mid]) {
                left = mid;
            } else if (nums[right] > nums[mid] && nums[left] > nums[mid]) {
                right = mid;
            } else {
                // 须考虑兼容非旋转数组
                return nums[left];
            }
        }

        return nums[mid];
    }

    /**
     * [162] 寻找峰值
     * 峰值元素是指其值大于左右相邻值的元素。
     * https://leetcode-cn.com/problems/find-peak-element/description/
     *
     * 输入: nums = [1,2,1,3,5,6,4]
     * 输出: 1 或 5
     * 解释: 你的函数可以返回索引 1，其峰值元素为 2；或者返回索引 5， 其峰值元素为 6。
     * 你的解法应该是 O(logN) 时间复杂度的。
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    public int helper(int[] num, int start, int end) {
        if (start == end) {
            return start;
        } else if (start + 1 == end) {
            if (num[start] > num[end]) return start;
            return end;
        } else {

            int m = (start + end) / 2;

            if (num[m] > num[m - 1] && num[m] > num[m + 1]) {

                return m;

            } else if (num[m - 1] > num[m] && num[m] > num[m + 1]) {

                return helper(num, start, m - 1);

            } else {

                return helper(num, m + 1, end);

            }

        }
    }

    /**
     *
     * [166] 分数到小数
     * https://leetcode-cn.com/problems/fraction-to-recurring-decimal/description/
     * 给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以字符串形式返回小数。
     * 如果小数部分为循环小数，则将循环的部分括在括号内。
     *
     * 输入: numerator = 1, denominator = 2
     * 输出: "0.5"
     *
     * @param numerator
     * @param denominator
     * @return
     */
    public static String fractionToDecimal(int numerator, int denominator) {
        boolean isNegative = (numerator < 0 && denominator > 0 || numerator > 0 && denominator < 0) ? true : false;
        long numeratorL = Math.abs((long) numerator);
        long denominatorL = Math.abs((long) denominator);
        Map<Long, Integer> previousRemains = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        long quotian = numeratorL / denominatorL;
        sb.append(quotian);

        numeratorL %= denominatorL;
        if (numeratorL != 0) {
            sb.append(".");
        }
        int quotianIndex = 0;
        while (numeratorL != 0) {
            numeratorL *= 10;
            quotian = Math.abs(numeratorL / denominatorL);
            if (!previousRemains.containsKey(numeratorL)) {
                sb.append(quotian);
                previousRemains.put(numeratorL, quotianIndex++);
            } else {
                int firstIndex = 1 + previousRemains.get(numeratorL) + sb.indexOf(".");
                sb.insert(firstIndex, '(');
                sb.append(")");
                break;
            }
            numeratorL %= denominatorL;
        }
        if (isNegative) {
            sb.insert(0, "-");
        }
        return sb.toString();
    }

    /**
     * [187] 重复的DNA序列
     *
     * https://leetcode-cn.com/problems/repeated-dna-sequences/description/
     * 所有 DNA 都由一系列缩写为 A，C，G 和 T 的核苷酸组成，例如：“ACGAATTCCG”。在研究 DNA 时，识别 DNA
     * 中的重复序列有时会对研究非常有帮助。
     *
     * 编写一个函数来查找 DNA 分子中所有出现超过一次的 10 个字母长的序列（子串）。
     * 示例：
     *
     * 输入：s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
     * 输出：["AAAAACCCCC", "CCCCCAAAAA"]
     * @param s
     * @return
     */
    public static List<String> findRepeatedDnaSequences(String s) {
        List<String> resultList = new ArrayList<>();
        if (s == null || s.equals("") || s.length() <= 10) {
            return resultList;
        }
        Set<String> records = new HashSet<>();
        Set<String> unRepeatedResult = new HashSet<>();
        for (int i = 0; i + 9 < s.length(); i++) {
            String ten = s.substring(i, i + 10);
            if (!records.add(ten))
                unRepeatedResult.add(ten);
        }

        return new ArrayList<>(unRepeatedResult);
    }

    /**
     * [199] 二叉树的右视图
     * https://leetcode-cn.com/problems/binary-tree-right-side-view/description/
     *
     * 示例:
     * 输入: [1,2,3,null,5,null,4]
     * 输出: [1, 3, 4]
     * 解释:
     *
     * ⁠  1            <---
     * ⁠/   \
     * 2     3         <---
     * ⁠\     \
     * ⁠ 5     4       <---
     *
     * @param root
     * @return
     */
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> resultList = new ArrayList<>();
        if (root == null) {
            return resultList;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode preLevelLast = root;
        TreeNode nextLevelLast = root;
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.left != null) {
                queue.offer(curr.left);
                nextLevelLast = curr.left;
            }
            if (curr.right != null) {
                queue.offer(curr.right);
                nextLevelLast = curr.right;
            }
            if (curr == preLevelLast) {
                resultList.add(curr.val);
                preLevelLast = nextLevelLast;
            }
        }

        return resultList;
    }

    /**
     * [201] 数字范围按位与
     * https://leetcode-cn.com/problems/bitwise-and-of-numbers-range/description/
     * 给定范围 [m, n]，其中 0 <= m <= n <= 2147483647，返回此范围内所有数字的按位与（包含 m, n 两端点）。
     *
     * 示例 1: 
     * 输入: [5,7]
     * 输出: 4
     *
     * 示例 2:
     * 输入: [0,1]
     * 输出: 0
     *
     * 想法：考虑每一位的最终结果，只要有一个0，最终结果也就是0；此时只需要得出m，n的公共位即可
     * @param m
     * @param n
     * @return
     */
    public int rangeBitwiseAnd(int m, int n) {
        int i = 0;
        for (; m != n; ++i) {
            m >>= 1;
            n >>= 1;
        }
        return n << i;
    }

    /**
     * [209] 长度最小的子数组
     *
     * https://leetcode-cn.com/problems/minimum-size-subarray-sum/description/
     * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。
     *
     * 示例: 
     *
     * 输入: s = 7, nums = [2,3,1,2,4,3]
     * 输出: 2
     * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
     *
     * 想法：O(N)解法：双端指针，滑动窗口的方式来寻找符合条件的数组
     * @param s
     * @param nums
     * @return
     */
    public static int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 定义窗口指针
        int leftPoint = 0;
        int rightPoint = 0;
        int sum = 0;
        int minLen = 0;
        while (rightPoint != nums.length) {
            sum += nums[rightPoint];
            if (sum >= s) {
                while ((sum - nums[leftPoint]) >= s) {
                    sum = sum - nums[leftPoint];
                    // 左窗口右移
                    leftPoint++;
                }

                minLen = minLen == 0 ? (rightPoint - leftPoint) + 1 : Math.min(minLen, (rightPoint -
                        leftPoint) + 1);
            }
            rightPoint++;
        }

        return minLen;
    }

    /**
     * [213] 打家劫舍 II
     *
     * https://leetcode-cn.com/problems/house-robber-ii/description/
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都围成一圈，这意味着第一个房屋和最后一个房屋是紧挨着的。
     * 同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。
     *
     * 示例 1:
     *
     * 输入: [2,3,2]
     * 输出: 3
     * 解释: 你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
     *
     * 状态转移方程: dp(n) = max(dp(n-1),dp(n-2)+num)
     * @param nums
     * @return
     */
    public static int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        // 考虑头元素和尾元素首尾相连，分两种情况考虑
        return Math.max(robHelper(Arrays.copyOfRange(nums, 0, nums.length - 1)), robHelper(Arrays
                .copyOfRange(nums, 1, nums.length)));
    }

    public static int robHelper(int[] nums) {
        if (nums.length == 1) return nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = nums[0] > nums[1] ? nums[0] : nums[1];

        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[nums.length - 1];
    }

    /**
     * [216] 组合总和 III
     *
     * https://leetcode-cn.com/problems/combination-sum-iii/description/
     * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
     * 说明：
     * 所有数字都是正整数。
     * 解集不能包含重复的组合。 
     *
     * 示例:
     * 输入: k = 3, n = 9
     * 输出: [[1,2,6], [1,3,5], [2,3,4]]
     * @param k
     * @param n
     * @return
     */
    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        Stack<Integer> path = new Stack<>();
        dfsHelper(path, res, k, n, 1);

        return res;
    }

    public static void dfsHelper(Stack<Integer> path, List<List<Integer>> res, int k, int n, int
            start) {
        if (k < 0 || n < 0) return;
        if (k == 0 && n == 0) {
            if (!path.isEmpty())
                res.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i <= 9; i++) {
            path.push(i);
            dfsHelper(path, res, k - 1, n - i, i + 1);
            path.pop();
        }
    }

    /**
     * [222] 完全二叉树的节点个数
     * https://leetcode-cn.com/problems/count-complete-tree-nodes/description/
     * 给出一个完全二叉树，求出该树的节点个数。
     *
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        int d = computeHeight(root);
        // if the tree contains 1 node
        if (d == 0) return 1;

        // 二分法找到最后一层最后一个节点
        int left = 1, right = (int) Math.pow(2, d) - 1;
        int pivot;
        while (left <= right) {
            pivot = (left + right) / 2;
            if (exists(pivot, d, root)) {
                left = pivot + 1;
            } else {
                right = pivot - 1;
            }
        }

        return (int) Math.pow(2, d) - 1 + left;
    }

    public boolean exists(int idx, int height, TreeNode root) {
        int left = 0, right = (int) Math.pow(2, height) - 1;
        int pivot = 0;
        TreeNode node = root;
        for (int i = 0; i < height; i++) {
            pivot = (left + right) / 2;
            if (idx <= pivot) {
                node = node.left;
                right = pivot;
            } else {
                node = node.right;
                left = pivot;
            }
        }

        return node != null;
    }

    public int computeHeight(TreeNode root) {
        int height = 0;
        TreeNode node = root;
        while (node.left != null) {
            node = node.left;
            height++;
        }
        return height;
    }
}
