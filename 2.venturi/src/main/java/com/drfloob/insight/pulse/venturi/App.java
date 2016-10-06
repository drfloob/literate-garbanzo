package com.drfloob.insight.pulse.venturi;

import com.drfloob.insight.pulse.schema.gh.main.Root;
import com.drfloob.insight.pulse.schema.gh.main.root.Actor;
import com.drfloob.insight.pulse.schema.skinny.SkinnyGHRecord;
import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.SpecificAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.ValueMapper;
import scala.reflect.ClassTag$;
import org.apache.kafka.common.serialization.Serdes.StringSerde;
import org.apache.kafka.common.serialization.Serdes.BytesSerde;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        final Serde<byte[]> serdeByte = Serdes.ByteArray();

	Properties venturiProps = new Properties();
	venturiProps.load(App.class.getResourceAsStream("/venturi.properties"));

	KStreamBuilder builder = new KStreamBuilder();
        KStream<byte[], byte[]> stream = builder.stream(serdeByte, serdeByte, venturiProps.getProperty("gh_fat_topic"));

        Properties streamsConfig = new Properties();
        streamsConfig.load(App.class.getResourceAsStream("/kafka.properties"));
        streamsConfig.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, StringSerde.class);
        streamsConfig.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, BytesSerde.class);


        final Injection<SkinnyGHRecord, byte[]> outByteToAvro = SpecificAvroCodecs.toBinary(ClassTag$.MODULE$.<SkinnyGHRecord>apply(SkinnyGHRecord.class));

        final SpecificDatumReader<Root> rootReader = new SpecificDatumReader<Root>(Root.class);
	final int displayEveryNRecords = Integer.parseInt(venturiProps.getProperty("display.every", "10000"));
	
        stream.flatMapValues(new ValueMapper<byte[], Iterable<byte[]>>() {
		private Long totalCount = 0L;
		private Long sentCount = 0L;
	
		public Iterable<byte[]> apply(byte[] bytes) {
		    totalCount++;
		    if (totalCount % 10000 == 0) {
			System.out.println("  " + sentCount + "/" + totalCount + " (" + sentCount/(double)totalCount+"%)");
		    }
		    ArrayList<byte[]> ret = new ArrayList<byte[]>();

		    BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
		    Root r;
		    try {
			r = rootReader.read(null, decoder);
		    } catch(IOException e) {
			System.err.println("Oops. Error decoding byte stream to Root avro record.");
			//TODO: better error handling
			return ret;
		    }

		
		    String toUser = PayloadParser.getToUser(r);
		    String fromUser = PayloadParser.getFromUser(r);
		    if (toUser == null || toUser.equals("") || fromUser == null || fromUser.equals("") || toUser.equals(fromUser)) {
			return ret;
		    }
		    
		    String url = PayloadParser.getUrl(r);
		    if (url == null)
			url = "null";
		    CharSequence id = r.getId();
		    if (id == null)
			id = "null";
		    
		    SkinnyGHRecord skinny = SkinnyGHRecord.newBuilder()
                        .setCreatedAt(r.getCreatedAt())
                        .setId(id)
                        .setType(r.getType())
                        .setFromUser(fromUser)
                        .setToUser(toUser)
                        .setUrl(url)
                        .build();
		    ret.add(outByteToAvro.apply(skinny));
		    // System.out.println("Processed record: " + skinny);
		    sentCount++;

		    return ret;
		}
	    }).to(serdeByte, serdeByte, venturiProps.getProperty("gh_skinny_topic"));
        KafkaStreams streams = new KafkaStreams(builder, streamsConfig);
        streams.start();
    }
}
