package com.loess.edu.transformation;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

public class BasicTransformation {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource dataStreamSource = streamExecutionEnvironment.fromCollection(Arrays.asList("a,1,28_e,5,32", "b,2,33", "c,3,38_d,4,36"));

        // 1.flatMap
//        SingleOutputStreamOperator singleOutputStreamOperator = dataStreamSource.flatMap(new FlatMapFunction<String, String>() {
//            @Override
//            public void flatMap(String value, Collector<String> out) throws Exception {
//                for (String s : value.split("_")) {
//                    out.collect(s);
//                }
//            }
//        });
//        singleOutputStreamOperator.print();

        SingleOutputStreamOperator singleOutputStreamOperator = dataStreamSource.flatMap((FlatMapFunction<String, String>) (value, out) -> {
            Arrays.stream(value.split("_")).forEach(out::collect);
        }).returns(String.class);

        //2.map 把字符串数据 "c,3,38" 变成  Person对象数据
        singleOutputStreamOperator = singleOutputStreamOperator.map(new MapFunction<String, Person>() {

            @Override
            public Person map(String value) throws Exception {
                String[] arr = value.split(",");
                return new Person(arr[0], Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));
            }
        });

        // 3.filter 过滤掉age<30的人
        singleOutputStreamOperator = singleOutputStreamOperator.filter(new FilterFunction<Person>() {

            @Override
            public boolean filter(Person person) {
                return person.getAge() >= 30;
            }
        });
        singleOutputStreamOperator.print();


        // 通过本地集合来构建一个数据流
        DataStreamSource<String> dataStreamSource1 = streamExecutionEnvironment.fromCollection(Arrays.asList("{\"name\":\"aaa\",\"age\":18,\"id\":1}",
                "{\"name\":\"bbb\",\"age\":28,\"id\":2}",
                "{\"name\":\"ccc\",\"age\":38,\"id\":3}",
                "{\"name\":\"ddd\",\"age\":48,\"id\":4}"));
        dataStreamSource1.map((MapFunction<String, Person>) value -> JSON.parseObject(value,Person.class))
                .filter((FilterFunction<Person>) person -> person.getAge()>=30).print();
        streamExecutionEnvironment.execute();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Person {
    private String name;
    private int id;
    private int age;

}