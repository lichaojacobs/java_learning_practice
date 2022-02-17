//给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
//
// k 是一个正整数，它的值小于或等于链表的长度。 
//
// 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。 
//
// 进阶： 
//
// 
// 你可以设计一个只使用常数额外空间的算法来解决此问题吗？ 
// 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4,5], k = 2
//输出：[2,1,4,3,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [1,2,3,4,5], k = 3
//输出：[3,2,1,4,5]
// 
//
// 示例 3： 
//
// 
//输入：head = [1,2,3,4,5], k = 1
//输出：[1,2,3,4,5]
// 
//
// 示例 4： 
//
// 
//输入：head = [1], k = 1
//输出：[1]
// 
//
// 
// 
//
// 提示： 
//
// 
// 列表中节点的数量在范围 sz 内 
// 1 <= sz <= 5000 
// 0 <= Node.val <= 1000 
// 1 <= k <= sz 
// 
// Related Topics 递归 链表 
// 👍 1463 👎 0


package com.jacobs.basic.leetcode.editor.cn;

import com.jacobs.basic.models.ListNode;

public class ReverseNodesInKGroup {
    public static void main(String[] args) {
        Solution solution = new ReverseNodesInKGroup().new Solution();
    }
    //leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {
        public ListNode reverseKGroup(ListNode head, int k) {
            if (head == null) return null;
            ListNode a, b;
            a = head;
            b = head;
            for (int i = 0; i < k; i++) {
                // 不满足k个一组，则直接返回head
                if (b == null) return head;
                b = b.next;
            }
            ListNode newHead = reverseBetween(a, b);
            // 递归
            // 翻转后末尾节点即为a，将a与翻转后头节点相连
            a.next = reverseKGroup(b, k);
            return newHead;
        }

        /**
         * 翻转链表区间 [a, b)
         * @param a
         * @param b
         * @return
         */
        private ListNode reverseBetween(ListNode a, ListNode b) {
            ListNode pre = null;
            ListNode curr = a;
            ListNode next = a;
            while (curr != b) {
                next = curr.next;
                curr.next = pre;
                pre = curr;
                curr = next;
            }
            return pre;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}