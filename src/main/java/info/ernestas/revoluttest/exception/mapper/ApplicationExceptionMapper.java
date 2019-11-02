package info.ernestas.revoluttest.exception.mapper;

import info.ernestas.revoluttest.model.dto.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error("Exception has been thrown", exception);

        ExceptionDto response = new ExceptionDto(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }

}
