package com.jacobs.module.messagecloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lichao
 * @date 2018/05/13
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Topic {
    private String topicKey;//topicKey
    private int messageType;//消息类型 {"0":"普通消息","1":"多机房分发消息"}
    private List<String> instanceLocations;//机房 ["TC","YF","ALI_YUN"]
    private String producerKey;//生产者appkey
    private String consumerKey;//消费者appkey
    private int initialQPS;//初始qps
    private String description;//topic描述
    private String bcpId;//钱包Id
    private long createAt;//创建时间: 1526201521
    private long updateAt;//更新时间: 1526201521
}