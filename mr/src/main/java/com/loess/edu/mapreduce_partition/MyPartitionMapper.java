package com.loess.edu.mapreduce_partition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MyPartitionMapper  extends Mapper<LongWritable, Text,Text,Text> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        //1:得到K2，拆分V1，得到州名，就是K2
        String k2 = value.toString().split(",")[2];
        //2:得到V2,V1就是V2
        //3:将K2和V2写入上下文中
        context.write(new Text(k2),value);
    }
}
