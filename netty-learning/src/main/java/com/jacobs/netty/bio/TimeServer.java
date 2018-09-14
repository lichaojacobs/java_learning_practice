package com.jacobs.netty.bio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lichao on 2016/11/5.
 */
public class TimeServer {

  public static void main(String[] args) throws IOException {
    int port = 8080;
    if (args != null && args.length > 0) {

    }
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port, 20, InetAddress.getLocalHost());
      System.out.println("The time server is start in port : " + port);
      while (true) {
        Socket socket = serverSocket.accept();
        //考虑到多请求情况,每个请求分配一个thread
        new Thread(new TimeServerHandler(socket)).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (serverSocket != null) {
        System.out.println("The time server close");
        serverSocket.close();
        serverSocket = null;
      }

    }
  }
}
