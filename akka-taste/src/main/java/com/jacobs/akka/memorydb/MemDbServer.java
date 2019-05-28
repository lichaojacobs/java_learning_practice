package com.jacobs.akka.memorydb;

import java.util.Map;

import com.google.common.collect.Maps;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import lombok.Getter;

/**
 * @author lichao
 * Created on 2019-05-18
 */
public class MemDbServer extends AbstractActor {

    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    //简单的内存k-v存储
    @Getter
    private final Map<String, Object> store = Maps.newHashMap();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SetRequest.class,
                        message -> {
                            log.info("Received Set request: {}", message);
                            store.put(message.getKey(), message.getValue());
                        })
                .match(GetRequest.class, message -> {
                    log.info("Received Get request: {}", message);
                    String value = (String) store.get(message.key);
                    Object response = (value != null)
                            ? value
                            : new Status.Failure(new KeyNotFoundException(message.key));
                    sender().tell(response, self());
                })
                .matchAny(o -> sender().tell(new Status.Failure(new ClassNotFoundException()), self()))
                .build();
    }
}
