package com.jacobs.basic.thrift;

import org.apache.thrift.TException;

/**
 * Created by lichao on 16/8/25.
 */
public class AddtionServiceHandler implements AdditionService.Iface {
  @Override
  public int add(int n1, int n2) throws TException {
    return n1 + n2;
  }
}
