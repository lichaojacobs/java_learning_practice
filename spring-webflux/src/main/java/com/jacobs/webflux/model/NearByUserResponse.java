// Copyright 2017 Mobvoi Inc. All Rights Reserved.

package com.jacobs.webflux.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
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
public class NearByUserResponse {

  private int status;
  private int rank;
  private int code;
  private int steps;
  @SerializedName("privacy_state")
  private boolean privacyState;
  @SerializedName("watch_device_id")
  private String watchDeviceId;
  private List<NearByUserModel> data;
  @SerializedName("num_likes")
  private int numLikes;
  private String nickname;
  @SerializedName("img_url")
  private String imgUrl;
}
