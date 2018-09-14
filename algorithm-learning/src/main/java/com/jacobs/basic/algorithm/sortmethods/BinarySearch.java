package com.jacobs.basic.algorithm.sortmethods;

import java.util.List;

/**
 * 二分排序决定必须是有序序列
 * Created by lichao on 2017/1/21.
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] list = {1, 5, 6, 8, 10, 25, 16};
        //System.out.println(binarySearch2(list, 0, list.length - 1, 25));
        //System.out.println(partialBinarySearch_03(new int[]{3, 1}, 1));
        System.out.println(removeDuplicates(new int[]{1, 2, 3}));
    }

    /**
     * 非递归
     */
    public static int binarySearch1(int[] list, int key) {
        if (list == null || list.length < 1) {
            return -1;
        }
        int low = 0;
        int high = list.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (list[mid] == key) {
                return mid;
            }
            if (list[mid] > key) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return list[low] == key ? low : -1;
    }

    public static int binarySearch2(int[] list, int low, int high, int key) {
        if (low > high) {
            return list[low] == key ? low : -1;
        }
        int mid = (low + high) / 2;
        if (list[mid] == key) {
            return mid;
        }
        if (list[mid] > key) {
            return binarySearch2(list, low, mid - 1, key);
        } else {
            return binarySearch2(list, mid + 1, high, key);
        }
    }

    public static int partialBinarySearch(int[] nums, int key) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] == key) {
                return mid;
            }
            // 正常的排序情况
            if (nums[left] <= nums[right]) {
                if (nums[mid] < key) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                // 异常的情况分为到底在哪边
                // 先是mid在左边
                if (nums[mid] >= nums[left]) {
                    // 于是在左边的情况
                    if (nums[mid] > key && nums[left] <= key) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                } else {
                    // 于是在右边的情况
                    if (nums[mid] < key && nums[right] >= key) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        }

        return -1;
    }

    /**
     * leetcode上的解法
     *
     * @param nums
     * @param target
     * @return
     */
    public int partialBinarySearch_02(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if (nums[mid] > nums[end]) {  // eg. 3,4,5,6,1,2
                if (target > nums[mid] || target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid;
                }
            } else {  // eg. 5,6,1,2,3,4
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid;
                }
            }
        }
        if (start == end && target != nums[start]) return -1;
        return start;
    }

    //    Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
//
//            (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
//
//    Write a function to determine if a given target is in the array.
//
//    The array may contain duplicates.
    public static boolean partialBinarySearch_03(int[] nums, int target) {
        int start = 0, end = nums.length - 1, mid = -1;
        while (start <= end) {
            mid = (start + end) / 2;
            if (nums[mid] == target) {
                return true;
            }
            //If we know for sure right side is sorted or left side is unsorted
            if (nums[mid] < nums[end] || nums[mid] < nums[start]) {
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
                //If we know for sure left side is sorted or right side is unsorted
            } else if (nums[mid] > nums[start] || nums[mid] > nums[end]) {
                if (target < nums[mid] && target >= nums[start]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
                //If we get here, that means nums[start] == nums[mid] == nums[end], then shifting out
                //any of the two sides won't change the result but can help remove duplicate from
                //consideration, here we just use end-- but left++ works too
            } else {
                end--;
            }
        }

        return false;
    }

    //    Given nums = [1,1,2],
//
//    Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
//    It doesn't matter what you leave beyond the new length.
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int pre = nums[0];
        int aft;
        int length = 1;

        for (int i = 1; i < nums.length; i++) {
            aft = nums[i];
            if (aft != pre) {
                pre = aft;
                nums[length++] = nums[i];
            }
        }

        return length;
    }
}
