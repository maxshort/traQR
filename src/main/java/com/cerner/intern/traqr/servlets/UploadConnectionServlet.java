package com.cerner.intern.traqr.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cerner.intern.traqr.core.Connection;
import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.db.Database;
import com.cerner.intern.traqr.util.CustomConfigs;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created on 7/15/15.
 */
public class UploadConnectionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1380725235847280919L;
	private final Map<Integer, Location> locations;
	private final Map<Integer, Connection> connections;

	public UploadConnectionServlet(Map<Integer, Location> locations, Map<Integer, Connection> connections) {
		this.locations = locations;
		this.connections = connections;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Configuration config = CustomConfigs.XSS_SAFE_CONFIG;

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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out.println("PARAMETERS: " + request.getParameterMap());
		int fromLocationId = Integer.parseInt(request.getParameter("fromLocation"));
		int toLocationId = Integer.parseInt(request.getParameter("toLocation"));
		String description = request.getParameter("description");

		// Integer input sanitizing
		String minutes = request.getParameter("minutes");
		int mins = 0;
		try {
			mins = Integer.parseInt(minutes);
		} catch (NumberFormatException nfe) {
			for (Character c : minutes.toCharArray()) {
				if (Character.isDigit(c)) {
					mins = (mins * 10) + Character.getNumericValue(c);
				} else {
					break;
				}
			}
		}

		Duration durationInMinutes = Duration.ofMinutes(mins);

		Location start = locations.get(fromLocationId);
		Location end = locations.get(toLocationId);

		try {
			Connection connection = Database.insertConnection(description, start, end, durationInMinutes);
			start.connections.add(connection);
			connections.put(connection.getId(), connection);
			Configuration config = CustomConfigs.XSS_SAFE_CONFIG;

			Template template = config.getTemplate("connectionSuccess.ftl");

			Map<String, Object> root = new HashMap<>();
			root.put("connection", connection);
			try {
				template.process(root, response.getWriter());
			} catch (TemplateException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
