package com.jacobs.basic.algorithm.dp;

import java.util.Stack;

/**
 * @author lichao
 * Created on 2020-01-29
 */
public class DynamicProgramProblems {
    public static void main(String[] args) {
        DynamicProgramProblems dynamicProgramProblems = new DynamicProgramProblems();
        char[][] matrix = new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};

        System.out.println(dynamicProgramProblems.maximalRectangle(matrix));
    }

    /**
     * [85] 最大矩形
     *
     * https://leetcode-cn.com/problems/maximal-rectangle/description/
     * 给定一个仅包含 0 和 1 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
     *
     * 输入:
     * [
     * ⁠ ["1","0","1","0","0"],
     * ⁠ ["1","0","1","1","1"],
     * ⁠ ["1","1","1","1","1"],
     * ⁠ ["1","0","0","1","0"]
     * ]
     * 输出: 6
     * 思路：
     * 定义一个数组heights，表示以某一行为底，往上有多少个连续的1；接下来就是对height数组进行压栈，出栈的操作
     *
     *
     * @param matrix
     * @return
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int maxSize = 0;
        int[] heights = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                // 每层更新height数组
                heights[j] = matrix[i][j] == '0' ? 0 : heights[j] + 1;
            }
            // 每层求最大矩形面积
            maxSize = Math.max(maxSize, maxSize(heights));
        }
        return maxSize;
    }

    public int maxSize(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        int maxSize = 0;
        for (int i = 0; i < heights.length; i++) {
            //当heights[i]小于等于栈顶元素时，不断弹出栈顶元素，直到大于栈顶（栈顶左右扩展已经到头了）
            while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                // 向左扩展到(k+1)，向右扩展到i
                maxSize = Math.max(maxSize, (i - k - 1) * heights[j]);
            }
            //当heights[i]大于栈顶元素或者栈为空时，直接入栈即可
            stack.push(i);
        }

        //最后清理栈中残余的
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            //留下来的向右肯定扩充到了最边缘
            maxSize = Math.max(maxSize, (heights.length - k - 1) * heights[j]);
        }
        return maxSize;
    }

    /**
     *
     * [72] 编辑距离
     *
     * https://leetcode-cn.com/problems/edit-distance/description/
     * 给定两个单词 word1 和 word2，计算出将 word1 转换成 word2 所使用的最少操作数 。
     *
     * 你可以对一个单词进行如下三种操作：插入一个字符，删除一个字符，替换一个字符
     * 示例 1:
     * 输入: word1 = "horse", word2 = "ros"
     * 输出: 3
     * 解释:
     * horse -> rorse (将 'h' 替换为 'r')
     * rorse -> rose (删除 'r')
     * rose -> ros (删除 'e')
     *
     * 递归式：dp[i][j] 表示由长度i-1的word1变到长度j-1的word2所需的最小编辑
     * if word1.charAt(i) == word2.charAt(j):
     *      dp[i][j] = Math.min(dp[i-1][j-1],dp[i-1][j]+1,dp[i][j-1]+1)
     * else
     *      dp[i][j] = Math.min(dp[i-1][j-1]+1,dp[i-1][j]+1,dp[i][j-1]+1)
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null || "".equals(word1) || word2 == null || "".equals(word2)) {
            return 0;
        }
        if (word1.length() == 0) {
            return word2.length();
        }
        if (word2.length() == 0) {
            return word1.length();
        }

        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();

        for (int i = 0; i <= chars1.length; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= chars2.length; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                int minDelete = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                int replace = dp[i - 1][j - 1];
                if (chars1[i - 1] != chars2[j - 1]) {
                    replace++;
                }
                dp[i][j] = Math.min(minDelete, replace);
            }
        }

        return dp[chars1.length][chars2.length];
    }
}
