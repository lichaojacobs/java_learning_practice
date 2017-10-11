package com.jacobs.basic.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lichao on 16/8/25.
 */
public class ZookeeperGetNotice implements Watcher {
  private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
  private static ZooKeeper zk = null;

  public static void main(String[] args) throws Exception {
    String path = "/zk-book";
    zk = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperGetNotice());
    zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    List<String> childrenList = zk.getChildren(path, true);
    System.out.println(childrenList);

    zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    Thread.sleep(Integer.MAX_VALUE);
  }

  @Override
  public void process(WatchedEvent event) {
    if (Event.KeeperState.SyncConnected == event.getState()) {
      if (Event.EventType.None == event.getType() && null == event.getPath()) {
        connectedSemaphore.countDown();
      } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
        try {
          System.out.println("ReGet Child:" + zk.getChildren(event.getPath(), true));
        } catch (Exception e) {

        }
      }
    }
  }
}
