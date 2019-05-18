package com.jacobs.akka.basicdemo.router;

import com.jacobs.akka.basicdemo.MyWorker;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

/**
 * Created by lichao on 2016/12/2.
 */
public class WatchRouteActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Router router;

    {
        List<Routee> routes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ActorRef worker = getContext().actorOf(Props.create(MyWorker.class), "worker" + i);
            getContext().watch(worker);
            routes.add(new ActorRefRoutee(worker));
        }
        //选择路由策略
        router = new Router(new RoundRobinRoutingLogic(), routes);
    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if (msg instanceof MyWorker.Msg) {
            router.route(msg, getSender());
        } else if (msg instanceof Terminated) {
            router = router.removeRoutee(((Terminated) msg).actor());
            System.out.println(((Terminated) msg).actor().path() + " is closed, routees="
                    + router.routees().size());
            if (router.routees().size() == 0) {
                System.out.println("Close system");
                RouteMain.flag.send(false);
                getContext().system().terminate();
            }

        } else {
            unhandled(msg);
        }
    }
}
