package com.jacobs.basic.zookeeper.zkclient;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by lichao on 16/8/26.
 */
public class TestMainClient implements Watcher {
  protected static ZooKeeper zk = null;
  protected static Integer mutex;
  int sessionTimeout = 10000;
  protected String root;

  public TestMainClient(String connectString) {
    if (zk == null) {
      try {
        System.out.println("启动zookeeper");
        zk = new ZooKeeper(connectString, sessionTimeout, this);

        mutex = new Integer(-1);
      } catch (IOException e) {
        zk = null;
      }
    }
  }

  synchronized public void process(WatchedEvent event) {
    synchronized (mutex) {
      mutex.notify();
    }
  }
}
