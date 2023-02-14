package org.sk00sha.Consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.joda.time.DateTime;
import org.sk00sha.s3FS.BucketController;

import java.io.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

public class MatchDataConsumer extends DefaultConsumer{
    private Properties consumerProps=new Properties();
    private String fileNameProps="Matches";
    public MatchDataConsumer(Properties consumerProperties){
        //this.props=consumerProperties;
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG,"MyConsumerClient1");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG,"GroupConsumer1");
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
