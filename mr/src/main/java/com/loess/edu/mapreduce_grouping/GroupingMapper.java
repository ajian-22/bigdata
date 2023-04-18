package com.loess.edu.mapreduce_grouping;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class GroupingMapper extends Mapper<LongWritable, Text,CovidBean,Text> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, CovidBean, Text>.Context context) throws IOException, InterruptedException {
        //1:得到K2
        String[] split = value.toString().split(",");
        CovidBean k2 = new CovidBean();
        k2.setState(split[2]);
        k2.setCases(Integer.parseInt(split[4]));

        //2:得到V2,V2就是V1
        //3:将K2和V2写入上下文中
        context.write(k2,value);
    }
}
