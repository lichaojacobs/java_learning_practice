package com.jacobs.basic.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lichao on 16/8/25.
 */
public class ZookeeperGetData implements Watcher{
  private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
  private static ZooKeeper zk = null;

  public static void main(String[] args) throws Exception {
    String path = "/zk-book-data";
    zk = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperGetNotice());
    zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    zk.getData(path, true, new IDataCallback(), null);
    zk.setData(path, "345".getBytes(), -1,new IStatCallback(),null);
    zk.getData(path, true, new IDataCallback(), null);
    Thread.sleep(Integer.MAX_VALUE);
  }

  @Override
  public void process(WatchedEvent event) {
    if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
      if (Watcher.Event.EventType.None == event.getType() && null == event.getPath()) {
        connectedSemaphore.countDown();
      } else if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
        try {
          zk.getData(event.getPath(), true, new IDataCallback(), null);
        } catch (Exception e) {
        }
      }
    }
  }
}

class IStatCallback implements AsyncCallback.StatCallback{

  @Override
  public void processResult(int rc, String path, Object ctx, Stat stat) {
    if(rc==0){
      System.out.println("SUCCESS");
    }
  }
}

class IDataCallback implements AsyncCallback.DataCallback {

  @Override
  public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
    System.out.println(rc + "," + path + ", " + new String(data));
    System.out.println(stat.getCzxid() + "," + stat.getMzxid() + ", " + stat.getVersion());
  }
}