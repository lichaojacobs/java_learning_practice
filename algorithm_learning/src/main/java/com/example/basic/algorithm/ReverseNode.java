package com.example.basic.algorithm;

/**
 * Created by lichao on 2016/12/4.
 */
public class ReverseNode {
  public static void main(String[] args) {
    Node node1 = new Node(1);
    node1.next = new Node(2);
    node1.next.next = new Node(3);
    node1.next.next.next = new Node(4);
    node1.next.next.next.next = new Node(5);
    //Node head = reversePartNodeList(node1, 2, 4);
    Node head = reverseNodeList(node1);
    while (head != null) {
      System.out.println(head.value);
      head = head.next;
    }
  }

  public static Node reverseNodeList(Node head) {
    if (head == null) {
      return null;
    }

    Node pre = head;
    Node fir = head;
    Node aft = head;

    while (fir.next != null) {
      aft = fir.next;
      fir.next = aft.next;
      aft.next = pre;
      pre = aft;
    }

    return pre;
  }

  public static Node reversePartNodeList(Node head, int from, int to) {
    int len = 0;
    Node phead = head;
    Node fromNode = null;
    Node toNode = null;

    while (phead != null) {
      len++;
      fromNode = len == from - 1 ? phead : fromNode;
      toNode = len == to + 1 ? phead : toNode;
      phead = phead.next;
    }

    if (from > to || from < 1 || to > len) {
      return head;
    }

    phead = fromNode == null ? head : fromNode.next;
    Node fir = phead;
    Node aft;
    while (fir.next != toNode) {
      aft = fir.next;
      fir.next = aft.next;
      aft.next = phead;
      phead = aft;
    }
    if (fromNode != null) {
      fromNode.next = phead;
      return head;
    }

    return phead;
  }

}
