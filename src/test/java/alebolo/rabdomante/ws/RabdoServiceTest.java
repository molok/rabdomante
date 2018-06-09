package alebolo.rabdomante.ws;

import org.junit.Test;

import java.io.IOException;

public class RabdoServiceTest {
    @Test public void service() throws IOException {
        RabdoService service = new RabdoService();
        System.out.println(service.calc(
                "{\n" +
                        "    \"waters\": [ { \n" +
                        "        \"name\": \"Sant'anna\", \n" +
                        "        \"l\": 10, \n" +
                        "        \"ca\": 200, \n" +
                        "        \"mg\": 100,\n" +
                        "        \"na\": 1,\n" +
                        "        \"so4\": 1,\n" +
                        "        \"cl\": 1,\n" +
                        "        \"hco3\": 1\n" +
                        "    } ],\n" +
                        "    \"salts\": [ { \n" +
                        "        \"name\": \"Table Salt\", \n" +
                        "        \"dg\": 1000, \n" +
                        "        \"ca\": 20, \n" +
                        "        \"mg\": 10,\n" +
                        "        \"na\": 10,\n" +
                        "        \"so4\": 11,\n" +
                        "        \"cl\": 12,\n" +
                        "        \"hco3\": 13\n" +
                        "    } ],\n" +
                        "    \"target\": { \n" +
                        "        \"name\": \"Sant'anna\", \n" +
                        "        \"l\": 10, \n" +
                        "        \"ca\": 200, \n" +
                        "        \"mg\": 100,\n" +
                        "        \"na\": 100,\n" +
                        "        \"so4\": 100,\n" +
                        "        \"cl\": 100,\n" +
                        "        \"hco3\": 100\n" +
                        "    }\n" +
                        "}"));
    }

}