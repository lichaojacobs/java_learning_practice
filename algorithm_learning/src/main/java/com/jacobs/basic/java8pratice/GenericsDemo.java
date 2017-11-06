package com.jacobs.basic.java8pratice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lichao
 * @date 2017/11/5
 */
public class GenericsDemo {

  public static void main(String[] args) {

  }

  //不能把null之外的任何值放到List<?>中。所以可以编写一个helper方法用来转换
  public static void swap(List<?> list, int i, int j) {
    swapHelper(list, i, j);
  }

  private static <E> void swapHelper(List<E> list, int i, int j) {
    list.set(i, list.set(j, list.get(i)));
  }

  public static class Favorites {

    private Map<Class<?>, Object> favorites = new HashMap<Class<?>, Object>();

    public <T> void putFavorites(Class<T> type, T instance) {
      if (type == null) {
        throw new NullPointerException("type is null");
      }
      favorites.put(type, instance);
    }

    public <T> T getFavorites(Class<T> type) {
      return type.cast(favorites.get(type));
    }
  }
}
