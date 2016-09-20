This is currently a brian-dump of steps required to get this project going, roughly in the order they need to be done.
It will need some cleanup, don't mind the mess :-)

`java -jar /mnt/sd/avro-tools-1.8.1.jar getschema /mnt/sd/2011-000000000000.avro  > gha_schema.avsc`

Add `namespace: "com.drfloob.insight.pulse.firehose"` to schema file, and add this prefix to all 
namespaces in the schema.

`java -jar /mnt/sd/avro-tools-1.8.1.jar compile schema gha_schema.avsc src/`

Create the file `./resources/awsCredentials.properties` to resemble:

```
accessKey=MY_AWS_ACCESS_KEY
secretKey=MY_AWS_SECRET_KEY
```

