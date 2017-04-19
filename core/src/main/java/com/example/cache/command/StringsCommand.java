package com.example.cache.command;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lichao on 2017/3/24.
 */
public interface StringsCommand {
  Map<String, JedisStrings> map = new ConcurrentHashMap<>();

  static JedisStrings getJedis(final String name) {
    return map.get(name);
  }

  static void putJedis(final String name) {
    map.put(name, new JedisStrings(name));
  }

  Long append(final String key, final String value);

  Long decr(final String key);

  String get(final String key);

  String getRange(String key, long startOffset, long endOffset);

  String getOldValueAndSet(final String key, final String value);

  Long incr(final String key);

  Long incrBy(final String key, final long integer);

  Double incrByDouble(final String key, final double value);

  @Deprecated
  List<String> multiGetAsList(final String... keys);

  @Deprecated
  Map<String, String> multiGetAsMap(final String... keys);

  @Deprecated
  void multiSet(final String... keysValues);

  @Deprecated
  void multiSet(Map<String, String> kvMap);

  void set(final String key, String value);

  void setWithExpire(final String key, final int seconds, final String value);

  Long setIfNotExist(final String key, final String value);

  boolean setIfNotExistWithExpire(final String key, final String value, final int seconds);

  Long setRange(String key, long offset, String value);

  Long getLength(final String key);
}
