package com.loess.edu.source;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.*;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class BasicSourceFromTextFile {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> dataSource = environment.readTextFile("G:\\BIGDATA\\Spark\\projects\\bigdata-master\\flink\\data\\wc\\wordcount.txt");
        FlatMapOperator<String, String> op1 = dataSource.flatMap(new FlatMapFunction<String, String>() {

            @Override
            public void flatMap(String line, Collector<String> out) {
                String[] texts = line.split(" ");
                for (String text : texts) {
                    out.collect(text);
                }
            }
        });

        MapOperator<String, Tuple2<String, Integer>> op2 = op1.map(new MapFunction<String, Tuple2<String, Integer>>() {

            @Override
            public Tuple2<String, Integer> map(String value) {
                return Tuple2.of(value,1);
            }
        });
        AggregateOperator<Tuple2<String, Integer>> op3 = op2.groupBy(0).sum(1);
        op3.print();
    }
}
