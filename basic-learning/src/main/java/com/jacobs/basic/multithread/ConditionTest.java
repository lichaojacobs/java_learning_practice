package com.jacobs.basic.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lichao on 16/8/29.
 */
public class ConditionTest {
  final static Lock lock = new ReentrantLock();
  final static Condition notFull = lock.newCondition();
  final static Condition notEmpty = lock.newCondition();

  final static List<Object> items = new ArrayList<>();//缓存队列
  final static int size = 5;
  static int index = 0;

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.submit(new ConditionTest().new Consumer());
    executorService.submit(new ConditionTest().new Producer());
  }

  class Producer implements Runnable {
    @Override
    public void run() {
      lock.lock();
      try {
        while (true) {
          if (items.size() >= size) {
            notFull.await();
          }
          items.add(index);
          System.out.println("生产了第" + index + "号产品");
          index++;
          notEmpty.signal();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }
  }

  class Consumer implements Runnable {

    @Override
    public void run() {
      lock.lock();
      try {
        while (true) {
          if (items.size() == 0) {
            notEmpty.await();
          }
          items.remove(--index);
          System.out.println("消费了第" + index + "号产品");
          notFull.signal();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      } finally {
        lock.unlock();
      }
    }
  }

}

