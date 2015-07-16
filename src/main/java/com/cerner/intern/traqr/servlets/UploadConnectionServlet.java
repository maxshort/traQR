package com.cerner.intern.traqr.servlets;

import com.cerner.intern.traqr.core.Connection;
import com.cerner.intern.traqr.core.Location;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * Created on 7/15/15.
 */
public class UploadConnectionServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        int fromLocationId = Integer.parseInt(request.getParameter("fromLocation"));
        int toLocationId = Integer.parseInt(request.getParameter("toLocation"));
        String description = request.getParameter("description"); //TODO: db uses prepared statements?
        Duration durationInMinutes = Duration.ofMinutes(Integer.parseInt(request.getParameter("minutes")));

        Location one = null; //TODO: look this up somehow from id
        Location two = null; //TODO: look this up somehow from id

        //TODO: have to give to db first in order to get id back...
        Connection connection = new Connection(42, description, one, two, durationInMinutes);

    }
}
