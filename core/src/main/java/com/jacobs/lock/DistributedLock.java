package com.jacobs.lock;

import java.io.Closeable;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Executor;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lichao
 * @date 2019/03/28
 */
@Slf4j
@Service("DistributedLock")
public class DistributedLock {

    @Autowired
    private ZkConfig zkConfig;
    private static final Random random = new Random();

    private CuratorFramework curator;
    @Getter
    private String client;
    byte[] clientBytes;

    @PostConstruct
    public void init() {
        curator = CuratorFrameworkFactory.newClient(zkConfig.getConnectString(), zkConfig.getSessionTimeoutMs(),
            zkConfig.getConnectionTimeoutMs(), new RetryNTimes(zkConfig.getRetryCount(), zkConfig.getElapsedTimeMs()));
        curator.start();

        try {
            client = InetAddress.getLocalHost()
                                .getHostName();
        } catch (Exception ex) {
            log.error("DistributedLock init error in getting hostname");
            client = String.format("palo-loader-node-%d", random.nextInt(1000));
        }
        clientBytes = client.getBytes(StandardCharsets.UTF_8);
    }


    public boolean lock(String lockPath) {
        log.debug("{} trying to lock {}", client, lockPath);

        try {
            curator.create()
                   .creatingParentsIfNeeded()
                   .withMode(CreateMode.EPHEMERAL)
                   .forPath(lockPath, clientBytes);
        } catch (KeeperException.NodeExistsException ex) {
            log.debug("{} see {} is already locked", client, lockPath);
        } catch (Exception ex) {
            throw new IllegalStateException("Error while " + client + " trying to lock " + lockPath, ex);
        }

        String lockOwner = peekLock(lockPath);
        if (client.equals(lockOwner)) {
            log.info("{} acquired lock at {}", client, lockPath);
            return true;
        } else {
            log.debug("{} failed to acquire lock at {}, which is held by {}", client, lockPath, lockOwner);
            return false;
        }
    }


    public boolean lock(String lockPath, long timeout) {
        if (lock(lockPath)) {
            return true;
        }

        if (timeout <= 0) {
            timeout = Long.MAX_VALUE;
        }

        log.debug("{} will wait for lock path {}", client, lockPath);
        long waitStart = System.currentTimeMillis();
        long sleep = 10 * 1000L; // 10 seconds

        while (System.currentTimeMillis() - waitStart <= timeout) {
            try {
                Thread.sleep((long) (1000 + sleep * random.nextDouble()));
            } catch (InterruptedException e) {
                Thread.currentThread()
                      .interrupt();
                return false;
            }

            if (lock(lockPath)) {
                log.debug("{} waited {} ms for lock path {}", client, (System.currentTimeMillis() - waitStart),
                    lockPath);
                return true;
            }
        }

        return false;
    }


    public String peekLock(String lockPath) {
        try {
            byte[] bytes = curator.getData()
                                  .forPath(lockPath);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (KeeperException.NoNodeException ex) {
            return null;
        } catch (Exception ex) {
            throw new IllegalStateException("Error while peeking at " + lockPath, ex);
        }
    }


    public boolean isLocked(String lockPath) {
        return peekLock(lockPath) != null;
    }


    public boolean isLockedByMe(String lockPath) {
        return client.equals(peekLock(lockPath));
    }


    public void unlock(String lockPath) {
        log.debug("{} trying to unlock {}", client, lockPath);

        String owner = peekLock(lockPath);
        if (owner == null) {
            throw new IllegalStateException(
                client + " cannot unlock path " + lockPath + " which is not locked currently");
        }
        if (client.equals(owner) == false) {
            throw new IllegalStateException(
                client + " cannot unlock path " + lockPath + " which is locked by " + owner);
        }

        try {
            curator.delete()
                   .guaranteed()
                   .deletingChildrenIfNeeded()
                   .forPath(lockPath);

            log.info("{} released lock at {}", client, lockPath);

        } catch (Exception ex) {
            throw new IllegalStateException("Error while " + client + " trying to unlock " + lockPath, ex);
        }
    }


    public void purgeLocks(String lockPathRoot) {
        try {
            curator.delete()
                   .guaranteed()
                   .deletingChildrenIfNeeded()
                   .forPath(lockPathRoot);

            log.info("{} purged all locks under {}", client, lockPathRoot);

        } catch (Exception ex) {
            throw new IllegalStateException("Error while " + client + " trying to purge " + lockPathRoot, ex);
        }
    }


    public Closeable watchLocks(String lockPathRoot, Executor executor, final Watcher watcher) {
        PathChildrenCache cache = new PathChildrenCache(curator, lockPathRoot, true);
        try {
            cache.start();
            cache.getListenable()
                 .addListener((client, event) -> {
                     switch (event.getType()) {
                         case CHILD_ADDED:
                             watcher.onLock(event.getData()
                                                 .getPath(), new String(event.getData()
                                                                             .getData(), StandardCharsets.UTF_8));
                             break;
                         case CHILD_REMOVED:
                             watcher.onUnlock(event.getData()
                                                   .getPath(), new String(event.getData()
                                                                               .getData(), StandardCharsets.UTF_8));
                             break;
                         default:
                             break;
                     }
                 }, executor);
        } catch (Exception ex) {
            log.error("Error to watch lock path " + lockPathRoot, ex);
        }
        return cache;
    }
}
