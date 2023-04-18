package com.loess.edu.sink;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


/**
 * 读取用户的行为日志流（前一个项目的数据）
 *     生成日志
 *     配置flume采集数据写入 kafka
 *     用flink去消费 kafka
 *
 *     然后做计算
 *
 *     最终生成如下结果（用户画像标签）
 *        guid,首次登陆时间，最近一次登陆时间, 历史累计的搜索行为次数，最近1天的（自然天）访问次数,最近 1小时 的访问总时长（每5分钟更新一次），活跃等级（1,2,3）
 *      结果写入mysql中
 *
 *     结果更新到es或者hbase
 *
 *
 ==== >>>>>  开发解答：  ======= >>>>>>
 * 0.  log
 * 1 . flume采集配置
 a1.sources = r1
 a1.channels = c1

 a1.sources.r1.type = TAILDIR
 a1.sources.r1.channels = c1
 a1.sources.r1.filegroups = g1
 a1.sources.r1.filegroups.g1 = /root/moni_data/log/applog/app.access.log.2022-03-13

 a1.channels.c1.type = org.apache.flume.channel.kafka.KafkaChannel
 a1.channels.c1.kafka.bootstrap.servers = bigdata001:9092,bigdata002:9092,bigdata003:9092
 a1.channels.c1.kafka.topic = applog
 a1.channels.c1.parseAsFlumeEvent = false
 * 2. flume启动命令
 bin/flume-ng agent -c conf/ -f myconf/taildir-kfk.conf -n a1 -Dflume.root.logger=INFO,console

 * 3. 用kafka的命令行消费者去检查一下，数据是否成功进入kafka
 kafka-console-consumer.sh --topic applog --bootstrap-server bigdata001:9092,bigdata002:9092,bigdata003:9092

 * 4. 开始写flink程序，去读kafka，然后打印数据看，数据是否成功达到flink

 * 5. 开发画像标签需求


 */
public class UserProfileRealTimeTags {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        // 构造一个kafka 的source对象
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setTopics("applog")
                .setBootstrapServers("bigdata001:9092,bigdata002:9092,bigdata001:9093")
                .setGroupId("x001")
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setStartingOffsets(OffsetsInitializer.latest())
                .build();

        // 读kafka
        DataStreamSource<String> source = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kfk");
        // 把json数据转成pojo数据流
        source.print();
        env.execute();
    }
}
