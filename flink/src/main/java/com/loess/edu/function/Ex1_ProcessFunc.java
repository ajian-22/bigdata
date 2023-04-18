package com.loess.edu.function;

import com.loess.edu.bean.EventLog;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class Ex1_ProcessFunc extends KeyedProcessFunction<String, EventLog,Long> {
    long cnt = 0;

    @Override
    public void processElement(EventLog eventLog, KeyedProcessFunction<String, EventLog, Long>.Context context, Collector<Long> collector) {
        cnt++;
        collector.collect(cnt);
    }
}
