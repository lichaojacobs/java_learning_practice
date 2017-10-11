package com.jacobs.basic.java8pratice.lazylist;

/**
 * Created by lichao on 16/8/14.
 */
public interface MyList<T> {
  T head();

  MyList<T> tail();

  default boolean isEmpty() {
    return true;
  }

}

class MyLinkedList<T> implements MyList {
  private final T head;
  private final MyList<T> tail;

  public MyLinkedList(T head, MyList<T> tail) {
    this.head = head;
    this.tail = tail;
  }

  @Override
  public Object head() {
    return head;
  }

  @Override
  public MyList tail() {
    return tail;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }
}

class Empty<T> implements MyList<T> {
  @Override
  public T head() {
    return head();
  }

  @Override
  public MyList<T> tail() {
    return tail();
  }

  @Override
  public boolean isEmpty() {
    return false;
  }
}


