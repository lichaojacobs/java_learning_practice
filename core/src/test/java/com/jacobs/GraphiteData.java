package com.jacobs;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GraphiteData {
    String target;
    @SerializedName("datapoints")
    List<List<Double>> dataPoints;
}
