package info.ernestas.revoluttest;

import info.ernestas.revoluttest.model.Account;
import info.ernestas.revoluttest.util.JacksonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AccountResourceIT {

    private static final int PORT = 8090;

    private static final JettyServer JETTY_SERVER = new JettyServer();
    public static final int STATUS_CODE_OK = 200;

    private CloseableHttpClient client;
    private static Server SERVER;

    @BeforeAll
    static void beforeAll() throws Exception {
        SERVER = JETTY_SERVER.create(PORT);
        SERVER.start();
    }

    @AfterAll
    static void afterAll() throws Exception {
        SERVER.stop();
        SERVER.destroy();
    }

    @BeforeEach
    void setup() {
        client = HttpClients.createDefault();
    }

    @AfterEach
    void tearDown() throws Exception {
        client.close();
    }

    @Test
    void openAccount() throws IOException {
        HttpResponse response = openAccountRequest();

        Account account = JacksonUtil.toObject(response.getEntity().getContent(), Account.class);

        assertThat(response.getStatusLine().getStatusCode(), is(STATUS_CODE_OK));
        assertThat(account.getName(), is("John Doe"));
        assertThat(account.getAccountNumber(), is(notNullValue()));
        assertThat(account.getBalance(), is(0.0));
    }

    @Test
    void getAccountInformation() throws IOException {
        HttpResponse openAccountResponse = openAccountRequest();
        Account account = JacksonUtil.toObject(openAccountResponse.getEntity().getContent(), Account.class);

        HttpGet request = new HttpGet("http://localhost:8090/account/" + account.getAccountNumber());

        HttpResponse response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), is(STATUS_CODE_OK));
        assertThat(account.getName(), is("John Doe"));
        assertThat(account.getAccountNumber(), is(notNullValue()));
        assertThat(account.getBalance(), is(0.0));
    }

    private HttpResponse openAccountRequest() throws IOException {
        String url = "http://localhost:8090/account/";
        HttpPost httpPost = new HttpPost(url);

        String createAccountRequest = "{\"name\": \"John Doe\"}";
        StringEntity entity = new StringEntity(createAccountRequest);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        return client.execute(httpPost);
    }

}
