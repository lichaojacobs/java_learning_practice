package com.jacobs.basic.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 高效可伸缩结果缓存
 *
 * @author lichao
 * @date 2018/1/29
 */
public class Memoizer<A, V> implements Computable<A, V> {

  private final ConcurrentMap<A, Future<V>> cache
      = new ConcurrentHashMap<A, Future<V>>();
  private final Computable<A, V> c;

  public Memoizer(Computable<A, V> c) {
    this.c = c;
  }

  @Override
  public V compute(final A arg) throws InterruptedException {
    while (true) {
      Future<V> f = cache.get(arg);
      if (f == null) {
        Callable<V> eval = () -> c.compute(arg);
        FutureTask<V> ft = new FutureTask<>(eval);
        f = cache.putIfAbsent(arg, ft);
        //省去重复计算的过程
        if (f == null) {
          f = ft;
          ft.run();
        }
      }
      try {
        return f.get();
      } catch (CancellationException e) {
        cache.remove(arg, f);
      } catch (ExecutionException e) {
        throw new RuntimeException(e.getCause());
      }
    }
  }
}


interface Computable<A, V> {

  V compute(A arg) throws InterruptedException;
}
