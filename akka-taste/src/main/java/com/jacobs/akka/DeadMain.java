package com.jacobs.akka;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by lichao on 2016/12/1.
 */
public class DeadMain {
  public static void main(String[] args) {
    //    ActorSystem actorSystem = ActorSystem.create("deadwatch",
    //        ConfigFactory.load("samplehello.conf"));
    //    ActorRef worker = actorSystem.actorOf(Props.create(MyWorker.class), "worker");
    //    actorSystem.actorOf(Props.create(WatchActor.class, worker), "watcher");
    //    worker.tell(MyWorker.Msg.WORKING, ActorRef.noSender());
    //    worker.tell(MyWorker.Msg.DONE, ActorRef.noSender());
    //    worker.tell(PoisonPill.getInstance(), ActorRef.noSender());
    ActorSystem actorSystem = ActorSystem.create("lifecycle",
        ConfigFactory.load("samplehello.conf"));
    customStrategy(actorSystem);
  }

  public static void customStrategy(ActorSystem system) {
    ActorRef a = system.actorOf(Props.create(Supervisor.class), "Supervisor");
    System.out.println("a path is:" + a.path());
    //a.tell(Props.create(RestartActor.class), ActorRef.noSender());
    ActorRef restart = system.actorOf(Props.create(RestartActor.class), "restartActor");
    System.out.println("restart path is: " + restart.path());

    //ActorSelection sel = system.actorSelection("akka://lifecycle/user/Supervisor/restartActor");
    for (int i = 0; i < 100; i++) {
      restart.tell(RestartActor.Msg.RESTART, ActorRef.noSender());
    }
  }
}
