package com.example.multithread;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by lichao on 2016/11/30.
 */
public class CompleteFutureTest {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    //combineTest();
    //acceptBoth();
    littleTest();
    //mutipleTest();
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    try {
      System.out.println(executorService.submit(new CallableTask())
          .get(3, TimeUnit.SECONDS));
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
  }

  public static class CallableTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
      Thread.currentThread()
          .sleep(5000);
      return 1;
    }
  }

  public static void littleTest() {
    CompletableFuture.supplyAsync(
        () -> {
          System.out.println("run--" + Thread.currentThread()
              .getName());
          return "hello";
        })
        .thenApply(s -> {
          System.out.println("second apply");
          return s + " hhh";
        })
        .exceptionally(throwable -> {
          System.out.println(throwable.getMessage());
          return throwable.getMessage();
        })
        .thenAccept(s -> System.out.println(s + "lichao"))
        .completeExceptionally(new Exception("Error"));
  }

  public static void combineTest() throws ExecutionException, InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(5);
    CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "zero", executor);
    CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "hello", executor);

    CompletableFuture<String> resultFuture =
        f1.thenCombine(f2, (t, u) -> t.concat(u));

    System.out.println(resultFuture.get());
  }

  public static void acceptBoth() {
    ExecutorService executor = Executors.newFixedThreadPool(5);
    CompletableFuture<Long> f1 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return 1L;
    }, executor);
    CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return "hello";
    }, executor);

    CompletableFuture<Void> reslutFuture = f1.thenAcceptBoth(f2, (t, u) -> {
      System.out.println(t + " over");
      System.out.println(u + " over");
    });

    try {
      System.out.println(reslutFuture.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }


  public static void mutipleTest() {
    CompletableFuture<String> responseFuture = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("over");
      return "hello world!";
    });
    failAfter(Duration.ofSeconds(2)).acceptEither(responseFuture,
        (x) -> System.out.println("responseFuture is over successed! the response is " + x))
        .exceptionally(throwable -> { //exceptionally必须在最后
          System.out.println("responseFuture is not over on time!");
          return null;
        });
  }

  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  public static <T> CompletableFuture<T> failAfter(Duration duration) {
    final CompletableFuture<T> promise = new CompletableFuture<>();
    scheduler.schedule(() -> {
      final TimeoutException ex = new TimeoutException("Timeout after " + duration);
      return promise.completeExceptionally(ex);
    }, 0, TimeUnit.MILLISECONDS);
    return promise;
  }

}
