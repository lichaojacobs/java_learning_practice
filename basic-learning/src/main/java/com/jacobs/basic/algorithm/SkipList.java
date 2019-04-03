package com.jacobs.basic.algorithm;

import java.util.Random;

/**
 * @author lichao
 * @date 2019/01/28
 */
public class SkipList<T extends Comparable<? super T>> {

    //The max level of this list
    int level;
    //Head node
    Node<T> head;
    //The length of this list
    int len;
    //For determining the level for a new node
    Random rand = new Random();


    public SkipList(int level) {
        if (level < 2) {
            level = 2;
        } else if (level > 63) {
            level = 63; //up to 63 levels due to the algorithm of choosing level for new node
        }
        this.level = level;
        this.head = new Node<T>(null, level);
    }

    public void add(T val) {
        //Determine the level of the new node
        int nodeLevel = chooseLevel(this.level);
        //Create the new Node
        Node<T> node = new Node<T>(val, nodeLevel);
        //Add to skipList
        Node<T> curNode = head;
        int curLevel = nodeLevel - 1;
        while (curLevel >= 0) {
            while (curNode.forward[curLevel] != null && curNode.forward[curLevel].val.compareTo(val) < 0) {
                curNode = curNode.forward[curLevel];
            }
            node.forward[curLevel] = curNode.forward[curLevel];
            curNode.forward[curLevel] = node;
            curLevel--;
        }
        this.len++;
    }

    public void remove(T val) {
        Node<T> curNode = this.head;
        int curLevel = this.level - 1;
        boolean isFound = false;
        while (curLevel >= 0) {
            if (curNode.forward[curLevel] == null || curNode.forward[curLevel].val.compareTo(val) > 0) {
                curLevel--;
            } else if (curNode.forward[curLevel].val.compareTo(val) < 0) {
                curNode = curNode.forward[curLevel];
            } else {
                curNode.forward[curLevel] = curNode.forward[curLevel].forward[curLevel];
                curLevel--;
                isFound = true;
            }
        }
        if (isFound) {
            this.len--;
        }
    }

    //Search for the value, and return true if it exists
    public boolean contains(T val) {
        Node<T> curNode = this.head;
        int curLevel = this.level - 1;
        while (curLevel >= 0) {
            if (curNode.forward[curLevel] == null || curNode.forward[curLevel].val.compareTo(val) > 0) {
                curLevel--;
            } else if (curNode.forward[curLevel].val.compareTo(val) < 0) {
                curNode = curNode.forward[curLevel];
            } else {
                //found
                return true;
            }
        }
        return false;
    }

    public T get(int index) {
        Node<T> curNode = this.head;
        while (index >= 0) {
            curNode = curNode.forward[0];
            index--;
        }
        return curNode.val;
    }

    public int size() {
        return this.len;
    }

    //Get the level for the new node
    private int chooseLevel(int level) {
        long n = (long) 1 << (level - 1);
        long ranNum;
        if (level < 32) {
            ranNum = rand.nextInt((int) n) + 1; //[1, n]
        } else {
            //int overflow, use long for calculation
            ranNum = rand.nextLong();
            if (ranNum < 0) {
                ranNum = (ranNum + Long.MAX_VALUE) % n + 1; // [1, n]
            } else if (ranNum > n) {
                ranNum = ranNum % n + 1;  // [1, n]
            }
        }
        int ranLevel = 1;
        while (true) {
            n >>= 1;
            if (ranNum > n) {
                return ranLevel;
            }
            ranLevel++;
        }
    }

    //Node structure
    public static class Node<T extends Comparable<? super T>> {

        T val;
        Node[] forward;

        public Node(T val, int level) {
            this.val = val;
            this.forward = new Node[level];
        }
    }

    //Test
    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<Integer>(60);
        skipList.add(1);
        skipList.add(3);
        skipList.add(2);
        skipList.add(4);
        skipList.add(10);
        skipList.add(100);
        skipList.add(100);
        skipList.add(99);
        for (int i = 0; i < skipList.size(); i++) {
            System.out.print(skipList.get(i) + " ");
        }
        System.out.println();

        skipList.remove(100);
        for (int i = 0; i < skipList.size(); i++) {
            System.out.print(skipList.get(i) + " ");
        }
        System.out.println();

        skipList.remove(1);
        skipList.remove(3);
        skipList.remove(2);
        skipList.remove(4);
        skipList.remove(10);
        skipList.remove(100);
        skipList.remove(100);
        for (int i = 0; i < skipList.size(); i++) {
            System.out.print(skipList.get(i) + " ");
        }
    }
}
