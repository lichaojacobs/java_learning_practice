package com.jacobs.basic.designpattern;

/**
 * Created by lichao on 2017/2/15.
 */
public class SingleTonTest {

  //延迟加载，由java内部实现线程安全
  private static class Holder {

    static SingleTonTest singleTonTest = new SingleTonTest();
  }

  public static SingleTonTest getInstance() {
    return Holder.singleTonTest;
  }
}
