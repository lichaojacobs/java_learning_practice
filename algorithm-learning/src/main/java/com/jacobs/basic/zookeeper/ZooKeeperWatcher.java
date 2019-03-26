package com.jacobs.basic.zookeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author lichao
 * @date 2019/03/26
 */
public class ZooKeeperWatcher implements Watcher {

    /**
     * 定义原子变量
     */
    AtomicInteger seq = new AtomicInteger();
    /**
     * 定义session失效时间
     */
    private static final int SESSION_TIMEOUT = 10000;
    /**
     * zookeeper服务器地址
     */
    private static final String CONNECTION_ADDR = "192.168.80.88:2181";
    /**
     * zk父路径设置
     */
    private static final String PARENT_PATH = "/testWatch";
    /**
     * zk子路径设置
     */
    private static final String CHILDREN_PATH = "/testWatch/children";
    /**
     * 进入标识
     */
    private static final String LOG_PREFIX_OF_MAIN = "【Main】";
    /**
     * zk变量
     */
    private ZooKeeper zk = null;
    /**
     * 信号量设置，用于等待zookeeper连接建立之后 通知阻塞程序继续向下执行
     */
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 创建ZK连接
     *
     * @param connectAddr ZK服务器地址列表
     * @param sessionTimeout Session超时时间
     */
    public void createConnection(String connectAddr, int sessionTimeout) {
        this.releaseConnection();
        try {
            zk = new ZooKeeper(connectAddr, sessionTimeout, this);
            System.out.println(LOG_PREFIX_OF_MAIN + "开始连接ZK服务器");
            connectedSemaphore.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭ZK连接
     */
    public void releaseConnection() {
        if (this.zk != null) {
            try {
                this.zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建节点
     *
     * @param path 节点路径
     * @param data 数据内容
     */
    public boolean createPath(String path, String data) {
        try {
            //设置监控(由于zookeeper的监控都是一次性的所以 每次必须设置监控)
            this.zk.exists(path, true);
            System.out.println(LOG_PREFIX_OF_MAIN + "节点创建成功, Path: " + this.zk.create(  /**路径*/
                path,
                /**数据*/
                data.getBytes(),
                /**所有可见*/
                Ids.OPEN_ACL_UNSAFE,
                /**永久存储*/
                CreateMode.PERSISTENT) + ", content: " + data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读取指定节点数据内容
     *
     * @param path 节点路径
     */
    public String readData(String path, boolean needWatch) {
        try {
            return new String(this.zk.getData(path, needWatch, null));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 更新指定节点数据内容
     *
     * @param path 节点路径
     * @param data 数据内容
     */
    public boolean writeData(String path, String data) {
        try {
            System.out.println(
                LOG_PREFIX_OF_MAIN + "更新数据成功，path：" + path + ", stat: " + this.zk.setData(path, data.getBytes(), -1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除指定节点
     *
     * @param path 节点path
     */
    public void deleteNode(String path) {
        try {
            this.zk.delete(path, -1);
            System.out.println(LOG_PREFIX_OF_MAIN + "删除节点成功，path：" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断指定节点是否存在
     *
     * @param path 节点路径
     */
    public Stat exists(String path, boolean needWatch) {
        try {
            return this.zk.exists(path, needWatch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取子节点
     *
     * @param path 节点路径
     */
    private List<String> getChildren(String path, boolean needWatch) {
        try {
            return this.zk.getChildren(path, needWatch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除所有节点
     */
    public void deleteAllTestPath() {
        if (this.exists(CHILDREN_PATH, false) != null) {
            this.deleteNode(CHILDREN_PATH);
        }
        if (this.exists(PARENT_PATH, false) != null) {
            this.deleteNode(PARENT_PATH);
        }
    }

    /**
     * 收到来自Server的Watcher通知后的处理。
     */
    @Override
    public void process(WatchedEvent event) {

        System.out.println("进入 process 。。。。。event = " + event);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (event == null) {
            return;
        }

        // 连接状态
        KeeperState keeperState = event.getState();
        // 事件类型
        EventType eventType = event.getType();
        // 受影响的path
        String path = event.getPath();

        String logPrefix = "【Watcher-" + this.seq.incrementAndGet() + "】";

        System.out.println(logPrefix + "收到Watcher通知");
        System.out.println(logPrefix + "连接状态:\t" + keeperState.toString());
        System.out.println(logPrefix + "事件类型:\t" + eventType.toString());

        if (KeeperState.SyncConnected == keeperState) {
            // 成功连接上ZK服务器
            if (EventType.None == eventType) {
                System.out.println(logPrefix + "成功连接上ZK服务器");
                connectedSemaphore.countDown();
            }
            //创建节点
            else if (EventType.NodeCreated == eventType) {
                System.out.println(logPrefix + "节点创建");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.exists(path, true);
            }
            //更新节点
            else if (EventType.NodeDataChanged == eventType) {
                System.out.println(logPrefix + "节点数据更新");
                System.out.println("我看看走不走这里........");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(logPrefix + "数据内容: " + this.readData(PARENT_PATH, true));
            }
            //更新子节点
            else if (EventType.NodeChildrenChanged == eventType) {
                System.out.println(logPrefix + "子节点变更");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(logPrefix + "子节点列表：" + this.getChildren(PARENT_PATH, true));
            }
            //删除节点
            else if (EventType.NodeDeleted == eventType) {
                System.out.println(logPrefix + "节点 " + path + " 被删除");
            } else {
                ;
            }
        } else if (KeeperState.Disconnected == keeperState) {
            System.out.println(logPrefix + "与ZK服务器断开连接");
        } else if (KeeperState.AuthFailed == keeperState) {
            System.out.println(logPrefix + "权限检查失败");
        } else if (KeeperState.Expired == keeperState) {
            System.out.println(logPrefix + "会话失效");
        } else {
            ;
        }

        System.out.println("--------------------------------------------");

    }

    /**
     * <B>方法名称：</B>测试zookeeper监控<BR> <B>概要说明：</B>主要测试watch功能<BR>
     */
    public static void main(String[] args) throws Exception {

        //建立watcher
        ZooKeeperWatcher zkWatch = new ZooKeeperWatcher();
        //创建连接
        zkWatch.createConnection(CONNECTION_ADDR, SESSION_TIMEOUT);
        //System.out.println(zkWatch.zk.toString());

        Thread.sleep(1000);

        // 清理节点
        zkWatch.deleteAllTestPath();

        if (zkWatch.createPath(PARENT_PATH, System.currentTimeMillis() + "")) {

            Thread.sleep(1000);

            // 读取数据
            System.out.println("---------------------- read parent ----------------------------");
            //zkWatch.readData(PARENT_PATH, true);

            // 读取子节点
            System.out.println("---------------------- read children path ----------------------------");
            zkWatch.getChildren(PARENT_PATH, true);

            // 更新数据
            zkWatch.writeData(PARENT_PATH, System.currentTimeMillis() + "");

            Thread.sleep(1000);

            // 创建子节点
            zkWatch.createPath(CHILDREN_PATH, System.currentTimeMillis() + "");

            Thread.sleep(1000);

            zkWatch.writeData(CHILDREN_PATH, System.currentTimeMillis() + "");
        }

        Thread.sleep(50000);
        // 清理节点
        zkWatch.deleteAllTestPath();
        Thread.sleep(1000);
        zkWatch.releaseConnection();
    }

}
