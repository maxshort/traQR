package com.cerner.intern.traqr.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 7/15/15.
 */
public class QRServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("location"));
        //TODO: PASS TO QR GENERATOR, GET BACK QR PAGE...
    }

}
