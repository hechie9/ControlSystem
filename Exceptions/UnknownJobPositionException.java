package Exceptions;

public class UnknownJobPositionException extends Exception {

    private static final String MESSAGE = "Unknown job position.";

    public UnknownJobPositionException() {
        super(MESSAGE);
    }
}
