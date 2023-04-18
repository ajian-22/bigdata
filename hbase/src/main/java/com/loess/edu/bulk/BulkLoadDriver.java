package com.loess.edu.bulk;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

public class BulkLoadDriver {
    public static void main(String[] args) throws Exception{

        //1. 创建Job对象
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","node1:2181,node2:2181,node3:2181");
        Job job = Job.getInstance(conf);

        //2. 设置提交Yarn的必备参数
        job.setJarByClass(BulkLoadDriver.class);

        //3. 设置MR的天龙八大步
        // 3.1 设置输入类和输入的路径
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,new Path("hdfs://node1:8020/hbase/bulkload/input/bank_record.csv"));

        // 3.2 设置Mapper类 和 输出 k2和v2 的类型
        job.setMapperClass(BulkLoadMapper.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(Put.class);


        //3.3 设置Shuffle: 3.3 分区 3.4 排序 3.5规约 3.6分组  (全部都是默认值)

        // 3.7 设置Reudce 和 输出 k3和v3的类型: 没有Reduce
        job.setNumReduceTasks(0);

        // 建议: 不管有没有reduce, 都设置k3和v3 的类型, 如果没有reduce, 直接使用k2和v2的类型
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(Put.class);

        // 3.8 设置输出类 和 输出的路径: 输出HFile文件格式
        // 3.8.1 设置HFile文件格式输出类
        job.setOutputFormatClass(HFileOutputFormat2.class);

        // 3.8.2 设置HFile的相关信息: 表信息 和 Region信息
        Connection hbaseConn = ConnectionFactory.createConnection(conf);
        Table table = hbaseConn.getTable(TableName.valueOf("TRANSFORM_RECORD"));
        HFileOutputFormat2.configureIncrementalLoad(job,table,hbaseConn.getRegionLocator(TableName.valueOf("TRANSFORM_RECORD")));

        // 3.8.3 设置输出路径
        HFileOutputFormat2.setOutputPath(job,new Path("hdfs://node1:8020/hbase/bulkload/output"));

        // 4 提交任务
        boolean flag = job.waitForCompletion(true);

        System.exit(flag ? 0:1);

    }
}