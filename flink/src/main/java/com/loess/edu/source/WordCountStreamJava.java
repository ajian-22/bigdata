package com.loess.edu.source;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;


public class WordCountStreamJava {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置运行模式为批处理模式，虽然用的流式处理api,但是依然会以流计算模式运行
//        environment.setRuntimeMode(RuntimeExecutionMode.BATCH);
        DataStreamSource<String> dataStreamSource = environment.readTextFile("G:\\BIGDATA\\Spark\\projects\\bigdata-master\\flink\\data\\wc\\wordcount.txt");
        dataStreamSource.flatMap(new SplitWordFlatMapFunction()).keyBy(value -> value.f0).sum(1).print();
        //流式处理模式下，触发job运行
        environment.execute();
    }
}

class SplitWordFlatMapFunction implements FlatMapFunction<String, Tuple2<String, Integer>> {
    @Override
    public void flatMap(String line, Collector<Tuple2<String, Integer>> out) {
        String[] words = line.split(" ");
        for (String word : words) {
            out.collect(Tuple2.of(word, 1));
        }
    }
}
