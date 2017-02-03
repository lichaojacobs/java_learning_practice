package com.example.java8pratice.models;

import com.jacobs.exercise.java8.FcInterfaceTest;

/**
 * Created by lichao on 16/9/19.
 */
public class TestModel<T> {
  private Integer value;

  public TestModel() {
    this.value = 10;
  }

  public T getValue(FcInterfaceTest<T> fcInterfaceTest) {
    System.out.println("value is" + value);
    return fcInterfaceTest.parseString(value);
  }
}
