package com.jacobs.hbase;

import com.jacobs.hbase.callback.MutatorCallback;
import com.jacobs.hbase.callback.TableCallback;
import com.jacobs.hbase.results.ResultsExtractor;
import com.jacobs.hbase.results.RowMapper;
import com.jacobs.hbase.results.RowMapperResultsExtractor;
import com.jacobs.hbase.util.HBaseUtil;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 2017/3/10.
 */
@Slf4j
public class HBaseTemplate extends HBaseAccessor implements HBaseOperations {
  public HBaseTemplate(Configuration configuration) {
    this.setConfiguration(configuration);
    this.init();
  }

  public <T> T execute(String tableName, TableCallback<T> action) {
    Assert.notNull(action, "Callback object must not be null");
    Assert.notNull(tableName, "No table specified");
    Table table = this.getTable(tableName);

    T th;
    try {
      th = action.doInTable(table);
    } catch (Throwable var8) {
      if (var8 instanceof Error) {
        throw (Error) var8;
      }

      if (var8 instanceof RuntimeException) {
        throw (RuntimeException) var8;
      }

      throw HBaseUtil.convertHBaseException((Exception) var8);
    } finally {
      HBaseUtil.releaseTable(table, this.getTableFactory());
    }

    return th;
  }

  public <T> T executeBatch(String tableName, MutatorCallback<T> action) {
    Assert.notNull(action, "Callback object must not be null");
    Assert.notNull(tableName, "No table specified");
    BufferedMutator mutator = this.getMutator(tableName);

    T var5;
    try {
      T th = action.doInMutator(mutator);
      mutator.flush();
      var5 = th;
    } catch (Throwable var9) {
      if (var9 instanceof Error) {
        throw (Error) var9;
      }

      if (var9 instanceof RuntimeException) {
        throw (RuntimeException) var9;
      }

      throw HBaseUtil.convertHBaseException((Exception) var9);
    } finally {
      HBaseUtil.releaseMutator(mutator, this.getTableFactory());
    }

    return var5;
  }

  public <T> T find(String tableName, String family, ResultsExtractor<T> action) {
    Scan scan = new Scan();
    scan.addFamily(family.getBytes(this.getCharset()));
    return this.find(tableName, scan, action);
  }

  public <T> T find(String tableName, String family, String qualifier, ResultsExtractor<T> action) {
    Scan scan = new Scan();
    scan.addColumn(family.getBytes(this.getCharset()), qualifier.getBytes(this.getCharset()));
    return this.find(tableName, scan, action);
  }

  public <T> T find(String tableName, Scan scan, ResultsExtractor<T> action) {
    return this.execute(tableName, (table) -> {
      ResultScanner scanner = table.getScanner(scan);

      T var4;
      try {
        var4 = action.extractData(scanner);
      } finally {
        scanner.close();
      }

      return var4;
    });
  }

  public <T> List<T> find(String tableName, String family, RowMapper<T> action) {
    Scan scan = new Scan();
    scan.addFamily(family.getBytes(this.getCharset()));
    return this.find(tableName, scan, action);
  }

  public <T> List<T> find(String tableName, String family, String qualifier, RowMapper<T> action) {
    Scan scan = new Scan();
    scan.addColumn(family.getBytes(this.getCharset()), qualifier.getBytes(this.getCharset()));
    return this.find(tableName, scan, action);
  }

  public <T> List<T> find(String tableName, Scan scan, RowMapper<T> action) {
    return (List) this.find(tableName, (Scan) scan,
        (ResultsExtractor) (new RowMapperResultsExtractor(action)));
  }

  public <T> T get(String tableName, String rowName, RowMapper<T> mapper) {
    return this.get(tableName, rowName, (String) null, (String) null, mapper);
  }

  public <T> T get(String tableName, String rowName, String familyName, RowMapper<T> mapper) {
    return this.get(tableName, rowName, familyName, (String) null, mapper);
  }

  public <T> T get(String tableName, String rowName, String familyName, String qualifier,
      RowMapper<T> mapper) {
    return this.execute(tableName, (table) -> {
      Get get = new Get(rowName.getBytes(this.getCharset()));
      if (familyName != null) {
        byte[] result = familyName.getBytes(this.getCharset());
        if (qualifier != null) {
          get.addColumn(result, qualifier.getBytes(this.getCharset()));
        } else {
          get.addFamily(result);
        }
      }

      Result result1 = table.get(get);
      return mapper.mapRow(result1, 0);
    });
  }

  public void put(String tableName, String rowName, String familyName, String qualifier,
      byte[] data) {
    Assert.hasLength(rowName);
    Assert.hasLength(familyName);
    Assert.hasLength(qualifier);
    Assert.notNull(data);
    this.executeBatch(tableName, (mutator) -> {
      Put put = new Put(rowName.getBytes(this.getCharset()));
      put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifier), data);
      mutator.mutate(put);
      return null;
    });
  }

  public void delete(String tableName, String rowName, String familyName) {
    this.delete(tableName, rowName, familyName, (String) null);
  }

  public void delete(String tableName, String rowName, String familyName, String qualifier) {
    Assert.hasLength(rowName);
    Assert.hasLength(familyName);
    this.executeBatch(tableName, (mutator) -> {
      Delete delete = new Delete(rowName.getBytes(this.getCharset()));
      byte[] family = familyName.getBytes(this.getCharset());
      if (qualifier != null) {
        delete.addColumn(family, qualifier.getBytes(this.getCharset()));
      } else {
        delete.addFamily(family);
      }

      mutator.mutate(delete);
      return null;
    });
  }

  public void createTable(AdminConfig adminConfig, String tableName, String... families) {
    Admin admin = this.getAdmin();

    try {
      if (!admin.isTableAvailable(TableName.valueOf(tableName))) {
        HTableDescriptor e = new HTableDescriptor(TableName.valueOf(tableName));
        String[] var6 = families;
        int var7 = families.length;

        for (int var8 = 0; var8 < var7; ++var8) {
          String family = var6[var8];
          e.addFamily(new HColumnDescriptor(family));
        }

        e.setMaxFileSize(adminConfig.getMaxFileSize());
        e.setReadOnly(adminConfig.isReadOnly());
        e.setMemStoreFlushSize(adminConfig.getMemStoreFlushSize());
        e.setRegionReplication(adminConfig.getRegionReplication());
        e.setNormalizationEnabled(adminConfig.isNormalizationEnabled());
        admin.createTable(e);
      } else {
        log.error("HBase table {} already exists.", tableName);
      }
    } catch (IOException var13) {
      throw HBaseUtil.convertHBaseException(var13);
    } finally {
      HBaseUtil.releaseAdmin(admin, this.getTableFactory());
    }

  }

  private Table getTable(String tableName) {
    return HBaseUtil.getTable(tableName, this.getTableFactory());
  }

  private BufferedMutator getMutator(String tableName) {
    return HBaseUtil.getMutator(tableName, this.getTableFactory());
  }

  private Admin getAdmin() {
    return HBaseUtil.getAdmin(this.getTableFactory());
  }
}
