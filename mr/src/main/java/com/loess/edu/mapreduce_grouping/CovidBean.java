package com.loess.edu.mapreduce_grouping;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CovidBean implements WritableComparable<CovidBean> {
    private String state;
    private int cases;

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

    @Override
    public String toString() {
        return  state + "\t" + cases ;
    }

    /*
      指定比较器规则:
        1:州名相同，则按照确诊病例数进行降序排序
     */
    @Override
    public int compareTo(CovidBean o) {
        int result = this.getState().compareTo(o.getState()); //这里的compareTo是String类的
        if(result == 0){
            return (this.getCases() - o.getCases()) * -1;
        }
        return result;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(state);
        dataOutput.writeInt(cases);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.state = dataInput.readUTF();
        this.cases = dataInput.readInt();
    }
}
