package com.jacobs.basic.multithread;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

/**
 * Created by lichao on 2016/11/30.
 */
public class ForkJoinTest extends RecursiveTask<Long> {
  private static final int THRESHOLD = 10000;
  private long start;
  private long end;

  public ForkJoinTest(long start, long end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {
    long sum = 0;
    boolean canCompute = (end - start) < THRESHOLD;
    if (canCompute) {
      for (long i = start; i <= end; i++) {
        sum += i;
      }
    } else {
      //分成一百个小任务
      long step = (end - start) / 100;
      ArrayList<ForkJoinTest> subTasks = new ArrayList<>();
      long pos = start;
      for (int i = 0; i < 100; i++) {
        long lastOne = pos + step;
        if (lastOne > end) {
          lastOne = end;
        }
        ForkJoinTest subTask = new ForkJoinTest(pos, lastOne);
        pos += step + 1;
        subTasks.add(subTask);
        subTask.fork();
      }
      for (ForkJoinTest forkJoinTest : subTasks) {
        sum += forkJoinTest.join();
      }
    }

    return sum;
  }
}
