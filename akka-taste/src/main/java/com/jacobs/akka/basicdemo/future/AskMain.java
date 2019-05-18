package com.jacobs.akka.basicdemo.future;

import com.jacobs.akka.basicdemo.MyWorker;
import com.jacobs.akka.basicdemo.WatchActor;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by lichao on 2016/12/2.
 */
public class AskMain {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("askdemo", ConfigFactory.load("samplehello.conf"));
        ActorRef worker = system.actorOf(Props.create(MyWorker.class), "worker");
        ActorRef printer = system.actorOf(Props.create(Printer.class), "printer");
        system.actorOf(Props.create(WatchActor.class, worker), "watcher");
    }
}
