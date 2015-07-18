package com.cerner.intern.traqr.core;

import java.util.Map;

/**
 * Created on 7/14/15.
 */
public class GraphBuilder {
	// will modify the points passed in!
	public static void buildGraph(Map<Integer, Location> points, Map<Integer, Connection> connections) {

		connections.values().stream().forEach(c -> {
			// first 2 important b/c graph algo relies on reference equality
			// -HACKFEST MAGIC
			points.put(c.getStart().getId(), c.getStart());
			points.put(c.getEnd().getId(), c.getEnd());
			c.getStart().connections.add(c);
		});

		System.out.println("POINTS AFTER BUILd: " + points);

	}

}
