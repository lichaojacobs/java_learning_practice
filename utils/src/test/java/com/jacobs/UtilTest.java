package com.jacobs;

import com.jacobs.DataHttpUtil.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * Created by lichao on 2017/4/12.
 */
public class UtilTest {

  private DataHttpUtil dataHttpUtil = DataHttpUtil.builder().build();
  private SimpleDateFormat simpleDateTime = new SimpleDateFormat("yyyy/MM/dd");

  @Test
  public void testGetMethod() {
    try {
      //System.out.println(dataHttpUtil.get("http://visual-be/data").send().tryString());
      Future<HttpResponse> responseFuture = dataHttpUtil.get("http://visual-be/data")
          .async();
      HttpResponse response = responseFuture.get(1000, TimeUnit.MILLISECONDS);
      response.setJson(true);
      System.out.println(response.tryJson());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void regTest() {
    System.out.println(simpleDateTime.format(Calendar.getInstance().getTime()));
  }
}
