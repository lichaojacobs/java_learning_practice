//路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不
//一定经过根节点。 
//
// 路径和 是路径中各节点值的总和。 
//
// 给你一个二叉树的根节点 root ，返回其 最大路径和 。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,2,3]
//输出：6
//解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6 
//
// 示例 2： 
//
// 
//输入：root = [-10,9,20,null,null,15,7]
//输出：42
//解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目范围是 [1, 3 * 104] 
// -1000 <= Node.val <= 1000 
// 
// Related Topics 树 深度优先搜索 动态规划 二叉树 
// 👍 1393 👎 0


package com.jacobs.basic.leetcode.editor.cn;

import com.jacobs.basic.algorithm.TreeNode;

public class BinaryTreeMaximumPathSum {
    public static void main(String[] args) {
        Solution solution = new BinaryTreeMaximumPathSum().new Solution();
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
        // 维护一个全局的变量，用于更新以各个节点为根节点的最大路径和
        int maxResult = Integer.MIN_VALUE;

        // 二叉树的后序遍历搞定
        public int maxPathSum(TreeNode root) {
            maxPathSumHelper(root);
            return maxResult;
        }

        public int maxPathSumHelper(TreeNode root) {
            if (root == null) {
                return 0;
            }
            // 分为左右两子树的情况
            // 0表示，若左子树结果为正，则记入贡献中，否则不计入，即为0
            int left = Math.max(0, maxPathSumHelper(root.left));
            int right = Math.max(0, maxPathSumHelper(root.right));

            //以当前节点为根节点从下延伸得到的结果
            int current = left + right + root.val;
            // 更新当前最新的值
            maxResult = Math.max(maxResult, current);
            //TODO 要搞明白返回给上层则只能选择左边或者右边这一条路径，而不是三种情况 !!!!
            return Math.max(left, right) + root.val;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}