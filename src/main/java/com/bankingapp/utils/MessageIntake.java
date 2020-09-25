package com.bankingapp.utils;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.bankingapp.actors.MessageRouteByType;

/**
 * Created by sean on 9/25/2020.
 */
public class MessageIntake {

    private static ActorSystem system = ActorSystem.create("bankingapp");
    private static ActorRef messageRouter = system.actorOf(new RoundRobinPool(1).props(Props.create(MessageRouteByType.class)));//a pool of one means that only one actor is running at any moment and if it crashes, the actor restarts

    public static synchronized String route(Object message){
        messageRouter.tell(message,ActorRef.noSender());
        return "Sent";
    }
}
