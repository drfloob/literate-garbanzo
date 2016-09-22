package com.drfloob.insight.pulse.cc;

import com.drfloob.insight.pulse.cc.schema.skinny.SkinnyGHRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.streaming.SimpleEdgeStream;
import org.apache.flink.graph.streaming.example.util.DisjointSet;
import org.apache.flink.streaming.StephanEwen.AvroDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.IngestionTimeExtractor;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer082;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer08;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;

import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class StreamingJob {

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties flinkProps = new Properties();
        flinkProps.load(StreamingJob.class.getResourceAsStream("/flink.properties"));

        DatumReader<SkinnyGHRecord> reader = new SpecificDatumReader<SkinnyGHRecord>(SkinnyGHRecord.class);

        AvroDeserializationSchema<SkinnyGHRecord> skinnySchema = new AvroDeserializationSchema<>(SkinnyGHRecord.class);
        FlinkKafkaConsumer082<SkinnyGHRecord> kafkaConsumer = new FlinkKafkaConsumer082<>("gh_skinny_topic", skinnySchema, flinkProps);
		DataStream<SkinnyGHRecord> skinnyStream = env.addSource(kafkaConsumer);

        SingleOutputStreamOperator<SkinnyGHRecord> sos =  skinnyStream.assignTimestampsAndWatermarks(new IngestionTimeExtractor<SkinnyGHRecord>());
        SimpleEdgeStream<String, SkinnyGHRecord> ses = new SimpleEdgeStream<String, SkinnyGHRecord>(sos.map(
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

        // 1 hour window to start, emitted every second
        Long windowLength = 500L;
        DataStream<DisjointSet<String>> cc = ses.aggregate(new WindowedConnectedComponents<String, SkinnyGHRecord>(windowLength));

//        cc.timeWindowAll(Time.of(windowLength, TimeUnit.MILLISECONDS))
//                .apply(new AllWindowFunction<DisjointSet<String>, String, TimeWindow>() {
//                    @Override
//                    public void apply(TimeWindow window, Iterable<DisjointSet<String>> values, Collector<String> out) throws Exception {
//                        for (DisjointSet<String> djs : values)
//                            out.collect(djs.toString());
//                    }
//                }).addSink(new FlinkKafkaProducer08<String>("gh_components", new SimpleStringSchema(), flinkProps));

        cc.map(new MapFunction<DisjointSet<String>, String>() {
            @Override
            public String map(DisjointSet<String> value) throws Exception {
                   return value.toString();
            }
        }).addSink(new FlinkKafkaProducer08<String>("gh_components", new SimpleStringSchema(), flinkProps));

        // execute program
		env.execute("Flink Streaming Connected Components");
	}



}
