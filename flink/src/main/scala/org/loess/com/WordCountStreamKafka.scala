package org.loess.com

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.configuration.{Configuration, RestOptions}
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.kafka.clients.consumer.OffsetResetStrategy

object WordCountStreamKafka {
  def main(args: Array[String]): Unit = {
    val conf = new Configuration()
    conf.setInteger(RestOptions.PORT, 9898)

    val env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf)
    env.setParallelism(2)

    val kafkaSource = KafkaSource.builder[String]()
      .setBootstrapServers("node1:9092,node2:9002,node3:9003")
      .setTopics("demo-topic")
      .setGroupId("demo-group")
      .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.LATEST))
      .setValueOnlyDeserializer(new SimpleStringSchema())
      .build()

    import org.apache.flink.api.scala._
    env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "demo Kafka Source")
      .flatMap(_.split(" "))
      .map(word => (word, 1))
      .keyBy(_._1)
      .sum(1)
      .print()
    env.execute("wordcount-scala kafka source streaming ...")
  }
}
