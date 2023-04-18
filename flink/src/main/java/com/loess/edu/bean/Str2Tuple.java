package com.loess.edu.bean;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple4;

public class Str2Tuple implements MapFunction<String, Tuple4<Integer, String, String, Double>> {
    @Override
    public Tuple4<Integer, String, String, Double> map(String value) throws Exception {
        String[] arr = value.split(",");
        return Tuple4.of(Integer.parseInt(arr[0]), arr[1], arr[2], Double.parseDouble(arr[3]));
    }
}
