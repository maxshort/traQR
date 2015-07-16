package com.cerner.intern.traqr.servlets;

import com.cerner.intern.traqr.core.Location;
import generator.pdfGenerator;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created on 7/15/15.
 */
public class QRServlet extends HttpServlet {

    private final Map<Integer, Location> locations;

    public QRServlet(Map<Integer, Location> locations) {
        this.locations = locations;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("location"));
        Location location = locations.get(id);
        try {
            File file = pdfGenerator.createPDF(location.getName(), id);
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //TODO: PASS TO QR GENERATOR, GET BACK QR PAGE...
    }

}
