package com.drfloob.insight.pulse.firehose;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.drfloob.insight.pulse.firehose.schema.Root;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by aj on 9/20/16.
 */
public class S3DeserializeExperiment {
    private static final String BUCKET = "githubarchive-truth";
    private static final String KEY = "2011-000000000000.avro";

    public static void main(String[] args) throws IOException {
        AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(new File("resources/awsCredentials.properties")));
        S3Object s3object = s3.getObject(new GetObjectRequest(BUCKET, KEY));
        System.out.println(s3object.getObjectMetadata().getContentType());
        System.out.println(s3object.getObjectMetadata().getContentLength());

        DatumReader<Root> rootDatumReader = new SpecificDatumReader<Root>(Root.class);
        DataFileStream<Root> fatReader = new DataFileStream(s3object.getObjectContent(), rootDatumReader);
        //DataFileReader<Root> fatReader = new DataFileReader<Root>(new File("/mnt/sd/2011-000000000000.avro"), rootDatumReader);
        int i = 0;
        while (fatReader.hasNext()) {
            System.out.println(fatReader.next());
            i++;
            if (i >= 10)
                break;
        }

    }
}
