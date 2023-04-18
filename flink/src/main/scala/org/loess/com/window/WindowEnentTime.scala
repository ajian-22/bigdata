package org.loess.com.window

import org.apache.flink.api.common.eventtime.{SerializableTimestampAssigner, WatermarkStrategy}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

import java.time.Duration

object WindowEnentTime {
  def main(agrs: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment


    //水印策略定义
    val strategy = WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(2))
      .withTimestampAssigner(new SerializableTimestampAssigner[Click] {
        override def extractTimestamp(element: Click, recordTimestamp: Long): Long = element.ts
      })

    val stream1: DataStream[Click] = env.socketTextStream("localhost", 9999)
      .map(line => {
        val strs = line.split(",")
        Click(strs(0).toInt, strs(1), strs(2).toLong, strs(3).toInt)
      }).assignTimestampsAndWatermarks(strategy)


    stream1.windowAll(TumblingEventTimeWindows.of(Time.seconds(5))).sum("age").print("TumblingEventTimeWindows")
  }
}
