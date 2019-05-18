package com.jacobs.akka.basicdemo;

import com.typesafe.config.ConfigFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.actor.Terminated;
import scala.concurrent.duration.Duration;

/**
 * Created by lichao on 2016/12/2.
 */
public class InboxMain {

    public static void main(String[] args) throws TimeoutException {
        ActorSystem system = ActorSystem.create("inboxdemo",
                ConfigFactory.load("samplehello.conf"));
        ActorRef worker = system.actorOf(Props.create(MyWorker.class), "worker");
        ActorRef helloworld = system.actorOf(Props.create(HelloWorld.class), "hello");

        final Inbox inbox = Inbox.create(system);
        inbox.watch(worker);
        inbox.send(worker, MyWorker.Msg.WORKING);
        inbox.send(worker, MyWorker.Msg.DONE);
        inbox.send(worker, MyWorker.Msg.CLOSE);

        while (true) {
            Object msg = inbox.receive(Duration.create(1, TimeUnit.SECONDS));
            if (msg == MyWorker.Msg.CLOSE) {
                System.out.println("My worker is Closing");
            } else if (msg instanceof Terminated) {
                System.out.println("My worker is dead");
                system.terminate();
                break;
            } else {
                System.out.println("received:" + msg);
            }
        }
    }
}
