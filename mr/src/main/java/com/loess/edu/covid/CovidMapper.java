package com.loess.edu.covid;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CovidMapper extends Mapper<LongWritable, Text,Text,CovidCountBean> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, CovidCountBean>.Context context) throws IOException, InterruptedException {
        //1:得到K2,截取V1，获取索引为2的数据
        String[] split = value.toString().split(",");

        String k2 = split[2];

        //2:得到V2:截取V1,获取索引为4和5的字段数据，并封装到CovidCountBean对象
        CovidCountBean v2 = new CovidCountBean();
        v2.setCases(Integer.parseInt(split[4]));

//        if (split.length != 6){
//            v2.setDeaths(0);
//        }else{
//            v2.setDeaths(Integer.parseInt(split[5]));
//        }
        //如果索引为5的字段缺失，则按照0来处理
        v2.setDeaths(split.length == 6 ? Integer.parseInt(split[5]):0);

        //3:将K2和V2写入上下文中
        context.write(new Text(k2),v2);
    }
}
