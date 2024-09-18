package kz.sab1tm.juggler.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {

    private JsonUtil() {
    }

    public static String toPrettyString(String body) {
        if (body == null)
            return "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(body, Object.class);
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            return body;
        }
    }
}
