package org.loess.com.window

import com.loess.edu.window.{MyCountTrigger1, MyEvictor1}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.{GlobalWindows, SlidingEventTimeWindows, SlidingProcessingTimeWindows, TumblingProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.evictors.CountEvictor
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger


object WindowFunctionPrastice {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    import org.apache.flink.api.scala._
    val streams: DataStream[Click] = env.socketTextStream("127.0.0.1", 9999).map(line => {
      val word = line.split(",")
      Click(word(0).toInt, word(1), word(2).toLong, word(3).toInt)
    })
    streams.countWindowAll(5).sum("age").print()

    streams.windowAll(GlobalWindows.create())
      .trigger(CountTrigger.of(2))
      .evictor(CountEvictor.of(5))
      .sum("age")
      .print()

    streams.windowAll(GlobalWindows.create())
      .trigger(new MyCountTrigger1())
      .evictor(new MyEvictor1())
      .sum("age")
      .print()

    streams.windowAll(TumblingProcessingTimeWindows.of(Time.seconds(5)))
      .sum("age")
      .print()

    streams.windowAll(SlidingProcessingTimeWindows.of(Time.seconds(5), Time.seconds(2)))
      .sum("age")
      .print();

    streams.windowAll(SlidingEventTimeWindows.of(Time.milliseconds(5000), Time.milliseconds(2000)))

    streams.windowAll(SlidingProcessingTimeWindows.of(Time.seconds(5), Time.seconds(2)))
      .apply(new MyWindowFunction)
      .print()

    env.execute("test windows")
  }
}

case class Click(id: Int, event: String, ts: Long, age: Int)