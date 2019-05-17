package com.jacobs.akka;

import com.typesafe.config.ConfigFactory;

import java.util.stream.LongStream;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by lichao on 2016/12/1.
 */
public class HelloMainSimple {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("Hello", ConfigFactory.load("simplehello.conf"));
        //ActorRef a = system.actorOf(Props.create(HelloWorld.class), "helloWorld");
        ActorRef a = system.actorOf(Props.create(HelloWorld.class), "helloWorld");
        LongStream.range(0, 100).forEach(index -> {
            a.tell(Greeter.Msg.GREET, ActorRef.noSender());
            //System.out.println("HelloWorld Actor Path:" + a.path());
        });
    }
}
