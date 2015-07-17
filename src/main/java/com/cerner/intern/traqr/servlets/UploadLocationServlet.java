package com.cerner.intern.traqr.servlets;

import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.db.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created on 7/15/15.
 */
@MultipartConfig
public class UploadLocationServlet extends HttpServlet {

    private final Map<Integer, Location> locations;

    public UploadLocationServlet(Map<Integer, Location> locations) {
        this.locations = locations;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        System.out.println(request.getParameterMap());
        String name = request.getParameter("name"); //todo: db uses prepared statements??
        System.out.println("NAME HERE: "+name);
        try {
            Location l = Database.insertLocation(name);
            locations.put(l.getId(), l);
            System.out.println(l);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //TODO: ADD TO LIST OF LOCATIONS AND DB -- NEED DB TO KNOW ID
        //TODO: FORWARD ON TO QR PAGE???
    }

}
