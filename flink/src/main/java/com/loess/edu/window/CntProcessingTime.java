package com.loess.edu.window;

import com.loess.edu.bean.EventLog;
import com.loess.edu.function.Ex1_FlatMap1;
import com.loess.edu.function.Ex1_MapFunc;
import com.loess.edu.function.Ex1_WindowFunc;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.CountEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.kafka.clients.consumer.ConsumerConfig;

/**
 * 基于processTime
 */
public class CntProcessingTime {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("")
                .setTopics("")
                .setGroupId("")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
                .setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                .build();
        DataStreamSource<String> dataStreamSource = streamExecutionEnvironment.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kfk");

        // 将原始数据打散成一条一条的日志
        // 1,addCart,14763473469458,app,12387_1,pageView,14763473488458,app,3387
        SingleOutputStreamOperator<String> stringSingleOutputStreamOperator = dataStreamSource.flatMap(new Ex1_FlatMap1());
        // 把字符串转成EventLog对象
        SingleOutputStreamOperator<EventLog> stream = stringSingleOutputStreamOperator.map(new Ex1_MapFunc());

        // 每隔5条数据计算一次，事件停留时长总和 --> 滚动窗口，size=5， step=5
        stream.countWindowAll(5).sum("stayLong").print();

        // 每隔2条数据计算一次，事件停留时长总和 --> 滑动窗口，size=5， step=2
        stream.countWindowAll(5, 2).sum("stayLong").print();

        // 通用的开窗的方法
        stream.windowAll(GlobalWindows.create())
                .trigger(CountTrigger.of(2))
                .evictor(CountEvictor.of(5))
                .sum("stayLong").print();

        stream.windowAll(GlobalWindows.create())
                .trigger(new MyCountTrigger())
                .evictor(new MyEvictor())
                .sum("stayLong")
                .print();

        //滚动窗口
        stream.windowAll(TumblingProcessingTimeWindows.of(Time.milliseconds(5000))).sum("stayLong").print();
        // 滑动窗口
        stream.windowAll(SlidingProcessingTimeWindows.of(Time.milliseconds(5000),Time.milliseconds(2000))).apply(new Ex1_WindowFunc()).print();

        streamExecutionEnvironment.execute("processingTimeWindows...");

    }
}
