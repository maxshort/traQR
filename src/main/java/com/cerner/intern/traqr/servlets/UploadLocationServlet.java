package com.cerner.intern.traqr.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.db.Database;
import com.cerner.intern.traqr.util.CustomConfigs;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created on 7/15/15.
 */
@MultipartConfig
public class UploadLocationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 480699709350892417L;
	private final Map<Integer, Location> locations;

	public UploadLocationServlet(Map<Integer, Location> locations) {
		this.locations = locations;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Configuration config = CustomConfigs.XSS_SAFE_CONFIG;

		Template template = config.getTemplate("addLocation.ftl");

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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		System.out.println(request.getParameterMap());
		String name = request.getParameter("name"); // todo: db uses prepared
													// statements??
		System.out.println("NAME HERE: " + name);
		try {
			Location l = Database.insertLocation(name);
			locations.put(l.getId(), l);
			Configuration config = CustomConfigs.XSS_SAFE_CONFIG;

			Template template = config.getTemplate("locationSuccess.ftl");
			Map<String, Object> root = new HashMap<>();
			root.put("location", l);
			try {
				template.process(root, response.getWriter());
			} catch (TemplateException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		// TODO: ADD TO LIST OF LOCATIONS AND DB -- NEED DB TO KNOW ID
		// TODO: FORWARD ON TO QR PAGE???
	}

}
