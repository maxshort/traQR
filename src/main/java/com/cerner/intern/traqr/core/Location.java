package com.cerner.intern.traqr.core;

/**
 * Created on 7/14/15.
 */
public class Location {

    private final int id;
    private final String name;

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
}
