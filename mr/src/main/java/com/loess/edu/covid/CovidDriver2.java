package com.loess.edu.covid;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;

public class CovidDriver2 extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        //配置文件对象

        // 创建作业实例
        Job job = Job.getInstance(getConf(), "covidcount");
        // 设置作业驱动类(不加该参数，有的电脑系统会报错)
        job.setJarByClass(CovidDriver.class);

        // 设置作业mapper reducer类
        job.setMapperClass(CovidMapper.class);
        job.setReducerClass(CovidReducer.class);

        // 设置作业mapper阶段输出key(K2) value(V2)数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(CovidCountBean.class);

        //设置作业reducer阶段输出key(K3) value(V3)数据类型 也就是程序最终输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(CovidCountBean.class);

        // 配置作业的输入数据路径
//        FileInputFormat.addInputPath(job,new Path("file:///D:\\input\\wordcount"));
//        FileInputFormat.addInputPath(job,new Path("hdfs://node1:8020/input/wordcount"));
        FileInputFormat.addInputPath(job,new Path(args[0]));

        // 配置作业的输出数据路径,该路径不能存在，否则报错
        Path outputPath = new Path(args[1]);
        FileOutputFormat.setOutputPath(job,outputPath);

        //判断输出路径是否存在 如果存在删除
        FileSystem fileSystem = FileSystem.get(new URI("file:///"),getConf());
//        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:8020"),configuration);
        boolean bl = fileSystem.exists(outputPath);
        if(bl){
            fileSystem.delete(outputPath,true);
        }
        // 提交作业并等待执行完成

        //退出任务进程
        return   job.waitForCompletion(true) ? 0 :1;
    }

    public static void main(String[] args) throws Exception {
        //配置文件对象
        Configuration conf = new Configuration();
        //使用工具类ToolRunner提交程序
        int status = ToolRunner.run(conf, new CovidDriver2(), args);
        //退出客户端程序 客户端退出状态码和MapReduce程序执行结果绑定
        System.exit(status);
    }
}
