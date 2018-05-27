package alebolo.rabdomante.ws;

import alebolo.rabdomante.core.Water;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class JacksonTest {

    @Ignore
    @Test public void deserialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter();
        Water input = mapper.readValue(
                "{ \n" +
                        "        \"ca\": 200, \n" +
                        "        \"mg\": 100,\n" +
                        "        \"na\": 1,\n" +
                        "        \"so4\": 1,\n" +
                        "        \"cl\": 1,\n" +
                        "        \"hco3\": 1,\n" +
                        "        \"name\": \"Sant'anna\", \n" +
                        "        \"l\": 10\n" +
                        "    }"
                , Water.class);
        String response = mapper.writeValueAsString(input);
        assertThat(response).isEqualTo("{ \"l\": \"100\", \"ca\": 200 }");
    }
}
