package com.jacobs.basic.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by lichao on 2016/12/1.
 */
public class HelloWorld extends UntypedActor {
  ActorRef greeter;

  @Override
  public void preStart() throws Exception {
    greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
    System.out.println("Greeter Actor Path:" + greeter.path());
    greeter.tell(Greeter.Msg.GREET, getSelf());
  }

  @Override
  public void onReceive(Object o) throws Throwable {
    if (o == Greeter.Msg.DONE) {
      greeter.tell(Greeter.Msg.GREET, getSelf());
      getContext().stop(getSelf());
    } else {
      unhandled(o);
    }
  }
}
