package interpreter.exception;

public class IncompatibleType extends Exception {
    public IncompatibleType(String message, int line) {
        super(message + " at line : ", line);
    }
}
