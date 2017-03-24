package com.example.cache;

import com.google.common.base.Strings;

import com.example.cache.config.MultiBusinessRedisProperties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by lichao on 2017/3/23.
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(MultiBusinessRedisProperties.class)
public class BussinessRedisConfiguration implements BeanPostProcessor {

  @Autowired
  MultiBusinessRedisProperties multiBusinessRedisProperties;

  @PostConstruct
  public void init() {
    if (multiBusinessRedisProperties.getRedis() != null) {
      multiBusinessRedisProperties.getRedis()
          .forEach((name, properties) -> {
            String connection = properties.getConnection();
            checkArgument(!Strings.isNullOrEmpty(connection), "redis connnection is null");
            List<JedisPool> jedisPools = RedisUtil.getJedisPool(properties);
            BussinessRedis.addPools(name, jedisPools);
            log.info("business redis connection is {}, name is {}", properties.getConnection(),
                name);
          });
    }
  }

  @PreDestroy
  public void destroy() {
    if (BussinessRedis.getPools() != null && !BussinessRedis.getPools()
        .isEmpty()) {
      BussinessRedis.getPools()
          .forEach((name, pool) -> {
            log.info("close business redis pool, name is {}", name);
            RedisUtil.closePool(pool);
          });
    }
  }

  //使加载顺序提前，无实际意义
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }
}
