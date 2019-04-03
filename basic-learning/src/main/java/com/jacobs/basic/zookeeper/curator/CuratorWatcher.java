package com.jacobs.basic.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * @author lichao
 * @date 2019/03/26
 */
public class CuratorWatcher {


    public static void main(String[] args) throws Exception {
        CuratorFramework cf = CuratorBase.getInstance();
        //3 建立连接
        cf.start();

        //4 建立一个PathChildrenCache缓存,第三个参数为是否接受节点数据内容 如果为false则不接受
        PathChildrenCache cache = new PathChildrenCache(cf, "/super", true);
        //5 在初始化的时候就进行缓存监听
        cache.start(StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable()
             .addListener(new PathChildrenCacheListener() {
                 /**
                  * <B>方法名称：</B>监听子节点变更<BR>
                  * <B>概要说明：</B>新建、修改、删除<BR>
                  * @see org.apache.curator.framework.recipes.cache.PathChildrenCacheListener#childEvent(org.apache.curator.framework.CuratorFramework, org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent)
                  */
                 @Override
                 public void childEvent(CuratorFramework cf, PathChildrenCacheEvent event) throws Exception {
                     switch (event.getType()) {
                         case CHILD_ADDED:
                             System.out.println("CHILD_ADDED :" + event.getData()
                                                                       .getPath());
                             break;
                         case CHILD_UPDATED:
                             System.out.println("CHILD_UPDATED :" + event.getData()
                                                                         .getPath());
                             break;
                         case CHILD_REMOVED:
                             System.out.println("CHILD_REMOVED :" + event.getData()
                                                                         .getPath());
                             break;
                         default:
                             break;
                     }
                 }
             });

        //创建本身节点不发生变化
        cf.create()
          .forPath("/super", "init".getBytes());

        //添加子节点
        Thread.sleep(1000);
        cf.create()
          .forPath("/super/c1", "c1内容".getBytes());
        Thread.sleep(1000);
        cf.create()
          .forPath("/super/c2", "c2内容".getBytes());

        //修改子节点
        Thread.sleep(1000);
        cf.setData()
          .forPath("/super/c1", "c1更新内容".getBytes());

        //删除子节点
        Thread.sleep(1000);
        cf.delete()
          .forPath("/super/c2");

        //删除本身节点
        Thread.sleep(1000);
        cf.delete()
          .deletingChildrenIfNeeded()
          .forPath("/super");

        Thread.sleep(Integer.MAX_VALUE);
    }
}
