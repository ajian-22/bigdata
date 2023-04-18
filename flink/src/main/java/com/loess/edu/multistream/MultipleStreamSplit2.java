package com.loess.edu.multistream;

import com.loess.edu.bean.Str2Student;
import com.loess.edu.bean.Student;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

public class MultipleStreamSplit2 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        //从Socket构建数据流
        DataStreamSource<String> dataStreamSource = streamExecutionEnvironment.socketTextStream("127.0.0.1", 9099);
        SingleOutputStreamOperator<Student> singleOutputStreamOperator = dataStreamSource.map(new Str2Student());
        // 测流标签在构造时，最好加上类型声明
        OutputTag<Student> maleOutputTag = new OutputTag<>("male", TypeInformation.of(Student.class));
        OutputTag<Student> famaleOutputTag = new OutputTag<>("famale", TypeInformation.of(Student.class));

        SingleOutputStreamOperator<Student> mainStream = singleOutputStreamOperator.process(new ProcessFunction<Student, Student>() {
            @Override
            public void processElement(Student student, ProcessFunction<Student, Student>.Context context, Collector<Student> collector) throws Exception {
                if ("male".equals(student.getGender())) {
                    // 输出到测流
                    context.output(maleOutputTag, student);
                } else if ("famale".equals(student.getGender())) {
                    // 输出到测流
                    context.output(famaleOutputTag, student);
                } else {
                    // 在主流中输出
                    collector.collect(student);
                }
            }
        });

        mainStream.print("主流输出");

        DataStream<Student> maleStudent = mainStream.getSideOutput(maleOutputTag);
        maleStudent.print("maleStudent流输出");

        DataStream<Student> femaleStudents = mainStream.getSideOutput(famaleOutputTag);
        femaleStudents.print("femaleStudents流输出");

        streamExecutionEnvironment.execute();
    }
}
