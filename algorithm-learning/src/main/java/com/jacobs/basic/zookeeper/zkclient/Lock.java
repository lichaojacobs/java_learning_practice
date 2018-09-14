package com.jacobs.basic.zookeeper.zkclient;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lichao on 16/8/26.
 */
@Slf4j
public class Lock extends TestMainClient {
  String myZnode;
  protected static ZooKeeper zooKeeper = null;
  protected static int sessionTimeout = 10000;
  protected static Integer mutex;
  protected static String connectString;

  public Lock(String connectString, String root) throws IOException {
    super(connectString);
    this.root = root;
    this.connectString = connectString;
    if (zooKeeper != null) {
      try {
        Stat s = zooKeeper.exists(root, false);
        if (s == null) {
          zooKeeper.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
      } catch (KeeperException ex) {
        log.error(ex.getMessage());
      } catch (InterruptedException ex) {
        log.error(ex.getMessage());
      }
    }
  }

  void getLock() throws KeeperException, InterruptedException {
    List<String> list = zooKeeper.getChildren(root, false);
    String[] nodes = list.toArray(new String[list.size()]);
    Arrays.sort(nodes);
    if (myZnode.equals(root + "/" + nodes[0])) {
      doAction();
    } else {
      waitForLock(nodes[0]);
    }
  }

  void check() throws KeeperException, InterruptedException {
    myZnode = zooKeeper.create(root + "/lock_", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL_SEQUENTIAL);
    getLock();
  }

  void waitForLock(String lower) throws KeeperException, InterruptedException {
    Stat stat = zooKeeper.exists(root + "/" + lower, true);
    if (stat != null) {
      mutex.wait();
    } else {
      getLock();
    }
  }

  void doAction() {
    System.out.println("拿到锁!");
  }

  @Override
  public void process(WatchedEvent event) {
    if (event.getType() == Event.EventType.NodeDeleted) {
      System.out.println("锁被删除");
      super.process(event);
      doAction();
    }
  }

  //  public static void main(String[] args) {
  //    TestMainServer.start();
  //    String connectString = "localhost:"+TestMainServer.CLIENT_PORT;
  //
  //    Locks lk = new Locks(connectString, "/locks");
  //    try {
  //      lk.check();
  //    } catch (InterruptedException e) {
  //      logger.error(e);
  //    } catch (KeeperException e) {
  //      logger.error(e);
  //    }
  //  }
}
