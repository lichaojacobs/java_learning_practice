package com.jacobs.webflux.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 2017/7/6.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

  Integer id;
  String name;
  String email;
  String password;
  String eid;
  String epasswd;
  Integer major;
  Integer isRegistered;

  public User(int id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }
}