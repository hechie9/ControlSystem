package Exceptions;

public class UnknownProjectTypeException extends Exception {
    private static final String MESSAGE = "Unknown project type.";
    public UnknownProjectTypeException() {
        super(MESSAGE);
    }
}
