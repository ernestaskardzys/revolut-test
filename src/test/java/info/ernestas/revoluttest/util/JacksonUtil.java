package info.ernestas.revoluttest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.ernestas.revoluttest.model.Account;

import java.io.IOException;
import java.io.InputStream;

public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T toObject(InputStream inputStream, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(inputStream, clazz);
    }

}
