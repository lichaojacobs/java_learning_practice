package com.jacobs.basic.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.RetryNTimes;

/**
 * @author lichao
 * @date 2019/03/26
 */
public class DistributeCounter {

    public static void main(String[] args) throws Exception {
        CuratorFramework cf = CuratorBase.getInstance();
        //3 开启连接
        cf.start();
        //cf.delete().forPath("/super");
        //4 使用DistributedAtomicInteger
        DistributedAtomicInteger atomicIntger = new DistributedAtomicInteger(cf, "/super", new RetryNTimes(3, 1000));
        AtomicValue<Integer> value = atomicIntger.add(1);
        System.out.println(value.succeeded());
        System.out.println(value.postValue());  //最新值
        System.out.println(value.preValue());  //原始值
    }
}
