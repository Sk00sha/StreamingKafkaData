package org.sk00sha.Consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class MatchDataConsumer {
    private Properties producerProps=new Properties();

    public MatchDataConsumer(Properties consumerProperties){
        //this.props=consumerProperties;
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put(ConsumerConfig.CLIENT_ID_CONFIG,"MyConsumerClient1");
        producerProps.put(ConsumerConfig.GROUP_ID_CONFIG,"GroupConsumer1");
        producerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        producerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    public void ConsumeDataFromTopic(String topicName) throws InterruptedException, IOException {
        KafkaConsumer<String,String> matchDataConsumer= new KafkaConsumer<>(producerProps);

        matchDataConsumer.subscribe(Arrays.asList(topicName));
        File mynewFile=new File("myJson.json");
        FileWriter fw=new FileWriter(mynewFile);
        BufferedWriter writer=new BufferedWriter(fw);
        while(true){
            ConsumerRecords<String, String> consumerRecord=matchDataConsumer.poll(Duration.ofMillis(1000));

            for(ConsumerRecord<String, String> record:consumerRecord){
                System.out.println("Key-> "+record.key()+" Value-> "+record.value()+" {TOPIC_NAME-> "+record.topic()+" }");
                writer.write(record.value()+"\n");
                Thread.sleep(1000);
            }
            matchDataConsumer.commitSync();
        }
    }

}
