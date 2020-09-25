package com.bankingapp.actors;

import akka.actor.UntypedActor;
import com.bankingapp.model.KafkaTopicMessage;
import com.bankingapp.utils.KafkaProducerUtil;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Optional;
import java.util.logging.Logger;

public class KafkaTopicProducerActor extends UntypedActor {
    private static Logger logger = Logger.getLogger(KafkaTopicProducerActor.class.getName());
    private Optional<Producer<Long,String>> kafkaProducer = Optional.empty();

    public void onReceive(Object object){

        if (object instanceof KafkaTopicMessage) {
            KafkaTopicMessage kafkaTopicMessage = (KafkaTopicMessage) object;
            if (!kafkaProducer.isPresent()){
                logger.info("Creating Kafka Producer");
                kafkaProducer = Optional.of(KafkaProducerUtil.createProducer());
            }
            logger.info("Sending to Kafka Topic: "+kafkaTopicMessage.getTopic());

            ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(kafkaTopicMessage.getTopic(), System.currentTimeMillis(),kafkaTopicMessage.getMessage());
            kafkaProducer.get().send(record);
        }

    }

}
