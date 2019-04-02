package com.jacobs.basic.multithread.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Disruptor 多生产者，多消费者的情况
 *
 * @author lichao
 * @date 2019/03/31
 */
public class DisruptorWithMultiProducer {

    public static void main(String[] args) throws InterruptedException {
        //创建ringBuffer
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI, () -> new Order(), 1024 * 1024,
            new BlockingWaitStrategy());

        SequenceBarrier barriers = ringBuffer.newBarrier();

        Consumer[] consumers = new Consumer[3];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("c" + i);
        }

        //添加多个消费者
        WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer, barriers, new IntEventExceptionHandler(),
            consumers);

        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime()
                                                             .availableProcessors()));

        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            final Producer p = new Producer(i, ringBuffer);
            new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 100; j++) {
                    p.onData(String.format("%s, element: %d", p.getProducerName(), j));
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println("---------------开始生产-----------------");
        latch.countDown();
        Thread.sleep(5000);
        System.out.println("总数:" + consumers[1].getCount());
    }

    static class IntEventExceptionHandler implements ExceptionHandler {

        public void handleEventException(Throwable ex, long sequence, Object event) {
        }

        public void handleOnStartException(Throwable ex) {
        }

        public void handleOnShutdownException(Throwable ex) {
        }
    }

}
