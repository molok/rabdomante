package alebolo.rabdomante.ws;


import alebolo.rabdomante.core.WSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static spark.Spark.*;


public class SparkRouting {
    static Logger log = LoggerFactory.getLogger(SparkRouting.class);

    public static void start() {
        port(8080);
        defineResources();
    }

    public static class WsResponse {
        public WsInput input;
        public WSolution output;
    }

    public static void defineResources() {
        before((request, response) -> response.type("application/json"));

        get("/ping", (req, res) -> {
            res.status(200);
            return "pong";
        });

        post("/calc", (Request req, Response res) -> new RabdoService().calc(req, res) );
    }
}