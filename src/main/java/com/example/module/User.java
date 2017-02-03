package com.example.module;

import org.springframework.beans.BeanUtils;

/**
 * Created by lichao on 16/7/20.
 */
public class User {
  private Long id;
  private String firstName;
  private String lastName;

  public User(Long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public User clone() throws CloneNotSupportedException {
    User user = new User();
    BeanUtils.copyProperties(this, user);
    return user;
  }
}
