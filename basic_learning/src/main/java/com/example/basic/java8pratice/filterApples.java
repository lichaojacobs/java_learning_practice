package com.example.basic.java8pratice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichao on 16/7/18.
 */
public class filterApples {
  List<Apple> inventory = new ArrayList<>();
  List<Apple> redApples = predicate
      .filter(inventory, (apple) -> "red".equals(apple.getColor()));
}

class Apple {
  String color;
  String name;

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}