package com.bankingapp.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import com.bankingapp.model.ContinueBalanceSimulation;
import com.bankingapp.model.KafkaTopicMessage;
import java.util.logging.Logger;

/**
 * Created by sean on 8/16/2016.
 */
public class MessageRouteByType extends UntypedActor {

    private static Logger logger = Logger.getLogger(MessageRouteByType.class.getName());
    private ActorRef kafkaTopicProducerActor = getContext().actorOf(new RoundRobinPool(1).props(Props.create(KafkaKeyValueTopicProducerActor.class)));//a pool of one means that only one actor is running at any moment and if it crashes, the actor restarts
    private ActorRef balanceSimulationActor = getContext().actorOf(new RoundRobinPool(1).props(Props.create(BalanceSimulationActor.class)));//a pool of one means that only one actor is running at any moment and if it crashes, the actor restarts


    public void onReceive(Object object){

        if (object instanceof KafkaTopicMessage){
            kafkaTopicProducerActor.tell(object, self());
        }
        else if (object instanceof ContinueBalanceSimulation){
            balanceSimulationActor.tell(object, self());
        }
        else{
                logger.info("Unable to route Message Type: "+object.getClass().getName());
        }
    }
}
