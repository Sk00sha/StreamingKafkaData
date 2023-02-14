package org.sk00sha.Consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

public class GoalsScoredConsumer extends DefaultConsumer{
    private String fileNameProps="Goals";
    private Properties consumerProps=new Properties();

    public GoalsScoredConsumer(){
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG,"MyConsumerClient2");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG,"GroupConsumer2");
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    @Override
    public String getFileProps() {
        return this.fileNameProps;
    }

    @Override
    public Properties getConsumerProps() {
        return this.consumerProps;
    }
}
