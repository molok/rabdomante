package alebolo.rabdomante.web;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class HelloWorld implements RequestHandler<Object, String> {
    @Override public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        // TODO: implement your handler
        return "Hello from Lambda";
    }

}
