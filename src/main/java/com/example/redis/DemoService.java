package com.example.redis;

import com.example.module.Address;
import com.example.module.User;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by lichao on 16/7/20.
 */
@Component
public class DemoService {

  @Resource
  RedisTemplate<String, String> redisTemplate;

  public boolean insertUserName(User user) {
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set("username", user.getFirstName());
    return true;
  }

  @Transactional
  public String getUserNameFromCache() {
    try {
      ValueOperations<String, String> valueops = redisTemplate.opsForValue();
      return valueops.get("username").toString();
    } catch (Exception ex) {
      return null;
    }
  }

  @Cacheable(value = "usercache", keyGenerator = "wiselyKeyGenerator")
  public User findUser(Long id, String firstName, String lastName) {
    return new User(id, firstName, lastName);
  }
}
