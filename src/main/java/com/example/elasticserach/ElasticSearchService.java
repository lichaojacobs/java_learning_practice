package com.example.elasticserach;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lichao on 2017/2/17.
 */
@Component
public class ElasticSearchService {
  @Autowired
  @Qualifier("transportClient")
  private Client client;

  private static final String indexName = "megacorp";
  private static final String typeName = "employee";

  public void getTestReuslt() {
    QueryBuilder query = QueryBuilders.termQuery("about", "like");
    SearchResponse searchResponse = client.prepareSearch(indexName)
        .setTypes(typeName)
        .setQuery(query)
        .setSize(10)
        .get();

    System.out.println(Stream.of(searchResponse.getHits()
        .getHits())
        .map(SearchHit::getSourceAsString)
        .collect(
            Collectors.toList()));

  }
}
