package com.jacobs.basic.algorithm.leetcode;

/**
 * @author lichao
 * @date 2018/09/09
 */
public class TopInterviewQuestions {

    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{}, new int[]{1}));
    }


    // 4. Median of Two Sorted Arrays
    // 复杂度 O(m+n)
    //    There are two sorted arrays nums1 and nums2 of size m and n respectively.
    //
    //    Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
    //
    //    You may assume nums1 and nums2 cannot be both empty.
    //
    //    Example 1:
    //
    //    nums1 = [1, 3]
    //    nums2 = [2]
    //
    //    The median is 2.0
    //    Example 2:
    //
    //    nums1 = [1, 2]
    //    nums2 = [3, 4]
    //
    //    The median is (2 + 3)/2 = 2.5
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int preIndex = (len1 + len2) / 2;
        int[] mids = (len1 + len2) % 2 == 0 ? new int[]{preIndex - 1, preIndex} : new int[]{preIndex};
        //一次归并
        int start1 = 0;
        int start2 = 0;
        int index = 0;
        int target1 = 0;
        int target2 = 0;
        while (start1 < len1 && start2 < len2) {
            int temp;
            if (nums1[start1] < nums2[start2]) {
                temp = nums1[start1];
                start1++;
            } else {
                temp = nums2[start2];
                start2++;
            }

            if (index == mids[0]) {
                target1 = temp;
                if (mids.length == 1) {
                    return target1;
                }
            }
            if (index == mids[0] + 1) {
                target2 = temp;
                if (mids.length == 2) {
                    return (double) (target1 + target2) / 2;
                }
            }
            index++;
        }

        while (start1 < len1) {
            if (index == mids[0]) {
                target1 = nums1[start1];
                if (mids.length == 1) {
                    return target1;
                }
            }
            if (index == mids[0] + 1) {
                target2 = nums1[start1];
                if (mids.length == 2) {
                    return (double) (target1 + target2) / 2;
                }
            }
            start1++;
            index++;
        }

        while (start2 < len2) {
            if (index == mids[0]) {
                target1 = nums2[start2];
                if (mids.length == 1) {
                    return target1;
                }
            }
            if (index == mids[0] + 1) {
                target2 = nums2[start2];
                if (mids.length == 2) {
                    return (double) (target1 + target2) / 2;
                }
            }
            start2++;
            index++;
        }

        return 0.0;
    }

    //    Explanation 使用binary search
    ////
    ////    The key point of this problem is to ignore half part of A and B each step recursively by comparing the median of remaining A and B:
    ////
    ////        if (aMid < bMid) Keep [aRight + bLeft]
    ////        else Keep [bRight + aLeft]
    ////    As the following: time=O(log(m + n))
    public double findMedianSortedArrays2(int[] A, int[] B) {
        int m = A.length, n = B.length;
        int l = (m + n + 1) / 2;
        int r = (m + n + 2) / 2;
        return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
    }

    public double getkth(int[] A, int aStart, int[] B, int bStart, int k) {
        if (aStart > A.length - 1) {
            return B[bStart + k - 1];
        }
        if (bStart > B.length - 1) {
            return A[aStart + k - 1];
        }
        if (k == 1) {
            return Math.min(A[aStart], B[bStart]);
        }

        int aMid = Integer.MAX_VALUE, bMid = Integer.MAX_VALUE;
        if (aStart + k / 2 - 1 < A.length) {
            aMid = A[aStart + k / 2 - 1];
        }
        if (bStart + k / 2 - 1 < B.length) {
            bMid = B[bStart + k / 2 - 1];
        }

        if (aMid < bMid) {
            return getkth(A, aStart + k / 2, B, bStart, k - k / 2);// Check: aRight + bLeft
        } else {
            return getkth(A, aStart, B, bStart + k / 2, k - k / 2);// Check: bRight + aLeft
        }
    }


}
