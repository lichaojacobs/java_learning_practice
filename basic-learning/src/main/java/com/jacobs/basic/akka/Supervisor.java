package com.jacobs.basic.akka;

import java.util.concurrent.TimeUnit;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;

/**
 * Created by lichao on 2016/12/2.
 */
public class Supervisor extends UntypedActor {

  //一分钟内进行三次尝试
  private static SupervisorStrategy strategy = new OneForOneStrategy(3,
      Duration.create(1, TimeUnit.MINUTES),
      throwable -> {
        if (throwable instanceof ArithmeticException) {
          System.out.println("meet ArithmeticException, just resume");
          return SupervisorStrategy.resume();
        } else if (throwable instanceof NullPointerException) {
          System.out.println("meet NullPointerException ,restart");
          return SupervisorStrategy.restart();
        } else if (throwable instanceof IllegalArgumentException) {
          return SupervisorStrategy.stop();
        } else {
          return SupervisorStrategy.escalate();
        }
      }
  );

  @Override
  public SupervisorStrategy supervisorStrategy() {
    //自定义监督策略
    return strategy;
  }

  @Override
  public void onReceive(Object o) throws Throwable {
    if (o instanceof Props) {
      getContext().actorOf((Props) o, "restartActor");
    } else {
      unhandled(o);
    }
  }
}
