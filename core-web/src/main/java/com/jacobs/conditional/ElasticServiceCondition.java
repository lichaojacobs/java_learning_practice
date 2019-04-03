package com.jacobs.conditional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by lichao on 2017/4/19.
 */
@Slf4j
public class ElasticServiceCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(ConditionContext conditionContext,
      AnnotatedTypeMetadata annotatedTypeMetadata) {
    String properties = conditionContext.getEnvironment().getProperty("elasticsearch.clusterName");
    log.info("elasticsearch property: {}", properties);
    return new ConditionOutcome(!StringUtils.isBlank(properties), "elasticsearch property");
  }
}
