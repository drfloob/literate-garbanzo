Beginning with the [Github Archive on Google's BigQuery](https://bigquery.cloud.google.com/table/githubarchive:year.2015), I exported all years to a google cloud storage bucket in Avro format, export into multiple Avro files.

The 2015 table was too large for a single export job, so I split the table in two by adding a random value, saving the results to a temp table, and splitting on the random value into two new tables.

```sql
SELECT *, RAND() as rnd FROM [jovial-syntax-126918:githubarchive_copy.2015_copy] -- into 2015_tmp
SELECT * as rnd FROM [jovial-syntax-126918:githubarchive_copy.2015_tmp] WHERE rnd <= 0.5 -- into 2015_1
SELECT * as rnd FROM [jovial-syntax-126918:githubarchive_copy.2015_tmp] WHERE rnd > 0.5 -- into 2015_2
-- delete 2015_copy, 2015_tmp
```

I then spun up a cloud compute instance to sync cloud storage to S3, where I have the resources I need to run my cluster.

```bash
gsutil config -e
gsutil -m rsync -r gs://githubarchive s3://githubarchive-truth/
```

Note: you need to add your AWS credentials to ~/.boto in the Cloud Compute instance, and I also had to set a custom s3 host (Oregon) to fix a common ["Connection reset by peer" bug](https://github.com/GoogleCloudPlatform/gsutil/issues/311).