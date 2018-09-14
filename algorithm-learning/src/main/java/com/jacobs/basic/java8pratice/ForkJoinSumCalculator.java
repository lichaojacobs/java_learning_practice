package com.jacobs.basic.java8pratice;

/**
 * Created by lichao on 16/7/27.
 */
public class ForkJoinSumCalculator extends java.util.concurrent.RecursiveTask<Long> {

  private final long[] numbers;
  private final int start;
  private final int end;

  public static final long THRESHOLD = 10000;

  public ForkJoinSumCalculator(long[] numbers) {
    this(numbers, 0, numbers.length);
  }

  private ForkJoinSumCalculator(long[] numbers, int start, int end) {
    this.numbers = numbers;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {
    int length = end - start;
    if (length <= THRESHOLD) {
      return computeSequentially();
    }
    ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
    //利用另一个forkjoinpool线程异步执行新创建的任务
    leftTask.fork();
    ForkJoinSumCalculator rigthTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
    Long rightResult = rigthTask.join();
    Long leftResult = leftTask.join();
    return leftResult + rightResult;
  }

  private long computeSequentially() {
    long sum = 0;
    for (int i = start; i < end; i++) {
      sum += numbers[i];
    }
    return sum;
  }
}
