package com.example.designpattern;

/**
 * Created by lichao on 2017/2/15.
 */
public class SingleTonTest {
  private static class Holder {
    static SingleTonTest singleTonTest = new SingleTonTest();
  }

  public static SingleTonTest getInstance() {
    return Holder.singleTonTest;
  }
}
