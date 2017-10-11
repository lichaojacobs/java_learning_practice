package com.jacobs.hbase.results;

import org.apache.hadoop.hbase.client.ResultScanner;

/**
 * Created by lichao on 2017/3/10.
 */
public interface ResultsExtractor<T> {
  T extractData(ResultScanner var1) throws Exception;
}
