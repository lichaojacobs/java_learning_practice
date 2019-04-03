package com.jacobs;

import com.google.gson.annotations.SerializedName;
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
  @SerializedName("passwd")
  String password;
  String eid;
  String epasswd;
  Integer major;
  Integer isRegistered;
}