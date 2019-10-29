package info.ernestas.revoluttest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class JettyServer {

    private static final String BASE_PACKAGE = "info.ernestas.revoluttest.";

    public Server create(int port) {
        Server server = new Server(port);

        ServletContextHandler servletContextHandler = new ServletContextHandler(NO_SESSIONS);

        servletContextHandler.setContextPath("/");
        server.setHandler(servletContextHandler);

        ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter("jersey.config.server.provider.packages", BASE_PACKAGE + "resources");
        servletHolder.setInitParameter("javax.ws.rs.Application", BASE_PACKAGE + "App");

        return server;
    }

}
