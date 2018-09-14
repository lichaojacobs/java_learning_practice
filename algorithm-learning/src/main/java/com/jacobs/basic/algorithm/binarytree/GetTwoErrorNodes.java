package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;

import java.util.Stack;

/**
 * Created by lichao on 2017/9/10.
 */
public class GetTwoErrorNodes {
    public static void main(String[] args) {

    }

    /**
     * 思路是中序遍历的时候会出现两次降序
     *
     * @param head
     * @return
     */
    public static TreeNode[] getTwoErrorNodes(TreeNode head) {
        TreeNode[] errors = new TreeNode[2];
        if (head == null) {
            return errors;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;

        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (head != null && pre != null && head.val < pre.val) {
                    errors[0] = errors[0] == null ? pre : errors[0];
                    errors[1] = head;
                }

                pre = head;
                head = head.right;
            }

        }

        return errors;
    }
}
