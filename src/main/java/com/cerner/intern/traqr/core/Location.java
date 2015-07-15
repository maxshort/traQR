package com.cerner.intern.traqr.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/14/15.
 */
public class Location {

    private final int id;
    private final String name;
    public final List<Connection> connections = new ArrayList<>();

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "name: " + getName() + ", id: " + getId();
    }
}
