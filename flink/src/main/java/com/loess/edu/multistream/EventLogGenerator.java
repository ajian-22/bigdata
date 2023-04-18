package com.loess.edu.multistream;

import com.alibaba.fastjson.JSON;
import com.loess.edu.bean.EventLog;
import org.apache.commons.lang3.RandomUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class EventLogGenerator {
    public static void main(String[] args) throws InterruptedException {
        String[] channels = {"app", "wxapp", "h5", "web"};
        String[] events = {"pageview", "share", "addcart", "payment", "subscribe", "videoplay"};


        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "bigdata001:9092,bigdata002:9092,bigdata003:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);


        // {"guid":1,"eventId":"addCart","channel":"app","timeStamp":1646784001000,"stayLong":200}
        boolean flag = true;
        while (flag) {
            EventLog eventLog = new EventLog();

            eventLog.setGuid(RandomUtils.nextInt(1, 10));
            eventLog.setChannel(channels[RandomUtils.nextInt(0, channels.length)]);
            eventLog.setEventId(events[RandomUtils.nextInt(0, events.length)]);
            eventLog.setStayLong(RandomUtils.nextLong(100, 2000));
            eventLog.setTimeStamp(System.currentTimeMillis());

            String json = JSON.toJSONString(eventLog);

            ProducerRecord<String, String> record = new ProducerRecord<>("flink-exercise", json);
            producer.send(record);

            Thread.sleep(1000);
        }

        producer.flush();
    }
}
