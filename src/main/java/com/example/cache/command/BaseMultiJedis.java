package com.example.cache.command;

import lombok.Data;

/**
 * Created by lichao on 2017/3/24.
 */
@Data
public abstract class BaseMultiJedis {
  protected String name;

  public BaseMultiJedis(String name) {
    this.name = name;
  }
}
