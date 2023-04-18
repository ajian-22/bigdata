package com.loess.edu.multistream;

import com.loess.edu.bean.Student;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

public class MultipleStreamConnect {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource1 = streamExecutionEnvironment.fromElements("1,aa,m,18","2,bb,m,28","3,cc,f,38");
        DataStreamSource<String> dataStreamSource2 = streamExecutionEnvironment.fromElements("1,aa,m,18","2,bb,m,28","3,cc,f,38");
        ConnectedStreams<String,String> connectedStreams = dataStreamSource1.connect(dataStreamSource2);

        SingleOutputStreamOperator<Student> singleOutputStreamOperator = connectedStreams.map(new CoMapFunction<String, String, Student>(){

            @Override
            public Student map1(String o) {
                String[] split = o.split(",");
                return new Student(Integer.parseInt(split[0]), split[1], split[2], Integer.parseInt(split[3]));
            }

            @Override
            public Student map2(String o) {
                String[] split = o.split(",");
                return new Student(Integer.parseInt(split[0]), split[1], split[2], Integer.parseInt(split[3]));            }
        });
        singleOutputStreamOperator.print("stream connect");
        streamExecutionEnvironment.execute();
    }
}
