package com.drfloob.insight.pulse.venturi;

import com.drfloob.insight.pulse.venturi.schema.Root;
import com.drfloob.insight.pulse.venturi.schema.root.Actor;
import com.drfloob.insight.pulse.venturi.schema.skinny.SkinnyGHRecord;
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
        KStreamBuilder builder = new KStreamBuilder();
        KStream<byte[], byte[]> stream = builder.stream(serdeByte, serdeByte, "gh_fat_topic");

        Schema.Parser parser = new Schema.Parser();
        Schema inSchema = parser.parse(App.class.getResourceAsStream("/gha_schema.avsc"));
        Schema outSchema = parser.parse(App.class.getResourceAsStream("/skinny.avsc"));

        Properties streamsConfig = new Properties();
        streamsConfig.load(App.class.getResourceAsStream("/kafka.properties"));
        streamsConfig.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, StringSerde.class);
        streamsConfig.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, BytesSerde.class);


        final Injection<SkinnyGHRecord, byte[]> outByteToAvro = SpecificAvroCodecs.toBinary(ClassTag$.MODULE$.<SkinnyGHRecord>apply(SkinnyGHRecord.class));

        final SpecificDatumReader<Root> rootReader = new SpecificDatumReader<Root>(Root.class);

        stream.flatMapValues(new ValueMapper<byte[], Iterable<byte[]>>() {
            public Iterable<byte[]> apply(byte[] bytes) {
                ArrayList<byte[]> ret = new ArrayList<byte[]>();

		// System.out.println("\n\n");
		// System.out.println("Deserializing bytes");
		
                BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
                Root r;
                try {
                    r = rootReader.read(null, decoder);
                } catch(IOException e) {
                    System.err.println("Oops. Error decoding byte stream to Root avro record.");
                    //TODO: better error handling
                    return ret;
                }

		// System.out.println("Processing: " + r);

		
                // Actor tmpActor = r.getActor();
                // if (tmpActor == null)
                //     return ret;
                // CharSequence tmpLogin = tmpActor.getLogin();
                // if (tmpLogin == null)
                //     return ret;
                String toUser = PayloadParser.getToUser(r);
                String fromUser = PayloadParser.getFromUser(r);
                // String fromUser = tmpLogin.toString();
                if (toUser == null || toUser.equals("") || fromUser == null || fromUser.equals("") || toUser.equals(fromUser)) {
		    // System.err.println("Erroring out on user parsing: (" + toUser + "), (" + fromUser + ")");
                    return ret;
		}
		    
                String url = PayloadParser.getUrl(r);

                SkinnyGHRecord skinny = SkinnyGHRecord.newBuilder()
                        .setCreatedAt(r.getCreatedAt())
                        .setId(r.getId())
                        .setType(r.getType())
                        .setFromUser(fromUser)
                        .setToUser(toUser)
                        .setUrl(url)
                        .build();
                ret.add(outByteToAvro.apply(skinny));
                System.out.println("Processed record: " + skinny);
                return ret;
            }
        }).to(serdeByte, serdeByte, "gh_skinny_topic");
        KafkaStreams streams = new KafkaStreams(builder, streamsConfig);
        streams.start();
    }
}
