package com.loess.edu.source;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class BasicSourceFromSelfDefinition {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
//        DataStreamSource<String> dataStreamSource = environment.addSource(new MySourceFunction()).setParallelism(Integer.valueOf(2));
//        DataStreamSource<String> dataStreamSource = environment.addSource(new MyRichSourceFunction()).setParallelism(Integer.valueOf(2));
//        DataStreamSource<String> dataStreamSource = environment.addSource(new MyParallelSourceFunction()).setParallelism(Integer.valueOf(2));
        DataStreamSource<String> dataStreamSource = environment.addSource(new MyRichParallelSourceFunction()).setParallelism(Integer.valueOf(2));
        dataStreamSource.print();
        environment.execute();
    }


}

/**
 * The parallelism of non parallel operator must be 1.
 */
class MySourceFunction implements SourceFunction<String> {
    boolean flag = true;

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        while (flag) {
            int id = RandomUtils.nextInt(1, 1000);
            String name = RandomStringUtils.randomAlphabetic(3);
            int age = RandomUtils.nextInt(18, 38);
            String gender = RandomUtils.nextInt(1, 8) % 2 == 0 ? "male" : "female";
            sourceContext.collect(String.format("%d,%s,%d,%s", id, name, age, gender));
        }
    }

    @Override
    public void cancel() {
        flag = false;
    }
}

/**
 * The parallelism of non parallel operator must be 1.
 */
class MyRichSourceFunction extends RichSourceFunction<String> {
    boolean flag = true;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        while (flag) {
            int id = RandomUtils.nextInt(1, 1000);
            String name = RandomStringUtils.randomAlphabetic(3);
            int age = RandomUtils.nextInt(18, 38);
            String gender = RandomUtils.nextInt(1, 8) % 2 == 0 ? "male" : "female";
            sourceContext.collect(String.format("%d,%s,%d,%s", id, name, age, gender));
        }
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public void cancel() {
        flag = false;
    }
}

/**
 * 并行的source算子，并行度允许设置为大于1
 */
class MyParallelSourceFunction implements ParallelSourceFunction<String> {
    boolean flag = true;


    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        while (flag) {
            int id = RandomUtils.nextInt(1, 1000);
            String name = RandomStringUtils.randomAlphabetic(3);
            int age = RandomUtils.nextInt(18, 38);
            String gender = RandomUtils.nextInt(1, 8) % 2 == 0 ? "male" : "female";

            sourceContext.collect(String.format("%d,%s,%d,%s", id, name, age, gender));
            Thread.sleep(RandomUtils.nextInt(100, 2000));
        }
    }

    @Override
    public void cancel() {

    }
}

class MyRichParallelSourceFunction extends RichParallelSourceFunction<String> {
    boolean flag = true;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        while (flag) {
            int id = RandomUtils.nextInt(1, 1000);
            String name = RandomStringUtils.randomAlphabetic(3);
            int age = RandomUtils.nextInt(18, 38);
            String gender = RandomUtils.nextInt(1, 8) % 2 == 0 ? "male" : "female";
            sourceContext.collect(String.format("%d,%s,%d,%s", id, name, age, gender));
        }
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public void cancel() {
        flag = false;
    }
}