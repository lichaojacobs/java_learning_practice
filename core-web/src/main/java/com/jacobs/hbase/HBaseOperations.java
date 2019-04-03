package com.jacobs.hbase;

import com.jacobs.hbase.callback.MutatorCallback;
import com.jacobs.hbase.callback.TableCallback;
import com.jacobs.hbase.results.ResultsExtractor;
import com.jacobs.hbase.results.RowMapper;

import org.apache.hadoop.hbase.client.Scan;

import java.util.List;

/**
 * Created by lichao on 2017/3/10.
 */
public interface HBaseOperations {
  <T> T execute(String var1, TableCallback<T> var2);

  <T> T executeBatch(String var1, MutatorCallback<T> var2);

  <T> T find(String var1, String var2, ResultsExtractor<T> var3);

  <T> T find(String var1, String var2, String var3, ResultsExtractor<T> var4);

  <T> T find(String var1, Scan var2, ResultsExtractor<T> var3);

  <T> List<T> find(String var1, String var2, RowMapper<T> var3);

  <T> List<T> find(String var1, String var2, String var3, RowMapper<T> var4);

  <T> List<T> find(String var1, Scan var2, RowMapper<T> var3);

  <T> T get(String var1, String var2, RowMapper<T> var3);

  <T> T get(String var1, String var2, String var3, RowMapper<T> var4);

  <T> T get(String var1, String var2, String var3, String var4, RowMapper<T> var5);

  void put(String var1, String var2, String var3, String var4, byte[] var5);

  void delete(String var1, String var2, String var3);

  void delete(String var1, String var2, String var3, String var4);

  void createTable(AdminConfig var1, String var2, String... var3);
}
