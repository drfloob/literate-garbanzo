package com.drfloob.insight.pulse.cc;

import com.drfloob.insight.pulse.cc.schema.skinny.SkinnyGHRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.streaming.SimpleEdgeStream;
import org.apache.flink.graph.streaming.example.util.DisjointSet;
import org.apache.flink.graph.streaming.library.ConnectedComponents;
import org.apache.flink.streaming.StephanEwen.AvroDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.IngestionTimeExtractor;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer08;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;


public class StreamingJob {

    public static void main(String[] args) throws Exception {
	// set up the streaming execution environment
	final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
	env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
	    99999, // number of restart attempts
	   0 // delay in milliseconds
	));
        Properties flinkProps = new Properties();
        flinkProps.load(StreamingJob.class.getResourceAsStream("/flinkCC.properties"));

        DatumReader<SkinnyGHRecord> reader = new SpecificDatumReader<SkinnyGHRecord>(SkinnyGHRecord.class);

        AvroDeserializationSchema<SkinnyGHRecord> skinnySchema = new AvroDeserializationSchema<>(SkinnyGHRecord.class);
        FlinkKafkaConsumer08<SkinnyGHRecord> kafkaConsumer = new FlinkKafkaConsumer08<>("gh_skinny_topic", skinnySchema, flinkProps);
	DataStream<SkinnyGHRecord> skinnyStream = env.addSource(kafkaConsumer);

        SingleOutputStreamOperator<SkinnyGHRecord> sos =  skinnyStream.assignTimestampsAndWatermarks(new IngestionTimeExtractor<SkinnyGHRecord>());
        // SingleOutputStreamOperator<SkinnyGHRecord> sos =  skinnyStream.assignTimestampsAndWatermarks(new GHEventTimeExtractor());

        SimpleEdgeStream<String, SkinnyGHRecord> ses = new SimpleEdgeStream<String, SkinnyGHRecord>(
	    sos.map(
		new MapFunction<SkinnyGHRecord, Edge<String, SkinnyGHRecord>>() {
		    @Override
		    public Edge<String, SkinnyGHRecord> map(SkinnyGHRecord value) throws Exception {
			return new Edge<String, SkinnyGHRecord>(
								value.getFromUser().toString(),
								value.getToUser().toString(),
								value);
		    }
		}
	    ), env);
	
	Long windowLength = Long.parseLong(flinkProps.getProperty("window.length"));
	Long windowSlide = Long.parseLong(flinkProps.getProperty("window.slide"));
	SlidingEventTimeWindows windowAssigner = SlidingEventTimeWindows.of(Time.milliseconds(windowLength), Time.milliseconds(windowSlide));
        // DataStream<DisjointSet<String>> cc = ses.aggregate(new ConnectedComponents<String, SkinnyGHRecord>(windowAssigner));
        DataStream<DisjointSet<String>> cc = ses.aggregate(new WindowedConnectedComponents<String, SkinnyGHRecord>(windowAssigner));

	final ObjectMapper mapper = new ObjectMapper();
        cc.map(new MapFunction<DisjointSet<String>, String>() {
		@Override
		public String map(DisjointSet<String> value) throws Exception {
		    return mapper.writeValueAsString(value.buildMap());
		}
	    })
	    .addSink(new FlinkKafkaProducer08<String>("gh_components", new SimpleStringSchema(), flinkProps));

	while (true) {
        // execute program
	    try {
		env.execute("Flink Streaming Connected Components");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	    
    }


    private static class GHEventTimeExtractor implements AssignerWithPeriodicWatermarks<SkinnyGHRecord> {
	private final long maxOutOfOrderness = 3500; // 3.5 seconds

	private long currentMaxTimestamp;

	@Override
	public long extractTimestamp(SkinnyGHRecord element, long previousElementTimestamp) {
	    long timestamp = element.getCreatedAt() / 1000; // microseconds to milliseconds
	    currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
	    return timestamp;
	}

	@Override
	public Watermark getCurrentWatermark() {
	    // return the watermark as current highest timestamp minus the out-of-orderness bound
	    return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
	}
    }

}
