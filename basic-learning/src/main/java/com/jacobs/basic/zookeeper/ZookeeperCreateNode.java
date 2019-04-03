package com.jacobs.basic.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lichao on 16/8/25.
 */
public class ZookeeperCreateNode implements Watcher {
  private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

  public static void main(String[] args) throws Exception {
    ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperCreateNode());
    connectedSemaphore.await();
    zooKeeper.create("/zk-test-zookeeper-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I am context.");

    zooKeeper.create("/zk-test-zookeeper-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL, new IStringCallback(), "I am context.");

    Thread.sleep(Integer.MAX_VALUE);
  }

  @Override
  public void process(WatchedEvent event) {
    if (Event.KeeperState.SyncConnected == event.getState()) {
      connectedSemaphore.countDown();
    }
  }
}

class IStringCallback implements AsyncCallback.StringCallback {

  @Override
  public void processResult(int rc, String path, Object ctx, String name) {
    System.out.println(
        "Create path result:[" + rc + ", " + path + ", " + ctx + ", real path name: " + name);
  }
}
