package com.example.basic.algorithm.binarytree;

import com.example.basic.algorithm.Node;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lichao on 2017/3/14.
 */
public class PrintByLevel {
  public static void main(String[] args) {

  }

  public static void printByLevel(Node head) {
    if (head == null) {
      return;
    }

    //定义俩个栈
    List<Node> current = new LinkedList<>();
    List<Node> reverse = new LinkedList<>();
    current.add(head);
    boolean isEven = true;
    while (current.size() > 0) {
      Node temp = current.remove(current.size() - 1);
      System.out.println(temp.value + ", ");
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
        List<Node> tempList = current;
        current = reverse;
        reverse = tempList;
        isEven = false;
      }
    }
  }
}
