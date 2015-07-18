package com.cerner.intern.traqr.servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.core.PathFinder;
import com.cerner.intern.traqr.core.Trip;
import com.cerner.intern.traqr.util.CustomConfigs;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created on 7/15/15.
 */
public class DirectionsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4978910270722575160L;
	private Map<Integer, Location> locations;

	public DirectionsServlet(Map<Integer, Location> locations) {
		this.locations = locations;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (request.getParameter("toLocation") != null) { // full trip
			int fromId = Integer.parseInt(request.getParameter("fromLocation"));
			int toId = Integer.parseInt(request.getParameter("toLocation"));
			Location tripStart = locations.get(fromId);
			Location tripEnd = locations.get(toId);
			try {
				Trip trip = PathFinder.findPath(tripStart, tripEnd);
				process(response.getWriter(), trip, tripStart, tripEnd);
			} catch (TemplateException e) {
				throw new RuntimeException(e);
			}

		}

		else if (request.getParameter("fromLocation") != null) {
			int fromId = Integer.parseInt(request.getParameter("fromLocation"));
			Location start = locations.get(fromId);
			try {
				List<Location> locationsWithoutStart = new ArrayList<>(locations.values());
				locationsWithoutStart.remove(start);
				processFromOnly(response.getWriter(), start, locationsWithoutStart);
			} catch (TemplateException e) {
				throw new RuntimeException(e);
			}
		}

		else {
			process(response.getWriter());
		}

	}

	public void process(Writer out) throws IOException {
		Configuration config = CustomConfigs.XSS_SAFE_CONFIG;

		Template template = config.getTemplate("directionsLanding.ftl");

		Map<String, Object> root = new HashMap<>();
		root.put("locations", locations.values());

		try {
			template.process(root, out);
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void process(Writer out, Trip trip, Location start, Location end)
			throws IOException, TemplateException {
		Configuration config = CustomConfigs.XSS_SAFE_CONFIG;

		Map<String, Object> root = new HashMap<>();
		root.put("tripStart", start);
		root.put("tripEnd", end);

		// Location nodeA = new Location(1, "nodeA");
		// Location nodeB = new Location(2, "nodeB");
		// Location nodeC = new Location(3, "nodeC");
		// Connection connAB = new Connection(1, "Head North 40 feet", nodeA,
		// nodeB, Duration.ofMinutes(2));
		// Connection connBC = new Connection(1, "Turn left, walk 10 feet",
		// nodeB, nodeC, Duration.ofMinutes(1));

		// Trip testTrip = new Trip(Arrays.asList(connAB, connBC));

		root.put("trip", trip);

		Template template = config.getTemplate("directions.ftl");

		template.process(root, out);
	}

	public static void processFromOnly(Writer out, Location start, List<Location> locationsWithoutStart)
			throws IOException, TemplateException {
		Configuration config = CustomConfigs.XSS_SAFE_CONFIG;

		Map<String, Object> root = new HashMap<>();
		root.put("tripStart", start);

		root.put("locationsWithoutStart", locationsWithoutStart);

		Template template = config.getTemplate("fromOnly.ftl");
		template.process(root, out);
	}

}
