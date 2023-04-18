package com.loess.edu.mapreduce_sort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CovidSortMapper  extends Mapper<LongWritable, Text,CovidBean, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, CovidBean, NullWritable>.Context context) throws IOException, InterruptedException {
        //1:得到K2
        String[] split = value.toString().split("\t");
        CovidBean k2 = new CovidBean();
        k2.setState(split[0]);
        k2.setCases(Integer.parseInt(split[1]));
        k2.setDeaths(Integer.parseInt(split[2]));

        //2:得到V2，NullWritable

        //3:将K2和V2写入上下文中
        context.write(k2,NullWritable.get());

    }
}
