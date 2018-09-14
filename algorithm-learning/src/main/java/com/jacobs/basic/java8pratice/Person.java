package com.jacobs.basic.java8pratice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 16/7/11.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
  private String firstName;
  private String lastName;
  private String job;
  private String gender;
  private int salary;
  private int age;
}
