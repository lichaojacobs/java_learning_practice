package com.example.module;

import com.example.aspects.MapperProcessorAnnotation;
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
