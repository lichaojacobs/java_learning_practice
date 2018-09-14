package com.jacobs.basic.algorithm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Least Frequently Used algorithm LFU是首先淘汰一定时期内被访问次数最少的页!
 */
public class LFUCache {

  private static final int DEFAULT_MAX_SIZE = 3;
  private int capacity = DEFAULT_MAX_SIZE;
  //保存缓存的访问频率和时间
  private final Map<Integer, HitRate> cache = new HashMap<>();
  //保存缓存的KV
  private final Map<Integer, Integer> KV = new HashMap<>();

  // @param capacity, an integer
  public LFUCache(int capacity) {
    this.capacity = capacity;
  }

  // @param key, an integer
  // @param val, an integer
  // @return nothing
  public void set(int key, int value) {
    Integer v = KV.get(key);
    if (v == null) {
      if (cache.size() == capacity) {
        Integer k = getKickedKey();
        KV.remove(k);
        cache.remove(k);
      }
      cache.put(key, new HitRate(key, 1, System.nanoTime()));
    } else { //若是key相同只增加频率,更新时间,并不进行置换
      HitRate hitRate = cache.get(key);
      hitRate.hitCount += 1;
      hitRate.lastTime = System.nanoTime();
    }
    KV.put(key, value);
  }

  public int get(int key) {
    Integer v = KV.get(key);
    if (v != null) {
      HitRate hitRate = cache.get(key);
      hitRate.hitCount += 1;
      hitRate.lastTime = System.nanoTime();
      return v;
    }
    return -1;
  }

  // @return 要被置换的key
  private Integer getKickedKey() {
    HitRate min = Collections.min(cache.values());
    return min.key;
  }

  //key 访问频率类
  class HitRate implements Comparable<HitRate> {

    Integer key;
    Integer hitCount; // 命中次数
    Long lastTime; // 上次命中时间

    public HitRate(Integer key, Integer hitCount, Long lastTime) {
      this.key = key;
      this.hitCount = hitCount;
      this.lastTime = lastTime;
    }

    @Override
    public int compareTo(HitRate o) {
      int hr = hitCount.compareTo(o.hitCount);
      return hr != 0 ? hr : lastTime.compareTo(o.lastTime);
    }
  }

  public static void main(String[] as) throws Exception {
    LFUCache cache = new LFUCache(3);
    cache.set(2, 2);
    cache.set(1, 1);

    System.out.println(cache.get(2));
    System.out.println(cache.get(1));
    System.out.println(cache.get(2));

    cache.set(3, 3);
    cache.set(4, 4);

    System.out.println(cache.get(3));
    System.out.println(cache.get(2));
    System.out.println(cache.get(1));
    System.out.println(cache.get(4));

  }
}

