package org.sk00sha.Configuration;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public class KafkaConfig {
    private final Properties clientProps=new Properties();
    private final Properties producerProps=new Properties();;
    private final Properties consumerProps=new Properties();;

    public KafkaConfig(String bootstrapServer){
        setClientConfig(bootstrapServer);
        setProducerConfig(bootstrapServer);
        setConsumerConfig(bootstrapServer);
    }


    private void setClientConfig(String bootstrapServer){
        clientProps.put(AdminClientConfig.CLIENT_ID_CONFIG,"AdminClient");
        clientProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
    }
    private void setProducerConfig(String bootstrapServer){
        producerProps.put("bootstrap.servers", bootstrapServer);
        producerProps.put(ProducerConfig.CLIENT_ID_CONFIG,"ProducerClient1");
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("acks", "all");

    }
    private void setConsumerConfig(String bootstrapServer){
        producerProps.put("bootstrap.servers", bootstrapServer);
        producerProps.put(ConsumerConfig.CLIENT_ID_CONFIG,"MyConsumerClient1");
        producerProps.put(ConsumerConfig.GROUP_ID_CONFIG,"GroupConsumer1");
        producerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        producerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");



    }

    public Properties getClientProps() {
        return clientProps;
    }

    public Properties getProducerProps() {
        return producerProps;
    }

    public Properties getConsumerProps() {
        return consumerProps;
    }
}
