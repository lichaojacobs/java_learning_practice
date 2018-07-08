package com.jacobs.basic.multithread;

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

    public static void main(String[] args) throws Exception {
        //        combineTest();
        //acceptBoth();
        eateCake();
        //        littleTest();
        // mutipleTest();
        // ExecutorService executorService = Executors.newFixedThreadPool(3);
        // try {
        // System.out.println(executorService.submit(new CallableTask())
        // .get(3, TimeUnit.SECONDS));
        // } catch (TimeoutException e) {
        // e.printStackTrace();
        // }
        // mutipleTest();

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "test ";
        }).thenApply(u -> {
            System.out.println(Thread.currentThread().getName());
            return u + "in thenApply first";
        })
            .thenCompose(u -> CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread().getName());
                    return u + "in thenCompose second";
                })
            ).thenAccept(u -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(u + "in thenAccept last");
        });
        Thread.sleep(1000);
    }

    public static String getTestResult() {
        int i = 10 / 1;
        return "test";
    }


    public static void eateCake() throws Exception {
        CompletableFuture
            // 委托师傅做蛋糕
            .supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    System.out.println("师傅准备做蛋糕");
                    //如果使用threadSleep的话，由于这个阶段很慢，
                    // 所以最后的回调函数会在common-pool里面执行，否则会直接在main线程里面执行
                    Thread.sleep(1000);
                    System.out.println("师傅做蛋糕做好了");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "cake";
            })
            .exceptionally(ex -> new String(ex.getMessage()))
            // 做好了告诉我一声
            .thenApply(s -> {
                System.out.println(Thread.currentThread().getName());
                System.out.println("我要去通知某人吃: " + s);
                return s;
            })
            .thenAccept(cake -> {
                System.out.println(Thread.currentThread().getName());
                System.out.println("我吃蛋糕:" + cake);
            });
        System.out.println("我先去喝杯牛奶");
        Thread.currentThread().join();
    }

    public static void littleTest() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("run--" + Thread.currentThread().getName());
            return "hello";
        }).thenApply(s -> {
            System.out.println("second apply");
            return s + " hhh";
        }).exceptionally(throwable -> {
            System.out.println(throwable.getMessage());
            return throwable.getMessage();
        }).thenAccept(s -> System.out.println(s + "lichao"))
            .completeExceptionally(new Exception("Error"));
    }

    public static void combineTest() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "zero", executor);
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "hello", executor);

        CompletableFuture<String> resultFuture = f1.thenCombine(f2, (t, u) -> t.concat(u));

        resultFuture.whenCompleteAsync((s, throwable) -> {
            System.out.println(s);
        });
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
        failAfter(Duration.ofSeconds(1))
            .applyToEither(responseFuture,
                (x) -> {
                    System.out.println(String.format("responseFuture is over success! the response is %s", x));
                    return x;
                })
            .exceptionally(throwable -> { // exceptionally必须在最后
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
