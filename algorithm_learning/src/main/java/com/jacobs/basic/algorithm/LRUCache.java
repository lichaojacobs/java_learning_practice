package com.jacobs.basic.algorithm;

import java.util.HashMap;

/**
 * Created by lichao on 2017/9/22.
 */
public class LRUCache {

  private class Node {

    Node prev;
    Node next;
    int key;
    int value;

    public Node(int key, int value) {
      this.key = key;
      this.value = value;
      this.prev = null;
      this.next = null;
    }
  }

  private int capacity;
  private HashMap<Integer, Node> hs = new HashMap<Integer, Node>();
  private Node head = new Node(-1, -1);
  private Node tail = new Node(-1, -1);

  // @param capacity, an integer
  public LRUCache(int capacity) {
    // write your code here
    this.capacity = capacity;
    tail.prev = head;
    head.next = tail;
  }

  // @return an integer
  public int get(int key) {
    // write your code here
    if (!hs.containsKey(key)) {
      return -1;
    }

    // remove current
    Node current = hs.get(key);
    current.prev.next = current.next;
    current.next.prev = current.prev;

    // move current to tail
    move_to_tail(current);

    return hs.get(key).value;


  }

  // @param key, an integer
  // @param val, an integer
  // @return nothing
  public void set(int key, int value) {
    // write your code here
    if (get(key) != -1) {
      hs.get(key).value = value;
      return;
    }

    if (hs.size() == capacity) {
      hs.remove(head.next.key);
      head.next = head.next.next;
      head.next.prev = head;
    }

    Node insert = new Node(key, value);
    hs.put(key, insert);
    move_to_tail(insert);
  }

  private void move_to_tail(Node current) {
    current.prev = tail.prev;
    tail.prev = current;
    current.prev.next = current;
    current.next = tail;
  }

  public static void main(String[] as) throws Exception {
    LRUCache cache = new LRUCache(3);
    cache.set(2, 2);
    cache.set(1, 1);

    System.out.println(cache.get(2));
    System.out.println(cache.get(1));
    System.out.println(cache.get(2));

    cache.set(3, 3);
    cache.set(4, 4);

    System.out.println(cache.get(3));
    System.out.println(cache.get(2));
    System.out.println(cache.get(1));
    System.out.println(cache.get(4));

  }
}
