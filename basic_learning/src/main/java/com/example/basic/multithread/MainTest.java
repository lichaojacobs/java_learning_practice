package com.example.basic.multithread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by lichao on 2016/11/30.
 */
public class MainTest {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    ForkJoinTest task = new ForkJoinTest(0, 200000);
    System.out.println(forkJoinPool.invoke(task));
  }
}
