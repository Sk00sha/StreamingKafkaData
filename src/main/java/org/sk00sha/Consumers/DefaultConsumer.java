package org.sk00sha.Consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.sk00sha.s3FS.BucketController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

abstract public class DefaultConsumer {

    abstract public String getFileProps();

    abstract public Properties getConsumerProps();
    public void consumeDataFromTopic(String topicName,String bucketToUpload) throws InterruptedException, IOException {
        KafkaConsumer<String,String> matchDataConsumer= new KafkaConsumer<>(getConsumerProps());

        matchDataConsumer.subscribe(Collections.singletonList(topicName));

        BucketController bucketController=new BucketController();

        while(true){
            ConsumerRecords<String, String> consumerRecord=matchDataConsumer.poll(Duration.ofMillis(1000));

            for(ConsumerRecord<String, String> record:consumerRecord){
                Date dt=new Date();
                String fileName=dt.getTime()/1000 +getFileProps()+".json";
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
