package com.example.algorithm.binarytree;

import com.example.algorithm.Node;

import java.util.Stack;

/**
 * Created by lichao on 2017/2/8.
 */
public class BinaryTreeRecur {
  public static void main(String[] args) {
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
}
