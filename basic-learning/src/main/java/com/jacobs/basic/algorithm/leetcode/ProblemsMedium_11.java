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
     * leetcode: 74 搜索二维矩阵
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
}
