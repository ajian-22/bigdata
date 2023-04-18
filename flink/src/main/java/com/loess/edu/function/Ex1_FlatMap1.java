package com.loess.edu.function;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class Ex1_FlatMap1 implements FlatMapFunction<String, String> {
    @Override
    public void flatMap(String value, Collector<String> out) {
        String[] arr = value.split("_");
        for (String log : arr) {
            out.collect(log);
        }
    }
}
