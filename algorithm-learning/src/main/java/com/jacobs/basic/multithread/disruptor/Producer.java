package com.jacobs.basic.multithread.disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * @author lichao
 * @date 2019/03/31
 */
public class Producer {

    private final String producerName;
    private final RingBuffer<Order> ringBuffer;

    public Producer(int producerId, RingBuffer<Order> ringBuffer) {
        this.producerName = String.format("producer-%d", producerId);
        this.ringBuffer = ringBuffer;
    }

    /**
     * onData用来发布事件，每调用一次就发布一次事件 它的参数会用过事件传递给消费者
     */
    public void onData(String data) {
        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();
        try {
            //用上面的索引取出一个空的事件用于填充（获取该序号对应的事件对象）
            Order order = ringBuffer.get(sequence);
            //获取要通过事件传递的业务数据
            order.setId(data);
            System.out.println(producerName + String.format("produce order: %s", order));
        } finally {
            //发布事件
            //注意，最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
            ringBuffer.publish(sequence);
        }
    }
}
