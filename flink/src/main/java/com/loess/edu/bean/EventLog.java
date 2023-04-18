package com.loess.edu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventLog implements Serializable {
    private long guid;
    private String eventId;
    private String channel;
    private long timeStamp;
    private long stayLong;
}
