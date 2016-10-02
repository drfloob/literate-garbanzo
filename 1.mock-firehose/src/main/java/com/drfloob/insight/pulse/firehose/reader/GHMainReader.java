package com.drfloob.insight.pulse.firehose.reader;

import com.drfloob.insight.pulse.schema.gh.main.Root;
import com.twitter.bijection.avro.SpecificAvroCodecs;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.io.DatumReader;
import scala.reflect.ClassTag$;
import com.twitter.bijection.Injection;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.avro.AvroTypeException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;

public class GHMainReader {

    private S3Object s3obj;
    private DataFileStream<Root> fatReader;
    private KafkaProducer<String, byte[]> producer;
    private Injection<Root, byte[]> recordInjection;
    private String topic;
    
    public GHMainReader(S3Object s3, KafkaProducer<String, byte[]> producer, String topic) {
	this.s3obj = s3;
        this.recordInjection = SpecificAvroCodecs.toBinary(ClassTag$.MODULE$.<Root>apply(Root.class));
	this.topic = topic;
	
        DatumReader<Root> rootDatumReader = new SpecificDatumReader<Root>(Root.class);
	try {
	    this.fatReader = new DataFileStream(s3obj.getObjectContent(), rootDatumReader);
	} catch (IOException e) {
	    e.printStackTrace();
	    System.err.println("not reading, error creating data file stream");
	}
	this.producer = producer;
    }

    public boolean hasNext() {
	if (this.fatReader == null)
	    return false;
	return this.fatReader.hasNext();
    }

    public void readAndPublishNext() throws AvroTypeException {
	byte[] bytes = this.recordInjection.apply(this.fatReader.next());
	ProducerRecord<String, byte[]> pr = new ProducerRecord<String, byte[]>(this.topic, bytes);
	this.producer.send(pr);
    }

    public void close() {
	try {
	    this.fatReader.close();
	} catch(IOException e) {
	    e.printStackTrace();
	    System.err.println("IOException closing reader. Moving along ...");
	}
    }
}


