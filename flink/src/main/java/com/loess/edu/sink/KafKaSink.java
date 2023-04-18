package com.loess.edu.sink;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

public class KafKaSink {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.enableCheckpointing(1000);
        env.getCheckpointConfig().setCheckpointStorage("file:///Users/tianye/Desktop/yao_tmp/flink_status");
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.setStateBackend(new HashMapStateBackend());
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, 1000));

        // 读数据，写入kafka
        DataStreamSource<String> source = env.socketTextStream("localhost", 9099);

        Properties properties = new Properties();
        properties.setProperty("transaction.timeout.ms","300000");
        properties.setProperty("compression.type","snappy");


        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                .setBootstrapServers("bigdata001:9092,bigdata002:9092,bigdata003:9092")
                .setRecordSerializer(
                        KafkaRecordSerializationSchema.builder()
                                .setTopic("kafka_sink")
                                .setValueSerializationSchema(new SimpleStringSchema())
                                .build()
                )
                .setDeliverGuarantee(DeliveryGuarantee.EXACTLY_ONCE)
                .setTransactionalIdPrefix("gupao-teach2-")
                .setKafkaProducerConfig(properties)
                .build();

        source.sinkTo(kafkaSink);

        env.execute();
    }
}
