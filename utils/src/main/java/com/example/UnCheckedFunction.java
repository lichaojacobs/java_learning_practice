package com.example;

/**
 * Created by lichao on 2017/4/12.
 */
@FunctionalInterface
public interface UnCheckedFunction<T, R> {

  R apply(T t) throws Exception;
}
