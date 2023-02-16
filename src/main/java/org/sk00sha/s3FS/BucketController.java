package org.sk00sha.s3FS;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.io.File;
import java.io.IOException;


/**
 * Controller for data upload to S3 when data is consumed from topic
 */
public class BucketController {
    private final AmazonS3 client;
    {
        this.client=configureClient();
    }
    public BucketController() throws IOException {

    }


    private AWSCredentials defineCreds(){
        AWSCredentials credentials = new BasicAWSCredentials(
                System.getenv("AWS_ACCESS_KEY_ID"),
                System.getenv("AWS_SECRET")
        );
        return credentials;
    }

    private AmazonS3 configureClient(){
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(defineCreds()))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();

    return s3client;
    }

    private  boolean checkAvailabilityOfBucketName(String bucketName){
        if(this.client.doesBucketExist(bucketName)) {
            return true;
        }
        return false;
    }
    public void uploadToBucket(String bucketName,String fileName){
        if(checkAvailabilityOfBucketName(bucketName)){
            this.client.putObject(
                    bucketName,
                    fileName,
                    new File(fileName)
            );
        }
        else{
            System.out.println("File or "+fileName+" this bucket "+bucketName+" doesn't exist");
        }
    }
}
