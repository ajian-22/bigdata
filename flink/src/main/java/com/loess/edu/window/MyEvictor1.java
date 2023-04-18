package com.loess.edu.window;

import org.apache.flink.streaming.api.windowing.evictors.Evictor;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue;
import org.loess.com.window.Click;

import java.util.Iterator;

public class MyEvictor1 implements Evictor<Click, GlobalWindow> {
    /**
     * 窗口计算触发前调用该方法（可以用来在触发计算前剔除一些元素）
     *
     * @param iterable       此刻，窗口中的所有元素的迭代器
     * @param i              此刻，窗口中的元素的总个数
     * @param globalWindow   当前窗口的元信息
     * @param evictorContext
     */
    @Override
    public void evictBefore(Iterable<TimestampedValue<Click>> iterable, int i, GlobalWindow globalWindow, EvictorContext evictorContext) {
        System.out.println("evictBefore called...");

        if (i > 5) {
            int evictedCount = 0;
            for (Iterator<TimestampedValue<Click>> iterator = iterable.iterator(); iterator.hasNext();) {
                TimestampedValue<Click> next = iterator.next();
                iterator.remove();
                evictedCount++;
                if (i - evictedCount <= 5) {
                    break;
                }
            }
        }
    }

    /**
     * 窗口计算触发后调用该方法（可以用来在窗口计算完毕后剔除一些元素）
     * @param iterable
     * @param i
     * @param globalWindow
     * @param evictorContext
     */
    @Override
    public void evictAfter(Iterable<TimestampedValue<Click>> iterable, int i, GlobalWindow globalWindow, EvictorContext evictorContext) {
        System.out.println("evictAfter called...");
    }
}
