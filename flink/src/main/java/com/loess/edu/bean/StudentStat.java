package com.loess.edu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentStat implements Serializable {
    private String gender;
    private double minScore;
    private double maxScore;
    private double avgScore;
    private double userCount;
}
