package com.jacobs.basic.multithread.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ThreadFactory;

/**
 * @author lichao
 * @date 2018/12/02
 */
public class DisruptorWithSingleProducer {

    public static void main(String[] args) throws Exception {
        // 队列中的元素
        class Element {

            private int value;

            public int get() {
                return value;
            }

            public void set(int value) {
                this.value = value;
            }

        }

        // 线程工厂
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "simpleThread");
            }
        };

        // RingBuffer生产工厂,初始化RingBuffer的时候使用
        EventFactory<Element> factory = () -> new Element();

        // 处理Event的handler
        EventHandler<Element> handler = (element, sequence, endOfBatch) -> {
            System.out.println("consumer sleep...");
            //模拟消费者阻塞情况
            Thread.sleep(1000000L);
            System.out.println("Consume element: " + element.get());
        };

        // 消费者的阻塞策略
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        //生产者的等待策略
        //暂时只有休眠1ns.
        //LockSupport.parkNanos(1);

        // 指定RingBuffer的大小
        int bufferSize = 16;

        // 创建disruptor，采用单生产者模式
        Disruptor<Element> disruptor = new Disruptor(factory, bufferSize, threadFactory, ProducerType.SINGLE, strategy);

        // 设置EventHandler
        disruptor.handleEventsWith(handler);

        // 启动disruptor的线程
        // 默认也启动一个消费者线程
        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        for (int l = 0; true; l++) {
            // 获取下一个可用位置的下标
            long sequence = ringBuffer.next();
            try {
                // 返回可用位置的元素
                Element event = ringBuffer.get(sequence);
                // 设置该位置元素的值
                event.set(l);
                System.out.println("Produce element: " + event.get());
            } finally {
                ringBuffer.publish(sequence);
            }
            Thread.sleep(10);
        }
    }
}
