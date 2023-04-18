package com.loess.edu.mapreduce_grouping;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

//1:自定义类继承WritableComparator类
public class MyGrouping extends WritableComparator {

    //2:在自定义类中定义无参构造，在无参构造中调用父类的构造方法
    public MyGrouping(){
        //将子类自定义分组的JavaBean类传给父类，并允许父类通过反射创建该JavaBean对象
        super(CovidBean.class,true);
    }

    //3:重写compare方法，在该方法中指定分组规则
    //该方法只看是否返回0，只要返回0，则传入的两个JavaBean就应该在同一组

//    public int compare(CovidBean a, CovidBean b) {
//        return a.getState().compareTo(b.getState()); //只比较CovidBean的州名是否相同，如果相同，则分到同一组
//    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        CovidBean cb1 = (CovidBean) a;
        CovidBean cb2 = (CovidBean) b;

        return cb1.getState().compareTo(cb2.getState()) ;
    }
}
