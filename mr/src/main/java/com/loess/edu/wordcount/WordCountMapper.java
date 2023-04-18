package com.loess.edu.wordcount;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
/*
    1:继承Mapper类
       四个泛型：K1,V1,K2,V2 类型
 */

public class WordCountMapper extends Mapper<LongWritable, Text,Text,LongWritable> {
    /*
        2:重写map方法,在该方法中将K1、V1转为K2和V2
           key   :就是K1
           value ：就是V1
           Context：表示上下文对象
     */

    @Override
    protected void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException {
        //获取计数器
        Counter counter = context.getCounter("My Counter Group", "My Counter");

        //1:得到K2：拆分V1
        String[] split = value.toString().split(",");
        //2:得到V2:V2就是固定值1
        //3:将K2和V2写入上下文（下一个处理环节）
        for (String k2 : split) {
            if(k2.equals("sb")){
                //将计数器加一
                counter.increment(1L);
            }
            context.write(new Text(k2),new LongWritable(1));
        }
    }
}
