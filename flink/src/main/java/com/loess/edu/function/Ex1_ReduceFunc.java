package com.loess.edu.function;

import com.loess.edu.bean.EventLog;
import org.apache.flink.api.common.functions.ReduceFunction;

public class Ex1_ReduceFunc implements ReduceFunction<EventLog> {
    @Override
    public EventLog reduce(EventLog value1, EventLog value2) throws Exception {
        value1.setStayLong(value1.getStayLong()+ value2.getStayLong());
        return value1;
    }
}
