package com.example.module;

/**
 * Created by lichao on 16/7/20.
 */
public class Address {
  private Long id;
  private String province;
  private String city;

  public Address(Long id, String province, String city) {
    this.id = id;
    this.province = province;
    this.city = city;
  }

  public Address() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
