package com.loess.edu.function;

import com.loess.edu.bean.EventLog;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class Ex1_WindowFunc implements AllWindowFunction<EventLog, String, TimeWindow> {

    @Override
    public void apply(TimeWindow window, Iterable<EventLog> values, Collector<String> out) {
        long sum = 0;

        for (EventLog value : values) {
            sum += value.getStayLong();
        }
        out.collect(window.getStart() + "," + window.getEnd() + "," + window.maxTimestamp() + " : " + sum);
    }
}
