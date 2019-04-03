package com.jacobs.basic.proxy;

/**
 * Created by lichao on 16/8/5.
 */
public class studentImpl implements student {
  private String name;

  public studentImpl() {

  }

  public studentImpl(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void print() {
    System.out.println("Hello world" + name);
  }

  @Override
  public void yell() {
    System.out.println("yell");
  }
}
