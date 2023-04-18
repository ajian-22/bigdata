package com.loess.edu.multistream;

import com.loess.edu.bean.Student;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class MultiStreamUnion {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> dataStreamSource1 = streamExecutionEnvironment.socketTextStream("127.0.0.1", 9099);
        DataStreamSource<String> dataStreamSource2 = streamExecutionEnvironment.socketTextStream("127.0.0.1", 9098);

        SingleOutputStreamOperator<Student> map1 = dataStreamSource1.map(s -> {
            String[] arr = s.split(",");
            return new Student(Integer.parseInt(arr[0]), arr[1], arr[2], Double.parseDouble(arr[3]));
        });
        SingleOutputStreamOperator<Student> map2 = dataStreamSource2.map(s -> {
            String[] arr = s.split(":");
            return new Student(Integer.parseInt(arr[0]), arr[1], arr[2], Double.parseDouble(arr[3]));
        });

        DataStream<Student> unionStream = map1.union(map2);
        unionStream.filter(s -> s.getScore() > 60).print("unionStream");

        streamExecutionEnvironment.execute();
    }
}
