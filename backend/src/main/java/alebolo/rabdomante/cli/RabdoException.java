package alebolo.rabdomante.cli;

public class RabdoException extends RuntimeException {
    public RabdoException(String e) { super(e); }
    public RabdoException(Exception e) { super(e); }
}
