package com.jacobs.hbase.factory;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

/**
 * Created by lichao on 2017/3/10.
 */
public interface TableFactory {
  Table createTable(byte[] var1) throws IOException;

  void releaseTable(Table var1) throws IOException;

  Admin createAdmin() throws IOException;

  void releaseAdmin(Admin var1) throws IOException;

  BufferedMutator createBufferedMutator(byte[] var1) throws IOException;

  void releaseBufferedMutator(BufferedMutator var1) throws IOException;
}
