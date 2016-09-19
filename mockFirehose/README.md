`java -jar /mnt/sd/avro-tools-1.8.1.jar getschema /mnt/sd/2011-000000000000.avro  > gha_schema.avsc`

Add `namespace: "com.drfloob.insight.pulse.firehose"` to schema file, and add this prefix to all 
namespaces in the schema.

`java -jar /mnt/sd/avro-tools-1.8.1.jar compile schema gha_schema.avsc src/`

