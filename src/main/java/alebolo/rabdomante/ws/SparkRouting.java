package alebolo.rabdomante.ws;


import alebolo.rabdomante.core.WSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;

import static spark.Spark.*;


public class SparkRouting {
    static Logger log = LoggerFactory.getLogger(SparkRouting.class);

    private static final HashMap<String, String> corsHeaders = new HashMap<>();

    static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
    }

    public final static void allowCors() {
        Filter filter = (request, response) -> corsHeaders.forEach((key, value) -> response.header(key, value));
        Spark.after(filter);
    }


    public static void start() {
        port(8080);
        defineResources();
    }

    public static class WsResponse {
        public WsInput input;
        public WSolution output;
    }

    public static void defineResources() {
        allowCors();
        before((request, response) -> response.type("application/json"));

        get("/ping", (req, res) -> {
            res.status(200);
            return "pong";
        });

        post("/calc", (Request req, Response res) -> new RabdoService().calc(req, res) );
    }
}