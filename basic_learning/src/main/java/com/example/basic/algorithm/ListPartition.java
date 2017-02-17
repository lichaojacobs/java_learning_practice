package com.example.basic.algorithm;

/**
 * 将单向链表按某值划分成左边小，中间相等，右边大的形式
 * Created by lichao on 2017/1/8.
 */
public class ListPartition {
//  public static void main(String[] args) {
//
//  }

  public static Node listPartition1(Node head, int pivot) {
    if (head == null) {
      return head;
    }
    Node curr = head;
    int i = 0;
    while (curr != null) {
      i++;
      curr = curr.next;
    }
    Node[] nodeArray = new Node[i];
    curr = head;
    for (i = 0; i != nodeArray.length; i++) {
      nodeArray[i] = curr;
      curr = curr.next;
    }
    arrPartition(nodeArray, pivot);
    for (i = 1; i != nodeArray.length; i++) {
      nodeArray[i - 1].next = nodeArray[i];
    }
    nodeArray[i - 1].next = null;
    return nodeArray[0];
  }

  public static void arrPartition(Node[] nodeArray, int pivot) {
    int small = -1;
    int big = nodeArray.length;
    int index = 0;
    while (index != big) {
      if (nodeArray[index].value < pivot) {
        swap(nodeArray, ++small, index++);
      } else if (nodeArray[index].value == pivot) {
        index++;
      } else {
        swap(nodeArray, --big, index);
      }
    }
  }

  public static void swap(Node[] nodeArr, int a, int b) {
    Node temp = nodeArr[a];
    nodeArr[a] = nodeArr[b];
    nodeArr[b] = temp;
  }
}
