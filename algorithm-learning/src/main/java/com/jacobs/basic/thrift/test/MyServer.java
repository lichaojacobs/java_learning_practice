package com.jacobs.basic.thrift.test;

import com.jacobs.basic.thrift.AdditionService;
import com.jacobs.basic.thrift.AddtionServiceHandler;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * Created by lichao on 16/8/25.
 */
public class MyServer {
  public static void StartsimplServer(AdditionService.Processor<AddtionServiceHandler> processor) {
    try {
      TServerTransport serverTransport = new TServerSocket(9090);
      TServer tServer = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
      System.out.println("Starting the simple server....");
      tServer.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    StartsimplServer(
        new AdditionService.Processor<>(new AddtionServiceHandler()));
  }
}
