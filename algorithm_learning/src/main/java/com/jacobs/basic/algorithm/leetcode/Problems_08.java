package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.models.ListNode;

import java.util.*;

/**
 * @author lichao
 * @date 2018/03/23
 */
public class Problems_08 {
    public static void main(String[] args) {
        //System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        for (int i : twoSum(new int[]{2, 7, 11, 15}, 9)) {
//            System.out.println(i);
//        }
        //System.out.println(isUgly(10));
        System.out.println(1 << 2);
    }

    //    1、与题目Two Sum类似，先将数组nums进行排序，然后从左往右依次枚举，在枚举的过程中左右夹逼；
//
//    2、枚举位置为i，当nums[i]为正数时，结束枚举（因为不可能三个正数之和为0）；另外，要枚举过整数不再重复枚举；
//
//    3、枚举到i时，左、右位置分别用l、r表示，当nums[i]+nums[l]+nums[r]>0，右边界往左夹逼；当nums[i]+nums[l]+nums[r]<0，左边界往右夹逼；当nums[i]+nums[l]+nums[r]==0，则算找到一个，要存起来，再将左边界往右夹逼同时要跳过与之前左边界相同的值，否则会出现重复的三个数。
//
//    4、在步骤3的整个过程中，始终要保证l<r
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();
        if (nums == null || nums.length == 0 || nums.length < 3) {
            return resultList;
        }
        //排序
        Arrays.sort(nums);
        int l = 0;
        int r = 0;
        int[] tempArr = new int[3];
        //从左到右枚举
        for (int i = 0; i < nums.length && nums[i] <= 0; ++i) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                l = i + 1;
                r = nums.length - 1;
                while (l < r) {
                    while (l < r && nums[i] + nums[l] + nums[r] > 0) {
                        --r;
                    }//限制右边界
                    if (l < r && nums[i] + nums[l] + nums[r] == 0) {
                        tempArr[0] = nums[i];
                        tempArr[1] = nums[l];
                        tempArr[2] = nums[r];
                        List<Integer> tempList = new ArrayList<>();
                        for (int j : tempArr) {
                            tempList.add(j);
                        }
                        resultList.add(tempList);
                        while (l < r && nums[l] == tempArr[1]) {
                            ++l;
                        }//限制左边界
                    } else {
                        ++l;
                    }
                }
            }
        }
        return resultList;
    }

    public static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        int[] resultArr = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                int index = map.get(target - nums[i]);
                if (index == i) {
                    continue;
                }
                resultArr[0] = index > i ? i : index;
                resultArr[1] = index > i ? index : i;
            }
        }

        return resultArr;
    }

    //    Given a linked list, swap every two adjacent nodes and return its head.
//
//    For example,
//    Given 1->2->3->4, you should return the list as 2->1->4->3.
//
//    Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.
    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;
        while (current.next != null && current.next.next != null) {
            ListNode first = current.next;
            ListNode second = current.next.next;
            first.next = second.next;
            current.next = second;
            current.next.next = first;
            current = current.next.next;
        }
        return dummy.next;
    }

    // (hard)   For example,
//    Given this linked list: 1->2->3->4->5
//
//    For k = 2, you should return: 2->1->4->3->5
//
//    For k = 3, you should return: 3->2->1->4->5
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k < 2) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode tail = dummy, prev = dummy, temp;
        int count;
        while (true) {
            count = k;
            while (count > 0 && tail != null) {
                count--;
                tail = tail.next;
            }
            if (tail == null) break;//Has reached the end


            head = prev.next;//for next cycle
            // prev-->temp-->...--->....--->tail-->....
            // Delete @temp and insert to the next position of @tail
            // prev-->...-->...-->tail-->head-->...
            // Assign @temp to the next node of @prev
            // prev-->temp-->...-->tail-->...-->...
            // Keep doing until @tail is the next node of @prev
            while (prev.next != tail) {
                temp = prev.next;//Assign
                prev.next = temp.next;//Delete

                temp.next = tail.next;
                tail.next = temp;//Insert

            }

            tail = head;
            prev = head;

        }
        return dummy.next;
    }

    //    Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
    //今日头条一面
//    Example:
//    Input: 1->2->4, 1->3->4
//    Output: 1->1->2->3->4->4
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        ListNode head1 = l1;
        ListNode head2 = l2;

        while (head1 != null && head2 != null) {
            if (head1.val > head2.val) {
                head.next = head2;
                head2 = head2.next;
                head = head.next;
            } else {
                head.next = head1;
                head1 = head1.next;
                head = head.next;
            }
        }

        while (head1 != null) {
            head.next = head1;
            head1 = head1.next;
            head = head.next;
        }

        while (head2 != null) {
            head.next = head2;
            head2 = head2.next;
            head = head.next;
        }

        return dummy.next;
    }


    /**
     * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return partition(lists, 0, lists.length - 1);
    }

    public ListNode partition(ListNode[] lists, int start, int end) {
        if (start == end) return lists[start];
        else if (start < end) {
            int mid = (start + end) / 2;
            ListNode pre = partition(lists, start, mid - 1);
            ListNode aft = partition(lists, mid + 1, end);
            return merge(pre, aft);
        } else {
            return null;
        }
    }

    public static ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = merge(l1.next, l2);
            return l1;
        } else {
            l2.next = merge(l1, l2.next);
            return l2;
        }
    }
//
//    /**
//     * 合并n条有序的链表，使得新链表与旧链表排序一致（今日头条二面题）
//     * O(n log(n))
//     * 使用归并的思想
//     *
//     * @param arrList
//     */
//    public static void mergeMutiArrayList(List<List<Integer>> arrList) {
//        if (arrList == null || arrList.size() == 0) {
//            return;
//        }
//
//
//    }
//
//    public static List<Integer> partHelper(List<List<Integer>> lists, List<Integer> resultList, int start, int end) {
//        if (start == end) return resultList;
//        while (start < end) {
//            int mid = (start + end) / 2;
//            List<>partHelper(lists, resultList, start, mid);
//            partHelper(lists, resultList, mid + 1, end);
//        }
//    }
//
//    public static List<Integer> mergeList(List<Integer> list1, List<Integer> list2, List<Integer> resultList) {
//        int index1 = 0;
//        int index2 = 0;
//
//        while (index1 != list1.size() && index2 != list2.size()) {
//            if (list1.get(index1) > list2.get(index2)) {
//                resultList.add(list2.get(index2++));
//            } else {
//                resultList.add(list1.get(index1++));
//            }
//        }
//
//        while (index1 != list1.size()) {
//            resultList.add(list1.get(index2++));
//        }
//
//        while (index2 != list2.size()) {
//            resultList.add(list2.get(index1++));
//        }
//
//        return resultList;
//    }


    //    Write a program to find the n-th ugly number.
//
//    Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.
//
//    Note that 1 is typically treated as an ugly number, and n does not exceed 1690.
//    The ugly-number sequence is 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, …
//    because every number can only be divided by 2, 3, 5, one way to look at the sequence is to split the sequence to three groups as below:
//
//            (1) 1×2, 2×2, 3×2, 4×2, 5×2, …
//            (2) 1×3, 2×3, 3×3, 4×3, 5×3, …
//            (3) 1×5, 2×5, 3×5, 4×5, 5×5, …
//    We can find that every subsequence is the ugly-sequence itself (1, 2, 3, 4, 5, …) multiply 2, 3, 5.
//5
//    Then we use similar merge method as merge sort, to get every ugly number from the three subsequence.
//
//    Every step we choose the smallest one, and move one step after,including nums with same value.
    public int nthUglyNumber(int n) {
        int[] uglyNums = new int[n];
        int index2 = 0, index3 = 0, index5 = 0;
        uglyNums[0] = 1;
        for (int i = 1; i < n; i++) {
            uglyNums[i] = Math.min(Math.min(uglyNums[index2] * 2, uglyNums[index3] * 3), uglyNums[index5] * 5);
            if (uglyNums[i] == uglyNums[index2] * 2) {
                index2++;
            }
            if (uglyNums[i] == uglyNums[index3] * 3) {
                index3++;
            }
            if (uglyNums[i] == uglyNums[index5] * 5) {
                index5++;
            }
        }

        return uglyNums[n - 1];
    }

    public static boolean isUgly(int num) {
        if (num <= 0) {
            return false;
        }
        if (num == 1) {
            return true;
        }

        int temp = num;
        while (temp > 1) {
            int yu = 0;
            if ((yu = temp % 2) == 0) {
                temp = temp / 2;
                continue;
            }
            if ((yu = temp % 3) == 0) {
                temp = temp / 3;
                continue;
            }
            if ((yu = temp % 5) == 0) {
                temp = temp / 5;
                continue;
            }

            return false;
        }

        return true;
    }


    //    Given an array of integers sorted in ascending order, find the starting and ending position of a given target value.
//
//    Your algorithm's runtime complexity must be in the order of O(log n).
//
//    If the target is not found in the array, return [-1, -1].
//
//    For example,
//    Given [5, 7, 7, 8, 8, 10] and target value 8,
//            return [3, 4].
    public static int[] searchRange(int[] nums, int target) {
        int[] resultArr = new int[]{-1, -1};
        if (nums == null || nums.length == 0) {
            return resultArr;
        }
        int startIndex = firstGreaterEqual(nums, target);
        if (startIndex == nums.length || nums[startIndex] != target) {
            return new int[]{-1, -1};
        }
        resultArr[0] = startIndex;
        resultArr[1] = firstGreaterEqual(nums, target + 1) - 1;

        return resultArr;
    }

    //找到第一个比目标大的数，总共调用两次函数，一次target，一次target+1
    private static int firstGreaterEqual(int[] A, int target) {
        int low = 0, high = A.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (A[mid] < target) {
                low = mid + 1;
            } else {
                //可能A[mid]=target，但是现在还不确定是否就是第一个出现target的位置
                high = mid;
            }
        }
        return low;
    }

    //    Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
//
//    For example, given n = 3, a solution set is:
//
//            [
//            "((()))",
//            "(()())",
//            "(())()",
//            "()(())",
//            "()()()"
//            ]
// Instead of adding '(' or ')' every time as in Approach #1, let's only add them when we know it will remain a valid sequence.
// We can do this by keeping track of the number of opening and closing brackets we have placed so far.
//We can start an opening bracket if we still have one (of n) left to place.
// And we can start a closing bracket if it would not exceed the number of opening brackets.
    public List<String> generateParenthesis(int n) {
        List<String> results = new ArrayList<>();
        if (n <= 0) {
            return results;
        }
        backTrack(results, "", 0, 0, n);

        return results;
    }

    public void backTrack(List<String> ans, String cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur);
        }

        //采用空格放置的思路，先放置全部的左括号，通过栈弹出后，塞右括号，只要右括号比左括号少就一直压栈
        //这样能确保所有的情况
        if (open < max) {
            backTrack(ans, cur + '(', open + 1, close, max);
        }
        if (open < close) {
            backTrack(ans, cur + ')', open, close + 1, max);
        }
    }

    //    Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
//
//    If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
//
//    The replacement must be in-place and use only constant extra memory.
//
//    Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
//
//            1,2,3 → 1,3,2
//            3,2,1 → 1,2,3
//            1,1,5 → 1,5,1
    // 给出一种全排列的情况，求出下一个全排列,要求字典序排序，下一个排列得刚好排在当前的后面
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }

        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        //后面因为已经是降序排列了，所以需要整个reverse一下
        `
        int j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
