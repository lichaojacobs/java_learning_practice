package com.jacobs.basic.algorithm;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author lichao
 * @date 2017/11/10
 */
public class Practice {

  public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(-2);
    root.right = new TreeNode(3);
    System.out.println(maxPathSum(root));
  }


  /**
   * 大数相乘
   *
   * @param numStr1 num1
   * @param numStr2 num2
   */
  public static void multiply(String numStr1, String numStr2) {
    if (numStr1 == null || numStr2 == null) {
      return;
    }

    char[] chars1 = numStr1.toCharArray();
    char[] chars2 = numStr2.toCharArray();

    LinkedList<Integer> resultList = new LinkedList<>();

    int zeroNum = 0;
    for (int i = chars1.length - 1; i >= 0; i--) {
      LinkedList<Integer> tempResultList = getTempResultList(chars1[i], chars2);
      setResultList(resultList, zeroNum, tempResultList);
      zeroNum++;
    }

    for (int i = 0; i < resultList.size(); i++) {
      System.out.print(resultList.get(i));
    }
  }

  private static LinkedList<Integer> getTempResultList(char c, char[] chars2) {
    int up = 0;
    int curr = 0;
    LinkedList<Integer> tempResultList = new LinkedList<>();
    for (int j = chars2.length - 1; j >= 0; j--) {
      curr =
          Integer.valueOf(String.valueOf(c)) * Integer.valueOf(String.valueOf(chars2[j]))
              + up;
      if (curr > 10) {
        up = curr / 10;
        curr = curr % 10;
      } else {
        //注意up每次都得变
        up = 0;
      }
      //加入当前位的值
      tempResultList.addFirst(curr);
    }
    return tempResultList;
  }

  private static void setResultList(LinkedList<Integer> resultList, int index,
      LinkedList<Integer> tempResultList) {
    //每层先补0
    for (int k = 0; k < index; k++) {
      tempResultList.addLast(0);
    }
    //里层循环结束，将和加起来
    int tempi = resultList.size() - 1;
    int tempj = tempResultList.size() - 1;
    int tempUp = 0;
    int tempCurr = 0;
    while (tempi >= 0 && tempj >= 0) {
      tempCurr = resultList.get(tempi) + tempResultList.get(tempj) + tempUp;
      if (tempCurr > 10) {
        tempUp = tempCurr / 10;
        tempCurr = tempCurr % 10;
      } else {
        tempUp = 0;
      }
      resultList.set(tempi, tempCurr);
      tempi--;
      tempj--;
    }
    while (tempj >= 0) {
      resultList.addFirst(tempResultList.get(tempj));
      tempj--;
    }
  }

  /**
   * 二分查找
   */
  public static int binarySearch(int[] arr, int key) {
    if (arr == null || arr.length == 0) {
      return -1;//非法值
    }

    int left = 0;
    int right = arr.length - 1;
    while (left <= right) {
      int mid = (left + right) / 2;
      if (arr[mid] == key) {
        return mid;
      } else if (arr[mid] > key) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    return -1;
  }

  public static void mergeSort(int[] arr, int left, int right) {
    if (arr == null || arr.length == 0 || left >= right) {
      return;
    }

    int mid = (left + right) / 2;
    mergeSort(arr, left, mid);
    mergeSort(arr, mid + 1, right);
    merge(arr, left, right, mid);
  }

  public static void merge(int[] arr, int left, int right, int mid) {
    int[] tempArr = new int[right + 1];
    int tempLeft = left;
    int tempMid = mid + 1;

    int index = 0;
    while (tempLeft <= mid && tempMid <= right) {
      if (tempArr[tempLeft] >= tempArr[tempMid]) {
        tempArr[index++] = arr[tempMid++];
      } else {
        tempArr[index++] = arr[tempLeft++];
      }
    }

    if (tempLeft <= mid) {
      tempArr[index++] = arr[tempLeft++];
    }

    if (tempMid <= right) {
      tempArr[index++] = arr[tempMid++];
    }

    //复制到原数组
    for (int i = left; i <= right; i++) {
      arr[i] = tempArr[i];
    }
  }


  /**
   * 获取一棵树中，两个节点的最大距离
   *
   * @param root root 根节点
   * @return distance 最大距离
   */
  public static int getMaxDistance(TreeNode root, int[] res) {
    if (root == null) {
      res[0] = 0;
      return 0;
    }

    int leftMax = getMaxDistance(root.left, res);
    int maxFromLeft = res[0];

    int rightMax = getMaxDistance(root.right, res);
    int maxFromRight = res[0];

    int currNodeMax = maxFromLeft + maxFromRight + 1;
    res[0] = Math.max(leftMax, rightMax) + 1;

    return Math.max(Math.max(leftMax, rightMax), currNodeMax);
  }

  /**
   * 在其他数都出现k次的数组中找到只出现一次的数
   *
   * @param arr 整形数组
   * @param k k 进制
   * @return 返回只出现一次的数
   */
  public static int onceNum(int[] arr, int k) {
    int[] result = new int[32];
    for (int i = 0; i < arr.length; i++) {
      int[] kResult = getKvalue(arr[i], k);
      for (int j = 0; j < result.length; j++) {
        result[j] = (result[j] + kResult[j]) % k;
      }
    }

    return changeToTenValue(result, k);
  }

  public static int[] getKvalue(int num, int k) {
    int[] res = new int[32];

    int index = 0;
    while (num != 0) {
      res[index] = num % k;
      num = num / k;
      index++;
    }

    return res;
  }

  public static int changeToTenValue(int[] result, int k) {
    int num = 0;
    for (int i = result.length - 1; i >= 0; i--) {
      num = num * k + result[i];
    }

    return num;
  }


  /**
   * 判断一棵树是否是搜索二叉树
   *
   * @param root 根节点
   * @return 返回是否是搜索二叉树
   */
  public static boolean isValidBST(TreeNode root) {
    if (root == null || (root.left == null && root.right == null)) {
      return true;
    }

    Stack<TreeNode> stack = new Stack<>();
    TreeNode currentNode = root;
    TreeNode preNode = null;

    while (!stack.isEmpty() || currentNode != null) {
      if (currentNode != null) {
        stack.push(currentNode);
        currentNode = currentNode.left;
      } else {
        currentNode = stack.pop();
        if (preNode != null && (preNode.val >= currentNode.val)) {
          return false;
        }
        preNode = currentNode;
        currentNode = currentNode.right;
      }
    }

    return true;
  }

  //Given preorder and inorder traversal of a tree, construct the binary tree.
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    if (preorder == null || inorder == null || preorder.length != inorder.length) {
      return null;
    }

    return constructNode(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
  }

  public TreeNode constructNode(int[] preorder, int[] inorder, int preStart, int preEnd,
      int inStart, int inEnd) {
    if (preStart < preEnd) {
      return null;
    }

    TreeNode root = new TreeNode(preorder[preStart]);
    int index = inStart;
    for (; index <= inEnd; index++) {
      if (inorder[index] == preorder[preStart]) {
        break;
      }
    }

    root.left = constructNode(preorder, inorder, preStart + 1, preStart + index - inStart,
        inStart, index - 1);
    root.right = constructNode(preorder, inorder, preStart + index - inStart + 1, preEnd, index + 1,
        inEnd);

    return root;
  }

  // Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
  // Given the sorted array: [-10,-3,0,5,9],
//
//  One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
//
//      0
//      / \
//      -3   9
//      /   /
//      -10  5
  public static TreeNode sortedArrayToBST(int[] nums) {
    if (nums == null || nums.length == 0) {
      return null;
    }

    return constructBST(nums, 0, nums.length - 1);
  }

  public static TreeNode constructBST(int[] nums, int start, int end) {
    if (start > end) {
      return null;
    }

    int mid = (start + end) / 2;
    TreeNode head = new TreeNode(mid);
    head.left = constructBST(nums, start, mid - 1);
    head.right = constructBST(nums, mid + 1, end);
    return head;
  }

  //  Given a binary tree and a sum, determine if the tree has a root-to-leaf
//  path such that adding up all the values
//  along the path equals the given sum.
  public static boolean hasPathSum(TreeNode root, int sum) {
    if (root == null) {
      return false;
    }

    if ((root.left == null && root.right == null)) {
      if (root.val == sum) {
        return true;
      } else {
        return false;
      }
    }

    return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
  }

  //  Given a binary tree, find the maximum path sum.
//
//  For this problem, a path is defined as any sequence of nodes from some starting node
//
//  to any node in the tree along the parent-child connections. The path must contain at least one node
//
//  and does not need to go through the root.
  //无非来自三个地方，左边，右边，跨节点，或者就是当前节点
  public static int maxPathSum(TreeNode root) {
    return help(root, new int[1]);
  }

  public static int help(TreeNode root, int[] res) {
    if (root == null) {
      res[0] = 0;
      return Integer.MIN_VALUE;
    }

    int leftMax = help(root.left, res);
    int maxFromLeft = res[0];

    int rightMax = help(root.right, res);
    int maxFromRight = res[0];

    int current = maxFromLeft + maxFromRight + root.val;
    res[0] = Math.max(maxFromLeft, maxFromRight) + root.val;

    return Math.max(Math.max(Math.max(leftMax, rightMax), current), root.val);
  }

  //杨辉三角
  public static void printYFTriangle() {
    int lines = 8;
    int[] a = new int[lines + 1];
    int previous = 1;
    for (int i = 1; i <= lines; i++) {
      for (int j = 1; j <= i; j++) {
        int current = a[j];
        a[j] = previous + current;
        previous = current;
        System.out.print(a[j] + " ");
      }
      System.out.println();
    }
  }
}
