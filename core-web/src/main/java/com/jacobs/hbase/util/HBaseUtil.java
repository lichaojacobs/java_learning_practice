package com.jacobs.hbase.util;

import com.jacobs.exception.HBaseSystemException;
import com.jacobs.hbase.factory.TableFactory;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 2017/3/10.
 */
@Slf4j
@Data
public class HBaseUtil {

  public static Table getTable(String tableName, TableFactory tableFactory) {
    Assert.notNull(tableFactory, " have not table factory");

    try {
      return tableFactory.createTable(tableName.getBytes());
    } catch (Exception var3) {
      log.error(var3.getMessage(), var3);
      throw convertHBaseException(var3);
    }
  }

  public static BufferedMutator getMutator(String tableName, TableFactory tableFactory) {
    Assert.notNull(tableFactory, " have not table factory");

    try {
      return tableFactory.createBufferedMutator(tableName.getBytes());
    } catch (Exception var3) {
      log.error(var3.getMessage(), var3);
      throw convertHBaseException(var3);
    }
  }

  public static Admin getAdmin(TableFactory tableFactory) {
    Assert.notNull(tableFactory, " have not table factory");

    try {
      return tableFactory.createAdmin();
    } catch (Exception var2) {
      log.error(var2.getMessage(), var2);
      throw convertHBaseException(var2);
    }
  }

  public static void releaseTable(Table table, TableFactory tableFactory) {
    try {
      doReleaseTable(table, tableFactory);
    } catch (IOException var3) {
      log.error(var3.getMessage(), var3);
      throw convertHBaseException(var3);
    }
  }

  public static void releaseMutator(BufferedMutator mutator, TableFactory tableFactory) {
    try {
      doReleaseMutator(mutator, tableFactory);
    } catch (IOException var3) {
      log.error(var3.getMessage(), var3);
      throw convertHBaseException(var3);
    }
  }

  public static void releaseAdmin(Admin admin, TableFactory tableFactory) {
    try {
      doReleaseAdmin(admin, tableFactory);
    } catch (IOException var3) {
      log.error(var3.getMessage(), var3);
      throw convertHBaseException(var3);
    }
  }

  private static void doReleaseTable(Table table, TableFactory tableFactory) throws IOException {
    if (table != null) {
      if (tableFactory != null) {
        tableFactory.releaseTable(table);
      } else {
        table.close();
      }

    }
  }

  private static void doReleaseMutator(BufferedMutator mutator, TableFactory tableFactory)
      throws IOException {
    if (mutator != null) {
      if (tableFactory != null) {
        tableFactory.releaseBufferedMutator(mutator);
      } else {
        mutator.close();
      }

    }
  }

  private static void doReleaseAdmin(Admin admin, TableFactory tableFactory) throws IOException {
    if (admin != null) {
      if (tableFactory != null) {
        tableFactory.releaseAdmin(admin);
      } else {
        admin.close();
      }

    }
  }

  public static RuntimeException convertHBaseException(Exception ex) {
    return new HBaseSystemException("", ex);
  }

  public static Charset getCharset(String encoding) {
    return StringUtils.hasText(encoding) ? Charset.forName(encoding) : Charset.forName("UTF-8");
  }

}
