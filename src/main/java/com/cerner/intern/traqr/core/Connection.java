package com.cerner.intern.traqr.core;

import java.time.Duration;

/**
 * Created on 7/14/15.
 */
public class Connection {

    private int id; //user does not see
    private String description;
    private Location start;
    private Location end;
    private Duration estimatedTime;

    public Connection(int id, String description, Location start, Location end, Duration estimatedTime) {
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

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    public String getNiceDuration() {
        if (estimatedTime.toHours() >0) {
            return estimatedTime.toHours() + " hours, " + (estimatedTime.toMinutes()-estimatedTime.toHours()*60) + " minutes";
        }
        else
        {
            return estimatedTime.toMinutes() + " minutes";
        }
    }

    public String toString() {
        return "CONNECTION {"+"Start: " + start + "end: " +end +"}-DURATION:"+estimatedTime;
    }
}
