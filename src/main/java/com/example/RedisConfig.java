package com.example;

import com.example.conditional.ConditionalOnMyProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

/**
 * Created by lichao on 16/7/20.
 */

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

  @Autowired
  private RedisSettings redisSettings;

  @Bean
  public KeyGenerator wiselyKeyGenerator() {
    return new KeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        for (Object obj : params) {
          sb.append(obj.toString());
        }
        return sb.toString();
      }
    };

  }

  @Bean
  @ConditionalOnMyProperties(name = "redisCondition")
  public JedisConnectionFactory redisConnectionFactory() {
    JedisConnectionFactory factory = new JedisConnectionFactory();
    factory.setHostName(redisSettings.getHost());
    factory.setPort(redisSettings.getPort());
    factory.setTimeout(redisSettings.getTimeout()); //设置连接超时时间
    return factory;
  }

  @Bean
  public CacheManager cacheManager(
      @SuppressWarnings("rawtypes")
          RedisTemplate redisTemplate) {
    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
    cacheManager.setDefaultExpiration(10000); //设置key-value超时时间
    return cacheManager;
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    StringRedisTemplate template = new StringRedisTemplate(factory);
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(
        Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    template.setValueSerializer(jackson2JsonRedisSerializer);
    template.afterPropertiesSet();
    return template;
  }

}
