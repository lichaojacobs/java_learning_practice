package com.example.service;

import com.example.cache.BussinessRedis;
import com.example.cache.command.StringsCommand;
import com.example.constants.BaseConstant;
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

  public boolean insertUserName(User user) {
    StringsCommand.getJedis(BaseConstant.REDIS_NAME)
        .set("username", user.getFirstName());
    return true;
  }

  @Transactional
  public String getUserNameFromCache() {
    try {
      return StringsCommand.getJedis(BaseConstant.REDIS_NAME)
          .get("username");
    } catch (Exception ex) {
      return null;
    }
  }

  @Cacheable(value = "usercache",
             keyGenerator = "wiselyKeyGenerator")
  public User findUser(Long id, String firstName, String lastName) {
    return new User(id, firstName, lastName);
  }
}
