package info.ernestas.revoluttest.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import info.ernestas.revoluttest.exception.mapper.JacksonExceptionMapper;
import info.ernestas.revoluttest.model.dto.ExceptionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class JacksonExceptionMapperTest {

    private static final int INTERNAL_SERVER_ERROR = org.eclipse.jetty.server.Response.SC_INTERNAL_SERVER_ERROR;

    private JacksonExceptionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new JacksonExceptionMapper();
    }

    @Test
    void toResponse() {
        JsonMappingException exception = new JsonMappingException(null, "Test exception");

        Response response = mapper.toResponse(exception);

        assertThat(response.getStatus(), is(INTERNAL_SERVER_ERROR));
        ExceptionDto exceptionDto = (ExceptionDto) response.getEntity();
        assertThat(exceptionDto.getErrorCode(), is(INTERNAL_SERVER_ERROR));
        assertThat(exceptionDto.getMessage(), is(exception.getMessage()));
    }

}