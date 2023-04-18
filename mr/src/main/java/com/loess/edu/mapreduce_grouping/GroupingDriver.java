package com.loess.edu.mapreduce_grouping;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;

public class GroupingDriver {
    public static void main(String[] args) throws Exception {
        //配置文件对象
        Configuration configuration = new Configuration();

        //mapred-site.xml

        configuration.set("mapreduce.input.fileinputformat.split.minsize","262144");
//        configuration.set("mapreduce.input.fileinputformat.split.maxsize","5888");

        // 创建作业实例
        Job job = Job.getInstance(configuration, "mygrouping");
        // 设置作业驱动类(不加该参数，有的电脑系统会报错)
        job.setJarByClass(GroupingDriver.class);

        // 设置作业mapper reducer类
        job.setMapperClass(GroupingMapper.class);
        job.setReducerClass(GroupingReducer.class);

        // 设置作业mapper阶段输出key(K2) value(V2)数据类型
        job.setMapOutputKeyClass(CovidBean.class);
        job.setMapOutputValueClass(Text.class);

        //设置作业reducer阶段输出key(K3) value(V3)数据类型 也就是程序最终输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);


        //设置自定义分组类
        job.setGroupingComparatorClass(MyGrouping.class);

        // 配置作业的输入数据路径
//        FileInputFormat.addInputPath(job,new Path("file:///D:\\input\\wordcount"));
//        FileInputFormat.addInputPath(job,new Path("hdfs://node1:8020/input/wordcount"));
        FileInputFormat.addInputPath(job, new Path(args[0]));

        // 配置作业的输出数据路径,该路径不能存在，否则报错
        Path outputPath = new Path(args[1]);
        FileOutputFormat.setOutputPath(job, outputPath);

        //判断输出路径是否存在 如果存在删除
        FileSystem fileSystem = FileSystem.get(new URI("file:///"), configuration);
//        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:8020"),configuration);
        boolean bl = fileSystem.exists(outputPath);
        if (bl) {
            fileSystem.delete(outputPath, true);
        }
        // 提交作业并等待执行完成
        //job.submit(); //第一代写法，但是不能详尽的输出运行过程
        boolean flag = job.waitForCompletion(true);//第二代，true表示一直监控任务完成，并打印大量的运行过程日志
//程序退出
        //退出任务进程
        System.exit(flag ? 0 : 1);
    }
}
