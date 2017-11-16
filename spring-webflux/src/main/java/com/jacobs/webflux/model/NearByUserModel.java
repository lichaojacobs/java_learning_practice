// Copyright 2017 Mobvoi Inc. All Rights Reserved.

package com.jacobs.webflux.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 2017/4/14.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearByUserModel {

  @SerializedName("watch_device_id")
  private String watchDeviceId;
  private String wwid;
  private String nickname;
  @SerializedName("img_url")
  private String imgUrl;
  private int distance;
  private int steps;
  private boolean liked;
  private int rank;
  @SerializedName("is_tic_mon")
  private boolean isTicMon;
  @SerializedName("is_tic_mon_clicked")
  private boolean isTicMonClicked;
  private int level;
}
