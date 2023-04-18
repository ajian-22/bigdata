package com.loess.edu.source;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple2;

public class WordCountBatchJavaLamda {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> dataSource = env.readTextFile("G:\\BIGDATA\\Spark\\projects\\bigdata-master\\flink\\data\\wc\\wordcount.txt");
        FlatMapOperator<String, String> op1 = dataSource.flatMap((FlatMapFunction<String, String>) (s, collector) -> {
            String[] arr = s.split(" ");
            for (String word : arr) {
                collector.collect(word);
            }
        }).returns(TypeInformation.of(new TypeHint<String>() {
        }));
        MapOperator<String, Tuple2<String, Integer>> op2 = op1.map(s -> Tuple2.of(s, 1)).returns(Types.TUPLE(Types.STRING, Types.INT));
        AggregateOperator<Tuple2<String, Integer>> op3 = op2.groupBy(0).sum(1);
        op3.print();
    }
}
