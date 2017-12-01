package com.jacobs.basic.algorithm.binarytree;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jacobs.basic.algorithm.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by lichao on 2017/2/8.
 */
public class BinaryTree {

  public static void main(String[] args) {
    TreeNode root = new TreeNode(2);
    root.left = new TreeNode(3);
    root.left.left = new TreeNode(1);

    inOrderRecur2(root);
  }

  /**
   * 先序遍历(递归)
   */
  public static void preOrderRecur1(TreeNode head) {
    if (head == null) {
      return;
    }

    System.out.println(head.val + " ");
    preOrderRecur1(head.left);
    preOrderRecur1(head.right);
  }

  /**
   * 先序遍历（非递归）
   */
  public static void preOrderRecur2(TreeNode head) {
    if (head == null) {
      return;
    }

    Stack<TreeNode> stack = new Stack<>();
    stack.push(head);
    TreeNode currentTreeNode = null;
    while (!stack.isEmpty()) {
      currentTreeNode = stack.pop();
      System.out.println(currentTreeNode.val + " ");
      if (currentTreeNode.right != null) {
        stack.push(currentTreeNode.right);
      }
      if (currentTreeNode.left != null) {
        stack.push(currentTreeNode.left);
      }
    }
  }

  /**
   * 中序遍历(递归)
   */
  public static void inOrderRecur1(TreeNode head) {
    if (head == null) {
      return;
    }

    preOrderRecur1(head.left);
    System.out.println(head.val + " ");
    preOrderRecur1(head.right);
  }

  /**
   * 中序遍历（非递归）
   */
  public static void inOrderRecur2(TreeNode head) {
    if (head == null) {
      return;
    }

    Stack<TreeNode> stack = new Stack<>();
    while (!stack.isEmpty() || head != null) {
      if (head != null) {
        stack.push(head);
        head = head.left;
      } else {
        head = stack.pop();
        System.out.println(head.val + " ");
        head = head.right;
      }
    }
  }

  /**
   * 后序遍历(递归)
   */
  public static void afterOrderRecur1(TreeNode head) {
    if (head == null) {
      return;
    }

    preOrderRecur1(head.left);
    preOrderRecur1(head.right);
    System.out.println(head.val + " ");
  }

  /**
   * 后序遍历（非递归）俩个栈
   */
  public static void afterOrderRecur2(TreeNode head) {
    if (head == null) {
      return;
    }

    Stack<TreeNode> stack = new Stack<>();
    Stack<TreeNode> output = new Stack<>();//构造一个中间栈来存储逆后续遍历的结果

    stack.push(head);
    while (!stack.isEmpty()) {
      head = stack.pop();
      output.push(head);

      if (head.left != null) {
        stack.push(head.left);
      }

      if (head.right != null) {
        stack.push(head.right);
      }
    }

    while (!output.isEmpty()) {
      System.out.println(output.pop().val + " ");
    }
  }

  /**
   * 后序遍历（非递归）一个栈 lastPrin 表示最近一次打印的节点，用于防止死循环压入已经弹出的节点
   */
  public static void afterOrderRecur3(TreeNode head) {
    if (head == null) {
      return;
    }

    Stack<TreeNode> stack = new Stack<>();
    stack.push(head);
    TreeNode current;
    TreeNode lastPrin = null;

    while (!stack.isEmpty()) {
      current = stack.peek();

      if (current.left != null && lastPrin != current.left && lastPrin != current.right) {
        stack.push(current.left);
      } else if (current.right != null && lastPrin != current.right) {
        stack.push(current.right);
      } else {
        System.out.println(stack.pop().val + " ");
        lastPrin = current;
      }
    }
  }

  public static ArrayList<Integer> postorderTraversal(TreeNode root) {
    ArrayList<Integer> list = new ArrayList<>();
    if (root == null) {
      return list;
    }
    Stack<TreeNode> stack = new Stack<>();
    TreeNode lastPrint = null;
    TreeNode curr = null;
    stack.push(root);

    while (!stack.isEmpty()) {
      curr = stack.peek();
      if (curr.left != null && curr.left != lastPrint && curr.right != lastPrint) {
        stack.push(curr.left);
      } else if (curr.right != null && curr.right != lastPrint) {
        stack.push(curr.right);
      } else {
        list.add(stack.pop().val);
        lastPrint = curr;
      }
    }

    return list;
  }


  /**
   * 根据先序和中序恢复二叉树
   */
  public static TreeNode constructTree(List<Integer> preOrderList, int startPre, int endPre,
      List<Integer> inOrderList,
      int startIn, int endIn) {
    if ((endPre - startPre) != (endIn - startIn)) {
      return null;
    }

    if (startPre > endPre) {
      return null;
    }

    Integer rootValue = preOrderList.get(startPre);
    TreeNode root = new TreeNode(rootValue);
    root.left = null;
    root.right = null;

    //终止条件
    if (startPre == endPre) {
      return root;
    }

    ///分拆子树的左子树和右子树
    int index = 0, length = 0;
    for (index = startIn; index <= endIn; index++) {
      if (inOrderList.get(index).equals(preOrderList.get(startPre))) {
        break;
      }
    }

    if (index > startIn) {
      length = index - startIn;
      root.left = constructTree(preOrderList, startPre + 1, startPre + 1 + length - 1, inOrderList,
          startIn, startIn + length - 1);
    }

    if (index < endIn) {
      root.right = constructTree(preOrderList, endPre - length + 1, endPre, inOrderList,
          endIn - length + 1, endIn);
    }

    return root;
  }

  /**
   * 层次遍历(队列)
   */
  public void layerorder(TreeNode root) {
    System.out.print("binaryTree层次遍历:");
    LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
    queue.addLast(root);
    TreeNode current = null;
    while (!queue.isEmpty()) {
      current = queue.removeFirst();
      if (current.left != null) {
        queue.addLast(current.left);
      }
      if (current.right != null) {
        queue.addLast(current.right);
      }
      System.out.print(current.val);
    }
    System.out.println();
  }

  /**
   * 层次遍历，打印行数
   *
   * @param root root of tree
   */
  public static void levelOrder(TreeNode root) {
    if (root == null) {
      return;
    }

    //只需要last等于nlast即可（last表示当前层的最右，nlast表示下一层的最右边节点）
    TreeNode last = root;
    TreeNode nLast = null;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    int level = 1;
    System.out.println(String.format("打印第%d层: ", level));
    while (!queue.isEmpty()) {
      TreeNode temp = queue.poll();
      System.out.print(temp.val + " ");
      if (temp.left != null) {
        queue.add(temp.left);
        nLast = temp.left;
      }
      if (temp.right != null) {
        queue.add(temp.right);
        nLast = temp.right;
      }

      if (temp == last) {
        last = nLast;
        level++;
        System.out.println(String.format("打印第%d层: ", level));
      }
    }
  }

  /**
   * 深入优先遍历(栈)，找出从跟节点到目标节点路径
   */
  public static int getDistance(TreeNode head, TreeNode left, TreeNode right) {
    if (head == null || left == null || right == null) {
      return 0;
    }

    List<TreeNode> leftList = getPathList(head, left);
    List<TreeNode> rightList = getPathList(head, right);

    leftList.stream()
        .forEach(System.out::print);
    System.out.println();
    rightList.stream()
        .forEach(System.out::print);

    return 0;
  }

  public static List<TreeNode> getPathList(TreeNode head, TreeNode dest) {
    Stack<TreeNode> treeNodeStack = new Stack<>();
    TreeNode lasCurr = null;
    TreeNode curr = null;
    treeNodeStack.push(head);
    List<TreeNode> treeNodeList = Lists.newArrayList();

    while (!treeNodeStack.isEmpty()) {
      curr = treeNodeStack.peek();
      if (curr == dest) {
        break;
      }
      if (curr.left != null && curr.left != lasCurr && curr.right != lasCurr) {
        treeNodeStack.push(curr.left);
      } else if (curr.right != null && curr.right != lasCurr) {
        treeNodeStack.push(curr.right);
      } else {
        treeNodeStack.pop();
        lasCurr = curr;
      }
    }

    while (!treeNodeStack.isEmpty()) {
      treeNodeList.add(treeNodeStack.pop());
    }

    return treeNodeList;
  }


  /**
   * 给定一个整形数组，已知其中没有重复值，判断arr是否可能是节点值类型为整形的搜索二叉树后序遍历的结果， 还是二分去判断,跟节点是数组的最末尾,从左到右，一开始都是小于最末尾节点，而后都是大于最末尾节点，要是有几次交替的话，说明不满足搜索二叉树的特点
   */
  public static Boolean isBSTAfterOrder(int[] arr) {
    if (arr == null || arr.length == 0) {
      return false;
    }

    return isPost(arr, 0, arr.length - 1);
  }

  public static Boolean isPost(int[] arr, int start, int end) {
    if (start == end) {
      return true;
    }

    int index = start;
    boolean largerThenEnd = false;
    int nextEnd = start;
    while (index < end) {
      if (arr[index] > arr[end]) {
        if (!largerThenEnd) {
          nextEnd = index - 1;
          largerThenEnd = true;
        }
      } else {
        if (largerThenEnd) {
          //中间出现几次波动
          return false;
        }
      }

      index++;
    }

    if (nextEnd == -1) {
      return isPost(arr, start, end - 1);
    }

    return isPost(arr, start, nextEnd) && isPost(arr, nextEnd + 1, end - 1);
  }


  /**
   *
   * @param arr
   * @return
   *
   */
  //根据后序遍历的结果重新构造搜索二叉树
  public static TreeNode constructBSTTreeUseAfterOrder(int[] arr) {
    if (arr == null || arr.length == 0) {
      return null;
    }

    //TreeNode head = new TreeNode(arr[arr.length - 1]);

    return constructBST(arr, 0, arr.length - 1);
  }

  public static TreeNode constructBST(int[] arr, int start, int end) {
    if (start > end) {
      return null;
    }

    TreeNode head = new TreeNode(arr[end]);

    //找出下一个根节点
    int index = start;
    int nextEnd = start;
    while (index < end) {
      if (arr[index] > arr[end]) {
        nextEnd = index - 1;
      }
      index++;
    }

    head.left = constructBST(arr, start, nextEnd);
    head.right = constructBST(arr, nextEnd + 1, end - 1);

    return head;
  }

  /**
   * 在二叉树中找到两个节点的最近公共祖先（后序遍历）
   */
  public static TreeNode getNearestParentNode(TreeNode head, TreeNode o1, TreeNode o2) {
    if (head == null || head == o1 || head == o2) {
      return head;
    }

    TreeNode left = getNearestParentNode(head.left, o1, o2);
    TreeNode right = getNearestParentNode(head.right, o1, o2);

    if (left != null && right != null) {
      //此时一定是最近的公共祖先
      return head;
    }

    //说明此分支两个节点并不是同时存在，这时候返回存在的那个节点，供上一层去判断
    return left != null ? left : right;
  }


  /**
   * 在二叉树中找到两个节点的最近公共祖先（后序遍历） 优化：利用哈希表存储所有的父节点
   */
  public static TreeNode getNearestParentNode2(TreeNode head, TreeNode o1, TreeNode o2) {
    HashMap<TreeNode, TreeNode> parentMap = Maps.newHashMap();
    parentMap.put(head, null);//设置初始值
    constructParentMap(head, parentMap);
    return findNearestParentNode(o2, o2, parentMap);
  }

  public static void constructParentMap(TreeNode head, HashMap<TreeNode, TreeNode> parentMap) {
    if (head == null) {
      return;
    }

    if (head.left != null && !parentMap.containsKey(head.left)) {
      parentMap.put(head.left, head);
    }
    if (head.right != null && !parentMap.containsKey(head.right)) {
      parentMap.put(head.right, head);
    }

    constructParentMap(head.left, parentMap);
    constructParentMap(head.right, parentMap);
  }

  public static TreeNode findNearestParentNode(TreeNode o1, TreeNode o2,
      HashMap<TreeNode, TreeNode> parentMap) {
    List<TreeNode> path = Lists.newArrayList();

    TreeNode tempParent = parentMap.get(o1);
    if (tempParent != null) {
      path.add(tempParent);
    }
    while (parentMap.containsKey(tempParent)) {
      tempParent = parentMap.get(tempParent);
      path.add(tempParent);
    }

    for (int i = 0; i < path.size(); i++) {
      o2 = parentMap.get(o2);
      if (path.get(i) == o2) {
        break;
      }
    }

    return o2;
  }


  /**
   * 根据中序和后序遍历数组重构二叉树
   */
  public static TreeNode InAftToTree(int[] in, int[] aft) {
    if (aft == null || in == null) {
      return null;
    }

    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < in.length; i++) {
      map.put(in[i], i);
    }

    return preIn(in, 0, in.length - 1, aft, 0, aft.length - 1, map);
  }

  public static TreeNode aftIn(int[] n, int nStart, int nEnd, int[] aft, int aftStart, int aftEnd,
      HashMap<Integer, Integer> map) {
    if (nStart > nEnd) {
      return null;
    }

    int index = map.get(aftEnd);
    TreeNode head = new TreeNode(n[index]);
    head.left = aftIn(n, nStart, index - 1, aft, aftStart, aftEnd - index - 1, map);
    head.right = aftIn(n, index + 1, nEnd, aft, aftEnd - index + 1, aftEnd - 1, map);

    return head;
  }


  /**
   * 先序与中序结合数组重构二叉树
   */
  public static TreeNode preInToTree(int[] pre, int[] in) {
    if (pre == null || in == null) {
      return null;
    }

    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < in.length; i++) {
      map.put(in[i], i);
    }

    return preIn(pre, 0, pre.length - 1, in, 0, in.length - 1, map);
  }

  public static TreeNode preIn(int[] p, int pi, int pj, int[] n, int ni, int nj,
      HashMap<Integer, Integer> map) {
    if (pi > pj) {
      return null;
    }

    TreeNode head = new TreeNode(p[pi]);
    int index = map.get(p[pi]);
    head.left = preIn(p, pi + 1, pi + index - ni, n, ni, index - 1, map);
    head.right = preIn(p, pi + index - ni + 1, pj, n, index + 1, nj, map);

    return head;
  }

  /**
   * (难度较高)根据先序和后序数组重构二叉树（一棵树，除叶子节点外，每个节点都有左孩子和右孩子才能重构成二叉树）
   */
  public static TreeNode preAftToTree(int[] pre, int[] aft) {
    if (pre == null || aft == null) {
      return null;
    }

    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < aft.length; i++) {
      map.put(aft[i], i);
    }

    return preAft(pre, 0, pre.length - 1, aft, 0, aft.length - 1, map);
  }

  public static TreeNode preAft(
      int[] p, int pi, int pj, int[] aft, int aftStart, int aftEnd,
      Map<Integer, Integer> map) {
    TreeNode head = new TreeNode(aft[aftEnd]);
    if (pi == pj) {
      return head;
    }

    int index = map.get(p[++pi]);
    head.left = preAft(p, pi, pi + index - aftStart, aft, aftStart, index, map);
    head.right = preAft(p, pi + index - aftStart + 1, pj, aft, index + 1, aftEnd - 1, map);

    return head;
  }

  /**
   * 根据先序和中序遍历数组生成后序遍历数组(从右往左依次填好后序数组)
   */
  public static int[] preInToAft(int[] pre, int[] in) {
    if (pre == null || in == null || pre.length != in.length) {
      return null;
    }

    int[] aft = new int[pre.length];

    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < in.length; i++) {
      map.put(in[i], i);
    }

    generateAft(pre, 0, pre.length - 1, in, 0, in.length - 1, aft, in.length - 1, map);

    return aft;
  }

  public static int generateAft(int[] pre, int preStart, int preEnd, int[] in, int inStart,
      int inEnd, int[] aft, int aftIndex, Map<Integer, Integer> map) {
    if (preStart > preEnd) {
      return aftIndex;
    }

    aft[aftIndex--] = pre[preStart];
    int index = map.get(pre[preStart]);
    aftIndex = generateAft(pre, preEnd - inEnd + index + 1, preEnd, in, index + 1, inEnd, aft,
        aftIndex, map);
    return generateAft(pre, preStart + 1, preStart + 1 - inStart, in, inStart, index - 1, aft,
        aftIndex, map);
  }

  //给定一个二叉树的根结点root，请依次返回二叉树的先序，中序和后续遍历(二维数组的形式)。
  public static int[][] convert_30(TreeNode root) {
    // write code here
    List<TreeNode> fisrtOrder = new ArrayList<>();
    List<TreeNode> midOrder = new ArrayList<>();
    List<TreeNode> aftOrder = new ArrayList<>();

    //first order
    TreeNode firstRoot = root;
    Stack<TreeNode> firstStack = new Stack<>();
    firstStack.push(firstRoot);
    while (!firstStack.isEmpty()) {
      TreeNode curr = firstStack.pop();
      fisrtOrder.add(curr);
      if (curr.right != null) {
        firstStack.push(curr.right);
      }
      if (curr.left != null) {
        firstStack.push(curr.left);
      }
    }

    //mid order
    TreeNode midRoot = root;
    Stack<TreeNode> midStack = new Stack<>();
    midStack.push(root);
    TreeNode curr = midRoot;
    TreeNode last = null;
    while (!midStack.isEmpty()) {
      curr = midStack.peek();
      if (curr.left != null && last != curr.left) {
        midStack.push(curr.left);
      } else {
        curr = midStack.pop();
        midOrder.add(curr);
        last = curr;
        if (curr.right != null) {
          midStack.push(curr.right);
        }
      }
    }

    //aft order
    TreeNode aftRoot = root;
    Stack<TreeNode> aftStack = new Stack<>();
    TreeNode lasPrint = null;
    aftStack.push(aftRoot);
    while (!aftStack.isEmpty()) {
      curr = aftStack.peek();
      if (curr.left != null && lasPrint != curr.left && lasPrint != curr.right) {
        aftStack.push(curr.left);
      } else if (curr.right != null && lasPrint != curr.right) {
        aftStack.push(curr.right);
      } else {
        curr = aftStack.pop();
        aftOrder.add(curr);
        lasPrint = curr;
      }
    }

    int[][] results = new int[3][fisrtOrder.size()];
    for (int i = 0; i < fisrtOrder.size(); i++) {
      results[0][i] = fisrtOrder.get(i).val;
      results[1][i] = midOrder.get(i).val;
      results[2][i] = aftOrder.get(i).val;
    }

    return results;
  }

  /**
   * 二叉树镜像
   *
   * @param root 根节点
   */
  public static void mirrorRecursively(TreeNode root) {
    if (root == null) {
      return;
    }

    if (root.left == null && root.right == null) {
      return;
    }

    TreeNode tempNode = root.left;
    root.left = root.right;
    root.right = tempNode;

    if (root.left != null) {
      mirrorRecursively(root.left);
    }

    if (root.right != null) {
      mirrorRecursively(root.right);
    }
  }
}
