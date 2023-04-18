package com.loess.edu.transformation;

import com.loess.edu.bean.Str2Tuple;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KeyByAggregation {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 通过Socket构建数据流
        //1,aaa,male,90
        //2,bbb,male,90
        //3,ccc,male,90
        DataStreamSource<String> dataStreamSource = streamExecutionEnvironment.socketTextStream("127.0.0.1", 9099);
//        SingleOutputStreamOperator singleOutputStreamOperator = dataStreamSource.map(new Str2Student()).keyBy("gender").sum("score");
//        4> Student(id=1, name=aaa, gender=male, score=90.0)
//        4> Student(id=1, name=aaa, gender=male, score=180.0)
//        4> Student(id=1, name=aaa, gender=male, score=270.0)
//        singleOutputStreamOperator.print();

        // 把数据变成Student对象数据，方便后续的处理
        // 非分组字段取该组中进来的第一条数据
        SingleOutputStreamOperator<Tuple4<Integer, String, String, Double>> students =dataStreamSource.map(new Str2Tuple());
        //聚合类算子，一定是在KeyBy后的流（KeyedStream）上才能调用,f0-f4
        KeyedStream<Tuple4<Integer, String, String, Double>,String> keyedStream = students.keyBy(tp->tp.f2);
        // sum  每种性别的同学的总分
        keyedStream.sum(3);
        //min   每种性别的同学的最低分
        keyedStream.min(3);
        // max  每种性别的同学的最高分
        keyedStream.max(3);


        // minBy 按找指定字段求最小值，并且会返回最小值所在的整条数据
        keyedStream.minBy(3);
        // maxBy 按找指定字段求最大值，并且会返回最大值所在的整条数据
        keyedStream.maxBy(3);

        //reduce ,每种性别的总分
        keyedStream.reduce((ReduceFunction<Tuple4<Integer, String, String, Double>>) (tuple1, tuple2) -> {
            tuple2.f3 += tuple1.f3;
            return tuple2;
        });

        // reduce 每个分组的成员用 | 分割
        dataStreamSource.keyBy(s->s.split(",")[2]).reduce(new ReduceFunction<String>() {
            @Override
            public String reduce(String value1, String value2) throws Exception {
                return value1 + " | " + value2;
            }
        });


        // 求 所有人中的 最高分，最低分，分数之和，平均分，到目前为止的人数
        KeyedStream<Tuple4<Integer, String, String, Double>,String> globalKeyedStream =  keyedStream.keyBy((KeySelector<Tuple4<Integer, String, String, Double>, String>) value -> {
            return "a";  // 返回常量，就会导致任意一条数据都会被分到同一组
        });
        globalKeyedStream.maxBy(3);
        globalKeyedStream.minBy(3);
        globalKeyedStream.sum(3);
        DataStream<Tuple1<Integer>> ss = keyedStream.map(s-> Tuple1.of(1)).returns(new TypeHint<Tuple1<Integer>>() { });
        ss.keyBy(a->a.f0).sum(0).print();//求数据总条数

        streamExecutionEnvironment.execute();

    }
}
