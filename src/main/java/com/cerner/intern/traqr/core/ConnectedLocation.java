package com.cerner.intern.traqr.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/14/15.
 */
public class ConnectedLocation extends Location {

    protected List<Connection> connections;

    //might be a factory later... don't know how to deal w/ not having an id yet
    public ConnectedLocation(int id, String name, List<Connection> connections) {
        super(id, name);
        this.connections = new ArrayList<>(connections);
    }

    public List<Connection> getConnections() {
        return new ArrayList<>(connections);
    }
}
