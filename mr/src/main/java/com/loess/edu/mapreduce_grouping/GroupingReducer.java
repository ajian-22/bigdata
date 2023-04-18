package com.loess.edu.mapreduce_grouping;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class GroupingReducer extends Reducer<CovidBean, Text,Text, NullWritable> {

    @Override
    protected void reduce(CovidBean key, Iterable<Text> values, Reducer<CovidBean, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
//        Text k3 = values.iterator().next();
//
//        context.write(k3,NullWritable.get());

            int count = 0;
            for (Text value : values) {
                //1：得到K3，遍历集合中的第一条
                //2: 得到V3，NullWriable
                //3: 将K3和V3写入上下文中
                context.write(value, NullWritable.get());
                System.out.println(key + "---------"+ value);
                count++;
                if (count == 3) {
                    break;
                }
            }

    }
}
