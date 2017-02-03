package com.example.akka.future;

import com.example.akka.MyWorker;
import com.example.akka.WatchActor;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.Future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.*;

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
