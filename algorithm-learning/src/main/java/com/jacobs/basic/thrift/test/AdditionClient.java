package com.jacobs.basic.thrift.test;

import com.jacobs.basic.thrift.AdditionService;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by lichao on 16/8/25.
 */
public class AdditionClient {
  public static void main(String[] args) {
    try {
      TTransport tTransport;
      tTransport = new TSocket("localhost", 9090);
      tTransport.open();

      TProtocol protocol = new TBinaryProtocol(tTransport);
      AdditionService.Client client = new AdditionService.Client(protocol);
      System.out.println(client.add(3, 5));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
