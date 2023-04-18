package com.loess.edu.mapreduce_partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

//1:自定义类继承Partitioner类
//两个泛型就是K2和V2类型
public class MyPartitioner extends Partitioner<Text,Text> {

    //2:重写getPartition方法，在该方法中对每一个K2和V2打标记

    /**
     *
     * @param text  K2
     * @param text2 V2
     * @param i  Reduce个数
     * @return
     */
    @Override
    public int getPartition(Text text, Text text2, int i) {
//        if(text.toString().equals("Alabama")){
//            return 0;  //打0编号
//        }else  if (text.toString().equals("Alaska")){
//            return 1; //打1编号
//        }else if (text.toString().equals("Arizona")) {
//            return 2;n
//        }else{
//            return 3;
//        }
        return (text.toString().hashCode() & 2147483647) % i;
    }
}
