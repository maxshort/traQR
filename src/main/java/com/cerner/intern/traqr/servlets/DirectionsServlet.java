package com.cerner.intern.traqr.servlets;

import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.core.PathFinder;
import com.cerner.intern.traqr.core.Trip;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created on 7/15/15.
 */
public class DirectionsServlet extends HttpServlet{

    private Map<Integer, Location> locations;

    public DirectionsServlet(Map<Integer, Location> locations) {
        this.locations = locations;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {



        if (request.getParameter("to") != null) { //full trip
            int fromId = Integer.parseInt(request.getParameter("from"));
            int toId = Integer.parseInt(request.getParameter("to"));
            Location tripStart = locations.get(fromId);
            Location tripEnd = locations.get(toId);
            Trip trip = PathFinder.findPath(tripStart, tripEnd);
            try {
                process(response.getWriter(), trip, tripStart, tripEnd);
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            }

        }

        else if (request.getParameter("from") != null) {
            int fromId = Integer.parseInt(request.getParameter("from"));
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
            throw new UnsupportedOperationException("NO from or to!");
        }

    }

    public static void process(Writer out, Trip trip, Location start, Location end) throws IOException, TemplateException {
        Configuration config = new Configuration(Configuration.VERSION_2_3_22);
        config.setDirectoryForTemplateLoading(new File("/Users/ms035644/Documents/traqr/src/main/resources/"));
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> root = new HashMap<>();
        root.put("tripStart", start);
        root.put("tripEnd", end);


        //Location nodeA = new Location(1, "nodeA");
        //Location nodeB = new Location(2, "nodeB");
        //Location nodeC = new Location(3, "nodeC");
        //Connection connAB = new Connection(1, "Head North 40 feet", nodeA, nodeB, Duration.ofMinutes(2));
        //Connection connBC = new Connection(1, "Turn left, walk 10 feet", nodeB, nodeC, Duration.ofMinutes(1));

        //Trip testTrip = new Trip(Arrays.asList(connAB, connBC));

        root.put("trip", trip);

        Template template = config.getTemplate("directions.ftl");

        template.process(root, out);
    }

    public static void processFromOnly(Writer out, Location start, List<Location> locationsWithoutStart) throws IOException, TemplateException {
        Configuration config = new Configuration(Configuration.VERSION_2_3_22);
        config.setDirectoryForTemplateLoading(new File("/Users/ms035644/Documents/traqr/src/main/resources/"));
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> root = new HashMap<>();
        root.put("tripStart", start);

        root.put("locationsWithoutStart", locationsWithoutStart);

        config.setDirectoryForTemplateLoading(new File("/Users/ms035644/Documents/traqr/src/main/resources/"));
        config.setDefaultEncoding("UTF-8") ;
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template = config.getTemplate("fromOnly.ftl");
        template.process(root, out);
    }



}
