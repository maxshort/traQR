package com.cerner.intern.traqr;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.cerner.intern.traqr.core.Connection;
import com.cerner.intern.traqr.core.GraphBuilder;
import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.db.Database;
import com.cerner.intern.traqr.servlets.DirectionsServlet;
import com.cerner.intern.traqr.servlets.QRServlet;
import com.cerner.intern.traqr.servlets.UploadConnectionServlet;
import com.cerner.intern.traqr.servlets.UploadLocationServlet;
import com.cerner.intern.traqr.util.CustomConfigs;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created on 7/14/15.
 */
public class Main {

	public static void main(String[] args) throws Exception {
		Server server = new Server(new InetSocketAddress("0.0.0.0", 8080));

		ServerConnector connector = new ServerConnector(server);
		// connector.setHost("127.0.0.1");
		connector.setPort(8080);

		server.setConnectors(new Connector[] { connector });

		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		server.setHandler(context);

		Map<Integer, Location> locations = Database.getAllLocationsById();
		Map<Integer, Connection> connections = Database.getAllConnectionsById();

		GraphBuilder.buildGraph(locations, connections);

		context.addServlet(new ServletHolder(new HelloServlet()), "/*");
		context.addServlet(new ServletHolder(new DirectionsServlet(locations)), "/directions/*");
		context.addServlet(new ServletHolder(new QRServlet(locations)), "/qr/*");
		context.addServlet(new ServletHolder(new UploadConnectionServlet(locations, connections)), "/connections/*");
		context.addServlet(new ServletHolder(new UploadLocationServlet(locations)), "/locations/*");

		// contextHandler.server.setHandler(new HelloServlet());

		server.start();
		server.join();
	}

	public static class HelloServlet extends HttpServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7836182019513680546L;

		@Override
		public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
				throws IOException, ServletException {
			httpServletResponse.setContentType("text/html;charset=utf-8");
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);

			Configuration config = CustomConfigs.XSS_SAFE_CONFIG;

			// Code for grabbing template from stream

			Template template = config.getTemplate("home.ftl");
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("title", "Welcome to TraQR");

			try {
				template.process(input, httpServletResponse.getWriter());
			} catch (TemplateException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

	public static class Person {
		private String name;
		private int age;

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}
	}

}
