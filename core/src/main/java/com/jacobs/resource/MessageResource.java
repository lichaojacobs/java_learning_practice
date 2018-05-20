package com.jacobs.resource;

import com.google.common.collect.Lists;
import com.jacobs.module.messagecloud.CommonResponse;
import com.jacobs.module.messagecloud.Product;
import com.jacobs.module.messagecloud.Topic;
import io.github.yedaxia.apidocs.ApiDoc;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息云接口
 */
@RestController
@RequestMapping("/v1")
public class MessageResource {


    /**
     * 创建 product
     *
     * @param name        产品线名称
     * @param description 描述
     * @return
     */
    @ApiDoc(CommonResponse.class)
    @PostMapping(value = "/products", produces = "application/json")
    public CommonResponse saveProduct(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description", required = false) String description
    ) {
        return new CommonResponse();
    }


    /**
     * product 列表
     *
     * @param page  page
     * @param count pageSize
     * @return
     */
    @ApiDoc(Product[].class)
    @GetMapping(value = "/products", produces = "application/json")
    public List<Product> productList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "count", defaultValue = "10") int count
    ) {
        return Lists.newArrayList();
    }

    /**
     * 根据productId 创建topic
     *
     * @param productId         产品Id
     * @param topicKey
     * @param messageType       消息类型: 普通消息，多机房分发消息
     * @param instanceLocations 机房 "TC,YF,ALI_YUN" 逗号分割
     * @param producerKey       生产者appkey
     * @param consumerKey       消费者appkey
     * @param initialQPS        初始qps
     * @param description       描述
     * @param bcpId             钱包Id
     * @return
     */
    @PreAuthorize("hasRole(#productId+'_ADMIN')")
    @ApiDoc(CommonResponse.class)
    @PostMapping(value = "/{productId}/topics", produces = "application/json")
    public CommonResponse saveTopic(
            @PathVariable long productId,
            @RequestParam(value = "topicKey", required = true) String topicKey,
            @RequestParam(value = "messageType", required = true) int messageType,
            @RequestParam(value = "instanceLocations", required = true) String instanceLocations,
            @RequestParam(value = "producerKey", required = true) String producerKey,
            @RequestParam(value = "consumerKey", required = true) String consumerKey,
            @RequestParam(value = "initialQPS", defaultValue = "1000") int initialQPS,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "bcpId", required = true) String bcpId
    ) {
        return new CommonResponse();
    }

    /**
     * topic 列表
     *
     * @param productId 产品线Id
     * @param page      page
     * @param count     pageSize
     * @return
     */
    @PreAuthorize("hasRole(#productId+'_ADMIN')")
    @ApiDoc(Topic[].class)
    @GetMapping(value = "/{productId}/topics", produces = "application/json")
    public List<Topic> topicList(
            @PathVariable long productId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "count", defaultValue = "10") int count
    ) {
        return Lists.newArrayList(Topic.builder()
                                       .topicKey("testTopicSecurity")
                                       .description("测试")
                                       .build());
    }

    /**
     * topic 状态管理
     *
     * @param productId 产品线Id
     * @param topicId   topicId
     * @param stateType 状态类型: 启动，停止，释放资源
     * @return
     */
    @PreAuthorize("hasRole(#productId+'_ADMIN')")
    @ApiDoc(CommonResponse.class)
    @PutMapping(value = "/{productId}/topics/{topicId}/state", produces = "application/json")
    public CommonResponse updateTopicState(
            @PathVariable long productId,
            @PathVariable long topicId,
            @RequestParam(value = "stateType", required = true) int stateType
    ) {
        return new CommonResponse();
    }

    /**
     * topic 绑定BCP钱包
     *
     * @param productId 产品线Id
     * @param topicId
     * @param bcpId     bcp钱包Id
     * @return
     */
    @PreAuthorize("hasRole(#productId+'_ADMIN')")
    @ApiDoc(CommonResponse.class)
    @PutMapping(value = "/{productId}/topics/{topicId}/bcp", produces = "application/json")
    public CommonResponse updateTopicBCPId(
            @PathVariable long productId,
            @PathVariable long topicId,
            @RequestParam(value = "bcpId", required = true) int bcpId
    ) {
        return new CommonResponse();
    }


    /**
     * topic 实例管理（运营方）
     *
     * @param productId
     * @param topicId
     * @param manageType 0：扩容，1：缩容
     * @return
     */
    @PreAuthorize("hasRole(#productId+'_ADMIN')")
    @ApiDoc(ManageResponse.class)
    @PutMapping(value = "/{productId}/topics/{topicId}/instances", produces = "application/json")
    public ManageResponse topicInstanceManage(
            @PathVariable long productId,
            @PathVariable long topicId,
            @RequestParam(value = "manageType", required = true) int manageType
    ) {
        return new ManageResponse();
    }

    @Data
    public static class ManageResponse {
        private int code; //自定义code如100，为正在扩缩容中，code为0表示扩缩容成功，其他表示扩缩容失败
        private String msg;//前端显示每一步的状态
    }
}