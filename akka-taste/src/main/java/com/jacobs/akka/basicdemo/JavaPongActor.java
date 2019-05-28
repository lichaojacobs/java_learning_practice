package com.jacobs.akka.basicdemo;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.Future;

/**
 * @author lichao
 * Created on 2019-05-18
 */
public class JavaPongActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .matchEquals("Ping", s ->
                        sender().tell("Pong", ActorRef.noSender()))
                .matchAny(x ->
                        sender().tell(
                                new Status.Failure(new Exception("unknown message")),
                                self())
                ).build();
    }

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create();
        ActorRef actorRef =
                system.actorOf(Props.create(JavaPongActor.class));
        //通过返回future来获取信息
        Future sFuture = ask(actorRef, "Ping", 1000);
        //将 Scala Future转为CompletableFuture
        final CompletionStage<String> cs = toJava(sFuture);
        final CompletableFuture<String> jFuture = (CompletableFuture<String>) cs;
        System.out.println(jFuture.get(1000, TimeUnit.MILLISECONDS));

        //打印到控制台
        askPong(actorRef, "Ping")
                .thenAccept(x -> System.out.println("replied with: " + x));

        //compose 将结果扁平化，使得结果只在一个 Future 中
        askPong(actorRef, "Ping").thenCompose(x ->
                askPong(actorRef, "Ping"));

        //组合
        askPong(actorRef, "Ping")
                .thenCombine(askPong(actorRef, "Ping"), (a, b) -> {
                    System.out.println(a + b);
                    return a + b; //"PongPong"
                });

        //处理失败情况: handle 中的函数在成功情况下会提供结果，在失败情况下则会提供 Throwable
        askPong(actorRef, "causeError").handle((x, t) -> {
            if (t != null) {
                System.out.println("Error: " + t);
            }
            //失败情况下不对返回值处理的话，返回null
            return null;
        });

        //从失败中恢复
        askPong(actorRef, "cause error")
                .exceptionally(t -> {
                    System.out.println("default");
                    return "default";
                });

        askPong(actorRef, "cause error")
                .handle((pong, ex) -> ex == null
                        ? CompletableFuture.completedFuture(pong)
                        : askPong(actorRef, "Ping")
                ).thenCompose(x -> x);

        Thread.sleep(1000000);
    }

    public static Object get(CompletionStage cs) throws Exception {
        return ((CompletableFuture<String>) cs).get(1000, TimeUnit.MILLISECONDS);
    }

    public static CompletionStage<String> askPong(ActorRef actorRef, String message) {
        Future sFuture = ask(actorRef, message, 1000);
        final CompletionStage<String> cs = toJava(sFuture);
        return cs;
    }
}
