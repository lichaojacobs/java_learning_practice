package com.jacobs.basic.multithread;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lichao
 * @date 2018/1/29
 */
public class CyclicBarrierTest2 {


  /**
   * 可以想象三个选手脚都绑到了一起，只有三个人都说ready准备好后才可以一起撤掉脚带，开始各自的奔跑
   */
  public static void main(String[] args) throws IOException,
      InterruptedException {
    // 如果将参数改为4，但是下面只加入了3个选手，这永远等待下去
    // Waits until all parties have invoked await on this barrier.
    CyclicBarrier barrier = new CyclicBarrier(3, new TotalTask());

    ExecutorService executor = Executors.newFixedThreadPool(3);
    executor.submit(new Thread(new Runner(barrier, "1号选手")));
    executor.submit(new Thread(new Runner(barrier, "2号选手")));
    executor.submit(new Thread(new Runner(barrier, "3号选手")));

    executor.shutdown();
  }


  static class Runner implements Runnable {

    private CyclicBarrier barrier;

    private String name;

    public Runner(CyclicBarrier barrier, String name) {
      super();
      this.barrier = barrier;
      this.name = name;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(1000 * (new Random()).nextInt(8));
        System.out.println(name + " 准备好了...");
        // barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
        barrier.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }
      System.out.println(name + " 起跑！");
    }
  }


  /**
   * 主任务：汇总任务
   */
  static class TotalTask implements Runnable {

    @Override
    public void run() {
      // 等到所人都准备好后，再开始
      System.out.println("=======================================");
      System.out.println("开始一起跑啦！");
    }
  }
}
