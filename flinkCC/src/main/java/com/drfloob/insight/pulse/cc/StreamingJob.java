package com.drfloob.insight.pulse.cc;

import com.drfloob.insight.pulse.cc.schema.skinny.SkinnyGHRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.streaming.SimpleEdgeStream;
import org.apache.flink.graph.streaming.example.util.DisjointSet;
import org.apache.flink.streaming.StephanEwen.AvroDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.IngestionTimeExtractor;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer08;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;

import java.util.Properties;


public class StreamingJob {

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties flinkProps = new Properties();
        flinkProps.load(StreamingJob.class.getResourceAsStream("/flink.properties"));

        DatumReader<SkinnyGHRecord> reader = new SpecificDatumReader<SkinnyGHRecord>(SkinnyGHRecord.class);

        AvroDeserializationSchema<SkinnyGHRecord> skinnySchema = new AvroDeserializationSchema<>(SkinnyGHRecord.class);
        FlinkKafkaConsumer08<SkinnyGHRecord> kafkaConsumer = new FlinkKafkaConsumer08<>("gh_skinny_topic", skinnySchema, flinkProps);
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

        // 1/2 second processing time window
        Long windowLength = 500L;
        DataStream<DisjointSet<String>> cc = ses.aggregate(new WindowedConnectedComponents<String, SkinnyGHRecord>(windowLength));

        cc.map(new MapFunction<DisjointSet<String>, String>() {
            @Override
            public String map(DisjointSet<String> value) throws Exception {
                return value.toString();
            }
        })
        .addSink(new FlinkKafkaProducer08<String>("gh_components", new SimpleStringSchema(), flinkProps));

        // execute program
		env.execute("Flink Streaming Connected Components");
	}



}
