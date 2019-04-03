package com.jacobs.hbase.results;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 16/9/18.
 */
@Slf4j
public class PutExtension extends Put {

  String columnFamilyName = "i";

  public PutExtension(String columnFamilyName, byte[] row) {
    super(row);
    this.columnFamilyName = columnFamilyName;
  }

  public PutExtension build(String paramName, Object param) throws IOException {
    if (param != null) {
      this.addColumn(columnFamilyName.getBytes(), paramName.getBytes(),
          Bytes.toBytes(param.toString()));
    }
    return this;
  }
}
