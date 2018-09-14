package com.jacobs.netty.timeserver1;

/**
 * Created by lichao on 2017/1/26.
 */
public class TimeServer {
  public static void main(String[] args) {
    int port = 8080;
    if (args != null && args.length > 0) {
      try {
        port = Integer.valueOf(args[0]);
      } catch (Exception ex) {

      }

    }

    new Thread(new MutiplexerTimeServer("127.0.0.1",port), "MULTIPLEXERTIMERSERVER-001").start();
  }
}
