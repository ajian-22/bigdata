package com.loess.edu.window;

import com.loess.edu.function.Ex1_FlatMap1;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class EventTimeWindow {
    public static void main(String[] args) {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = environment.socketTextStream("127.0.0.1", 9099);
        SingleOutputStreamOperator<String> stringSingleOutputStreamOperator = dataStreamSource.flatMap(new Ex1_FlatMap1());
    }
}
