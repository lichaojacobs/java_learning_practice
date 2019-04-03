package com.jacobs.basic.multithread.disruptor;

import lombok.Data;

/**
 * @author lichao
 * @date 2019/03/31
 */
@Data
public class Order {

    private String id;//ID
    private String name;
    private double price;//金额
}
