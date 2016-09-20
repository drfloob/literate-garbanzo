package com.drfloob.insight.pulse.firehose;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.drfloob.insight.pulse.firehose.schema.Root;
import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodec;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by aj on 9/20/16.
 */
public class S3DeserializeExperiment {
    private static final String BUCKET = "githubarchive-truth";
    private static final String KEY = "2011-000000000000.avro";

    public static void main(String[] args) throws IOException {
//        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(new File("resources/awsCredentials.properties")));
        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(S3DeserializeExperiment.class.getResourceAsStream("/awsCredentials.properties")));
        S3Object s3object = s3.getObject(new GetObjectRequest(BUCKET, KEY));
        System.out.println(s3object.getObjectMetadata().getContentType());
        System.out.println(s3object.getObjectMetadata().getContentLength());

        Schema.Parser parser = new Schema.Parser();
//        Schema rootSchema = parser.parse(new FileInputStream("gha_schema.avsc"));
        Schema rootSchema = parser.parse(S3DeserializeExperiment.class.getResourceAsStream("/gha_schema.avsc"));

        DatumReader<Root> rootDatumReader = new SpecificDatumReader<Root>(Root.class);
        DataFileStream<Root> fatReader = new DataFileStream(s3object.getObjectContent(), rootDatumReader);

        Properties kafkaProps = new Properties();
//        kafkaProps.load(new FileInputStream("resources/kafka.properties"));
        kafkaProps.load(S3DeserializeExperiment.class.getResourceAsStream("/kafka.properties"));
        Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(rootSchema);

        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(kafkaProps);

        int i = 0;
        while (fatReader.hasNext()) {
            Root next = fatReader.next();
            byte[] bytes = recordInjection.apply(next);
            ProducerRecord<String, byte[]> pr = new ProducerRecord<String, byte[]>("test_topic", bytes);
            System.out.println("Sending record: " + next);
            producer.send(pr, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("Callback called;");
                    System.out.println(recordMetadata);
                    System.err.println("Exception: "+ e);
                }
            });

            i++;
            if (i >= 10)
                break;
        }
        fatReader.close();
        producer.close();
        System.out.println("Done");
    }
}
