package com.bankingapp;

import com.bankingapp.model.ContinueBalanceSimulation;
import com.bankingapp.utils.MessageIntake;
import com.bankingapp.utils.BankingSimulationDataDriver;

public class Startup {

    public static void main (String[] args){
        BankingSimulationDataDriver.generateTestCustomers(30);
        MessageIntake.route(new ContinueBalanceSimulation());//send the signal to start balance messages at "balance-topic" topic
    }

}
