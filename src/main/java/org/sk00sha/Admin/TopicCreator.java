package org.sk00sha.Admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;


import java.util.*;
import java.util.concurrent.ExecutionException;

/***
 * Class used to create topics
 * Manages if topic is already created
 */
public class TopicCreator {
    private static Properties props;
    public static void setAdminProperties(Properties properties){
        props=properties;
    }

    /**
     *
     * @param topicName - specify name of topic to be created
     */
    public static void createTopic(String topicName){
        var admin=AdminClient.create(props);
        admin.createTopics(List.of(new NewTopic(topicName,1, (short) 1)));
    }
    public static boolean checkTopicExists(String topicName) throws ExecutionException, InterruptedException {
        if(!listTopics().contains(topicName)){
            return true;
        }
        return false;
    }
    private static Set<String> listTopics() throws ExecutionException, InterruptedException {
        var admin=AdminClient.create(props);
        return admin.listTopics().names().get();
     }
}
