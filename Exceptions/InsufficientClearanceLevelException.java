package Exceptions;

public class InsufficientClearanceLevelException extends Exception {
    private static final String MESSAGE = "%s: insufficient clearance level.";
    public InsufficientClearanceLevelException(String username) {
        super(String.format(MESSAGE, username));
    }
}
