package org.sk00sha.Producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.sk00sha.Pojos.GoalScorer;
import org.sk00sha.Pojos.MatchResult;
import org.sk00sha.Util.BatchConvertor;

import java.util.List;
import java.util.Properties;

public class ScorerProducer extends BatchConvertor {
    String fileSource="";
    Properties kafkaProdProps;

    {
        new Properties();
    }
    public ScorerProducer(Properties props, String filename){
        kafkaProdProps=props;
        fileSource=filename;
    }
    public void produceMessages(String topicName) throws InterruptedException {

        KafkaProducer<String, String> producer = new KafkaProducer<>(this.kafkaProdProps);

        List<GoalScorer> goalsScored=transformBatchGoalsToStream(this.fileSource);

        for (GoalScorer goalScorer : goalsScored) {
            ProducerRecord<String,String> goalRecord=new ProducerRecord<>(topicName,0,"goal",goalScorer.toString());

            System.out.println("Published message: key=" + goalRecord.key() +
                    ", value=" + goalRecord.value() +
                    ", topic=" + goalRecord.topic() +
                    ", partition=" + goalRecord.partition());
            try{
                producer.send(goalRecord);
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
