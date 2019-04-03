package com.jacobs.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 2017/1/6.
 */
@Slf4j
public class OnMyPropertiesCondition extends SpringBootCondition {
  @Override
  public ConditionOutcome getMatchOutcome(ConditionContext conditionContext,
      AnnotatedTypeMetadata annotatedTypeMetadata) {
    Object propertiesName = annotatedTypeMetadata.getAnnotationAttributes(
        ConditionalOnMyProperties.class.getName())
        .get("name");
    if (propertiesName != null) {
      log.info("OnMyPropertiesCondition matched");
      String value = conditionContext.getEnvironment()
          .getProperty(propertiesName.toString());
      if (value != null) {
        return new ConditionOutcome(true, "get properties");
      }
    }
    return new ConditionOutcome(false, "none get properties");
  }
}
