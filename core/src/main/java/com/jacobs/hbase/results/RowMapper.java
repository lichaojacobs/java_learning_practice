package com.jacobs.hbase.results;

import org.apache.hadoop.hbase.client.Result;

/**
 * Created by lichao on 2017/3/10.
 */
public interface RowMapper<T> {
  T mapRow(Result var1, int var2) throws Exception;
}
