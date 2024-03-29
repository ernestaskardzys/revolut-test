package info.ernestas.revoluttest.exception;

import info.ernestas.revoluttest.exception.mapper.ApplicationExceptionMapper;
import info.ernestas.revoluttest.model.dto.ExceptionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ApplicationExceptionMapperTest {

    private static final int INTERNAL_SERVER_ERROR = org.eclipse.jetty.server.Response.SC_INTERNAL_SERVER_ERROR;

    private ApplicationExceptionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ApplicationExceptionMapper();
    }

    @Test
    void toResponse() {
        Exception exception = new Exception("Test exception");

        Response response = mapper.toResponse(exception);

        assertThat(response.getStatus(), is(INTERNAL_SERVER_ERROR));
        ExceptionDto exceptionDto = (ExceptionDto) response.getEntity();
        assertThat(exceptionDto.getErrorCode(), is(INTERNAL_SERVER_ERROR));
        assertThat(exceptionDto.getMessage(), is(exception.getMessage()));
    }
}