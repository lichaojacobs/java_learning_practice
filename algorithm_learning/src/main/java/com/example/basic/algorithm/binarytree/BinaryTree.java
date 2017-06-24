package com.example.basic.algorithm.binarytree;

import com.google.common.collect.Lists;

import com.example.basic.algorithm.Node;
import com.example.basic.algorithm.dp.LIS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
    Node root = constructTree(Lists.newArrayList(1, 2, 4, 5, 3, 6), 0,
        5, Lists.newArrayList(4, 2, 5, 1, 6, 3), 0, 5);
    preOrderRecur2(root);
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
   * 后序遍历（非递归）一个栈
   * lastPrin 表示最近一次打印的节点，用于防止死循环压入已经弹出的节点
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
}
