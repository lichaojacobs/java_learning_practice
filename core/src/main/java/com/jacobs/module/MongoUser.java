package com.jacobs.module;

import lombok.Data;

/**
 * Created by lichao on 2017/4/18.
 */
@Data
public class MongoUser {

  private String from;
  private String to;
  private long identify;
  private long timestamp;
  private int count;
}
