package com.jacobs.akka.memorydb;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

import java.util.concurrent.CompletionStage;

import akka.actor.ActorRef;

/**
 * @author lichao
 * Created on 2019-05-18
 */
public class MemDbClient {

    private final ActorRef serverRef;

    public MemDbClient(ActorRef actorRef) {
        this.serverRef = actorRef;
    }

    public CompletionStage set(String key, Object value) {
        return toJava(ask(serverRef, SetRequest.builder().key(key).value(value).build(),
                2000));
    }

    public CompletionStage<Object> get(String key) {
        return toJava(ask(serverRef, new GetRequest(key), 2000));
    }
}
