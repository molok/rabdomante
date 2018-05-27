package alebolo.rabdomante.ws;

import alebolo.rabdomante.core.ChocoSolver;
import alebolo.rabdomante.core.WSolution;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.Optional;

public class RabdoService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    static final long TIME_LIMIS_S = 60L;

    public String calc(Request req, Response res) {
        try {
            WsInput input = new ObjectMapper().readValue(req.body(), WsInput.class);

            ObjectWriter respWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            SparkRouting.WsResponse wsResp = new SparkRouting.WsResponse();
            wsResp.input = input;

            Optional<WSolution> maybeSolution = new ChocoSolver().solve(
                    input.target,
                    input.salts,
                    input.waters,
                    TIME_LIMIS_S);

            wsResp.output = maybeSolution.orElse(null);

            String response = respWriter.writeValueAsString(wsResp);

            res.status(200);
            return response;
        } catch (Exception e) {
            log.error("mah", e);
            return e.getMessage();
//                throw(e);
        }
    }
}
