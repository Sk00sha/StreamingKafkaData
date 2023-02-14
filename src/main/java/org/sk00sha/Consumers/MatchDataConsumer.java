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

    public void ConsumeDataFromTopic(String topicName,String bucketToUpload) throws InterruptedException, IOException {
        KafkaConsumer<String,String> matchDataConsumer= new KafkaConsumer<>(producerProps);

        matchDataConsumer.subscribe(Collections.singletonList(topicName));

        BucketController bucketController=new BucketController();

        while(true){
            ConsumerRecords<String, String> consumerRecord=matchDataConsumer.poll(Duration.ofMillis(1000));

            for(ConsumerRecord<String, String> record:consumerRecord){
                Date dt=new Date();
                String fileName=dt.getTime()/1000 +".json";
                File myNewFile=new File(fileName);
                try(FileWriter fw=new FileWriter(myNewFile);
                    BufferedWriter writer=new BufferedWriter(fw);
                        )
                        {
                            writer.write(record.value());
                        }
                System.out.println("Key-> "+record.key()+" Value-> "+record.value()+" {TOPIC_NAME-> "+record.topic()+" }");
                bucketController.uploadToBucket(bucketToUpload,fileName);

                Thread.sleep(1000);
            }
            matchDataConsumer.commitSync();
        }
    }

}
