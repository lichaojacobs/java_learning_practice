package com.jacobs.resource;

import com.jacobs.exception.CommonException;
import com.jacobs.exception.CommonRestException;
import com.jacobs.module.User;
import com.jacobs.service.DemoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lichao on 16/7/20.
 */
@RestController
public class DemoResource {

  @Autowired
  DemoService demoService;

  @RequestMapping("/test")
  public void putCache() {
    try {
      User user = new User();
      user.setFirstName("lichao");
      demoService.insertUserName(user);
    } catch (Exception ex) {
      throw new CommonRestException(CommonException.PARAMETER_ERROR);
    }
  }

  @RequestMapping("/test2")
  public void testCache() {
    String name = demoService.getUserNameFromCache();
    if (name != null) {
      System.out.println("缓存为:" + name);
    }
  }


  @GetMapping(value = "/test3", produces = "application/json")
  public List<User> getMessage() {
    List<User> users = new ArrayList<>();
    User user1 = new User();
    user1.setFirstName("li");
    user1.setLastName("chao");
    user1.setId(1l);
    users.add(user1);
    User user2 = new User();
    user2.setFirstName("wang");
    user2.setLastName("lu");
    user2.setId(2l);
    users.add(user2);
    return users;
  }
}
