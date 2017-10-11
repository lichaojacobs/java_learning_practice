package com.jacobs.hbase.factory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Table;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 2017/3/10.
 */
@Slf4j
public class MainHTableFactory implements TableFactory, Closeable {

  Connection connection;
  ThreadPoolExecutor poolExecutor;

  public MainHTableFactory(Configuration configuration) {
    this.poolExecutor = HTable.getDefaultExecutor(configuration);
    try {
      this.connection = ConnectionFactory.createConnection(configuration, this.poolExecutor);
    } catch (Exception ex) {
      log.error("Create connection failed.", ex);
    }
  }

  @Override
  public Table createTable(byte[] var1) throws IOException {
    return this.connection.getTable(TableName.valueOf(var1));
  }

  @Override
  public void releaseTable(Table table) throws IOException {
    if (table != null) {
      table.close();
    }
  }

  @Override
  public Admin createAdmin() throws IOException {
    return this.connection.getAdmin();
  }

  @Override
  public void releaseAdmin(Admin admin) throws IOException {
    if (admin != null) {
      admin.close();
    }
  }

  @Override
  public BufferedMutator createBufferedMutator(byte[] var1) throws IOException {
    return this.connection.getBufferedMutator(TableName.valueOf(var1));
  }

  @Override
  public void releaseBufferedMutator(BufferedMutator var1) throws IOException {
    if (var1 != null) {
      var1.close();
    }
  }

  @Override
  public void close() throws IOException {
    if (this.connection != null && !this.connection.isClosed()) {
      try {
        this.connection.close();
        log.info("Close HBase connection.");
      } catch (IOException var3) {
        log.error("Close HBase connection failed.", var3);
      }
    }

    if (this.poolExecutor != null && !this.poolExecutor.isShutdown()) {
      this.poolExecutor.shutdown();

      try {
        this.poolExecutor.awaitTermination(3L, TimeUnit.SECONDS);
      } catch (InterruptedException var2) {
        log.error("Interrupt thread in HBase pool executor.");
      }

      log.info("Shutdown HBase pool executor.");
    }

  }
}
