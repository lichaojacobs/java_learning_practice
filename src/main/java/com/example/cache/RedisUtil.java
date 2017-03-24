package com.example.cache;

import com.example.cache.config.BaseRedisPoolProperties;
import com.example.cache.config.BaseRedisProperties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;

/**
 * Created by lichao on 2017/3/23.
 */
@Slf4j
public class RedisUtil {

  /**
   * build redis connection
   */
  private static Set<HostAndPort> getRedisConnection(String connection) {
    if (StringUtils.isBlank(connection)) {
      return Collections.EMPTY_SET;
    }

    return Arrays.stream(connection.split("\\|"))
        .filter(StringUtils::isNotBlank)
        .map(StringUtils::trim)
        .map(s -> s.split(":"))
        .filter(strings -> strings.length == 2)
        .map(tokens ->
            new HostAndPort(tokens[0].trim(), Integer.parseInt(tokens[1].trim())
            ))
        .collect(Collectors.toSet());
  }

  public static List<JedisPool> getJedisPool(BaseRedisProperties redisProperties) {
    BaseRedisPoolProperties poolProperties = redisProperties.getPool();
    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    poolConfig.setMaxIdle(poolProperties.getMaxIdle());
    poolConfig.setMinIdle(poolProperties.getMinIdle());
    poolConfig.setMaxTotal(poolProperties.getMaxTotal());
    poolConfig.setTestOnBorrow(poolProperties.isTestOnBorrow());
    poolConfig.setMaxWaitMillis(poolProperties.getMaxWaitMillis());

    Set<HostAndPort> nodes = getRedisConnection(redisProperties.getConnection());
    return nodes.stream()
        .map(node ->
            new JedisPool(
                poolConfig,
                node.getHost(),
                node.getPort(),
                redisProperties.getTimeout(),
                redisProperties.getPassword(),
                redisProperties.getDb()
            )
        )
        .collect(Collectors.toList());
  }

  public static void closePool(List<JedisPool> pools) {
    if (pools != null && !pools.isEmpty()) {
      pools.forEach(JedisPool::destroy);
    }
  }
}
