package com.jacobs.hbase.callback;

import org.apache.hadoop.hbase.client.BufferedMutator;

/**
 * Created by lichao on 2017/3/10.
 */
public interface MutatorCallback<T> {
  T doInMutator(BufferedMutator var1) throws Throwable;
}
