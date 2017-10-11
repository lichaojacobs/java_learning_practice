package com.jacobs.netty.aiotimeserver1;

import java.io.IOException;

/**
 * Created by lichao on 2017/1/28.
 */
public class TimeServer {
  public static void main(String[] args) throws IOException {
    int port = 8080;
    new Thread(new AsyncTimeServerHandler("127.0.0.1", port),
        "AIO-AsyncTimeServerHandler-001").start();
  }
}
