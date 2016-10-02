package com.drfloob.insight.pulse.firehose;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.avro.AvroTypeException;
import org.apache.kafka.clients.producer.KafkaProducer;
import com.drfloob.insight.pulse.firehose.reader.GHMainReader;
import com.drfloob.insight.pulse.firehose.reader.GH2015Reader;

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

        Properties kafkaProps = new Properties();
        kafkaProps.load(App.class.getResourceAsStream("/kafka.properties"));
        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(kafkaProps);

        Properties hoseProps = new Properties();
        hoseProps.load(App.class.getResourceAsStream("/hose.properties"));
        int i = 0,
	    max = Integer.parseInt(hoseProps.getProperty("max.count", "-1"));

	List<String> s3files = new ArrayList<String>();
	Scanner sc = new Scanner(App.class.getResourceAsStream("/s3files.txt"));
	while (sc.hasNext()) {
	    s3files.add(sc.next());
	}

	String topic = "gh_fat_topic";
	
	
	for (String key : s3files) {
	    System.out.println("Processing s3 file: " + key);
	    S3Object s3object = s3.getObject(new GetObjectRequest(BUCKET, key));

	    long j = 0;
	    try {
		GHMainReader reader = new GHMainReader(s3object, producer, topic);
		while(reader.hasNext()) {
		    reader.readAndPublishNext();
		    j++;
		    i++;
		    if (i >= max && max != -1)
			break;
		}
		reader.close();
	    } catch (AvroTypeException e) {
		// e.printStackTrace();
		System.err.println("Invalid avro format, read " + j + " records, trying 2015 from the beginning");
		j=0;
		try {
		    s3object.close();
		} catch(IOException hidden___this_may_be_evil) {
		    System.err.println("Could not close s3object. It may not matter");
		}
	    }

	    
	    if (j == 0) {
		s3object = s3.getObject(new GetObjectRequest(BUCKET, key));
		try {
		    GH2015Reader reader = new GH2015Reader(s3object, producer, topic);
		    while(reader.hasNext()) {
			reader.readAndPublishNext();
			j++;
			i++;
			if (i >= max && max != -1)
			    break;
		    }
		    reader.close();
		} catch (AvroTypeException e) {
		    e.printStackTrace();
		    System.err.println("Invalid avro format, giving up!");
		    try {
			s3object.close();
		    } catch(IOException hidden___this_may_be_evil) {
			System.err.println("Could not close s3object. It may not matter");
		    }
		}
	    }

	    System.out.println(" - file contained " + j + " avro records");
	}
	System.out.println("processed " + i + " records total");
        producer.close();
        System.out.println("Done");
    }
}
