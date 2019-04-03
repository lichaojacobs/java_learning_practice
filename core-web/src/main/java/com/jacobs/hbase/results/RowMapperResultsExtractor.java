package com.jacobs.hbase.results;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lichao on 2017/3/10.
 */
public class RowMapperResultsExtractor<T> implements ResultsExtractor<List<T>> {

  private final RowMapper<T> rowMapper;

  public RowMapperResultsExtractor(RowMapper<T> rowMapper) {
    this.rowMapper = rowMapper;
  }

  @Override
  public List<T> extractData(ResultScanner results) throws Exception {
    ArrayList rs = new ArrayList();
    int rowNum = 0;
    Iterator iterator = results.iterator();

    while (iterator.hasNext()) {
      Result result = (Result) iterator.next();
      rs.add(this.rowMapper.mapRow(result, rowNum++));
    }

    return rs;
  }
}
