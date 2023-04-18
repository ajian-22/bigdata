package com.loess.edu.mapreduce_sort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/*

 */
public class CovidBean implements WritableComparable<CovidBean> {
    private String state; //州
    private int cases;    //累计确诊病例数
    private int deaths;   //累计死亡病例数

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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
        return state + "\t" + cases + "\t" + deaths ;
    }

    /*
       制定排序规则：
         1:先按照确诊病例数排序（降序）
         2:如果确诊病例数相同，则按照死亡病例数排序(升序)
     */
    @Override
    public int compareTo(CovidBean o) {
        //1:先比较两个对象的确诊病例数
        int result = this.getCases() - o.getCases();
        //2:如果确诊病例数相同，则比较死亡病例数
        if(result == 0){
            return  this.getDeaths() - o.getDeaths(); //升序
        }

        return result * -1;
    }

    //序列化方法
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(state);
        dataOutput.writeInt(cases);
        dataOutput.writeInt(deaths);
    }

    //反序列方法
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.state = dataInput.readUTF();
        this.cases = dataInput.readInt();
        this.deaths = dataInput.readInt();
    }
}
