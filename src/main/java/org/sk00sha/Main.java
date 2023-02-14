package org.sk00sha;

import org.sk00sha.Admin.TopicCreator;
import org.sk00sha.Configuration.KafkaConfig;
import org.sk00sha.Consumers.MatchDataConsumer;
import org.sk00sha.Producers.MatchProducer;
import org.sk00sha.s3FS.BucketController;


public class Main {
    public static void main(String[] args)  {
        try{
            final String TOPIC="quickstart";
            final String bucketName="match-result-test-bucket";

            KafkaConfig config=new KafkaConfig("localhost:9092");
            TopicCreator.setAdminProperties(config.getClientProps());

            MatchProducer producerForMatches=new MatchProducer(config.getProducerProps(),"src/main/resources/sourceFiles/results.csv");
            producerForMatches.produceMessages(TOPIC);

            MatchDataConsumer consumer=new MatchDataConsumer(config.getConsumerProps());
            consumer.consumeDataFromTopic(TOPIC,bucketName);
        }
        catch (Exception e){
            System.out.println(e);
        }


    }



}