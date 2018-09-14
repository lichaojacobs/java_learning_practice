package com.jacobs.basic.java8pratice;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * Created by lichao on 16/7/27.
 */
public class Java8MainTest {

  public static void main(String[] args) {
    System.out.println(forkJoinSum(200000));
  }

  public static long forkJoinSum(long n) {
    long[] numbers = LongStream.rangeClosed(1, n).toArray();
    ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
    return new ForkJoinPool().invoke(task);
  }
}
