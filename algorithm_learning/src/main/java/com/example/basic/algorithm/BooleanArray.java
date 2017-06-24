package com.example.basic.algorithm;

import java.util.Arrays;

/**
 * Created by lichao on 2017/2/23.
 */
public class BooleanArray {

  private int size = 0;
  //private static final int DEFAULT_SIZE = 100;
  public int[] array;

  public BooleanArray(int size) {
    this.size = size;
    array = new int[size % 32 + 1];
  }

  public int getIndex(int number) {
    return number / 32;
  }

  public int getPosition(int number) {
    return number % 32;
  }

  public void add(int number) {
    array[getIndex(number)] = array[getIndex(number)] | (1 << getPosition(number));
  }

  public boolean getValue(int number) {
    if (number > size) {
      return false;
    }
    int index = getIndex(number);
    int position = getPosition(number);

    return ((array[index] >> position)) == 1;
  }

  public static void main(String[] args) {
    BooleanArray booleanArray = new BooleanArray(100);
    booleanArray.add(64);
    System.out.println(booleanArray.getValue(65));
    Arrays.stream(booleanArray.array)
        .forEach(System.out::print);

    System.out.println(55>>1);
  }

}
