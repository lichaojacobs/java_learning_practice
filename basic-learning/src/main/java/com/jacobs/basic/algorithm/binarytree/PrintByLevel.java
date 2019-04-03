package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lichao on 2017/3/14.
 */
public class PrintByLevel {
    public static void main(String[] args) {

    }


    /**
     * 判断t1树是否包含t2树全部的拓扑结构
     *
     * @param t1
     * @param t2
     */
    public static boolean contains(TreeNode t1, TreeNode t2) {
        return check(t1, t2) || check(t1.left, t2) || check(t1.right, t2);
    }

    public static boolean check(TreeNode t1, TreeNode t2) {
        if (t2 == null) {
            return true;
        }
        if (t1 == null || t1.val != t2.val) {
            return false;
        }

        return check(t1.left, t2.left) && check(t1.right, t2.right);
    }

    public static void printByLevel(TreeNode head) {
        if (head == null) {
            return;
        }

        //定义俩个栈
        List<TreeNode> current = new LinkedList<>();
        List<TreeNode> reverse = new LinkedList<>();
        current.add(head);
        boolean isEven = true;
        while (current.size() > 0) {
            TreeNode temp = current.remove(current.size() - 1);
            System.out.println(temp.val + ", ");
            if (isEven) {
                if (temp.right != null) {
                    reverse.add(temp.right);
                }
                if (temp.left != null) {
                    reverse.add(temp.left);
                }
            } else {
                if (temp.left != null) {
                    reverse.add(temp.left);
                }
                if (temp.right != null) {
                    reverse.add(temp.right);
                }
            }

            if (current.size() == 0) {
                List<TreeNode> tempList = current;
                current = reverse;
                reverse = tempList;
                isEven = false;
            }
        }
    }
}
