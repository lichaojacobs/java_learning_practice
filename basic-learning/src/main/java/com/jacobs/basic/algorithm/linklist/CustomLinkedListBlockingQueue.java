package com.jacobs.basic.algorithm.linklist;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 设计实现模拟生产者，消费者队列的类
 *
 * @author lichao
 * @date 2019/03/14
 */
public class CustomLinkedListBlockingQueue<E> {

    static class Node<E> {

        E item;
        CustomLinkedListBlockingQueue.Node<E> next;

        Node(E x) {
            item = x;
        }
    }

    private ReentrantLock putLock = new ReentrantLock();
    private Condition notFullCondition = putLock.newCondition();

    private ReentrantLock takeLock = new ReentrantLock();
    private Condition notEmptyCondition = takeLock.newCondition();

    /**
     * Head of linked list. Invariant: head.item == null
     */
    transient Node head;

    /**
     * Tail of linked list. Invariant: last.next == null
     */
    private transient Node last;
    private int capacity;

    /**
     * 由于是读写锁，为了应对出现竞
     */
    private AtomicInteger count = new AtomicInteger();

    public CustomLinkedListBlockingQueue(int capacity) {
        this.capacity = capacity;
        last = head = new Node(null);
    }

    private void enqueue(Node<E> node) {
        last = last.next = node;
    }

    private E dequeue() {
        Node<E> h = head;
        Node<E> first = h.next;
        h.next = h;
        head = first;
        E x = first.item;
        first.item = null;
        return x;
    }

    private void signalNotEmpty() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            notEmptyCondition.signal();
        } finally {
            takeLock.unlock();
        }
    }

    private void signalNotFull() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            notFullCondition.signal();
        } finally {
            putLock.unlock();
        }
    }

    public void put(E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        final int c;
        final Node<E> node = new Node<>(e);
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();

        try {

            while (count.get() == capacity) {
                notFullCondition.await();
            }
            enqueue(node);
            c = count.getAndIncrement();
            if (c + 1 < capacity) {
                notFullCondition.signal();
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0) // 由于存在放锁和拿锁，这里可能拿锁一直在消费数据，count会变化。这里的if条件表示如果队列中还有1条数据
        {
            signalNotEmpty(); // 在拿锁的条件对象notEmpty上唤醒正在等待的1个线程，表示队列里还有1条数据，可以进行消费
        }
    }

    public E take() throws InterruptedException {
        final E x;
        final int c;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                notEmptyCondition.await();
            }
            x = dequeue();
            c = count.getAndDecrement();
            if (c > 1) {
                notEmptyCondition.signal();
            }
        } finally {
            takeLock.unlock();
        }

        if (c == capacity) {
            signalNotFull();
        }
        return x;
    }
}
