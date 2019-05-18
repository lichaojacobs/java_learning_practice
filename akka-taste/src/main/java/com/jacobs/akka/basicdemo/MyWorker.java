package com.jacobs.akka.basicdemo;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

/**
 * Created by lichao on 2016/12/1.
 */
public class MyWorker extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public enum Msg {
        WORKING, DONE, CLOSE
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        System.out.println("MyWorker is starting");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("My working is stopping");
    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if (msg == Msg.WORKING) {
            System.out.println("I am working");
        }
        if (msg == Msg.DONE) {
            System.out.println("Stop working");
        }
        if (msg == Msg.CLOSE) {
            System.out.println("I will shutdown");
            getSender().tell(Msg.CLOSE, getSelf());
        }

        unhandled(msg);
    }
}
