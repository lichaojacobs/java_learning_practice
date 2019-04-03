package com.jacobs.lock;

/**
 * @author lichao
 * @date 2019/03/28
 */
public interface Watcher {

    void onLock(String lockPath, String client);

    void onUnlock(String lockPath, String client);
}
