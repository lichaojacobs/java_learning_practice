package com.jacobs.basic.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lichao on 2016/12/14.
 */
public class ReentrantLockTets<T> {
  private Object[] products;//存储产品
  volatile int count;//已经存储的数量
  int addIndex, reduceIndex;
  private final ReentrantLock lock = new ReentrantLock(true);
  private final Condition notEmpty = lock.newCondition();
  private final Condition notFull = lock.newCondition();


  public ReentrantLockTets(int size) {
    super();
    products = new Object[size];
    addIndex = 0;
    reduceIndex = 0;
  }

  public void put(T e) throws InterruptedException {
    if (e == null) {
      throw new NullPointerException();
    }
    lock.lock();
    try {
      while (count == products.length) {
        notFull.await();//如果products数组已经装满,生产线程进入等待
      }
      products[addIndex] = e;
      if (++addIndex == products.length) {
        addIndex = 0;
      }
      count++;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

  public T take() throws InterruptedException {
    lock.lock();
    try {
      while (count == 0) {
        notEmpty.await();
      }
      Object product = products[reduceIndex];
      if (++reduceIndex == products.length) {
        reduceIndex = 0;
      }
      count--;//消费一个商品
      notFull.signal();
      return (T) product;
    } finally {
      lock.unlock();
    }
  }


  //获取生产者线程
  public Thread getProduceThread(ReentrantLockTets<T> boundedQueue, Object products[]) {
    Producer<T> producer = new Producer(boundedQueue, products);
    Thread produceThread = new Thread(producer, "produceThread");
    return produceThread;
  }

  //获取消费者线程
  public Thread getConsumeThread(ReentrantLockTets<T> boundedQueue) {
    Consumer<T> consumer = new Consumer(boundedQueue);
    Thread consumeThread = new Thread(consumer, "consumeThread");
    return consumeThread;
  }


  class Producer<T> implements Runnable {

    private ReentrantLockTets<T> boundedQueue;
    private Object[] readyProducts;

    Producer(ReentrantLockTets<T> boundedQueue, Object[] readyProducts) {
      super();
      this.boundedQueue = boundedQueue;
      this.readyProducts = readyProducts;
      for (int i = 0; i < readyProducts.length; i++) {
        this.readyProducts[i] = readyProducts[i];
      }
    }

    @Override
    public void run() {
      for (int i = 0; i < readyProducts.length; i++) {
        try {
          boundedQueue.put((T) readyProducts[i]);
          System.out.println(Thread.currentThread()
              .getName() + " has produced [" + (T) readyProducts[i] + "]");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  class Consumer<T> implements Runnable {
    private ReentrantLockTets<T> boundedQueue;
    private boolean on;//开关

    Consumer(ReentrantLockTets<T> boundedQueue) {
      super();
      this.boundedQueue = boundedQueue;
      this.on = true;
    }

    @Override
    public void run() {
      while (on) {
        try {
          T product = boundedQueue.take();
          System.out.println(Thread.currentThread()
              .getName() + " has consumed [" + product +
              "]");//打印消费的商品信息
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    public void closeThread() {
      on = false;
    }
  }

  public static void main(String[] args) {
    ReentrantLockTets<String> boundedQueue = new ReentrantLockTets<>(3);
    String fruits[] = new String[]{"apple", "banana", "peach", "watermelon", "pear", "grape"};
    Thread produceThread = boundedQueue.getProduceThread(boundedQueue, fruits);
    Thread consumeThread = boundedQueue.getConsumeThread(boundedQueue);
    produceThread.start();
    consumeThread.start();
  }

}
