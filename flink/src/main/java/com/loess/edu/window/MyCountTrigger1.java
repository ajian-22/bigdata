package com.loess.edu.window;

import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.loess.com.window.Click;

public class MyCountTrigger1 extends Trigger<Click, GlobalWindow> {
    int maxCount = 2;
    int count = 0;

    /**
     * 每来一条数据调用一次该方法，以决定是否需要触发窗口计算
     *
     * @param click
     * @param l
     * @param globalWindow
     * @param triggerContext
     * @return
     * @throws Exception
     */
    @Override
    public TriggerResult onElement(Click click, long l, GlobalWindow globalWindow, TriggerContext triggerContext) throws Exception {
        count++;
        if (count >= maxCount) {
            count = 0;
            return TriggerResult.FIRE;
        }
        return TriggerResult.CONTINUE;
    }

    /**
     * 用于处理时间条件触发，当满足什么时间的时候去触发
     *
     * @param l
     * @param globalWindow
     * @param triggerContext
     * @return
     * @throws Exception
     */
    @Override
    public TriggerResult onProcessingTime(long l, GlobalWindow globalWindow, TriggerContext triggerContext) throws Exception {
        return TriggerResult.CONTINUE;
    }

    /**
     * 用于时间时间条件触发
     *
     * @param l
     * @param globalWindow
     * @param triggerContext
     * @return
     * @throws Exception
     */
    @Override
    public TriggerResult onEventTime(long l, GlobalWindow globalWindow, TriggerContext triggerContext) throws Exception {
        return TriggerResult.CONTINUE;
    }

    @Override
    public void clear(GlobalWindow globalWindow, TriggerContext triggerContext) throws Exception {
        System.out.println("窗口被清除...");
    }
}
