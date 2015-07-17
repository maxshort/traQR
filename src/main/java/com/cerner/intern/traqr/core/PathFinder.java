package com.cerner.intern.traqr.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

/**
 * Created on 7/14/15.
 */
public class PathFinder {

    private static Logger logger = LoggerFactory.getLogger(PathFinder.class);

    public static Trip findPath(Location start, Location end) {
        Map<Location, Duration> minDurations = new HashMap<>();
        minDurations.put(start, Duration.ofSeconds(0));
        Map<Location, Connection> previousInShortest = new HashMap<>(); //the connection that connects the previous shortest path TO the connection


        Queue<Location> queue = new PriorityQueue<>(new LocationComparator(minDurations));
        queue.add(start);
        while (!queue.isEmpty()) {
            Location location = queue.poll();
            logger.info("Connections HERE, above for = "+location.connections);
            for (Connection connection:location.connections) {
                Duration timeToLocation = minDurations.get(location);
                Duration currentBestTimeToEnd = minDurations.get(connection.getEnd());

                //if we don't have a current best time or we can beat it
                if (currentBestTimeToEnd == null || timeToLocation.plus(connection.getEstimatedTime()).compareTo(currentBestTimeToEnd) <0) {
                    logger.info("I RAN = "+currentBestTimeToEnd);
                    minDurations.put(connection.getEnd(), timeToLocation.plus(connection.getEstimatedTime()));
                    previousInShortest.put(connection.getEnd(), connection);
                    queue.add(connection.getEnd()); //TODO: need to sort this out so we don't cast
                }
                else {
                    logger.info("DID NOT RUN"+currentBestTimeToEnd);
                }

            }
        }
        System.out.println("PREV IN SHORTEST: " + previousInShortest);
        return extractPath(start, end, previousInShortest);
    }


    public static Trip extractPath(Location start, Location end, Map<Location, Connection> previousInShortest) {
        logger.info("END: "+end);
        logger.info("PREVIOUS IN SHORTEST: "+ previousInShortest);
        List<Connection> reversedPath = new ArrayList<>(); //(Note that start and end are _not_ reversed w/i individual connections.
        Connection currentCon = previousInShortest.get(end);
        reversedPath.add(currentCon);
        logger.info("REVERSED PATH: "+reversedPath);
        while ((currentCon=previousInShortest.get(currentCon.getStart())) != null) {
            reversedPath.add(currentCon);
        }
        Collections.reverse(reversedPath);
        return new Trip(reversedPath);
    }

    //compares two connected locations based on how long they take from a source as specified by the passed in map
    //only reads from map
    //not thread-safe
    private static class LocationComparator implements Comparator<Location> {

        private Map<Location, Duration> durations;

        public LocationComparator(Map<Location, Duration> map) {
            this.durations = map;
        }

        @Override
        public int compare(Location loc1, Location loc2) {

            Duration loc1Dur = durations.get(loc1);
            Duration loc2Dur = durations.get(loc2);
            if (loc1Dur == null && loc2Dur == null) {
                return 0; //they both haven't been found yet so we don't now
            } else if (loc1Dur == null) { //loc2 is not
                return 1;
            } else if (loc2Dur == null) { //loc1 is not
                return -1;
            }
            else {
                return loc1Dur.minus(loc2Dur).getNano(); //TODO: WHY DOES IT ACCEPT THIS??
            }
        }

    }
}
