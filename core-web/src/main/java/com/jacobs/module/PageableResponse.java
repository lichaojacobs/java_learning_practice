package com.jacobs.module;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 2017/9/23.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T> {

  List<T> results;
  @JSONField(name = "total_pages")
  long totalPages;
  @JSONField(name = "total_elements")
  long totalElements;
}
