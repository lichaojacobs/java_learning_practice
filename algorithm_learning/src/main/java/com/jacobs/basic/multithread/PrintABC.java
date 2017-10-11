package com.jacobs.basic.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lichao on 2016/12/31.
 */
public class PrintABC {

  static ReentrantLock lock = new ReentrantLock();
  static Condition conditionA = lock.newCondition();
  static Condition conditionB = lock.newCondition();
  static Condition conditionC = lock.newCondition();
  static int signal = 1;
  static int loopValue = 10;

  class taskA implements Runnable {
    @Override
    public void run() {
      lock.lock();
      try {
        for (int i = 0; i < loopValue; i++) {
          if (signal != 1) {
            conditionA.await();
            conditionB.signalAll();
            conditionC.signalAll();
          }
          signal = 2;
          System.out.print("A");
          conditionB.signal();
          conditionA.await();
        }
      } catch (Exception ex) {

      } finally {
        lock.unlock();
      }
    }
  }

  class taskB implements Runnable {

    @Override
    public void run() {
      lock.lock();
      try {
        for (int i = 0; i < loopValue; i++) {
          if (signal != 2) {
            conditionB.await();
            conditionA.signalAll();
            conditionC.signalAll();
          }
          signal = 3;
          System.out.print("B");
          conditionC.signal();
          conditionB.await();

        }
      } catch (Exception ex) {

      } finally {
        lock.unlock();
      }
    }
  }

  class taskC implements Runnable {

    @Override
    public void run() {
      lock.lock();
      try {
        for (int i = 0; i < loopValue; i++) {
          if (signal != 3) {
            conditionC.await();
            conditionB.signalAll();
            conditionA.signalAll();
          }
          signal = 1;
          System.out.print("C");
          conditionA.signal();
          conditionC.await();
        }
      } catch (Exception ex) {

      } finally {
        lock.unlock();
      }
    }
  }

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    executorService.submit(new PrintABC().new taskC());
    executorService.submit(new PrintABC().new taskB());
    executorService.submit(new PrintABC().new taskA());
  }
}
