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

    public static List <Location> testGraph() {
        Location a = new Location(1, "Place a");
        Location b = new Location(2, "Place b");
        Location c = new Location(3, "Place c");
        Location d = new Location(4, "Place d");

        List<Location> locations = Arrays.asList(a, b, c, d);

        Connection aToB = new Connection(1, "A to B", a, b, Duration.ofMinutes(1));
        Connection bToC = new Connection(2, "B to C", b, c, Duration.ofMinutes(2));
        Connection cToD = new Connection(3, "C to D", c, d, Duration.ofMinutes(3));
        Connection aToD = new Connection(4, "A to D", a, d, Duration.ofMinutes(4));

        List<Connection> connections = Arrays.asList(aToB, bToC, cToD, aToD);

        buildGraph(locations, connections);
        return locations;
    }

    public static void main(String[] args) {
        List<Location> testLocations = testGraph();
        System.out.println(testLocations);



        System.out.println(PathFinder.findPath(testLocations.get(0), testLocations.get(2)));
    }
}
