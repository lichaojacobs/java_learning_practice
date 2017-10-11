package com.jacobs.basic.multithread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lichao on 2016/11/30.
 */
public class MainTest {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
//    ForkJoinPool forkJoinPool = new ForkJoinPool();
//    ForkJoinTest task = new ForkJoinTest(0, 200000);
//    System.out.println(forkJoinPool.invoke(task));
    BlockingQueue<String> fruits = new LinkedBlockingDeque<>(2);
    new Thread(new MainTest().new Producer(fruits)).start();
    new Thread(new MainTest().new Consumer(fruits)).start();
  }

  private class Producer implements Runnable {

    private BlockingQueue<String> fruits;

    public Producer(BlockingQueue<String> fruits) {
      this.fruits = fruits;
    }

    @Override
    public void run() {
      for (int i = 0; i < 10; i++) {
        try {
          System.out.println("Produced a new product: product_" + i);
          fruits.put("product_ " + i);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private class Consumer implements Runnable {

    private BlockingQueue<String> fruits;

    public Consumer(BlockingQueue<String> fruits) {
      this.fruits = fruits;
    }

    @Override
    public void run() {
      while (true) {
        try {
          String temp = fruits.take();
          System.out.println("Consumed a new product: " + temp);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
