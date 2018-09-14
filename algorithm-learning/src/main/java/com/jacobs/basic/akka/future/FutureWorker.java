package com.jacobs.basic.akka.future;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lichao on 2016/12/3.
 */
public class FutureWorker extends UntypedActor {

  private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  public static enum Msg {
    WORKING,
    DONE,
    CLOSE;
  }

  @Override
  public void onReceive(Object msg) throws Throwable {
    if (msg instanceof Integer) {
      int i = (Integer) msg;
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {

      }
      getSender().tell(i * i, getSelf());
    }
    if (msg == Msg.DONE) {
      log.info("stop working");
    }
    if (msg == Msg.CLOSE) {
      log.info("I will shutdown");
      getSender().tell(Msg.CLOSE, getSelf());
      getContext().stop(getSelf());
    } else {
      unhandled(msg);
    }
  }
}
