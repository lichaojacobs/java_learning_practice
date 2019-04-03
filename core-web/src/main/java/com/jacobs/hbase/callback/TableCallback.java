package com.jacobs.hbase.callback;

import org.apache.hadoop.hbase.client.Table;

/**
 * Created by lichao on 2017/3/10.
 */
public interface TableCallback<T> {
  T doInTable(Table table) throws Throwable;
}
