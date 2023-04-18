package com.loess.edu.transformation;

import com.loess.edu.bean.Str2Student;
import com.loess.edu.bean.Student;
import com.loess.edu.bean.StudentStat;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

@Slf4j
public class ProcessTransformation {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = streamExecutionEnvironment.socketTextStream("127.0.0.1", 9099);
        KeyedStream<Student, String> keyedStream = dataStreamSource.map(new Str2Student()).keyBy(s -> s.getGender());
        keyedStream.process(new KeyedProcessFunction<String, Student, StudentStat>() {

            int cnt = 0;
            double sum = 0;
            double minScoure = -1;
            double maxScore = 0;

            @Override
            public void processElement(Student student, KeyedProcessFunction<String, Student, StudentStat>.Context context, Collector<StudentStat> collector) throws Exception {
                String currentKey = context.getCurrentKey();
                log.info("从上下文获取当前正在处理的数据，所在的分组：{}", currentKey);

                sum += student.getScore();
                log.info("从当前进来的第一条数据中取分数:{}", sum);

                cnt++;
                log.info("对本组中的条数自增:{}", cnt);

                if (student.getScore() < minScoure || minScoure == -1)
                    minScoure = student.getScore();

                if (student.getScore() > maxScore || maxScore == 0)
                    maxScore = student.getScore();

                collector.collect(new StudentStat(currentKey, minScoure, maxScore, sum / cnt, cnt));

            }
        }).print();
        streamExecutionEnvironment.execute("process function");
    }
}
