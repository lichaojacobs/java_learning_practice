package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lichao on 2017/9/14.
 */
public class BST {

  public static void main(String[] args) {
    TreeNode testHead = new TreeNode(6);
    testHead.left = new TreeNode(1);
    testHead.right = new TreeNode(12);
    testHead.left.left = new TreeNode(1);
    testHead.left.right = new TreeNode(3);
    testHead.right.left = new TreeNode(10);
    testHead.right.right = new TreeNode(13);

    System.out.println(isSearchTree2(testHead));
  }

  /**
   * record 记录四个信息：左子树最大搜索二叉树的头节点(lBST)，节点数(lSize)、最小值(lMin)和最大值(lMax)。
   * 再遍历右子树收集四个信息，分别是右子树上最大的搜索二叉树的头节点(rBST)，节点数(rSize)、最小值(rMin)和最大值(rMax) <p> 判断：
   * （1）根据收集的信息判断是否满足:如果node左子树最大搜索二叉树的和右子树最大搜索二叉树均为其子节点，并且 node 左子树上的最大搜索二叉树的最大值小于
   * 小于node.val，node右子树上的最大搜索二叉树的最小值大于node.val，那么称这整个树都是搜索二叉树。
   * （2）如果不满足1，则，以node为头的树上的最大搜索二叉树是来自node的左子树上的最大搜索二叉子树和来自node的右子树上的最大搜索二叉子树之间，节点数最大的那个
   * <p> 理解的时候试图递归到最小的单元去一步一步往上推导
   */
  public static TreeNode getBiggestSubBST(TreeNode head, int[] record) {
    if (head == null) {
      record[0] = 0;
      record[1] = Integer.MAX_VALUE;
      record[2] = Integer.MIN_VALUE;
      return null;
    }

    int value = head.val;
    TreeNode left = head.left;
    TreeNode right = head.right;

    TreeNode lBST = getBiggestSubBST(left, record);
    int lSize = record[0];
    int lMax = record[1];
    int lMin = record[2];

    TreeNode rBST = getBiggestSubBST(right, record);
    int rSize = record[0];
    int rMax = record[1];
    int rMin = record[2];

    record[1] = Math.max(rMax, value);
    record[2] = Math.min(lMin, value);
    if (lBST == left && rBST == right && lMax < value && value < rMin) {
      record[0] = lSize + rSize + 1;
      return head;
    }

    record[0] = Math.max(lSize, rSize);
    return lSize > rSize ? lBST : rBST;
  }


  //判断是否是搜索二叉树
  public static boolean isBST(TreeNode head) {
    if (head == null) {
      return true;
    }

    if (head.left != null && head.left.val > head.val) {
      return false;
    }

    if (head.right != null && head.right.val < head.val) {
      return false;
    }

    return isBST(head.left) && isBST(head.right);
  }


  /**
   * 判断一棵树是否为搜索二叉树 (中序遍历),这里采用了递归的思想，分治
   */
  public static boolean isSearchTree(TreeNode head) {
    if (head == null) {
      return true;
    }

    return check(head) && isSearchTree(head.left) && isSearchTree(head.right);
  }

  public static boolean check(TreeNode head) {
    boolean isSearchSubTree = true;
    if (head.left != null) {
      isSearchSubTree = head.val > head.left.val;
    }
    if (head.right != null) {
      isSearchSubTree = head.val < head.right.val;
    }

    return isSearchSubTree;
  }


  /**
   * 中序遍历，发现存在降序的情况就不是搜索二叉树，否则就是搜索二叉树O(N)的解决方法,Morris中序遍历
   */
  public static boolean isSearchTree2(TreeNode head) {
    if (head == null) {
      return true;
    }

    boolean res = true;
    TreeNode pre = null;
    TreeNode cur1 = head;
    TreeNode cur2 = null;

    while (cur1 != null) {
      cur2 = cur1.left;
      if (cur2 != null) {
        while (cur2.right != null && cur2.right != cur1) {
          cur2 = cur2.right;
        }
        if (cur2.right == null) {
          cur2.right = cur1;
          cur1 = cur1.left;
          continue;
        } else {
          cur2.right = null;
        }
      }
      if (pre != null && pre.val > cur1.val) {
        res = false;
      }
      pre = cur1;
      cur1 = cur1.right;
    }

    return res;
  }


  /**
   * 判断一棵树是否为完全二叉树 1。按层遍历,从每层的左边向右边依次遍历所有的节点 2. 如果当前节点有右孩子，但是没有左孩子，直接返回false
   * 3。如果，当前节点并不是左右孩子都有，那之后的节点必须都是叶子节点，否则返回false 4，遍历过程中如果不返回false,遍历结束后返回true
   */
  public static boolean isCBT(TreeNode head) {
    if (head == null) {
      return true;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    boolean leaf = false;
    TreeNode l = null;
    TreeNode r = null;
    queue.offer(head);

    while (!queue.isEmpty()) {
      head = queue.poll();
      l = head.left;
      r = head.right;

      if (l == null && r != null) {
        return false;
      }

      //如果确定是要叶子节点的话，l和r应该都为null
      if (leaf && (l != null || r != null)) {
        return false;
      }

      if (l == null || r == null) {
        leaf = true;//之后的节点必须都为叶子节点
      }

      if (l != null) {
        queue.offer(l);
      }

      if (r != null) {
        queue.offer(r);
      }
    }

    return true;
  }

}
