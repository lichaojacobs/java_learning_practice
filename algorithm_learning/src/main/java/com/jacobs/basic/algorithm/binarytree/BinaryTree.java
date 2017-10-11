package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.Node;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by lichao on 2017/2/8.
 */
public class BinaryTree {

  public static void main(String[] args) {
    Node head = new Node(1);
    head.right = new Node(2);
    //    postorderTraversal(head).stream()
    //        .sorted(Comparator.naturalOrder())
    //        .forEach(System.out::println);

    //System.out.println(postorderTraversal(head).size());
//    Node root = constructTree(Lists.newArrayList(1, 2, 4, 5, 3, 6), 0,
//        5, Lists.newArrayList(4, 2, 5, 1, 6, 3), 0, 5);
//    preOrderRecur2(root);
    int[] arr = new int[]{0, 3, 1, 10, 13, 12, 6};
    System.out.println(isBSTAfterOrder(arr));
  }

  /**
   * 先序遍历(递归)
   */
  public static void preOrderRecur1(Node head) {
    if (head == null) {
      return;
    }

    System.out.println(head.value + " ");
    preOrderRecur1(head.left);
    preOrderRecur1(head.right);
  }

  /**
   * 先序遍历（非递归）
   */
  public static void preOrderRecur2(Node head) {
    if (head == null) {
      return;
    }

    Stack<Node> stack = new Stack<>();
    stack.push(head);
    Node currentNode = null;
    while (!stack.isEmpty()) {
      currentNode = stack.pop();
      System.out.println(currentNode.value + " ");
      if (currentNode.right != null) {
        stack.push(currentNode.right);
      }
      if (currentNode.left != null) {
        stack.push(currentNode.left);
      }
    }
  }

  /**
   * 中序遍历(递归)
   */
  public static void inOrderRecur1(Node head) {
    if (head == null) {
      return;
    }

    preOrderRecur1(head.left);
    System.out.println(head.value + " ");
    preOrderRecur1(head.right);
  }

  /**
   * 中序遍历（非递归）
   */
  public static void inOrderRecur2(Node head) {
    if (head == null) {
      return;
    }

    Stack<Node> stack = new Stack<>();
    stack.push(head);
    Node currentNode = head;
    while (!stack.isEmpty()) {
      if (currentNode.left != null) {
        stack.push(currentNode.left);
        currentNode = currentNode.left;
      } else {
        currentNode = stack.pop();
        System.out.println(currentNode.value + " ");
        if (currentNode.right != null) {
          stack.push(currentNode.right);
          currentNode = currentNode.right;
        }
      }
    }
  }

  /**
   * 后序遍历(递归)
   */
  public static void afterOrderRecur1(Node head) {
    if (head == null) {
      return;
    }

    preOrderRecur1(head.left);
    preOrderRecur1(head.right);
    System.out.println(head.value + " ");
  }

  /**
   * 后序遍历（非递归）俩个栈
   */
  public static void afterOrderRecur2(Node head) {
    if (head == null) {
      return;
    }

    Stack<Node> stack = new Stack<>();
    Stack<Node> output = new Stack<>();//构造一个中间栈来存储逆后续遍历的结果

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
      System.out.println(output.pop().value + " ");
    }
  }

  /**
   * 后序遍历（非递归）一个栈 lastPrin 表示最近一次打印的节点，用于防止死循环压入已经弹出的节点
   */
  public static void afterOrderRecur3(Node head) {
    if (head == null) {
      return;
    }

    Stack<Node> stack = new Stack<>();
    stack.push(head);
    Node current;
    Node lastPrin = null;

    while (!stack.isEmpty()) {
      current = stack.peek();

      if (current.left != null && lastPrin != current.left && lastPrin != current.right) {
        stack.push(current.left);
      } else if (current.right != null && lastPrin != current.right) {
        stack.push(current.right);
      } else {
        System.out.println(stack.pop().value + " ");
        lastPrin = current;
      }
    }
  }

  public static ArrayList<Integer> postorderTraversal(Node root) {
    ArrayList<Integer> list = new ArrayList<>();
    if (root == null) {
      return list;
    }
    Stack<Node> stack = new Stack<>();
    Node lastPrint = null;
    Node curr = null;
    stack.push(root);

    while (!stack.isEmpty()) {
      curr = stack.peek();
      if (curr.left != null && curr.left != lastPrint && curr.right != lastPrint) {
        stack.push(curr.left);
      } else if (curr.right != null && curr.right != lastPrint) {
        stack.push(curr.right);
      } else {
        list.add(stack.pop().value);
        lastPrint = curr;
      }
    }

    return list;
  }


  /**
   * 根据先序和中序恢复二叉树
   */
  public static Node constructTree(List<Integer> preOrderList, int startPre, int endPre,
      List<Integer> inOrderList,
      int startIn, int endIn) {
    if ((endPre - startPre) != (endIn - startIn)) {
      return null;
    }

    if (startPre > endPre) {
      return null;
    }

    Integer rootValue = preOrderList.get(startPre);
    Node root = new Node(rootValue);
    root.left = null;
    root.right = null;

    //终止条件
    if (startPre == endPre) {
      return root;
    }

    ///分拆子树的左子树和右子树
    int index = 0, length = 0;
    for (index = startIn; index <= endIn; index++) {
      if (inOrderList.get(index) == preOrderList.get(startPre)) {
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
  public void layerorder(Node root) {
    System.out.print("binaryTree层次遍历:");
    LinkedList<Node> queue = new LinkedList<Node>();
    queue.addLast(root);
    Node current = null;
    while (!queue.isEmpty()) {
      current = queue.removeFirst();
      if (current.left != null) {
        queue.addLast(current.left);
      }
      if (current.right != null) {
        queue.addLast(current.right);
      }
      System.out.print(current.value);
    }
    System.out.println();
  }

  /**
   * 深入优先遍历(栈)，找出从跟节点到目标节点路径
   */
  public static int getDistance(Node head, Node left, Node right) {
    if (head == null || left == null || right == null) {
      return 0;
    }

    List<Node> leftList = getPathList(head, left);
    List<Node> rightList = getPathList(head, right);

    leftList.stream()
        .forEach(System.out::print);
    System.out.println();
    rightList.stream()
        .forEach(System.out::print);

    return 0;
  }

  public static List<Node> getPathList(Node head, Node dest) {
    Stack<Node> nodeStack = new Stack<>();
    Node lasCurr = null;
    Node curr = null;
    nodeStack.push(head);
    List<Node> nodeList = Lists.newArrayList();

    while (!nodeStack.isEmpty()) {
      curr = nodeStack.peek();
      if (curr == dest) {
        break;
      }
      if (curr.left != null && curr.left != lasCurr && curr.right != lasCurr) {
        nodeStack.push(curr.left);
      } else if (curr.right != null && curr.right != lasCurr) {
        nodeStack.push(curr.right);
      } else {
        nodeStack.pop();
        lasCurr = curr;
      }
    }

    while (!nodeStack.isEmpty()) {
      nodeList.add(nodeStack.pop());
    }

    return nodeList;
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
  public static Node constructBSTTreeUseAfterOrder(int[] arr) {
    if (arr == null || arr.length == 0) {
      return null;
    }

    //Node head = new Node(arr[arr.length - 1]);

    return constructBST(arr, 0, arr.length - 1);
  }

  public static Node constructBST(int[] arr, int start, int end) {
    if (start > end) {
      return null;
    }

    Node head = new Node(arr[end]);

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
  public static Node getNearestParentNode(Node head, Node o1, Node o2) {
    if (head == null || head == o1 || head == o2) {
      return head;
    }

    Node left = getNearestParentNode(head.left, o1, o2);
    Node right = getNearestParentNode(head.right, o1, o2);

    if (left != null && right != null) {
      //此时一定是最近的公共祖先
      return head;
    }

    return left != null ? left : right;
  }


  /**
   * 在二叉树中找到两个节点的最近公共祖先（后序遍历） 优化：利用哈希表存储所有的父节点
   */
  public static Node getNearestParentNode2(Node head, Node o1, Node o2) {
    HashMap<Node, Node> parentMap = Maps.newHashMap();
    parentMap.put(head, null);//设置初始值
    constructParentMap(head, parentMap);
    return findNearestParentNode(o2, o2, parentMap);
  }

  public static void constructParentMap(Node head, HashMap<Node, Node> parentMap) {
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

  public static Node findNearestParentNode(Node o1, Node o2, HashMap<Node, Node> parentMap) {
    List<Node> path = Lists.newArrayList();

    Node tempParent = parentMap.get(o1);
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
  public static Node InAftToTree(int[] in, int[] aft) {
    if (aft == null || in == null) {
      return null;
    }

    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < in.length; i++) {
      map.put(in[i], i);
    }

    return preIn(in, 0, in.length - 1, aft, 0, aft.length - 1, map);
  }

  public static Node aftIn(int[] n, int nStart, int nEnd, int[] aft, int aftStart, int aftEnd,
      HashMap<Integer, Integer> map) {
    if (nStart > nEnd) {
      return null;
    }

    int index = map.get(aftEnd);
    Node head = new Node(n[index]);
    head.left = aftIn(n, nStart, index - 1, aft, aftStart, aftEnd - index - 1, map);
    head.right = aftIn(n, index + 1, nEnd, aft, aftEnd - index + 1, aftEnd - 1, map);

    return head;
  }


  /**
   * 先序与中序结合数组重构二叉树
   */
  public static Node preInToTree(int[] pre, int[] in) {
    if (pre == null || in == null) {
      return null;
    }

    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < in.length; i++) {
      map.put(in[i], i);
    }

    return preIn(pre, 0, pre.length - 1, in, 0, in.length - 1, map);
  }

  public static Node preIn(int[] p, int pi, int pj, int[] n, int ni, int nj,
      HashMap<Integer, Integer> map) {
    if (pi > pj) {
      return null;
    }

    Node head = new Node(p[pi]);
    int index = map.get(p[pi]);
    head.left = preIn(p, pi + 1, pi + index - ni, n, ni, index - 1, map);
    head.right = preIn(p, pi + index - ni + 1, pj, n, index + 1, nj, map);

    return head;
  }

  /**
   * (难度较高)根据先序和后序数组重构二叉树（一棵树，除叶子节点外，每个节点都有左孩子和右孩子才能重构成二叉树）
   */
  public static Node preAftToTree(int[] pre, int[] aft) {
    if (pre == null || aft == null) {
      return null;
    }

    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < aft.length; i++) {
      map.put(aft[i], i);
    }

    return preAft(pre, 0, pre.length - 1, aft, 0, aft.length - 1, map);
  }

  public static Node preAft(
      int[] p, int pi, int pj, int[] aft, int aftStart, int aftEnd,
      Map<Integer, Integer> map) {
    Node head = new Node(aft[aftEnd]);
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
}
