package com.loess.edu.source;

import com.loess.edu.bean.EventLog;
import com.loess.edu.function.Ex1_FlatMap1;
import com.loess.edu.function.Ex1_MapFunc;
import com.loess.edu.function.Ex1_ProcessFunc;
import com.loess.edu.function.Ex1_ReduceFunc;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.consumer.ConsumerConfig;

public class BasicSourceFromKafka {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 构造一个Kafka数据源
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setTopics("logs")
                .setGroupId("ex01")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setBootstrapServers("node1:9092,node2:9092,node3:9092")
                .setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
                .setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                .build();

        // 通过Kafka数据源构建数据流
        DataStreamSource<String> dataStreamSource = streamExecutionEnvironment.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kfk");
        // 1,addCart,14763473469458,app,12387_1,pageView,14763473488458,app,3387
        SingleOutputStreamOperator<String> singleOutputStreamOperator = dataStreamSource.flatMap(new Ex1_FlatMap1());
        // 把字符串转成EventLog对象
        SingleOutputStreamOperator<EventLog> singleOutputStreamOperator1 = singleOutputStreamOperator.map(new Ex1_MapFunc());
        // 把eventid为空的数据过滤掉
        SingleOutputStreamOperator<EventLog> singleOutputStreamOperator2 = singleOutputStreamOperator1.filter(value -> StringUtils.isNotBlank(value.getEventId()));

        // 各类聚合练习：
        // 全局聚合，在api中不支持，必须先分组
        KeyedStream<EventLog, String> keyedStream = singleOutputStreamOperator2.keyBy(event -> event.getChannel());
        // 各种渠道的日志条数
        SingleOutputStreamOperator<Long> lcnt = keyedStream.process(new Ex1_ProcessFunc());
        // 各渠道的事件停留总时长
        SingleOutputStreamOperator<EventLog> lstayLongSum = keyedStream.sum("stayLong");
        // 与上面的sum的结果一模一样，会多出来  总时长以外的 EventLog中的字段（这些字段取的都是第一条日志的值）
        SingleOutputStreamOperator<EventLog> lstayLongSum2 = keyedStream.reduce(new Ex1_ReduceFunc());
        // 各渠道的事件的停留时长最大值
        SingleOutputStreamOperator<EventLog> stayLongMax = keyedStream.maxBy("stayLong");
        SingleOutputStreamOperator<EventLog> stayLongMaxOne = keyedStream.max("stayLong");

        // 各渠道的事件的停留时长最小值
        SingleOutputStreamOperator<EventLog> stayLongMix = keyedStream.minBy("stayLong");
        SingleOutputStreamOperator<EventLog> stayLongMixOne = keyedStream.min("stayLong");
        keyedStream.print();
        streamExecutionEnvironment.execute();
    }
}
