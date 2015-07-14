package com.cerner.intern.traqr.core;

import java.time.Duration;

/**
 * Created on 7/14/15.
 */
public class Connection {

    private int id; //user does not see
    private String description;
    private ConnectedLocation start;
    private ConnectedLocation end;
    private Duration estimatedTime;

    public Connection(int id, String description, ConnectedLocation start, ConnectedLocation end, Duration estimatedTime) {
        this.id = id;
        this.description = description;
        this.start = start;
        this.end = end;
        this.estimatedTime = estimatedTime;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ConnectedLocation getStart() {
        return start;
    }

    public ConnectedLocation getEnd() {
        return end;
    }

    public Duration getEstimatedTime() {
        return estimatedTime;
    }
}
