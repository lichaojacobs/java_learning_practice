package com.example.service;

import com.example.constants.BaseConstant;
import com.example.module.User;
import com.mobvoi.data.cache.commands.StringCommand;
import org.springframework.stereotype.Component;

/**
 * Created by lichao on 16/7/20.
 */
@Component
public class DemoService {

  public boolean insertUserName(User user) {
    StringCommand.getJedis(BaseConstant.REDIS_NAME)
        .setWithExpire("username", 100, user.getFirstName());
    return true;
  }

  public String getUserNameFromCache() {
    try {
      return StringCommand.getJedis(BaseConstant.REDIS_NAME)
          .get("username");
    } catch (Exception ex) {
      return null;
    }
  }
}
