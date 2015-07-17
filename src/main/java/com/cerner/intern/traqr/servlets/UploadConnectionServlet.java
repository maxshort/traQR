package com.cerner.intern.traqr.servlets;

import com.cerner.intern.traqr.core.Connection;
import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.db.Database;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	Configuration config = new Configuration(Configuration.VERSION_2_3_22);
        config.setDirectoryForTemplateLoading(new File(System.getProperty("user.home") + "/Documents/GitHub/traqr/src/main/resources/"));
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        
        Template template = config.getTemplate("addConnection.ftl");
        
        Map<String, Object> root = new HashMap<>();
        root.put("locations", locations.values());
        
        try {
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        
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
