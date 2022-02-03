//给定一个二叉树 根节点 root ，树的每个节点的值要么是 0，要么是 1。请剪除该二叉树中所有节点的值为 0 的子树。
//
// 节点 node 的子树为 node 本身，以及所有 node 的后代。 
//
// 
//
// 示例 1: 
//
// 
//输入: [1,null,0,0,1]
//输出: [1,null,0,null,1] 
//解释: 
//只有红色节点满足条件“所有不包含 1 的子树”。
//右图为返回的答案。
//
//
// 
//
// 示例 2: 
//
// 
//输入: [1,0,1,0,0,0,1]
//输出: [1,null,1,null,1]
//解释: 
//
//
// 
//
// 示例 3: 
//
// 
//输入: [1,1,0,1,1,0,1,0]
//输出: [1,1,0,1,1,null,1]
//解释: 
//
//
// 
//
// 
//
// 提示: 
//
// 
// 二叉树的节点个数的范围是 [1,200] 
// 二叉树节点的值只会是 0 或 1 
// 
//
// 
//
// 注意：本题与主站 814 题相同：https://leetcode-cn.com/problems/binary-tree-pruning/ 
// Related Topics 树 深度优先搜索 二叉树 
// 👍 17 👎 0


package com.jacobs.basic.leetcode.editor.cn;

import com.jacobs.basic.algorithm.TreeNode;

public class POCWxh {
    public static void main(String[] args) {
        Solution solution = new POCWxh().new Solution();
    }
    //leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */
    class Solution {
        // 采用后续遍历，标志可剪裁的子树val为-1
        int FLAG = -1;

        public TreeNode pruneTree(TreeNode root) {
            TreeNode result = pruneTreeHelper(root);
            if (result != null && result.val == FLAG) {
                return null;
            }
            return result;
        }

        public TreeNode pruneTreeHelper(TreeNode root) {
            if (root == null) {
                return null;
            }
            pruneTreeHelper(root.left);
            pruneTreeHelper(root.right);
            boolean prunedLeft = false;
            //可以裁剪左子树
            if (root.left == null || root.left.val == FLAG) {
                root.left = null;
                prunedLeft = true;
            }
            //可以裁剪右子树
            boolean prunedRight = false;
            if (root.right == null || root.right.val == FLAG) {
                root.right = null;
                prunedRight = true;
            }
            if (prunedLeft && prunedRight && root.val == 0) {
                root.val = FLAG;
            }
            return root;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}