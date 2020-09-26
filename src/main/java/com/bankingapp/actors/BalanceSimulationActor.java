package com.bankingapp.actors;

import akka.actor.UntypedActor;
import com.bankingapp.model.ContinueBalanceSimulation;
import com.bankingapp.utils.BankingSimulationDataDriver;


import java.util.logging.Logger;

/**
 * Created by sean on 8/16/2016.
 */
public class BalanceSimulationActor extends UntypedActor {
    private static Logger logger = Logger.getLogger(BalanceSimulationActor.class.getName());
    private static boolean stop = false;

    public void onReceive(Object object){

        if (object instanceof ContinueBalanceSimulation){
            try {
                BankingSimulationDataDriver.createBalanceUpdates();
                self().tell(new ContinueBalanceSimulation(), self());//continue simulation
            } catch (Exception e){
                logger.severe(e.getMessage());
            }
        }

    }
}
