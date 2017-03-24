package com.example.cache.command;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.BinaryClient;

/**
 * Created by lichao on 2017/3/24.
 */
public interface ListsCommand {
  Map<String, JedisLists> map = new ConcurrentHashMap<>();

  static JedisLists getJedis(final String name) {
    return map.get(name);
  }

  static void putJedis(final String name) {
    map.put(name, new JedisLists(name));
  }

  @Deprecated
  List<String> leftPopWithBlocking(final int timeout, final String... keys);

  String leftPopWithBlocking(final int timeout, final String key);

  @Deprecated
  List<String> rightPopWithBlocking(final int timeout, final String... keys);

  String rightPopWithBlocking(final int timeout, final String key);

  String indexFromLeft(final String key, final long index);

  Long leftInsert(final String key, final BinaryClient.LIST_POSITION where, final String pivot,
      final String value);

  Long length(final String key);

  String leftPop(final String key);

  Long leftPush(final String key, final String... strings);

  Long leftPush(final String key, final List<String> values);

  Long leftPushIfExist(final String key, final String... strings);

  Long leftPushIfExist(final String key, final List<String> values);

  List<String> range(final String key, final long start, final long end);

  Long removeFromLeft(final String key, final long count, final String value);

  String setFromLeft(final String key, final long index, final String value);

  String trimFromLeft(final String key, final long start, final long end);

  String rightPop(final String key);

  @Deprecated
  String rightPopLeftPush(final String srckey, final String dstkey);

  Long rightPush(final String key, final String... strings);

  Long rightPush(final String key, final List<String> values);

  Long rightPushIfExist(final String key, final String... string);

  Long rightPushIfExist(final String key, final List<String> values);
}
