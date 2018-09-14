package com.jacobs.basic.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * Created by lichao on 16/8/26.
 */
public class Create_Session_Sample {
  public static void main(String[] args){
    ZkClient zkClient=new ZkClient("127.0.0.1:2181",5000);
    System.out.println("Zookeeper session established");
  }
}
