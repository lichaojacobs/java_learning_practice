package com.jacobs.hbase;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Created by lichao on 2017/3/10.
 */
@ConfigurationProperties(prefix = "hbase.admin")
@Data
public class AdminConfig {
  private long maxFileSize = 536870912L;
  private boolean readOnly = false;
  private long memStoreFlushSize = 134217728L;
  private int regionReplication = 1;
  private boolean normalizationEnabled = false;
}
