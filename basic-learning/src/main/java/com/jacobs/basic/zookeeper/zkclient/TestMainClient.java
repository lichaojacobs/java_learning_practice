package com.jacobs.basic.zookeeper.zkclient;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by lichao on 16/8/26.
 */
public class TestMainClient implements Watcher {

    protected static ZooKeeper zooKeeper = null;
    protected static Integer mutex;
    int sessionTimeout = 10000;
    protected String root;

    public TestMainClient(String connectString) {
        if (zooKeeper == null) {
            try {
                System.out.println("启动zookeeper");
                zooKeeper = new ZooKeeper(connectString, sessionTimeout, this);

                mutex = new Integer(-1);
            } catch (IOException e) {
                zooKeeper = null;
            }
        }
    }

    synchronized public void process(WatchedEvent event) {
        synchronized (mutex) {
            mutex.notify();
        }
    }
}
