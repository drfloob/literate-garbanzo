package com.drfloob.pulse.insight.kafkaRethink;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlError;
import com.rethinkdb.gen.exc.ReqlQueryLogicError;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.io.IOException;
import java.util.Properties;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

	Properties rethinkProps = new Properties();
	Properties kafkaProps = new Properties();
	Properties krDaemonProps = new Properties();
	
	try {
	    rethinkProps.load(App.class.getResourceAsStream("/rethinkClusters.properties"));

	    kafkaProps.load(App.class.getResourceAsStream("/kafkaClusters.properties"));	

	    krDaemonProps.load(App.class.getResourceAsStream("/krDaemon.properties"));	
	} catch(IOException e) {
	    e.printStackTrace();
	    System.exit(1);
	}

	kafkaProps.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG,
		       "org.apache.kafka.streams.processor.WallclockTimestampExtractor");
	
	KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> stream = builder.stream(Serdes.String(),
							Serdes.String(),
							krDaemonProps.getProperty("topic"));

	

	final RethinkDB r = RethinkDB.r;

	final Connection conn = r.connection()
	    .hostname(rethinkProps.getProperty("hostname"))
	    .port(Integer.parseInt(rethinkProps.getProperty("port")))
	    .connect();

	final String rDB = krDaemonProps.getProperty("db");
	final String rTABLE = krDaemonProps.getProperty("table");
	
	stream.foreach(new ForeachAction<String, String>() {
		@Override
		public void apply(String key, String value) {
		    r.db(rDB)
			.table(rTABLE)
			.insert(r.json(value))
			.run(conn);
		}
	    });
        KafkaStreams streams = new KafkaStreams(builder, kafkaProps);
        streams.start();
	
	// r.db("test").tableCreate("tv_shows").run(conn);
    }
}
