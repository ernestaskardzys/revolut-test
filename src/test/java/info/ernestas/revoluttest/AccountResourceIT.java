package info.ernestas.revoluttest;

import info.ernestas.revoluttest.model.dto.AccountResponseDto;
import info.ernestas.revoluttest.model.dto.TransferResponseDto;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountResourceIT {

    private static final int PORT = 8090;

    private static final String ACCOUNT_URL = "http://localhost:8090/account/";
    private static final String JOHN_DOE = "John Doe";

    private static final JettyServer JETTY_SERVER = new JettyServer();

    private HttpResponse johnDoeResponse;
    private AccountResponseDto johnDoeAccount;
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
        johnDoeAccount = JacksonUtil.toObject(johnDoeResponse.getEntity().getContent(), AccountResponseDto.class);
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
        AccountResponseDto janeDoeAccount = JacksonUtil.toObject(janeDoeResponse.getEntity().getContent(), AccountResponseDto.class);

        final double amount = 10.0;
        TransferResponseDto transferResult = transfer(janeDoeAccount, amount);

        assertThat(transferResult.getAmount(), is(amount));
        assertTrue(transferResult.isTransferred());

        HttpResponse updatedJohnAccount = executeGetAccountRequest(johnDoeAccount.getAccountNumber());
        AccountResponseDto john = JacksonUtil.toObject(updatedJohnAccount.getEntity().getContent(), AccountResponseDto.class);

        HttpResponse updatedJaneAccount = executeGetAccountRequest(janeDoeAccount.getAccountNumber());
        AccountResponseDto jane = JacksonUtil.toObject(updatedJaneAccount.getEntity().getContent(), AccountResponseDto.class);

        assertThat(john.getBalance(), is(johnDoeAccount.getBalance() - amount));
        assertThat(jane.getBalance(), is(janeDoeAccount.getBalance() + amount));
    }

    private TransferResponseDto transfer(AccountResponseDto janeDoeAccount, double amount) throws IOException {
        final String requestAsJson = "{ \"accountFrom\": \"" + johnDoeAccount.getAccountNumber() + "\", \"accountTo\": \"" + janeDoeAccount.getAccountNumber() + "\", \"amount\": \"" + amount + "\" }";
        HttpResponse response = sendPutRequest(ACCOUNT_URL + "/transfer", requestAsJson);

        return JacksonUtil.toObject(response.getEntity().getContent(), TransferResponseDto.class);
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
