package com.loess.edu.source;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

public class BasicSourceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 通过本地集合(序列)来构造一个数据流
        DataStreamSource dataStreamSource = environment.fromSequence(1,1000);
        dataStreamSource.filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                return value % 2 == 1;
            }
        }).print();

        //通过本地集合List，来构造一个数据流
        dataStreamSource = environment.fromCollection(Arrays.asList("a","b","c","d","e"));
        dataStreamSource.map(s->s.toString().toUpperCase()).print();


    }
}
