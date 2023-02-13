package org.sk00sha.Producers;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.sk00sha.Pojos.MatchResult;
import org.sk00sha.Util.BatchConvertor;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.List;


public class MatchProducer extends BatchConvertor {

    private Properties kafkaProdProps;

    {
        new Properties();
    }

    private String fileSource="";


    public MatchProducer(Properties props,String filename){
        kafkaProdProps=props;
        fileSource=filename;
    }

    public void produceMessages(String topicName) throws InterruptedException {

        KafkaProducer<String, String> producer = new KafkaProducer<>(this.kafkaProdProps);

        List<MatchResult> matchResults=transformBatchDataMatchesToIndividualMatchResults(this.fileSource);

        for (MatchResult matchResult : matchResults) {
            ProducerRecord<String,String> matchRecord=new ProducerRecord<>(topicName,0,"match",matchResult.toString());

            System.out.println("Published message: key=" + matchRecord.key() +
                    ", value=" + matchRecord.value() +
                    ", topic=" + matchRecord.topic() +
                    ", partition=" + matchRecord.partition());
            try{
                producer.send(matchRecord);
            }
            catch(RuntimeException e){
                System.out.println("We caught and exception while producing match data Exception text:{ "+e.getMessage()+" }");
            }
            //Every second we produce message to topic
            Thread.sleep(1000);
        }
        producer.close();
    }

}
