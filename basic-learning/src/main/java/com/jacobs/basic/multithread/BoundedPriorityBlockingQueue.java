package com.jacobs.basic.multithread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A wrapped implement for bounded PriorityBlockingQueue
 */
public class BoundedPriorityBlockingQueue<E> implements BlockingQueue<E> {

    private static final Log LOG = LogFactory.getLog(BoundedPriorityBlockingQueue.class);

    private PriorityBlockingQueue<E> priorityBlockingQueue;
    private Method removeLastElem;
    private volatile int maxQueueSize;
    private final ReentrantLock lock;

    public BoundedPriorityBlockingQueue(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        this.lock = new ReentrantLock();
        this.priorityBlockingQueue = new PriorityBlockingQueue<>(maxQueueSize);
        try {
            this.removeLastElem = PriorityBlockingQueue.class.getDeclaredMethod("removeAt", int.class);
            this.removeLastElem.setAccessible(true);
        } catch (NoSuchMethodException e) {
            LOG.error("BoundedPriorityBlockingQueue init error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean offer(final E e) {
        try {
            lock.lockInterruptibly();
            if (size() <= maxQueueSize) {
                return increase(e);
            }

            final Comparable firstElem = (Comparable) priorityBlockingQueue.peek();
            final Comparable toCompareElem = (Comparable) e;
            if (firstElem == null) {
                return increase(e);
            }
            // higher value with low priority element, should reject
            if (toCompareElem.compareTo(firstElem) > 0) {
                return false;
            }

            // higher priority element, should remove lowest element
            decrease();
            increase(e);
            return true;
        } catch (InterruptedException ex) {
            LOG.error("BoundedPriorityBlockingQueue offer interrupted", ex);
            return false;
        } catch (Exception ex) {
            LOG.error("BoundedPriorityBlockingQueue offer got exception", ex);
            return false;
        } finally {
            lock.unlock();
        }
    }

    private boolean increase(final E e) {
        return priorityBlockingQueue.offer(e);
    }

    private void decrease() throws InvocationTargetException, IllegalAccessException {
        this.removeLastElem.invoke(priorityBlockingQueue, size() - 1);
    }

    @Override
    public E remove() {
        return priorityBlockingQueue.remove();
    }

    @Override
    public E poll() {
        return priorityBlockingQueue.poll();
    }

    @Override
    public E element() {
        return priorityBlockingQueue.element();
    }

    @Override
    public E peek() {
        return priorityBlockingQueue.peek();
    }

    @Override
    public void put(E e) throws InterruptedException {
        offer(e);
    }

    @Override
    public boolean offer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
        return offer(e);
    }

    @Override
    public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
        return priorityBlockingQueue.poll(l, timeUnit);
    }

    @Override
    public int remainingCapacity() {
        return priorityBlockingQueue.remainingCapacity();
    }

    @Override
    public boolean remove(Object o) {
        return priorityBlockingQueue.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return priorityBlockingQueue.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return priorityBlockingQueue.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return priorityBlockingQueue.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return priorityBlockingQueue.retainAll(collection);
    }

    @Override
    public void clear() {
        priorityBlockingQueue.clear();
    }

    @Override
    public int size() {
        return priorityBlockingQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return priorityBlockingQueue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return priorityBlockingQueue.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return priorityBlockingQueue.iterator();
    }

    @Override
    public Object[] toArray() {
        return priorityBlockingQueue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return priorityBlockingQueue.toArray(ts);
    }

    @Override
    public int drainTo(Collection<? super E> collection) {
        return priorityBlockingQueue.drainTo(collection);
    }

    @Override
    public int drainTo(Collection<? super E> collection, int i) {
        return priorityBlockingQueue.drainTo(collection, i);
    }

    @Override
    public E take() throws InterruptedException {
        return priorityBlockingQueue.take();
    }

    /**
     * store job into level db async, cause serialize/deserialize may be slow
     */
    private static class AsyncTask implements Runnable, Comparable<AsyncTask> {

        private String fileInfo;
        private AsyncTaskEnum taskType;
        private int priority;

        public AsyncTask(String fileInfo, int priority) {
            this.fileInfo = fileInfo;
            this.taskType = AsyncTaskEnum.STORE;
            this.priority = priority;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(100000L);
            } catch (InterruptedException e) {
                LOG.error(e);
            }
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public String toString() {
            return "AsyncTask{" +
                    "fileInfo='" + fileInfo + '\'' +
                    ", taskType=" + taskType +
                    ", priority=" + priority +
                    '}';
        }

        @Override
        public int compareTo(AsyncTask asyncTask) {
            if (getPriority() < asyncTask.getPriority()) {
                return -1;
            }
            if (getPriority() > asyncTask.getPriority()) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * task priority: task priority from move thread is higher
     */
    enum AsyncTaskPriorityEnum {
        MOVE(0),
        AD_HOC(1);

        private int priority;

        AsyncTaskPriorityEnum(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    enum AsyncTaskEnum {
        DELETE,
        STORE,
    }

    public static void main(String[] args) {
        BoundedPriorityBlockingQueue<AsyncTask> blockingQueue = new
                BoundedPriorityBlockingQueue<>(5);


        blockingQueue.offer(new AsyncTask("testMove", AsyncTaskPriorityEnum.MOVE.priority));
        for (int i = 1; i < 10; i++) {
            LOG.info(blockingQueue.offer(new AsyncTask("test", i)));
        }
        blockingQueue.offer(new AsyncTask("testMove2", AsyncTaskPriorityEnum.MOVE.priority));
    }
}



