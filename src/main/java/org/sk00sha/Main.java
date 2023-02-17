package org.sk00sha;

import org.sk00sha.Admin.TopicCreator;
import org.sk00sha.Configuration.KafkaConfig;
import org.sk00sha.Consumers.MatchDataConsumer;
import org.sk00sha.Producers.MatchProducer;
import org.sk00sha.s3FS.BucketController;

import java.io.IOException;


public class Main {
    public static void main(String[] args)  {
        /**
         * TODO - setup multithreaded implementation of consumer and producer
         */
        try{
            final String TOPIC="quickstart";
            final String bucketName="match-result-test-bucket";

            KafkaConfig config=new KafkaConfig("localhost:9092");
            TopicCreator.setAdminProperties(config.getClientProps());

        new Thread(() -> {
            MatchProducer producerForMatches=new MatchProducer(config.getProducerProps(),"src/main/resources/sourceFiles/results.csv");
            try {
                producerForMatches.produceMessages(TOPIC);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(()->{
            MatchDataConsumer consumer=new MatchDataConsumer(config.getConsumerProps());
            try {
                consumer.consumeDataFromTopic(TOPIC,bucketName);
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        }
        catch (Exception e){
            System.out.println(e);
        }


    }



}