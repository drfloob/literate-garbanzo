Beginning with the [Github Archive on Google's BigQuery](https://bigquery.cloud.google.com/table/githubarchive:year.2015), I exported all years to a google cloud storage bucket in Avro format, split into multiple ~1GB files.

I then spun up a cloud compute instance to sync cloud storage to S3, where I have the resources I need to run my cluster.

`gsutil rsync -d -r gs://githubarchive s3://githubarchive.dump`