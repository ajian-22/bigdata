package org.loess.com.window

import org.apache.flink.streaming.api.scala.function.AllWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

class MyWindowFunction extends AllWindowFunction[Click,String,TimeWindow]{
  override def apply(window: TimeWindow, input: Iterable[Click], out: Collector[String]): Unit = {
    val value = input.toList.map(_.age).sum
    val windowStart = window.getStart
    val windowEnd = window.getEnd
    out.collect(s"[$windowStart,$windowEnd) :$value ")
  }
}
