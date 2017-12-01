package com.jacobs.basic.multithread;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/**
 * @author lichao
 * @date 2017/11/19
 */
public class CyclicBarrierTest {

  public static class Soldier implements Runnable {

    private String soldier;
    private final CyclicBarrier cylic;

    Soldier(CyclicBarrier cyclicBarrier, String soldierName) {
      this.soldier = soldierName;
      this.cylic = cyclicBarrier;
    }

    @Override
    public void run() {
      try {
        //等待所有士兵到齐
        cylic.await();
        doWork();
        //等待所有士兵任务完成
        cylic.await();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    void doWork() {
      try {
        Thread.sleep(Math.abs(new Random().nextInt() % 10000));
      } catch (Exception ex) {
        ex.printStackTrace();
      }

      System.out.println(soldier + ": 任务完成！");
    }
  }


  public static class BarrierRun implements Runnable {

    boolean flag;
    int N;

    BarrierRun(boolean flag, int N) {
      this.flag = flag;
      this.N = N;
    }

    @Override
    public void run() {
      if (flag) {
        System.out.println("司令: [士兵 " + N + "个，任务完成！");
      } else {
        System.out.println("司令: 【士兵" + N + "个，集合完毕！");
        flag = true;
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    final int N = 10;
    Thread[] allSoldier = new Thread[N];
    boolean flag = false;

    //甚至屏障点，到达屏障后会执行BarrierRun
    CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(flag, N));
    System.out.println("集合队伍!");
    for (int i = 0; i < N; i++) {
      System.out.println("士兵" + i + " 报道!");
      allSoldier[i] = new Thread(new Soldier(cyclicBarrier, "士兵 " + i));
      if (i == 5) {
        //中断时抛出InterruptedException异常，避免永久等待
        allSoldier[0].interrupt();
      }
      allSoldier[i].start();
    }
  }
}
