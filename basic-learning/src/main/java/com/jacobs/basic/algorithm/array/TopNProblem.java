package com.jacobs.basic.algorithm.array;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * topn 的多种解法 https://my.oschina.net/7001/blog/1633536
 *
 * @author lichao
 * @date 2019/01/18
 */
public class TopNProblem {

    public static void main(String[] args) {
        int k = 4;
        int array[] = {20, 100, 4, 2, 87, 9, 8, 5, 46, 26};
        findTopK(array, 0, array.length - 1, k);
        System.out.println(String.format("array top k:%s",
            Arrays.stream(array).mapToObj(value -> String.valueOf(value)).limit(k).collect(Collectors.joining(","))));
    }

    /**
     * 解法一： 用快排partition的思想
     */
    public static int findTopK(int[] array, int left, int right, int k) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int index = -1;
        if (left < right) {
            int part = partition(array, left, right);
            int len = part - left + 1;
            if (len == k) {
                index = part;
            } else if (len < k) {
                //Sa中元素个数小于K，到Sb中查找k-len个数字
                index = findTopK(array, part + 1, right, k - len);
            } else {
                //Sa中元素的个数大于或等于k
                index = findTopK(array, left, part - 1, k);
            }
        }
        return index;
    }

    public static int partition(int[] array, int left, int right) {
        int num = array[left];
        while (left < right) {
            while (left < right && array[right] < num) {
                right--;
            }
            array[left++] = array[right];
            while (left < right && array[left] > num) {
                left++;
            }
            array[right--] = array[left];
        }
        array[left] = num;
        return left;
    }
}
