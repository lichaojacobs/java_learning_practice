package com.jacobs.akka.test;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jacobs.akka.memorydb.MemDbServer;
import com.jacobs.akka.memorydb.SetRequest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import akka.testkit.TestActorRef;

/**
 * @author lichao
 * Created on 2019-05-18
 */
public class MemDbServerTest {
    ActorSystem system = ActorSystem.create();

    @Test
    public void testMemoryDb() {
        //使用TestActorRef，是同步处理消息的，只有在tell调用请求处理完后才会执行后面的代码
        TestActorRef<MemDbServer> actorRef =
                TestActorRef.create(system, Props.create(MemDbServer.class));
        actorRef.tell(SetRequest.builder()
                        .key("testKey")
                        .value("testvalue")
                        .build(),
                //定义该消息不需要任何响应对象
                ActorRef.noSender());
        MemDbServer memDbServer = actorRef.underlyingActor();
        assertEquals(memDbServer.getStore().get("testKey"), "testvalue");
    }

    @Test
    public void testLoadNullAfterInvalidate() {
        LoadingCache<String, Optional<String>> stringCache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .build(new CacheLoader<String, Optional<String>>() {
                    int i = 0;

                    @Override
                    public Optional<String> load(String s) throws Exception {
                        if (i == 0) {
                            i++;
                            return Optional.of("world");
                        }
                        return Optional.empty();
                    }
                });

        try {
            System.out.println(stringCache.get("hello"));
            System.out.println(stringCache.get("hello"));

            // invalidate不会触发load
            stringCache.invalidate("hello");

            // invalidate后，再次get，触发load，抛出异常：
            // com.google.common.cache.CacheLoader$InvalidCacheLoadException: CacheLoader returned null for key hello.
            System.out.println(stringCache.get("hello"));
            stringCache.invalidate("hello");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
