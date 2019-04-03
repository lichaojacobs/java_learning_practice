package com.jacobs.basic.multithread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lichao on 2016/12/16.
 */
public class AtomicIntegerDemo {

  static AtomicInteger number = new AtomicInteger();

  public static class AddThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
      for (int k = 0; k < 10000; k++) {
        number.incrementAndGet();
      }
      return 1;
    }
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    Integer count = 0;
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    List<FutureTask<Integer>> futures = Stream.generate(() -> {
      FutureTask<Integer> future = new FutureTask<>(new AddThread());
      executorService.submit(future);
      return future;
    })
        .limit(30)
        .collect(Collectors.toList());
    for (FutureTask<Integer> futureTask : futures) {
      futureTask.get();
    }

    System.out.println(number.get());
  }
}
