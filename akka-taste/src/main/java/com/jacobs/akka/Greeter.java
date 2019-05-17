package com.jacobs.akka;

import akka.actor.UntypedActor;

/**
 * Created by lichao on 2016/12/1.
 */
public class Greeter extends UntypedActor {

    public static enum Msg {
        GREET, DONE
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o == Msg.GREET) {
            System.out.println("hello world");
            getSender().tell(Msg.DONE, getSelf());
        } else {
            unhandled(o);
        }
    }
}
