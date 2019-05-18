package com.jacobs.akka.basicdemo.future;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lichao on 2016/12/3.
 */
public class Printer extends UntypedActor {
  private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  @Override
  public void onReceive(Object msg) throws Throwable {
    if (msg instanceof Integer) {
            System.out.println("Printer:" + msg);
        }
    if (msg == FutureWorker.Msg.DONE) {
      log.info("stop working");
    }
    if (msg == FutureWorker.Msg.CLOSE) {
      log.info("I will shutdown");
      getSender().tell(FutureWorker.Msg.CLOSE, getSelf());
      getContext().stop(getSelf());
    } else {
      unhandled(msg);
    }
  }
}
