package com.example.cache.command;

import com.example.cache.BussinessRedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2017/3/24.
 */
public class JedisStrings extends BaseMultiJedis implements StringsCommand {

  public JedisStrings(String name) {
    super(name);
  }

  @Override
  public Long append(String key, String value) {
    return BussinessRedis.get(name)
        .append(key, value);
  }

  @Override
  public Long decr(String key) {
    return BussinessRedis.get(name)
        .decr(key);
  }

  @Override
  public String get(String key) {
    return BussinessRedis.get(name)
        .get(key);
  }

  @Override
  public String getRange(String key, long startOffset, long endOffset) {
    return BussinessRedis.get(name)
        .getrange(key, startOffset, endOffset);
  }

  @Override
  public String getOldValueAndSet(String key, String value) {
    return BussinessRedis.get(name)
        .getSet(key, value);
  }

  @Override
  public Long incr(String key) {
    return BussinessRedis.get(name)
        .incr(key);
  }

  @Override
  public Long incrBy(String key, long integer) {
    return BussinessRedis.get(name)
        .incrBy(key, integer);
  }

  @Override
  public Double incrByDouble(String key, double value) {
    return BussinessRedis.get(name)
        .incrByFloat(key, value);
  }

  @Override
  public List<String> multiGetAsList(String... keys) {
    return BussinessRedis.get(name)
        .mget(keys);
  }

  @Override
  public Map<String, String> multiGetAsMap(String... keys) {
    List<String> list = BussinessRedis.get(name)
        .mget(keys);
    HashMap<String, String> map = new HashMap<>(list.size());
    for (int index = 0; index < keys.length; index++) {
      map.put(keys[index], list.get(index));
    }

    return map;
  }

  @Override
  public void multiSet(String... keysValues) {
    BussinessRedis.get(name)
        .mset(keysValues);
  }

  @Override
  public void multiSet(Map<String, String> kvMap) {
    if (kvMap == null || kvMap.isEmpty()) {
      return;
    }

    int len = kvMap.size();
    String[] keys = kvMap.keySet()
        .toArray(new String[0]);
    String[] keysValues = new String[len * 2];

    for (int index = 0; index < len; index++) {
      keysValues[2 * index] = keys[index];
      keysValues[2 * index + 1] = kvMap.get(keys[index]);
    }
    BussinessRedis.get(name)
        .mset(keysValues);
  }

  @Override
  public void set(String key, String value) {
    BussinessRedis.get(name)
        .set(key, value);
  }

  @Override
  public void setWithExpire(String key, int seconds, String value) {
    BussinessRedis.get(name)
        .setex(key, seconds, value);
  }

  @Override
  public Long setIfNotExist(String key, String value) {
    return BussinessRedis.get(name)
        .setnx(key, value);
  }

  @Override
  public boolean setIfNotExistWithExpire(String key, String value, int seconds) {
    return BussinessRedis.get(name)
        .set(key, value, "nx", "ex", seconds) != null;
  }

  @Override
  public Long setRange(String key, long offset, String value) {
    return BussinessRedis.get(name)
        .setrange(key, offset, value);
  }

  @Override
  public Long getLength(String key) {
    return BussinessRedis.get(name)
        .strlen(key);
  }
}
