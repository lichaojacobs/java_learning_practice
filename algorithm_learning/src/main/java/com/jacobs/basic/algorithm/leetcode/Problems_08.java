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
        for (int i : twoSum(new int[]{2, 7, 11, 15}, 9)) {
            System.out.println(i);
        }
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
     * （今日头条二面原题)
     * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return null;
    }
}
