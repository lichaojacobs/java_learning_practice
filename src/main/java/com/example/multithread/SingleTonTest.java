package com.example.multithread;

import com.jacobs.exercise.Person;

/**
 * Created by lichao on 16/9/8.
 */
public class SingleTonTest {
  private static class InstanceHolder {
    static Person person = new Person();
  }

  public static Person getInstance() {
    return InstanceHolder.person;
  }
}
