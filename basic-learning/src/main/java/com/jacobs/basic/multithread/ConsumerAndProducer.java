package com.jacobs.basic.multithread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lichao on 16/8/21.
 */
public class ConsumerAndProducer {

  //所有
  private static BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(5);
  static ExecutorService executorService = Executors.newFixedThreadPool(2);

  public static void main(String[] args) {
    executorService.submit(new ConsumerAndProducer().new Consumer());
    executorService.submit(new ConsumerAndProducer().new Producer());
  }

  class Producer implements Runnable {

    @Override
    public void run() {
      for (int i = 0; i < 10; i++) {
        try {
          blockingQueue.put("第" + i + "号产品");
          System.out.println("生产了第" + i + "号产品");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  class Consumer implements Runnable {

    @Override
    public void run() {
      while (true) {
        try {
          String product = blockingQueue.take();
          System.out.println("消费了" + product);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}

