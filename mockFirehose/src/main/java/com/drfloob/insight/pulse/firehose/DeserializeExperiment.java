package com.drfloob.insight.pulse.firehose;

import com.drfloob.insight.pulse.firehose.schema.Root;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.File;
import java.io.IOException;


public class DeserializeExperiment {
    public static void main(String[] args) throws IOException {
        DatumReader<Root> rootDatumReader = new SpecificDatumReader<Root>(Root.class);
        DataFileReader<Root> fatReader = new DataFileReader<Root>(new File("/mnt/sd/2011-000000000000.avro"), rootDatumReader);
        int i = 0;
        while (fatReader.hasNext()) {
            System.out.println(fatReader.next());
            i++;
            if (i >= 10)
                break;
        }
    }
}