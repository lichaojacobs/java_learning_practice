package com.jacobs.akka.basicdemo.router;

import com.jacobs.akka.basicdemo.MyWorker;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.agent.Agent;
import akka.dispatch.ExecutionContexts;

/**
 * Created by lichao on 2016/12/2.
 */
public class RouteMain {
  public static Agent<Boolean> flag = Agent.create(true, ExecutionContexts.global());

  public static void main(String[] args) throws InterruptedException {
    ActorSystem system = ActorSystem.create("route", ConfigFactory.load("simplehello.conf"));
    ActorRef w = system.actorOf(Props.create(WatchRouteActor.class), "routeWatcher");
    int i = 0;
    while (flag.get()) {
      w.tell(MyWorker.Msg.WORKING, ActorRef.noSender());
      if (i % 10 == 0) {
        w.tell(MyWorker.Msg.CLOSE, ActorRef.noSender());
      }
      i++;
      Thread.sleep(100);
    }
  }

}
