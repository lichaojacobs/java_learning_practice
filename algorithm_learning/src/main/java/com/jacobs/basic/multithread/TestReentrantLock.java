package com.jacobs.basic.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

  private static int aCount = 0;
  private static int bCount = 0;
  private static int cCount = 0;

  private static Lock lock = new ReentrantLock();

  private static Condition aCondition = lock.newCondition();
  private static Condition bCondition = lock.newCondition();
  private static Condition cCondition = lock.newCondition();

  public static void main(String[] args) {
    Runnable a = new Runnable() {
      @Override
      public void run() {
        while (aCount < 10) {
          lock.lock();
          try {
            System.out.print("a");
            aCount++;
            bCondition.signal();
            aCondition.await();
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            lock.unlock();
          }
        }
      }
    };

    Runnable b = new Runnable() {
      @Override
      public void run() {
        while (bCount < 10) {
          lock.lock();
          try {
            System.out.print("b");
            bCount++;
            cCondition.signal();
            bCondition.await();
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            lock.unlock();
          }
        }
      }
    };

    Runnable c = new Runnable() {
      @Override
      public void run() {
        while (cCount < 10) {
          lock.lock();
          try {
            System.out.print("c");
            cCount++;
            aCondition.signal();
            cCondition.await();
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            lock.unlock();
          }
        }
      }
    };

    new Thread(a).start();
    new Thread(b).start();
    new Thread(c).start();
  }

}