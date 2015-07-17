package com.cerner.intern.traqr.core;


import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created on 7/15/15.
 */
public class GraphBuilderTest {

    //Graph from wikipedia
    @Test
    public void testBuildGraph_test1() {
        Location a = new Location(1, "Place a");
        Location b = new Location(2, "Place b");
        Location c = new Location(3, "Place c");
        Location d = new Location(4, "Place d");

        List<Location> locationsList = Arrays.asList(a, b, c, d);
        Map<Integer, Location> locations = new HashMap<>();
        locationsList.forEach(l->locations.put(l.getId(), l));


        Connection aToB = new Connection(1, "A to B", a, b, Duration.ofMinutes(1));
        Connection bToC = new Connection(2, "B to C", b, c, Duration.ofMinutes(2));
        Connection cToD = new Connection(3, "C to D", c, d, Duration.ofMinutes(3));
        Connection aToD = new Connection(4, "A to D", a, d, Duration.ofMinutes(4));

        List<Connection> connectionsList = Arrays.asList(aToB, bToC, cToD, aToD);
        Map<Integer, Connection> connections = new HashMap<>();
        connectionsList.forEach(con->connections.put(con.getId(), con));

        GraphBuilder.buildGraph(locations, connections);

        Trip trip = PathFinder.findPath(locations.get(1), locations.get(3));
        assertEquals(a, trip.getConnections().get(0).getStart());
        assertEquals(c, trip.getConnections().get(1).getEnd());
        assertEquals(2, trip.getConnections().size());
    }

    //from columbia.edu
    @Test
    public void testBuildGraph_test2() {
        Location s = new Location(1, "s");
        Location a = new Location(2, "a");
        Location b = new Location(3, "b");
        Location c = new Location(4, "c");
        Location f = new Location(5, "f");
        Location t = new Location(6, "t");

        List<Location> locationsList = Arrays.asList(s, a, b, c, f, t);
        Map<Integer, Location> locations = new HashMap<>();
        locationsList.forEach(l->locations.put(l.getId(), l));

        Connection sToA = new Connection(1, "description", s, a, Duration.ofMinutes(3));
        Connection sToB = new Connection(2, "description", s, b, Duration.ofMinutes(4));
        Connection aToB = new Connection(6, "description", a, b, Duration.ofMinutes(6));
        Connection aToC = new Connection(4, "description", a, c, Duration.ofMinutes(2));
        Connection aTof = new Connection(5, "description", a, f, Duration.ofMinutes(7));
        Connection bToF = new Connection(6, "description", b, f, Duration.ofMinutes(5));
        Connection cToF = new Connection(7, "description", c, f, Duration.ofMinutes(1));
        Connection cToT = new Connection(8, "description", c, t, Duration.ofMinutes(8));
        Connection fToT = new Connection(9, "description", f, t, Duration.ofMinutes(4));

        List<Connection> connectionsList = Arrays.asList(sToA, sToB, aToB, aToC, aTof, bToF, cToF, cToT, fToT);
        Map<Integer, Connection> connections = new HashMap<>();
        connectionsList.forEach(con->connections.put(con.getId(), con));

        GraphBuilder.buildGraph(locations, connections);

        Trip trip = PathFinder.findPath(s, t);

        assertEquals(4, trip.getConnections().size());
        assertEquals(s, trip.getConnections().get(0).getStart());
        assertEquals(a, trip.getConnections().get(0).getEnd());
        assertEquals(f, trip.getConnections().get(3).getStart());
        assertEquals(t, trip.getConnections().get(3).getEnd());


    }
}
