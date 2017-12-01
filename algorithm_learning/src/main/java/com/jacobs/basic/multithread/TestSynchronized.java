package com.jacobs.basic.multithread;

/**
 * @author lichao
 * @date 2017/11/16
 */
public class TestSynchronized {

  public void test1() {
    synchronized (this) {
      int i = 5;
      while (i-- > 0) {
        System.out.println(Thread.currentThread().getName() + " : " + i);
        try {
          Thread.sleep(500);
        } catch (InterruptedException ie) {
        }
      }
    }
  }

  public synchronized void test2() {
    int i = 5;
    while (i-- > 0) {
      System.out.println(Thread.currentThread().getName() + " : " + i);
      try {
        Thread.sleep(500);
      } catch (InterruptedException ie) {
      }
    }
  }

  /**
   * 类锁的修饰（静态）方法. 类的对象实例可以有很多个，但是每个类只有一个class对象，所以不同对象实例的对象锁是互不干扰的，但是每个类只有一个类锁。
   */
  public static synchronized void test3() {
    int i = 5;
    while (i-- > 0) {
      System.out.println(Thread.currentThread().getName() + " : " + i);
      try {
        Thread.sleep(500);
      } catch (InterruptedException ie) {
      }
    }
  }

  /**
   * 同样加了类锁
   */
  public void test4() {
    synchronized (TestSynchronized.class) {
      int i = 5;
      while (i-- > 0) {
        System.out.println(Thread.currentThread().getName() + " : " + i);
        try {
          Thread.sleep(500);
        } catch (InterruptedException ie) {
        }
      }
    }
  }

  public static void main(String[] args) {
    //第一个方法时用了同步代码块的方式进行同步，传入的对象实例是this，表明是当前对象，当然，如果需要同步其他对象实例，也不可传入其他对象的实例；
    // 第二个方法是修饰方法的方式进行同步。因为第一个同步代码块传入的this，所以两个同步代码所需要获得的对象锁都是同一个对象锁，
    // 下面main方法时分别开启两个线程，分别调用test1和test2方法，那么两个线程都需要获得该对象锁，另一个线程必须等待。
    // 上面也给出了运行的结果可以看到：直到test2线程执行完毕，释放掉锁，test1线程才开始执行
//    final TestSynchronized myt2 = new TestSynchronized();
//    Thread test1 = new Thread(() -> {
//      myt2.test1();
//    }, "test1");
//    Thread test2 = new Thread(() -> {
//      myt2.test2();
//    }, "test2");
//    test1.start();
//    test2.start();

    final TestSynchronized myt2 = new TestSynchronized();
    Thread test1 = new Thread(() -> {
      TestSynchronized.test3();
    }, "test1");
    Thread test2 = new Thread(() -> {
      myt2.test4();
    }, "test2");
    test1.start();
    test2.start();
  }
}
