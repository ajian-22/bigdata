package com.loess.edu.bulk;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class BulkLoadMapper extends Mapper<LongWritable,Text,ImmutableBytesWritable,Put> {
    private  ImmutableBytesWritable k2 = new ImmutableBytesWritable();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1. 获取一行数据
        String line = value.toString();

        //2. 判断数据是否为空
        if(line != null && !"".equals(line.trim())){

            //3. 执行数据切割操作, 将其转换为k2和v2
            String[] fields = line.split(",");

            // 3.1 封装k2(rowkey)
            k2.set(fields[0].getBytes());
            //3.2 封装v2 一行数据
            Put v2 = new Put(fields[0].getBytes());
            v2.addColumn("C1".getBytes(),"code".getBytes(),fields[1].getBytes());
            v2.addColumn("C1".getBytes(),"rec_account".getBytes(),fields[2].getBytes());
            v2.addColumn("C1".getBytes(),"rec_bank_name".getBytes(),fields[3].getBytes());
            v2.addColumn("C1".getBytes(),"rec_name".getBytes(),fields[4].getBytes());
            v2.addColumn("C1".getBytes(),"pay_account".getBytes(),fields[5].getBytes());
            v2.addColumn("C1".getBytes(),"pay_name".getBytes(),fields[6].getBytes());
            v2.addColumn("C1".getBytes(),"pay_comments".getBytes(),fields[7].getBytes());
            v2.addColumn("C1".getBytes(),"pay_channel".getBytes(),fields[8].getBytes());
            v2.addColumn("C1".getBytes(),"pay_way".getBytes(),fields[9].getBytes());
            v2.addColumn("C1".getBytes(),"status".getBytes(),fields[10].getBytes());
            v2.addColumn("C1".getBytes(),"timestamp".getBytes(),fields[11].getBytes());
            v2.addColumn("C1".getBytes(),"money".getBytes(),fields[12].getBytes());

            //4. 写出去
            context.write(k2,v2);

        }

    }
}