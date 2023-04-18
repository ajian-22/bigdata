package com.loess.edu.wordcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
  1:自定义类继承Reducer类
       四个泛型：K2 ，V2，K3，V3 类型
 */
public class WordCountReducer extends Reducer<Text, LongWritable,Text,LongWritable> {
    /*
       2:重写reduce方法,在该方法中将K2,[V2]转为K3和V3
         key:K2
         values: [V2]
     */


    @Override
    protected void reduce(Text key, Iterable<LongWritable> values,Context context) throws IOException, InterruptedException {
        //1:得到K3，K2就是K3
        //2:得到V3，将[V2]遍历，将值相加，则得到V3
        long count = 0;
        for (LongWritable value : values) {
            count += value.get();
        }

        //3:将K3和V3写入上下文中
        context.write(key,new LongWritable(count));
    }
}
