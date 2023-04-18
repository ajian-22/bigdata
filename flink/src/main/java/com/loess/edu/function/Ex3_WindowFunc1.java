package com.loess.edu.function;

import com.loess.edu.bean.EventLog;
import org.apache.flink.streaming.api.scala.function.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import scala.collection.Iterable;
import scala.collection.Iterator;

public class Ex3_WindowFunc1 implements AllWindowFunction<EventLog,String, TimeWindow> {
    @Override
    public void apply(TimeWindow window, Iterable<EventLog> input, Collector<String> out) {
        long sum = 0;
        Iterator<EventLog> iterator = input.iterator();
        while(iterator.hasNext()){
            EventLog eventLog =  iterator.next();
            sum += eventLog.getStayLong();
        }
        out.collect("["+window.getStart() + "," + window.getEnd()+")" + " : " + sum);
    }
}
