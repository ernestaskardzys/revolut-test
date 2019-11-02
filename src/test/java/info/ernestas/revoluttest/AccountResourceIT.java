package info.ernestas.revoluttest;

import info.ernestas.revoluttest.model.Account;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AccountResourceIT {

    private static final int PORT = 8090;

    private static final String ACCOUNT_URL = "http://localhost:8090/account/";
    private static final String JOHN_DOE = "John Doe";

    private static final JettyServer JETTY_SERVER = new JettyServer();

    private HttpResponse johnDoeResponse;
    private Account johnDoeAccount;
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
    void setup() throws IOException {
        client = HttpClients.createDefault();

        johnDoeResponse = sendPostRequest(ACCOUNT_URL, "{\"name\": \"" + JOHN_DOE + "\"}");
        johnDoeAccount = JacksonUtil.toObject(johnDoeResponse.getEntity().getContent(), Account.class);
    }

    @AfterEach
    void tearDown() throws Exception {
        client.close();
    }

    @Test
    void openAccount() {
        assertThat(johnDoeResponse.getStatusLine().getStatusCode(), is(Response.SC_OK));
        assertThat(johnDoeAccount.getName(), is(JOHN_DOE));
        assertThat(johnDoeAccount.getAccountNumber(), is(notNullValue()));
        assertThat(johnDoeAccount.getBalance(), is(100.0));
    }

    @Test
    void getAccountInformation() throws IOException {
        HttpResponse response = executeGetAccountRequest(johnDoeAccount.getAccountNumber());

        assertThat(response.getStatusLine().getStatusCode(), is(Response.SC_OK));
        assertThat(johnDoeAccount.getName(), is(JOHN_DOE));
        assertThat(johnDoeAccount.getAccountNumber(), is(notNullValue()));
        assertThat(johnDoeAccount.getBalance(), is(100.0));
    }

    @Test
    void transfer() throws IOException {
        HttpResponse janeDoeResponse = sendPostRequest(ACCOUNT_URL,"{\"name\": \"Jane Doe\"}");
        Account janeDoeAccount = JacksonUtil.toObject(janeDoeResponse.getEntity().getContent(), Account.class);

        final int amount = 10;
        transfer(janeDoeAccount, amount);

        HttpResponse updatedJohnAccount = executeGetAccountRequest(johnDoeAccount.getAccountNumber());
        Account john = JacksonUtil.toObject(updatedJohnAccount.getEntity().getContent(), Account.class);

        HttpResponse updatedJaneAccount = executeGetAccountRequest(janeDoeAccount.getAccountNumber());
        Account jane = JacksonUtil.toObject(updatedJaneAccount.getEntity().getContent(), Account.class);

        assertThat(john.getBalance(), is(johnDoeAccount.getBalance() - amount));
        assertThat(jane.getBalance(), is(janeDoeAccount.getBalance() + amount));
    }

    private void transfer(Account janeDoeAccount, int amount) throws IOException {
        final String requestAsJson = "{ \"accountFrom\": \"" + johnDoeAccount.getAccountNumber() + "\", \"accountTo\": \"" + janeDoeAccount.getAccountNumber() + "\", \"amount\": \"" + amount + "\" }";
        sendPutRequest(ACCOUNT_URL + "/transfer", requestAsJson);
    }

    private HttpResponse sendPostRequest(String url, String requestAsJson) throws IOException {
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(requestAsJson);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", MediaType.APPLICATION_JSON);
        httpPost.setHeader("Content-type", MediaType.APPLICATION_JSON);

        return client.execute(httpPost);
    }

    private HttpResponse sendPutRequest(String url, String requestAsJson) throws IOException {
        HttpPut httpPut = new HttpPut(url);

        StringEntity entity = new StringEntity(requestAsJson);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", MediaType.APPLICATION_JSON);
        httpPut.setHeader("Content-type", MediaType.APPLICATION_JSON);

        return client.execute(httpPut);
    }

    private HttpResponse executeGetAccountRequest(String accountNumber) throws IOException {
        HttpGet request = new HttpGet(ACCOUNT_URL + accountNumber);

        return client.execute(request);
    }

}
