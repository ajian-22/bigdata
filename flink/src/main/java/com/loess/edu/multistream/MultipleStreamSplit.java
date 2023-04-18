package com.loess.edu.multistream;

import com.loess.edu.bean.Str2Student;
import com.loess.edu.bean.Student;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class MultipleStreamSplit {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        //从Socket构建数据流
        DataStreamSource<String> dataStreamSource = streamExecutionEnvironment.socketTextStream("127.0.0.1", 9099);
        SingleOutputStreamOperator<Student> singleOutputStreamOperator = dataStreamSource.map(new Str2Student());

        // 按性别，将students数据流，分成2个流
        SingleOutputStreamOperator<Student> maleStudents = singleOutputStreamOperator.filter(s -> "m".equals(s.getGender()));
        SingleOutputStreamOperator<Student> femaleStudents = singleOutputStreamOperator.filter(s -> "f".equals(s.getGender()));

        // 分别处理流
        maleStudents.map(m -> {
            m.setName(m.getName().toUpperCase());
            return m;
        }).returns(Student.class).print();
        femaleStudents.map(f -> {
            f.setScore(f.getScore() * 10);
            return f;
        }).returns(Student.class).print();

        streamExecutionEnvironment.execute("MultipleStreamSplit");
    }
}
