package info.ernestas.revoluttest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class MoneyTransferApplicationTest {

    private static final int PORT = 8090;

    private Server server;

    @BeforeEach
    void setup() throws Exception {
        JettyServer jettyServer = new JettyServer();
        server = jettyServer.create(PORT);
        server.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        server.stop();
        server.destroy();
    }

    @Test
    void hello() throws IOException {
        String url = "http://localhost:8090/hello/5";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), is(200));
    }
}