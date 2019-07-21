package com.jacobs.basic.algorithm.linklist;

import com.jacobs.basic.algorithm.TreeNode;
import com.jacobs.basic.models.ListNode;

/**
 * Created by lichao on 2016/12/4.
 */
public class ReverseNode {

    public static void main(String[] args) {
        TreeNode treeNode1 = new TreeNode(1);
        treeNode1.next = new TreeNode(2);
        treeNode1.next.next = new TreeNode(3);
        treeNode1.next.next.next = new TreeNode(4);
        treeNode1.next.next.next.next = new TreeNode(5);
        //TreeNode head = reversePartNodeList(treeNode1, 2, 4);
        TreeNode head = reverserNodeList2(treeNode1);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    public static TreeNode reverseNodeList(TreeNode head) {
        if (head == null) {
            return null;
        }

        TreeNode pre = head;
        TreeNode fir = head;
        TreeNode aft = head;

        while (fir.next != null) {
            aft = fir.next;
            fir.next = aft.next;
            aft.next = pre;
            pre = aft;
        }

        return pre;
    }

    //假设result是前面已经排好逆序的头节点
    public static TreeNode reverserNodeList2(TreeNode head) {
        if (head == null) {
            return null;
        }

        TreeNode pre = null;

        while (head != null) {
            TreeNode aft = head.next;
            head.next = pre;
            pre = head;
            head = aft;
        }

        return pre;
    }

    public static TreeNode reversePartNodeList(TreeNode head, int from, int to) {
        int len = 0;
        TreeNode phead = head;
        TreeNode fromTreeNode = null;
        TreeNode toTreeNode = null;

        while (phead != null) {
            len++;
            fromTreeNode = len == from - 1 ? phead : fromTreeNode;
            toTreeNode = len == to + 1 ? phead : toTreeNode;
            phead = phead.next;
        }

        if (from > to || from < 1 || to > len) {
            return head;
        }

        phead = fromTreeNode == null ? head : fromTreeNode.next;
        TreeNode fir = phead;
        TreeNode aft;
        while (fir.next != toTreeNode) {
            aft = fir.next;
            fir.next = aft.next;
            aft.next = phead;
            phead = aft;
        }
        if (fromTreeNode != null) {
            fromTreeNode.next = phead;
            return head;
        }

        return phead;
    }

    /**
     * [92] 反转链表 II
     * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
     *
     * 说明:
     * 1 ≤ m ≤ n ≤ 链表长度。
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m <= 0 || n <= 0 || m >= n) {
            return head;
        }

        // 计算反转开始的前一个节点和后一个节点
        int len = 0;
        ListNode pHead = head;
        ListNode fromNode = null;
        ListNode toNode = null;
        while (pHead != null) {
            len++;
            if (len == m - 1) {
                fromNode = pHead;
            }
            if (len == n + 1) {
                toNode = pHead;
            }
            pHead = pHead.next;
        }
        // 过长则直接返回原链表
        if (n > len) {
            return head;
        }

        // 判断是否需要置换返回的头部节点
        pHead = fromNode == null ? head : fromNode.next;
        ListNode pre = pHead;
        ListNode curr = pHead;
        ListNode aft = pHead;
        while (curr.next != toNode) {
            aft = curr.next;
            curr.next = aft.next;
            aft.next = pre;
            pre = aft;
        }

        // 最后衔接转换后的几段链表
        if (fromNode != null) {
            fromNode.next = pre;
            // 这时直接返回原头节点，说明是在中间的部位反转
            return head;
        }
        //说明从头部开始反转
        return pre;
    }

}
