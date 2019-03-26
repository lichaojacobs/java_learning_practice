package com.jacobs.basic.zookeeper.zkclient;

import org.apache.zookeeper.server.ZooKeeperServerMain;

import java.io.IOException;

/**
 * Created by lichao on 16/8/30.
 */
public class TestMainServer extends ZooKeeperServerMain {

    public static final int CLIENT_PORT = 2181;

    public static class MainThread extends Thread {

        //final File confFile;
        final TestMainServer main;

        public MainThread(int clientPort) throws IOException {
            super("Standalone server with clientPort:" + clientPort);
            //confFile = new File(tmpDir, "zoo.cfg");
            main = new TestMainServer();
        }
    }
}
