package com.drfloob.insight.pulse.firehose.reader;

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

public class GH2015Reader {

    private S3Object s3obj;
    private DataFileStream<com.drfloob.insight.pulse.schema.gh.y2015.Root> fatReader;
    private KafkaProducer<String, byte[]> producer;
    private Injection<com.drfloob.insight.pulse.schema.gh.main.Root, byte[]> recordInjection;
    private String topic;
    
    public GH2015Reader(S3Object s3, KafkaProducer<String, byte[]> producer, String topic) {
	this.s3obj = s3;
        this.recordInjection = SpecificAvroCodecs.toBinary(ClassTag$.MODULE$.<com.drfloob.insight.pulse.schema.gh.main.Root>apply(com.drfloob.insight.pulse.schema.gh.main.Root.class));
	this.topic = topic;
	
        DatumReader<com.drfloob.insight.pulse.schema.gh.y2015.Root> rootDatumReader = new SpecificDatumReader<com.drfloob.insight.pulse.schema.gh.y2015.Root>(com.drfloob.insight.pulse.schema.gh.y2015.Root.class);
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
	com.drfloob.insight.pulse.schema.gh.y2015.Root next = this.fatReader.next();

	com.drfloob.insight.pulse.schema.gh.main.root.Repo repo =
	    new com.drfloob.insight.pulse.schema.gh.main.root.Repo
	    (next.getRepoId(), next.getRepoName(), next.getRepoUrl());
	com.drfloob.insight.pulse.schema.gh.main.root.Actor actor =
	    new com.drfloob.insight.pulse.schema.gh.main.root.Actor
	    (next.getActorId(), next.getActorLogin(), next.getActorGravatarId(), next.getActorAvatarUrl(), next.getActorUrl());
	com.drfloob.insight.pulse.schema.gh.main.root.Org org =
	    new com.drfloob.insight.pulse.schema.gh.main.root.Org
	    (next.getOrgId(), next.getOrgLogin(), next.getOrgGravatarId(), next.getOrgAvatarUrl(), next.getOrgUrl());
	com.drfloob.insight.pulse.schema.gh.main.Root mainRootForSending =
	    new com.drfloob.insight.pulse.schema.gh.main.Root
	    (next.getType(), next.getPublic$(), next.getPayload(),
	     repo, actor, org,
	     next.getCreatedAt(), next.getId(), next.getOther());
	
	byte[] bytes = this.recordInjection.apply(mainRootForSending);
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


