package com.jacobs;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Created by lichao on 2016/11/24.
 */
public class CommonTest {

  public static void main(String[] args) throws Exception {
    //testQuery("/Users/lichao/Desktop/4008100800_2017082508595400_00_00-00_45_51.mp3", 16000);
//    System.out.println(LocalDateTime.now().minusHours(48));
//    System.out.println(LocalDateTime.now());
//
//    LocalDateTime localDateTime = LocalDateTime.now().minusHours(48);
//    ZoneId zone = ZoneId.systemDefault();
//    Instant instant = localDateTime.atZone(zone).toInstant();
//    java.util.Date date = Date.from(instant);
//    System.out.println(date);
//    System.out.println(new Date());
    long expireTimestamp = System.currentTimeMillis() - 3600000 * 48;
    System.out.println(expireTimestamp);

    System.out.println("ali-hz-misc-srv-3".replaceAll("-docker", ""));
  }

  public static void testQuery(String filePath, int rate) throws Exception {
    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    RequestConfig.Builder requestBuilder = RequestConfig.custom();
    requestBuilder = requestBuilder.setConnectTimeout(30000);
    requestBuilder = requestBuilder.setSocketTimeout(30000);
    httpClientBuilder.setDefaultRequestConfig(requestBuilder.build());
    HttpClient client = httpClientBuilder.build();
    HttpPost httpPost = new HttpPost("http://streaming.mobvoi.com/speech2text");
    // 文件路径
    httpPost.setEntity(new InputStreamEntity(
        new FileInputStream(filePath)));
    // 16000是采样率
    httpPost.setHeader("Content-Type", String.format("audio/x-wav;rate=%d", rate));
    try {
      HttpResponse response = client.execute(httpPost);
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode == HttpStatus.SC_OK) {
        String content = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(content);
        return;
      }
      System.out.println(
          "Fail to do speech recognition with status code " + statusCode + ", " + EntityUtils
              .toString(response.getEntity()));
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Fail to do speech recognition.");
    } finally {
      System.out.println("closeing resource...");
    }
  }
}
