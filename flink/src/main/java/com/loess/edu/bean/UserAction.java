package com.loess.edu.bean;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserAction implements Serializable {
    // 2,1238598834956,pageview,20,weixin
    private long guid;
    private long actionTime;
    private String eventId;
    private long actionStaylong;
    private String channel;
}

