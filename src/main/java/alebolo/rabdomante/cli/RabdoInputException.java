package alebolo.rabdomante.cli;

public class RabdoInputException extends RuntimeException {
    public RabdoInputException(Exception e) {
        super(e);
    }
    public RabdoInputException(String s) { super(s); }
}
