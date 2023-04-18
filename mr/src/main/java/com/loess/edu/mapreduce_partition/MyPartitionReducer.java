package com.loess.edu.mapreduce_partition;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MyPartitionReducer extends Reducer<Text,Text, NullWritable,Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
        //1:得到K3，遍历V2集合，集合中的每个元素就是K3
        //2:得到V3，就是NullWritable
        //3:将K3和V3写入上下中
        for (Text value : values) {
            context.write(NullWritable.get(),value);
        }
    }
}
