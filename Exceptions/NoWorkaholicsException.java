package Exceptions;

public class NoWorkaholicsException extends Exception {

    private static final String MESSAGE = "There are no workaholics.";

    public NoWorkaholicsException() {
        super(MESSAGE);
    }
}
