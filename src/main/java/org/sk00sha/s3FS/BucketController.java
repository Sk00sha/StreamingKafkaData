package org.sk00sha.s3FS;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import java.io.IOException;
import java.util.List;


public class BucketController {
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

    public  void checkAvailabilityOfBucketName(){
        String bucketName = "tramtararaaa";

        List<Bucket> buckets = configureClient().listBuckets();
        for(Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }
}
