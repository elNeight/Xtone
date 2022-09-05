package interpreter.exception;

public class Exception extends RuntimeException {
    public Exception(String message, int line) {
        super(message + "" + line);
    }
}
