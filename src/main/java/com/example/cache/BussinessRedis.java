package com.example.cache;

import com.example.cache.command.ListsCommand;
import com.example.cache.command.StringsCommand;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by lichao on 2017/3/23.
 */
public class BussinessRedis {
  private static Map<String, List<JedisPool>> pools = new ConcurrentHashMap<>();

  public static Jedis get(String name) {
    if (name == null) {
      throw new RuntimeException("Jedis name is null. Get Jedis resource failed.");
    }

    if (pools == null || pools.isEmpty()) {
      throw new RuntimeException("Jedis pools is empty. Get Jedis resource failed.");
    }

    List<JedisPool> pool = pools.get(name);
    if (pool == null || pool.isEmpty()) {
      throw new RuntimeException(
          "Jedis pools is empty. Get Jedis resource failed. name is " + name);
    }
    return pool.get(new Random().nextInt(pool.size()))
        .getResource();
  }

  protected static Map<String, List<JedisPool>> getPools() {
    return pools;
  }

  protected static void addPools(String poolName, List<JedisPool> pools) {
    BussinessRedis.pools.put(poolName, pools);
    StringsCommand.putJedis(poolName);
    ListsCommand.putJedis(poolName);
  }
}
