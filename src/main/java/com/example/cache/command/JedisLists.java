package com.example.cache.command;

import com.example.cache.BussinessRedis;

import java.util.List;
import java.util.Optional;

import redis.clients.jedis.BinaryClient;

/**
 * Created by lichao on 2017/3/24.
 */
public class JedisLists extends BaseMultiJedis implements ListsCommand {

  public JedisLists(String name) {
    super(name);
  }

  @Override
  public List<String> leftPopWithBlocking(int timeout, String... keys) {
    return BussinessRedis.get(name)
        .blpop(timeout, keys);
  }

  @Override
  public String leftPopWithBlocking(int timeout, String key) {
    return Optional.ofNullable(leftPopWithBlocking(timeout, new String[]{key}))
        .map(list -> list.get(1))
        .orElse(null);
  }

  @Override
  public List<String> rightPopWithBlocking(int timeout, String... keys) {
    return BussinessRedis.get(name)
        .brpop(timeout, keys);
  }

  @Override
  public String rightPopWithBlocking(int timeout, String key) {
    return Optional.ofNullable(rightPopWithBlocking(timeout, new String[]{key}))
        .map(list -> list.get(1))
        .orElse(null);
  }

  @Override
  public String indexFromLeft(String key, long index) {
    return BussinessRedis.get(name)
        .lindex(key, index);
  }

  @Override
  public Long leftInsert(String key, BinaryClient.LIST_POSITION where,
      String pivot, String value) {
    return BussinessRedis.get(name)
        .linsert(key, where, pivot, value);
  }

  @Override
  public Long length(String key) {
    return BussinessRedis.get(name)
        .llen(key);
  }

  @Override
  public String leftPop(String key) {
    return BussinessRedis.get(name)
        .lpop(key);
  }

  @Override
  public Long leftPush(String key, String... strings) {
    return BussinessRedis.get(name)
        .lpush(key, strings);
  }

  @Override
  public Long leftPush(String key, List<String> values) {
    String[] strings = values.toArray(new String[0]);
    return BussinessRedis.get(name)
        .lpush(key, strings);
  }

  @Override
  public Long leftPushIfExist(String key, String... strings) {
    return BussinessRedis.get(name)
        .lpushx(key, strings);
  }

  @Override
  public Long leftPushIfExist(String key, List<String> values) {
    String[] strings = values.toArray(new String[0]);
    return BussinessRedis.get(name)
        .lpushx(key, strings);
  }

  @Override
  public List<String> range(String key, long start, long end) {
    return BussinessRedis.get(name)
        .lrange(key, start, end);
  }

  @Override
  public Long removeFromLeft(String key, long count, String value) {
    return BussinessRedis.get(name)
        .lrem(key, count, value);
  }

  @Override
  public String setFromLeft(String key, long index, String value) {
    return BussinessRedis.get(name)
        .lset(key, index, value);
  }

  @Override
  public String trimFromLeft(String key, long start, long end) {
    return BussinessRedis.get(name)
        .ltrim(key, start, end);
  }

  @Override
  public String rightPop(String key) {
    return BussinessRedis.get(name)
        .rpop(key);

  }

  @Override
  public String rightPopLeftPush(String srckey, String dstkey) {
    return BussinessRedis.get(name)
        .rpoplpush(srckey, dstkey);

  }

  @Override
  public Long rightPush(String key, String... strings) {
    return BussinessRedis.get(name)
        .rpush(key, strings);
  }

  @Override
  public Long rightPush(String key, List<String> values) {
    String[] strings = values.toArray(new String[values.size()]);
    return BussinessRedis.get(name)
        .rpush(key, strings);
  }

  @Override
  public Long rightPushIfExist(String key, String... strings) {
    return BussinessRedis.get(name)
        .rpushx(key, strings);

  }

  @Override
  public Long rightPushIfExist(String key, List<String> values) {
    String[] strings = values.toArray(new String[values.size()]);
    return BussinessRedis.get(name)
        .rpushx(key, strings);
  }
}
