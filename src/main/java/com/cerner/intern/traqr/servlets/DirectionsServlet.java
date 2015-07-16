package com.cerner.intern.traqr.servlets;

import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.core.PathFinder;
import com.cerner.intern.traqr.core.Trip;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
        int fromId = Integer.parseInt(request.getParameter("from"));

        if (request.getParameter("to") != null) {
            int toId = Integer.parseInt(request.getParameter("to"));
            //TODO: Give directions instead
            Trip trip = PathFinder.findPath(locations.get(fromId), locations.get(toId));
            response.getWriter().write("<h1>FROM:"+fromId+"TO:"+toId);

        }
        else {
            //TODO: give list of choices instead
            response.getWriter().write("<h1>JUST FROM:"+fromId);
        }



    }

}
