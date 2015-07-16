package com.cerner.intern.traqr.core;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 7/14/15.
 */
public class GraphBuilder
{
    //will modify the points passed in!
    public static void buildGraph(List<Location> points, List<Connection> connections) {


        connections.stream()
                .forEach(c->c.getStart().connections.add(c));

    }

}
