package com.jacobs.basic.algorithm;

import java.util.HashMap;

/**
 * * leetcode 146. LRU Cache
 *
 * @author lichao
 * @date 2019/01/30
 */
public class LRUCache {

    private class Node {
        int key;
        int value;
        Node pre;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.pre = null;
            this.next = null;
        }
    }

    static int DEFAULT_CAPACITY = 3;
    HashMap<Integer, Node> cache = new HashMap<>();
    Node head = new Node(-1, -1);
    Node tail = new Node(-1, -1);
    int capacity;

    public LRUCache(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        } else {
            this.capacity = DEFAULT_CAPACITY;
        }

        // 初始化双向链表
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        if (cache.get(key) == null) {
            return -1;
        }

        // remove current node in linkedList
        Node current = cache.get(key);
        current.pre.next = current.next;
        current.next.pre = current.pre;

        // 移到末尾，表示最新使用的节点
        moveToTail(current);

        return current.value;
    }

    public void moveToTail(Node curr) {
        curr.pre = tail.pre;
        tail.pre = curr;
        curr.pre.next = curr;
        curr.next = tail;
    }

    public void put(int key, int value) {
        // 说明已经存在相同的key
        if (get(key) != -1) {
            cache.get(key).value = value;
            return;
        }

        // 如果超过了容量
        if (cache.size() == capacity) {
            cache.remove(head.next.key);
            head.next = head.next.next;
            head.next.pre = head;
        }

        Node curr = new Node(key, value);
        cache.put(key, curr);
        moveToTail(curr);
    }
}
