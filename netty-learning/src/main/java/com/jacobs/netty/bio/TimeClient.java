package com.jacobs.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by lichao on 2016/11/5.
 */
public class TimeClient {
  public static void main(String[] args) {
    int port = 8080;
    if (args != null && args.length > 0) {
      try {
        port = Integer.valueOf(args[0]);
      } catch (NumberFormatException e) {
        // 采用默认值
      }
    }
    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out = null;
    try {
      socket = new Socket(InetAddress.getLocalHost().getHostAddress(), port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);
      out.println("QUERY TIME ORDER");
      System.out.println("Send order 2 server succeed.");
      String resp = in.readLine();
      System.out.println("Now is : " + resp);
    } catch (Exception e) {
      //不需要处理
    } finally {
      if (out != null) {
        out.close();
      }
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
