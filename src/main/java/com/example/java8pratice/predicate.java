package com.example.java8pratice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 16/7/18.
 */
public interface predicate<T> {
  boolean test(T t);

  static <T>List<T> filter(List<T> list, predicate<T> p){
    List<T> result=new ArrayList<>();
    for (T e: list){
      if(p.test(e)){
        result.add(e);
      }
    }
    return result;
  }
}

