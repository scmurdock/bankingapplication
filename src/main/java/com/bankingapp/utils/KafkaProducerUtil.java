package com.bankingapp.utils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class KafkaProducerUtil {

    private final static String BOOTSTRAP_SERVERS = Configuration.getConfiguration().getString("kafka.bootstrap.server")+":"+Configuration.getConfiguration().getString("kafka.bootstrap.port");

    public static Producer<Long, String>    createProducer(){

        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "BANKINGJAVAAPP");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        final Producer<Long, String> producer = new KafkaProducer<Long, String>(props);

        return producer;
    }
}
