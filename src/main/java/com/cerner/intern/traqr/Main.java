package com.cerner.intern.traqr;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        server.setHandler(new HelloHandler());

        server.start();
        server.join();
    }

    public static class HelloHandler extends AbstractHandler {

        public void handle(String s, Request request, HttpServletRequest httpServletRequest,
                HttpServletResponse httpServletResponse) throws IOException, ServletException {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            request.setHandled(true);

            try {
                process(httpServletResponse.getWriter());
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }

        public static void process(Writer out) throws IOException, TemplateException {
            Configuration config = new Configuration(Configuration.VERSION_2_3_22);
            config.setDirectoryForTemplateLoading(new File("C:\\Users\\Easy\\Documents\\GitHub\\traQR\\src\\main\\resources"));
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            Map<String, Object> root = new HashMap<>();
            root.put("time", LocalDateTime.now());
            root.put("node", Arrays.asList("A", "B", "C"));
            root.put("direction", Arrays.asList("Head North 40 feet","Turn left, walk 10 feet","Walk around the corner"));

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
