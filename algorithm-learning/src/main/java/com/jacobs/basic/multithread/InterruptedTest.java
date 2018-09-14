package com.jacobs.basic.multithread;

/**
 * Created by lichao on 2017/1/20.
 */
public class InterruptedTest extends Thread {

  volatile boolean stop = false;// 线程中断信号量

  public static void main(String args[]) throws Exception {
    InterruptedTest thread = new InterruptedTest();
    System.out.println("Starting thread...");
    thread.start();
    Thread.sleep(3000);
    System.out.println("Asking thread to stop...");
    // 设置中断信号量
    thread.stop = true;
    Thread.sleep(3000);
    System.out.println("Stopping application...");
  }

  public void run() {
    // 每隔一秒检测一下中断信号量
    while (!stop) {
      System.out.println("Thread is running...");
      long time = System.currentTimeMillis();
            /*
             * 使用while循环模拟 sleep 方法，这里不要使用sleep，否则在阻塞时会 抛
             * InterruptedException异常而退出循环，这样while检测stop条件就不会执行，
             * 失去了意义。
             */
      while ((System.currentTimeMillis() - time < 1000)) {
      }
    }
    System.out.println("Thread exiting under request...");
  }
}
