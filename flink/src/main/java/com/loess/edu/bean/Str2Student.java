package com.loess.edu.bean;

import org.apache.flink.api.common.functions.MapFunction;

public class Str2Student implements MapFunction<String, Student> {
    @Override
    public Student map(String value) throws Exception {
        String[] arr = value.split(",");
        return new Student(Integer.parseInt(arr[0]), arr[1], arr[2], Double.parseDouble(arr[3]));
    }
}
