package com.loess.edu.mapreduce_sort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CovidSortReducer extends Reducer<CovidBean, NullWritable,CovidBean,NullWritable> {
    @Override
    protected void reduce(CovidBean key, Iterable<NullWritable> values, Reducer<CovidBean, NullWritable, CovidBean, NullWritable>.Context context) throws IOException, InterruptedException {
        context.write(key,NullWritable.get());
    }
}
