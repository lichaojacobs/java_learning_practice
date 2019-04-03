package com.jacobs.module;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 16/7/20.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

  private Long id;
  private String province;
  private String city;

  public static <T extends Comparable> T[] minmax(T... a) {

    Object[] mm = new Object[2];
    mm[0] = a[0];
    mm[1] = a[1];

    return (T[]) mm;
  }

  public static void main(String[] args) {
    Arrays.stream(minmax(1, 2)).forEach(System.out::print);
  }
}
