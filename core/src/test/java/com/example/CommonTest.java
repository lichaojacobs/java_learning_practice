package com.example;

import java.util.TimeZone;
import java.util.concurrent.Callable;
import org.apache.commons.lang.time.FastDateFormat;

/**
 * Created by lichao on 2016/11/24.
 */
public class CommonTest {


  private static FastDateFormat formatter = FastDateFormat
      .getInstance("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("GMT"));

  class CallTask implements Callable<Integer> {

    private int number;

    CallTask(int number) {
      this.number = number;
    }

    @Override
    public Integer call() throws Exception {
      return number;
    }
  }
}
