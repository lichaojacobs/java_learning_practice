package com.jacobs.basic.algorithm.leetcode;

/**
 * @author lichao
 * Created on 2019-06-22
 */
public class ProblemsMedium_11 {
    public static void main(String[] args) {
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 50}};
        System.out.println(searchMatrix(matrix, 3));
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
}
