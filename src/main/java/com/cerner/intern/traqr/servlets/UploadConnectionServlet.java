package com.cerner.intern.traqr.servlets;

import com.cerner.intern.traqr.core.Connection;
import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.db.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Map;

/**
 * Created on 7/15/15.
 */
public class UploadConnectionServlet extends HttpServlet {

    private final Map<Integer, Location> locations;
    private final Map<Integer, Connection> connections;

    public UploadConnectionServlet(Map<Integer, Location> locations, Map<Integer, Connection> connections) {
        this.locations = locations;
        this.connections = connections;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("PARAMETERS: "+request.getParameterMap());
        int fromLocationId = Integer.parseInt(request.getParameter("fromLocation"));
        int toLocationId = Integer.parseInt(request.getParameter("toLocation"));
        String description = request.getParameter("description"); //TODO: db uses prepared statements?
        Duration durationInMinutes = Duration.ofMinutes(Integer.parseInt(request.getParameter("minutes")));

        Location start = locations.get(fromLocationId);
        Location end = locations.get(toLocationId);

        try {
            Connection connection = Database.insertConnection(description, start, end, durationInMinutes);
            start.connections.add(connection);
            connections.put(connection.getId(), connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
