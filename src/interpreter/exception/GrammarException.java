package interpreter.exception;

public class GrammarException extends Exception {
    public GrammarException(String message, int line) {
        super(message + " at line : ", line);
    }
}
