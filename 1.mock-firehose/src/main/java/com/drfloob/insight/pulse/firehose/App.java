package com.drfloob.insight.pulse.firehose;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.drfloob.insight.pulse.schema.gh.main.Root;
import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import com.twitter.bijection.avro.SpecificAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.AvroTypeException;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import scala.reflect.ClassTag$;

import java.io.IOException;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by aj on 9/20/16.
 */
public class App {
    private static final String BUCKET = "githubarchive-truth";

    public static void main(String[] args) throws IOException {
        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(App.class.getResourceAsStream("/awsCredentials.properties")));
        // System.out.println(s3object.getObjectMetadata().getContentType());
        // System.out.println(s3object.getObjectMetadata().getContentLength());

        Schema.Parser parser = new Schema.Parser();
        Schema rootSchema = parser.parse(App.class.getResourceAsStream("/gha_schema.avsc"));

        DatumReader<Root> rootDatumReader = new SpecificDatumReader<Root>(Root.class);

        Properties kafkaProps = new Properties();
        kafkaProps.load(App.class.getResourceAsStream("/kafka.properties"));
        Injection<Root, byte[]> recordInjection = SpecificAvroCodecs.toBinary(ClassTag$.MODULE$.<Root>apply(Root.class));

        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(kafkaProps);

        Properties hoseProps = new Properties();
        hoseProps.load(App.class.getResourceAsStream("/hose.properties"));
        int i = 0, max = Integer.parseInt(hoseProps.getProperty("max.count", "1000"));

	List<String> s3files = new ArrayList<String>();
	Scanner sc = new Scanner(App.class.getResourceAsStream("/s3files.txt"));
	while (sc.hasNext()) {
	    s3files.add(sc.next());
	}

	for (String key : s3files) {
	    System.out.println("Processing s3 file: " + key);
	    S3Object s3object = s3.getObject(new GetObjectRequest(BUCKET, key));
	    DataFileStream<Root> fatReader = new DataFileStream(s3object.getObjectContent(), rootDatumReader);
	    long j = 0;
	    while (fatReader.hasNext()) {
		Root next;
		try {
		    next = fatReader.next();
		} catch(AvroTypeException e) {
		    System.err.println(e.toString());
		    e.printStackTrace();
		    continue;
		}
		// System.out.println(next.getCreatedAt());
		byte[] bytes = recordInjection.apply(next);
		ProducerRecord<String, byte[]> pr = new ProducerRecord<String, byte[]>("gh_fat_topic", bytes);
		producer.send(pr, new Callback() {
			public void onCompletion(RecordMetadata recordMetadata, Exception e) {
			    //                    System.out.println("Callback called;");
			    //                    System.out.println(recordMetadata);
			    //                    System.err.println("Exception: "+ e);
			}
		    });

		j++;
		i++;
		if (i >= max && max != -1)
		    break;
	    }
	    fatReader.close();
	    System.out.println(" - file contained " + j + " avro records");
	}
	System.out.println("processed " + i + " records total");
        producer.close();
        System.out.println("Done");
    }
}
