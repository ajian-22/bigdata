package com.loess.edu.covid;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CovidReducer extends Reducer<Text,CovidCountBean,Text,CovidCountBean> {
    @Override
    protected void reduce(Text key, Iterable<CovidCountBean> values, Reducer<Text, CovidCountBean, Text, CovidCountBean>.Context context) throws IOException, InterruptedException {
        //1:得到K3，K2就是K3
        //2:得到V3，遍历V2集合，分别对累计确诊数和死亡数进行求和,并封装新的CovidCountBean对象
        int casesTotal = 0;
        int deathsTotal = 0;
        for (CovidCountBean value : values) {
            casesTotal += value.getCases();
            deathsTotal += value.getDeaths();
        }
        CovidCountBean v3 = new CovidCountBean();
        v3.setCases(casesTotal);
        v3.setDeaths(deathsTotal);

        //3:将K3和V3写入上下文中
        context.write(key,v3);
    }
}
