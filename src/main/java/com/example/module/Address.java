package com.example.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 16/7/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
  private Long id;
  private String province;
  private String city;
}
