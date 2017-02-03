package com.example.endpoint;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2017/1/6.
 */
public class MyEndPoint extends AbstractEndpoint implements Endpoint {
  private List<MemStatus> status;

  public MyEndPoint(List<MemStatus> status) {
    super("endPoint", false);
    this.status = status;
  }

  public String getId() {
    return "endPoint";
  }

  public boolean isEnabled() {
    return true;
  }

  public boolean isSensitive() {
    return false;
  }

  public Object invoke() {
    if (status == null || status.isEmpty()) {
      return "hello world";
    }
    Map<String, List<Map<String, Object>>> result = new HashMap<>();
    for (MemStatus memStatus : status) {
      for (Map.Entry<String, Object> entry : memStatus.getStatus()
          .entrySet()) {
        List<Map<String, Object>> collectList = result.get(entry.getKey());
        if (collectList == null) {
          collectList = new LinkedList<>();
          result.put(entry.getKey(), collectList);
        }
        Map<String, Object> soloCollect = new HashMap<>();
        soloCollect.put("date", memStatus.getDate());
        soloCollect.put(entry.getKey(), entry.getValue());
        collectList.add(soloCollect);
      }
    }
    return result;
  }
}
