package com.example.resource;

import com.example.module.User;
import com.example.service.DemoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lichao on 16/7/20.
 */
@Controller
public class Demo {
  @Autowired
  DemoService demoService;

  @RequestMapping("/test")
  public void putCache() {
    User user = new User();
    user.setFirstName("lichao");
    demoService.insertUserName(user);
  }

  @RequestMapping("/test2")
  public void testCache() {
    String name = demoService.getUserNameFromCache();
    if (name != null) {
      System.out.println("缓存为:" + name);
    }
  }
}
