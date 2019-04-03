package com.jacobs.basic.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lichao on 16/8/24.
 */
public class ZookeeperTest implements Watcher {

  private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

  public static void main(String[] args) {
    try {
      ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperTest());
      System.out.println(zooKeeper.getState());
      try {
        connectedSemaphore.await();
      } catch (InterruptedException e) {
      }
      System.out.println("Zookeeper session established");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void process(WatchedEvent event) {
    System.out.println("Receive watched event: " + event);
    if (Event.KeeperState.SyncConnected == event.getState()) {
      connectedSemaphore.countDown();
    }
  }
}
