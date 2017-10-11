package com.jacobs.service;

import com.jacobs.conditional.ElasticServiceCondition;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * Created by lichao on 2017/2/17.
 */
@Component
@Conditional({ElasticServiceCondition.class})
public class ElasticSearchService {

  @Autowired
  private Client client;

  private static final String indexName = "megacorp";
  private static final String typeName = "employee";

  public Object getTestReuslt() {
    QueryBuilder query = QueryBuilders.termQuery("about", "like");
    SearchResponse searchResponse = client.prepareSearch(indexName)
        .setTypes(typeName)
        .setQuery(query)
        .setSize(10)
        .get();

    return Stream.of(searchResponse.getHits()
        .getHits())
        .map(SearchHit::getSourceAsString)
        .collect(Collectors.toList());
  }
}
