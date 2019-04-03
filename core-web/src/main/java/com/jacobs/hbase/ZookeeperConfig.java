package com.jacobs.hbase;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Created by lichao on 2017/3/10.
 */
@Data
@ConfigurationProperties(prefix = "hbase")
public class ZookeeperConfig {
  private String quorum;
  private Integer port;
  private Integer maxSize = Integer.valueOf(10);
}
