package com.loess.edu.covid;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

/*
  1:在MR中，如果有自定义类，则该类必须要能够被序列化,实现Writable接口
 */
public class CovidCountBean implements Writable {
    private int  cases;//累计确诊病例数
    private int  deaths;//累计死亡病例数

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return cases + "\t" + deaths ;
    }

    //序列化方法
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(cases);
        dataOutput.writeInt(deaths);
    }

    //反序列化方法:反序列化的顺序一定要和序列化顺序一致
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.cases = dataInput.readInt();
        this.deaths = dataInput.readInt();
    }
}
