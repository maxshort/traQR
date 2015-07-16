package com.cerner.intern.traqr.servlets;

import com.cerner.intern.traqr.core.Location;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 7/15/15.
 */
public class UploadLocationServlet extends HttpServlet {

    //TODO: TAKE IN GLOBAL LIST OF LOCATIONS

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name"); //todo: db uses prepared statements??

        Location l = new Location(123, name);
        //TODO: ADD TO LIST OF LOCATIONS AND DB -- NEED DB TO KNOW ID
        //TODO: FORWARD ON TO QR PAGE???
    }

}
