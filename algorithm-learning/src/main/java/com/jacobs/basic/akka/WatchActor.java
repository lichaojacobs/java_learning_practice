package com.jacobs.basic.akka;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lichao on 2016/12/1.
 */
public class WatchActor extends UntypedActor {
  private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  public WatchActor(ActorRef actorRef) {
    getContext().watch(actorRef);
  }

  @Override
  public void onReceive(Object msg) throws Throwable {
    if (msg instanceof Terminated) {
      System.out.println(
          String.format("%s has terminated, shutting down system", ((Terminated) msg).getActor()
              .path()));
      getContext().system()
          .shutdown();
    } else {
      unhandled(msg);
    }
  }
}
