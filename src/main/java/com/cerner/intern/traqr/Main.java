package com.cerner.intern.traqr;

import com.cerner.intern.traqr.servlets.DirectionsServlet;
import com.cerner.intern.traqr.core.Connection;
import com.cerner.intern.traqr.core.Location;
import com.cerner.intern.traqr.core.Trip;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.eclipse.jetty.client.HttpRequest;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 7/14/15.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServerConnector connector = new ServerConnector(server);
        connector.setHost("127.0.0.1");
        connector.setPort(8080);

        server.setConnectors(new Connector[]{connector});

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new HelloServlet()), "/*");
        context.addServlet(new ServletHolder(new DirectionsServlet(null)), "/directions/*");
        

        //contextHandler.server.setHandler(new HelloServlet());

        server.start();
        server.join();
    }

    public static class HelloServlet extends HttpServlet {

        public void doGet(HttpServletRequest httpServletRequest,
                HttpServletResponse httpServletResponse) throws IOException, ServletException {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);

            try {
                process(httpServletResponse.getWriter());
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }

        public static void process(Writer out) throws IOException, TemplateException {
            Configuration config = new Configuration(Configuration.VERSION_2_3_22);
            config.setDirectoryForTemplateLoading(new File("/Users/ms035644/Documents/traqr/src/main/resources/"));
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            Map<String, Object> root = new HashMap<>();
            root.put("time", LocalDateTime.now());
            root.put("node", Arrays.asList("A", "B", "C"));
            root.put("direction", Arrays.asList("Head North 40 feet","Turn left, walk 10 feet","Walk around the corner"));
            
            Location nodeA = new Location(1, "nodeA");
            Location nodeB = new Location(2, "nodeB");
            Location nodeC = new Location(3, "nodeC");
            Connection connAB = new Connection(1, "Head North 40 feet", nodeA, nodeB, Duration.ofMinutes(2));
            Connection connBC = new Connection(1, "Turn left, walk 10 feet", nodeB, nodeC, Duration.ofMinutes(1));
            
            Trip testTrip = new Trip(Arrays.asList(connAB, connBC));
            
            root.put("trip", testTrip);
            
            Template template = config.getTemplate("test.ftl");

            template.process(root, out);
        }
    }

    public static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age =age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

}
