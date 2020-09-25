package com.bankingapp;

import com.bankingapp.model.KafkaTopicMessage;
import com.bankingapp.utils.MessageIntake;
import com.bankingapp.utils.SimulationDataDriver;

public class Startup {

    public static void main (String[] args){

        KafkaTopicMessage testMessage = new KafkaTopicMessage();
        testMessage.setTopic("test-topic");
        testMessage.setTopic("test-message");
        SimulationDataDriver.generateTestCustomers(30);
        MessageIntake.route(testMessage);
    }

}
