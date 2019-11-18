package com.jacobs.basic.algorithm;

/**
 * Created by lichao on 2016/11/2.
 */
public class TreeNode {

    public int val;
    public TreeNode next;
    public TreeNode left;
    public TreeNode right;
    public TreeNode parent;

    public TreeNode(int data) {
        this.val = data;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                ", next=" + next +
                ", left=" + left +
                ", right=" + right +
                ", parent=" + parent +
                '}';
    }
}
