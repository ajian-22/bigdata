package com.loess.edu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student implements Serializable {
    // 1,aaa,male,90
    private int id;
    private String name;
    private String gender;
    private double score;
}
