package com.jacobs.basic.multithread;

import com.alibaba.fastjson.JSON;
import java.util.concurrent.SynchronousQueue;

/**
 * @author lichao
 * @date 2017/11/28
 */
public class SynchronousQueueTest {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    SynchronousQueue<Object> queue = new SynchronousQueue<Object>();
    for (int i = 0; i < 5; i++) {
      Thread t = new SQThread(queue, 1);
      t.start();
    }
    //Thread.sleep(1000);
    for (int i = 0; i < 5; i++) {
      if (!queue.offer("test")) {
        System.out.println("Failure");
      }
    }
  }

  public static class SQThread extends Thread {

    private SynchronousQueue<Object> queue;
    int mode;

    SQThread(SynchronousQueue<Object> queue, int mode) {
      this.queue = queue;
      this.mode = mode;
    }

    @Override
    public void run() {
      Object item = null;
      try {
        System.out.println(Thread.currentThread().getId());
        if (mode == 1) {
          while ((item = queue.take()) != null) {
            System.out.println(JSON.toJSONString(item));
          }
        } else {
          //
        }
      } catch (Exception e) {
        //
      }
      System.out.println("end");
    }
  }
}
