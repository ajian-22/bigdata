package com.loess.edu.source;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class BasicSourceFromSocket {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
//        // 设置运行模式为批处理模式，虽然用的流式处理api,但是依然会以流计算模式运行
//        environment.setRuntimeMode(RuntimeExecutionMode.BATCH);
        DataStreamSource<String> dataStreamSource = environment.socketTextStream("localhost", 9099);
        dataStreamSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {

            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
                for (String word : value.split(" "))
                    out.collect(Tuple2.of(word,1));
            }
        }).keyBy(new KeySelector<Tuple2<String, Integer>, String>(){

            @Override
            public String getKey(Tuple2<String, Integer> value) throws Exception {
                return value.f0;
            }
        }).sum(1).print();
        environment.execute();
    }
}
