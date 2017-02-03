package com.example.proxy.principle;

import com.example.module.User;

/**
 * Created by lichao on 16/8/29.
 */
public class UserServiceImpl implements UserService {
  @Override
  public void add() {
    System.out.println("--------------------add---------------");
  }
}
