package com.cerner.intern.traqr.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.io.IOUtils;

import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.util.CustomConfigs;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import generator.pdfGenerator;

/**
 * Created on 7/15/15.
 */
public class QRServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1239481011027878347L;
	private final Map<Integer, Location> locations;

	public QRServlet(Map<Integer, Location> locations) {
		this.locations = locations;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String requestLocation = request.getParameter("location");
		if (requestLocation != null) {
			int id = Integer.parseInt(request.getParameter("location"));
			Location location = locations.get(id);
			try {
				File file = pdfGenerator.createPDF(location.getName(), id);
				IOUtils.copy(new FileInputStream(file), response.getOutputStream());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			// TODO: PASS TO QR GENERATOR, GET BACK QR PAGE...
		} else {
			Configuration config = CustomConfigs.XSS_SAFE_CONFIG;

			Template template = config.getTemplate("allLocations.ftl");

			Map<String, Object> root = new HashMap<>();
			root.put("locations", locations.values());

			try {
				template.process(root, response.getWriter());
			} catch (TemplateException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

}
