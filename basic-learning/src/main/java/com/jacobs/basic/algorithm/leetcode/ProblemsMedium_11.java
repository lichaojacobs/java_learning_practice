package com.jacobs.basic.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        int[] nums = new int[]{1, 2, 2};
        System.out.println(subsetsWithDup(nums));
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
}
