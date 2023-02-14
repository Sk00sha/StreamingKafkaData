# StreamingKafkaData
This project simulates streaming data by ingesting batch csv data from football matches and goalscorers.
After consuming our data, we send the data to AWS s3 bucket.
Finally, we create a crawler and then analyse the data in AWS athena.


## Steps taken to develop this mini project
1. Get the dataset from Kaggle.
2. Create convertors to prepare data for streaming.
3. Create Kafka Producers to produce data to topics.
4. Consume the data and send it to s3 buckets.
5. Create crawler in AWS glue.
6. Setup AWS Athena for data analysis.