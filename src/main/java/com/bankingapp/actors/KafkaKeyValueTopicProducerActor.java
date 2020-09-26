package com.bankingapp.actors;

import akka.actor.UntypedActor;
import com.bankingapp.model.KafkaTopicMessage;
import com.bankingapp.utils.KafkaProducerUtil;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Optional;
import java.util.logging.Logger;

public class KafkaKeyValueTopicProducerActor extends UntypedActor {
    private static Logger logger = Logger.getLogger(KafkaKeyValueTopicProducerActor.class.getName());
    private Optional<Producer<Long,String>> kafkaProducer = Optional.empty();


    public void onReceive(Object object){

        if (object instanceof KafkaTopicMessage) {
            KafkaTopicMessage kafkaTopicMessage = (KafkaTopicMessage) object;
            if (!kafkaProducer.isPresent()){
                logger.info("Creating Kafka Producer");
                kafkaProducer = Optional.of(KafkaProducerUtil.createProducer());
            }
            logger.info("Sending to Kafka Topic: "+kafkaTopicMessage.getTopic()+" message: "+kafkaTopicMessage.getMessage());

            ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(kafkaTopicMessage.getTopic(), kafkaTopicMessage.getKey(),kafkaTopicMessage.getMessage());
            kafkaProducer.get().send(record);
            kafkaProducer.get().flush();
        }

    }

}
