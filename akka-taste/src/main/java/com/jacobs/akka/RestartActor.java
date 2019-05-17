package com.jacobs.akka;

import akka.actor.UntypedActor;
import scala.Option;

/**
 * Created by lichao on 2016/12/2.
 */
public class RestartActor extends UntypedActor {

    public enum Msg {
        DONE, RESTART
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        System.out.println("prestart");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("postStop");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        super.postRestart(reason);
        System.out.println("post restart");
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("pre restart");
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o == Msg.DONE) {
            getContext().stop(getSelf());
        } else if (o == Msg.RESTART) {
            System.out.println(((Object) null).toString());
            //抛出异常，默认会被restart，但这里会resume
            double a = 0 / 0;
        }
        unhandled(o);
    }
}
