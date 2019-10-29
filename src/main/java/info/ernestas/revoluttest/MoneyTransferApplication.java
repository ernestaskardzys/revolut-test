package info.ernestas.revoluttest;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoneyTransferApplication {

    private static final Logger logger = LoggerFactory.getLogger(MoneyTransferApplication.class);

    public static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        JettyServer jettyServer = new JettyServer();
        Server server = jettyServer.create(PORT);

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            logger.error("Error occurred while starting Jetty", ex);
            System.exit(1);
        } finally {
            server.stop();
            server.destroy();
        }
    }

}
