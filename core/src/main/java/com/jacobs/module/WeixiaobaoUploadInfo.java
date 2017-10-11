package com.jacobs.module;

import com.jacobs.aspects.MapperProcessorAnnotation;
import lombok.Data;

/**
 * Created by lichao on 2017/6/28.
 */
@Data
@MapperProcessorAnnotation
public class WeixiaobaoUploadInfo {

  private String deviceId;
  private String deviceType;
  private String eventType;
  private String eventDate;
}
