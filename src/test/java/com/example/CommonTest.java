package com.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lichao on 2016/11/24.
 */
public class CommonTest {
  public static void main(String[] args) throws InterruptedException {
//    ExecutorService executorService = Executors.newCachedThreadPool();

    Thread.currentThread().join();
    System.out.println("hhhhh");
  }

  class CallTask implements Callable<Integer> {
    private int number;

    CallTask(int number) {
      this.number = number;
    }

    @Override
    public Integer call() throws Exception {
      return number;
    }
  }
}
